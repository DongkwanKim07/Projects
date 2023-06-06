package com.cst2335.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Displays the chosen Cocktail's details.
 */
public class DetailsFragment extends Fragment {

    private Bundle dataFromCocktailList;
    private AppCompatActivity parentActivity;
    private String cocktailName;
    private String cocktailInstructions;
    private String cocktailIngredient1;
    private String cocktailIngredient2;
    private String cocktailIngredient3;
    private String cocktailThumbnailStr;
    private Bitmap cocktailThumbnail;
    /** {@value} Used in printCursor() to debug **/
    private static final String TAG = "DETAILS_FRAGMENT";
    private SQLiteDatabase db;

    /**
     * Inflates the layout and sets the views to display a chosen Cocktail's recipe.
     * @param inflater the view's inflater
     * @param container the view's container
     * @param savedInstanceState Bundle parameter to be passed to the super-constructor.
     * @return the fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromCocktailList = getArguments();

        // Inflate the layout for the fragment
        View fragmentView = inflater.inflate(R.layout.fragment_details, container, false);

        if(CocktailResultList.isFromDatabase()) {
            Button addToDatabaseButton = fragmentView.findViewById(R.id.save_button);
            // Learned how to use setVisibility in [1]
            addToDatabaseButton.setVisibility(View.INVISIBLE);
        } else {
            // If the result list should come from Internet, retrieve the button. On click, save recipe to database.
            Button saveButton = fragmentView.findViewById(R.id.save_button);

            saveButton.setOnClickListener(v -> {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setTitle(R.string.list_alert_title)
                        .setMessage(getText(R.string.list_alert_selected_row).toString())
                        .setPositiveButton(R.string.list_prompt_yes, (click, arg) -> {
                            addCocktailToDatabase(cocktailName, cocktailInstructions, cocktailIngredient1, cocktailIngredient2, cocktailIngredient3, cocktailThumbnailStr);
                            Toast.makeText(getActivity(), cocktailName + " " + getText(R.string.toast_db_added), Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.list_prompt_no, (click, arg) -> { })
                        .create().show();
            });
        }

        // Retrieve the XML views
        TextView cocktailNameView = (TextView) fragmentView.findViewById(R.id.display_cocktail_name);
        TextView cocktailInstructionsView = (TextView) fragmentView.findViewById(R.id.display_cocktail_instructions);
        TextView cocktailFirstIngredientView = (TextView) fragmentView.findViewById(R.id.ingredient1);
        TextView cocktailSecondIngredientView = (TextView) fragmentView.findViewById(R.id.ingredient2);
        TextView cocktailThirdIngredientView = (TextView) fragmentView.findViewById(R.id.ingredient3);
        ImageView cocktailThumbnailView = (ImageView) fragmentView.findViewById(R.id.cocktail_thumbnail);

        // Retrieve the data from the intent
        cocktailName = dataFromCocktailList.getString(CocktailResultList.ITEM_COCKTAIL_NAME);
        cocktailInstructions = dataFromCocktailList.getString(CocktailResultList.ITEM_INSTRUCTIONS);
        cocktailIngredient1 = dataFromCocktailList.getString(CocktailResultList.ITEM_FIRST_INGREDIENT);
        cocktailIngredient2 = dataFromCocktailList.getString(CocktailResultList.ITEM_SECOND_INGREDIENT);
        cocktailIngredient3 = dataFromCocktailList.getString(CocktailResultList.ITEM_THIRD_INGREDIENT);
        cocktailThumbnailStr = dataFromCocktailList.getString(CocktailResultList.ITEM_PICTURE);

        // Set the views
        cocktailNameView.setText(getText(R.string.fragment_cocktail_name) + " " + cocktailName);
        cocktailInstructionsView.setText(getText(R.string.fragment_cocktail_instructions) + " " + cocktailInstructions);
        cocktailFirstIngredientView.setText(getText(R.string.fragment_cocktail_ingredient1) + " " + cocktailIngredient1);
        cocktailSecondIngredientView.setText(getText(R.string.fragment_cocktail_ingredient2) + " " + cocktailIngredient2);
        cocktailThirdIngredientView.setText(getText(R.string.fragment_cocktail_ingredient3) + " " + cocktailIngredient3);

        // Sets the view if the picture is found locally. Learned how to use getActivity() in [2]
        if (fileExists(cocktailThumbnailStr) != false) {
            FileInputStream fis = null;
            try {
                fis = getActivity().openFileInput(cocktailThumbnailStr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            cocktailThumbnail = BitmapFactory.decodeStream(fis);
            cocktailThumbnailView.setImageBitmap(cocktailThumbnail);
        }
        return fragmentView;
    }

    /**
     * Attached the fragment to its parent activity, which depends on the device being used.
     * @param context the current Context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }

    /**
     * Determines whether a picture is saved in local storage
     * @param fileName the picture's name
     * @return true if picture was found
     */
    // Checks if the file exists in the local storage directory
    public boolean fileExists(String fileName) {
        Log.e(TAG, "Image was found locally");
        File file = parentActivity.getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    /**
     * Adds a Cocktail to the local SQLite database.
     * @param name the Cocktail's name
     * @param instructions the Cocktail's instructions
     * @param ingredient1 the Cocktail's first ingredient
     * @param ingredient2 the Cocktail's second ingredient
     * @param ingredient3 the Cocktail's third ingredient
     * @param pictureName the Cocktail's picture name
     */
    public void addCocktailToDatabase(String name, String instructions, String ingredient1, String ingredient2, String ingredient3, String pictureName) {
        // Get a database connection:
        MyOpener dbOpener = new MyOpener(getActivity());
        db = dbOpener.getWritableDatabase();

        // Create ContentValues to insert row
        ContentValues cValues = new ContentValues();

        // Insert Cocktail information into the database's columns
        cValues.put(MyOpener.COL_NAME, name);
        cValues.put(MyOpener.COL_INSTRUCTIONS, instructions);
        cValues.put(MyOpener.COL_INGREDIENT1, ingredient1);
        cValues.put(MyOpener.COL_INGREDIENT2, ingredient2);
        cValues.put(MyOpener.COL_INGREDIENT3, ingredient3);
        cValues.put(MyOpener.COL_PICTURE, pictureName);

        long id = db.insert(MyOpener.TABLE_NAME, "NullColumnName", cValues);

        Cursor cursor = db.rawQuery("SELECT * FROM " + MyOpener.TABLE_NAME, null);
    }

    /**
     * Used to debug. Prints information from the database when the user adds or deletes a Cocktail.
     * @param cursor will iterate over the database's columns
     * @param version the database's version
     */
    public void printCursor(Cursor cursor, int version) {
        int dbVersion = db.getVersion();
        int numColumns = cursor.getColumnCount();
        String[] names = cursor.getColumnNames();
        int numRows = cursor.getCount();

        Log.e(TAG,"Database version: " + dbVersion + ", Number of columns: " + numColumns);
        Log.e(TAG, "Column names: ");

        for (int i = 0; i < names.length; i++) {
            Log.e(TAG, names[i]);
            cursor.moveToNext();
        }

        Log.e(TAG, "Number of rows: " + numRows);
        int collName = cursor.getColumnIndex("COCKTAIL_NAME");
        int collInstructions = cursor.getColumnIndex("INSTRUCTIONS");
        int collIngredient1 = cursor.getColumnIndex("INGREDIENT1");
        int collIngredient2 = cursor.getColumnIndex("INGREDIENT2");
        int collIngredient3 = cursor.getColumnIndex("INGREDIENT3");
        int collPictureName = cursor.getColumnIndex("PICTURE");
        int collId = cursor.getColumnIndex("_id");

        // Iterate over the database rows
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(collName);
            String instructions = cursor.getString(collInstructions);
            String id = cursor.getString(collId);
            String ingredient1 = cursor.getString(collIngredient1);
            String ingredient2 = cursor.getString(collIngredient2);
            String ingredient3 = cursor.getString(collIngredient3);
            String pictureName = cursor.getString(collPictureName);

            Log.e(TAG, MyOpener.COL_ID + ": " + id
                    + " " + MyOpener.COL_NAME + ": " + name
                    + " " + MyOpener.COL_INSTRUCTIONS + ": " + instructions
                    + " " + MyOpener.COL_INGREDIENT1 + ": " + ingredient1
                    + " " + MyOpener.COL_INGREDIENT2 + ": " + ingredient2
                    + " " + MyOpener.COL_INGREDIENT3 + ": " + ingredient3
                    + " " + MyOpener.COL_PICTURE + ": " + pictureName);
            cursor.moveToNext();
        }
    }
}

// Reference cited:
// [1] AndroidDeveloper, "View," Android Developers. https://developer.android.com/reference/android/view/View (accessed Aug. 06, 2022).
// [2] A. Xattar, "Answer to 'Error with openFileOutput in Fragments,'" Stack Overflow, Nov. 06, 2015. https://stackoverflow.com/a/33561763 (accessed Aug. 03, 2022).