package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Queries the Internet or the local SQLite database to retrieve either new Cocktail recipes or
 * previously saved favourites, respectively.
 */
public class CocktailResultList extends AppCompatActivity {

    public static final String ITEM_COCKTAIL_NAME = "COCKTAIL_NAME";
    public static final String ITEM_INSTRUCTIONS = "INSTRUCTIONS";
    public static final String ITEM_FIRST_INGREDIENT = "FIRST_INGREDIENT";
    public static final String ITEM_SECOND_INGREDIENT = "SECOND_INGREDIENT";
    public static final String ITEM_THIRD_INGREDIENT = "THIRD_INGREDIENT";
    public static final String ITEM_PICTURE = "PICTURE";
    private static boolean isFromDatabase = false;
    private static final String TAG = "CocktailResultList";

    private String cocktailName;
    private ArrayList<Cocktail> cocktailArrayList = new ArrayList<>();
    private ListAdapter cocktailListAdapter;
    private SQLiteDatabase db;

    /**
     * Loads the ArrayList containing Cocktail names
     * @param savedInstanceState Bundle parameter to be passed to the super-constructor
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_result_list);

        // Instantiate ListView and its Adapter
        ListView cocktailListView = findViewById(R.id.cocktail_results_listview);
        cocktailListView.setAdapter(cocktailListAdapter = new ListAdapter());

        // Determines whether to load from database or from an Internet query
        if (getIntent().getStringExtra(CocktailSearch.LOAD_FAVOURITES) != null) {
            setIsFromDatabase(true);
            loadDataFromDatabase();

            // Remove selected cocktail from the favourites database
            cocktailListView.setOnItemLongClickListener((list, item, position, id) -> {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle(R.string.list_alert_delete_title)
                        .setMessage(getText(R.string.list_alert_delete_message).toString())
                        .setPositiveButton(R.string.list_prompt_yes, (click, arg) -> {
                            String cocktailName = cocktailArrayList.get(position).getName();
                            // Remove the cocktail from the database
                            deleteCocktail(cocktailArrayList.get(position));
                            // Remove from ArrayList
                            cocktailArrayList.remove(position);
                            // Update the view
                            cocktailListAdapter.notifyDataSetChanged();
                            Snackbar.make(findViewById(R.id.cocktail_results_listview), cocktailName + " " + getText(R.string.toast_db_removed), Snackbar.LENGTH_LONG).show();
                        })
                        .setNegativeButton(R.string.list_prompt_no, (click, arg) -> { })
                        .create().show();
                return true;
            });
        } else {
            // Tells DetailsFragment to load the 'Add to Favourites' button
            setIsFromDatabase(false);
            // Set the Cocktail's name and format it for the URL
            cocktailName = getIntent().getStringExtra(CocktailSearch.COCKTAIL_NAME);
            cocktailName = formatDrinkName(cocktailName);

            CocktailQuery query = new CocktailQuery();
            query.execute();
        }

        boolean isTablet = findViewById(R.id.frame_Layout) != null;

        cocktailListView.setOnItemClickListener((list, item, position, id) -> {
                Bundle bundle = new Bundle();

                bundle.putString(ITEM_COCKTAIL_NAME, cocktailArrayList.get(position).getName());
                bundle.putString(ITEM_INSTRUCTIONS, cocktailArrayList.get(position).getInstructions());
                bundle.putString(ITEM_FIRST_INGREDIENT, cocktailArrayList.get(position).getIngredient1());
                bundle.putString(ITEM_SECOND_INGREDIENT, cocktailArrayList.get(position).getIngredient2());
                bundle.putString(ITEM_THIRD_INGREDIENT, cocktailArrayList.get(position).getIngredient3());
                bundle.putString(ITEM_FIRST_INGREDIENT, cocktailArrayList.get(position).getIngredient1());
                bundle.putString(ITEM_PICTURE, cocktailArrayList.get(position).getPictureName());

                if (isTablet) {
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_Layout, detailsFragment)
                            .commit();
                } else {
                    //will transition to EmptyActivity class
                    Intent nextActivity = new Intent(CocktailResultList.this, EmptyActivity.class);
                    nextActivity.putExtras(bundle);
                    startActivity(nextActivity);
                }
            });
        }

    /**
     * Formats the user's input for use in query's URL. Replaces spaces with '+'.
     * @param userInput the name of the Cocktail searched by the user
     * @return formatted String that can be used in a URL
     */
    public String formatDrinkName(String userInput) {
        StringBuilder sb = new StringBuilder(userInput);
        for (int i = 0; i < userInput.length(); i++) {
            if (sb.charAt(i) == ' ') {
                sb.replace(i, i+1,"+");
            }
        }
        return sb.toString();
    }

    /**
     * Delete a Cocktail from the database. Called upon confirmation that a message should be
     * deleted. Informed by [1] and [2].
     * @param cocktail the Cocktail to be deleted from the database
     */
    protected void deleteCocktail(Cocktail cocktail) {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(cocktail.getId())});
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyOpener.TABLE_NAME, null);
        printCursor(cursor, db.getVersion());
    }

    /**
     * Loads favourite Cocktails from the local SQLite database. Called if the user clicked the
     * 'My Favourites Cocktail' button in the CocktailSearch class.
     */
    private void loadDataFromDatabase() {
        // Get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        // Gets all of the columns found in MyOpener class
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_NAME, MyOpener.COL_INSTRUCTIONS, MyOpener.COL_INGREDIENT1, MyOpener.COL_INGREDIENT2, MyOpener.COL_INGREDIENT3, MyOpener.COL_PICTURE};
        // Queries all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        // Now the results object has rows of results that match the query.
        // Find the column indices:
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);
        int nameColumnIndex = results.getColumnIndex(MyOpener.COL_NAME);
        int instructionsColIndex = results.getColumnIndex(MyOpener.COL_INSTRUCTIONS);
        int ingredient1ColIndex = results.getColumnIndex(MyOpener.COL_INGREDIENT1);
        int ingredient2ColIndex = results.getColumnIndex(MyOpener.COL_INGREDIENT2);
        int ingredient3ColIndex = results.getColumnIndex(MyOpener.COL_INGREDIENT3);
        int pictureNameColIndex = results.getColumnIndex(MyOpener.COL_PICTURE);

        // Iterate over the results.
        while (results.moveToNext()) {
            String name = results.getString(nameColumnIndex);
            String instructions = results.getString(instructionsColIndex);
            String ingredient1 = results.getString(ingredient1ColIndex);
            String ingredient2 = results.getString(ingredient2ColIndex);
            String ingredient3 = results.getString(ingredient3ColIndex);
            String pictureName = results.getString(pictureNameColIndex);
            long id = results.getLong(idColIndex);

            // Add the new Cocktail to the ArrayList.
            cocktailArrayList.add(new Cocktail(id, name, instructions, ingredient1, ingredient2, ingredient3, pictureName));
        }
    }

    /**
     * Used to debug. Prints information from the database when the user adds or deletes a Cocktail.
     * @param cursor will iterate over the database.
     * @param version used to store the database's current version in the deleteCocktail() method.
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

        int collName = cursor.getColumnIndex("COCKTAIL_NAME");
        int collInstructions = cursor.getColumnIndex("INSTRUCTIONS");
        int collIngredient1 = cursor.getColumnIndex("INGREDIENT1");
        int collIngredient2 = cursor.getColumnIndex("INGREDIENT2");
        int collIngredient3 = cursor.getColumnIndex("INGREDIENT3");
        int collPictureName = cursor.getColumnIndex("PICTURE");
        int collId = cursor.getColumnIndex("_id");

        // Iterate over the rows in the database
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

    /**
     * Used by DetailsFragment to determine whether the 'Add to Favourites' button should be displayed or not.
     * @param answer Whether the user wants to see their saved Cocktails in the local SQLite database or not.
     */
    public void setIsFromDatabase(boolean answer) {
        isFromDatabase = answer;
    }

    /**
     * Returns whether the user wants to view their locally saved favourite Cocktails.
     * @return true if the user wants to view their locally saved favourite Cocktails.
     */
    public static boolean isFromDatabase() {
        return isFromDatabase;
    }

    /**
     * Queries thecocktaildb.com to retrieve Cocktail recipes and add them to the ArrayList of
     * Cocktails.
     */
    public class CocktailQuery extends AsyncTask<String, Integer, String> {

        private String id;
        private String name;
        private String instructions;
        private String ingredient1;
        private String ingredient2;
        private String ingredient3;
        private String cocktailThumbnailStr;
        private Bitmap cocktailThumbnail;

        /**
         * Queries the Cocktail database, retrieves JSON objects, parses information, and adds a
         * new Cocktail to the ArrayList.
         * @param strings doInBackground() arguments
         * @return a String that is passed to onPostExecute() [3]
         */
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL cocktailURL = new URL("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + cocktailName);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) cocktailURL.openConnection();

                //wait for the data
                InputStream response = urlConnection.getInputStream();

                // Learned how to use BufferedReader and use JSONObjects in [1]
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String stringResult = sb.toString();

                JSONObject cocktailReport = new JSONObject(stringResult);
                JSONArray cocktailArray = cocktailReport.getJSONArray("drinks");

                for(int i = 0; i < cocktailArray.length(); i++) {
                    JSONObject objectFromArray = cocktailArray.getJSONObject(i);

                    id = objectFromArray.getString("idDrink");
                    name = objectFromArray.getString("strDrink");
                    publishProgress(25);
                    instructions = objectFromArray.getString("strInstructions");
                    ingredient1 = objectFromArray.getString("strIngredient1");
                    ingredient2 =  objectFromArray.getString("strIngredient2");
                    publishProgress(50);
                    ingredient3 =  objectFromArray.getString("strIngredient3");
                    cocktailThumbnailStr = objectFromArray.getString("strDrinkThumb");
                    downloadThumbnail(cocktailThumbnailStr);
                    publishProgress(75);
                    cocktailThumbnailStr = formatThumbnailName(cocktailThumbnailStr);
                    saveThumbnailLocally(cocktailThumbnailStr);
                    // Add a new Cocktail to the Database, which also adds it to the Arraylist
                    cocktailArrayList.add(new Cocktail(Long.valueOf(id), name, instructions, ingredient1, ingredient2, ingredient3, cocktailThumbnailStr));
                    publishProgress(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Done";
        }

        /**
         * Downloads the Cocktail image that will be used in the DetailsFragment class.
         * @param thumbnailStr the image's name
         */
        public void downloadThumbnail(String thumbnailStr) {
            try {
                URL url = new URL(thumbnailStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    cocktailThumbnail = BitmapFactory.decodeStream(connection.getInputStream());
                }
            } catch (Exception e) {}
        }

        /**
         * Saves the image to the local application storage
         * @param cocktailThumbnailStr the image's name
         */
        public void saveThumbnailLocally(String cocktailThumbnailStr) {
            try {
                FileOutputStream outputStream = openFileOutput(cocktailThumbnailStr, Context.MODE_PRIVATE);
                cocktailThumbnail.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Removes path separators from the image name. Iterates through the name, starting from
         * the last character, keeping all characters until a path separator is reached.
         * @param thumbnail the image's name
         * @return the image name without path separators
         */
        public String formatThumbnailName(String thumbnail) {
            StringBuilder sb = new StringBuilder();
            int index = thumbnail.length() - 1;
            while(thumbnail.charAt(index) != '/') {
                sb.append(thumbnail.charAt(index));
                index--;
            }
            return sb.reverse().toString();
        }

        /**
         * Makes the progress bar visible. Informed by the code and information provided
         * in [1], [2], and [3].
         * @param value the program's current progress
         */
        @Override
        protected void onProgressUpdate(Integer ... value) {
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            // set progress bar to visible
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        /**
         * Sets the view's contents once doInBackground() is finished. Progress bar becomes
         * invisible and view is updated to display the results.
         * @param fromDoInBackground the String returned by doInBackground()
         */
        protected void onPostExecute(String fromDoInBackground) {
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.INVISIBLE);
            // Update the view
            cocktailListAdapter.notifyDataSetChanged();
        }

    }

    /**
     * Custom Adapter for the Cocktail Application
     */
    private class ListAdapter extends BaseAdapter {
        /**
         * Returns the number of Cocktails in the ArrayList
         * @return the number of Cocktails in the ArrayList
         */
        @Override
        public int getCount() { return cocktailArrayList.size(); }

        /**
         * Returns the selected row index
         * @return the selected row index
         */
        @Override
        public Cocktail getItem(int position) { return cocktailArrayList.get(position); }

        /**
         * Returns the database id
         * @param position the item's position in the ArrayList
         * @return the item's position in the databse
         */
        @Override
        public long getItemId(int position) { return getItem(position).getId(); }

        /**
         * Inflates the view. Consulted [2] to gain a better understanding of how the getView() method functions.
         * @return the inflated view.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            Cocktail cocktail = getItem(position);
            TextView layoutCocktail;

            convertView = inflater.inflate(R.layout.cocktail_name, parent, false);
            layoutCocktail = convertView.findViewById(R.id.cocktail_result_name);
            layoutCocktail.setText(cocktail.getName());

            return convertView;
        }
    }

    /**
     * Cocktail class represents an alcoholic beverage recipe. Has a name, instructions, three main
     * ingredients, and the name of its respective image.
     */
    private class Cocktail {

        private long id;
        private String name;
        private String instructions;
        private String ingredient1;
        private String ingredient2;
        private String ingredient3;
        private String pictureName;

        /**
         * Parameterized constructor.
         * @param id the Cocktail's id
         * @param name the Cocktail's name
         * @param instructions instructions on how to make this Cocktail
         * @param ingredient1 the Cocktail's first ingredient
         * @param ingredient2 the Cocktail's second ingredient
         * @param ingredient3 the Cocktail's third ingredient
         * @param pictureName the Cocktail's picture name. Used to retrieve the image from local
         *                    storage.
         */
        public Cocktail(long id, String name, String instructions, String ingredient1, String ingredient2, String ingredient3, String pictureName) {
            this.id = id;
            this.name = name;
            this.instructions = instructions;
            this.ingredient1 = ingredient1;
            this.ingredient2 = ingredient2;
            this.ingredient3 = ingredient3;
            this.pictureName = pictureName;
        }

        /**
         * Returns the Cocktail's id
         * @return the Cocktail's id
         */
        public long getId() { return id; }

        /**
         * Sets the the Cocktail's id
         * @param id the Cocktail's new id
         */
        public void setId(long id) { this.id = id; }

        /**
         * Returns the Cocktail's name
         * @return the Cocktail's name
         */
        public String getName() { return name; }

        /**
         * Sets the Cocktail's name
         * @param name the Cocktail's new name
         */
        public void setName(String name) { this.name = name; }

        /**
         * The Cocktail's instructions
         * @return the Cocktail's instructions
         */
        public String getInstructions() { return instructions; }

        /**
         * Returns the Cocktail's first ingredient
         * @return the Cocktail's first ingredient
         */
        public String getIngredient1() { return ingredient1; }

        /**
         * Returns the Cocktail's second ingredient
         * @return the Cocktail's second ingredient
         */
        public String getIngredient2() { return ingredient2; }

        /**
         * Returns the Cocktail's third ingredient
         * @return the Cocktail's third ingredient
         */
        public String getIngredient3() { return ingredient3; }

        /**
         * Returns the Cocktail's picture name. Used to retrieve the picture from local storage.
         * @return the Cocktail's picture name.
         */
        public String getPictureName() { return pictureName; }

        /**
         * Returns a String representation of the Cocktail
         * @return a String representation of the Cocktail
         */
        public String toString() {
            return "Cocktail details:\nName: " + name + "\nInstructions: " + instructions + "\nIngredients:\n" + ingredient1 + "\n" + ingredient2 + "\n" + ingredient3;
        }
    }
}

// References cited:
// [1] E. Torunski, "Week 11- Toolbar and Navigation Drawer," Algonquin College, Jul. 28, 2022.
// [2] F. Azzola, "Android Listview with multiple row layout," Java Code Geeks, Aug. 12, 2014. https://www.javacodegeeks.com/2014/08/android-listview-with-multiple-row-layout.html (accessed Jun. 25, 2022).
// [3] E. Torunski, "Week6_AsyncTask_Files_XML - 22S_CST2335_010 Mobile Graphical Interface Prog.," Algonquin College, Jul. 14, 2022. https://brightspace.algonquincollege.com/d2l/le/content/444410/viewContent/7030232/View (accessed Jul. 17, 2022).