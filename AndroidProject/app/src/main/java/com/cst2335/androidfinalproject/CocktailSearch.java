package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A view in which the user can either choose to query the Internet to find a Cocktail recipe, or
 * navigate to their locally saved favourites.
 */
public class CocktailSearch extends AppCompatActivity {

        /** {@value} The Cocktail's name */
        public static final String COCKTAIL_NAME = "cocktail";
        /** {@value} Flag sent to by CocktailResultList */
        public static final String LOAD_FAVOURITES = "favourites";
        private EditText userCocktail;
        private Button searchButton;
        private Button favouritesButton;

    /**
     * Loads the view and sets listeners to the buttons
     * @param savedInstanceState Bundle parameter to be passed to the super-constructor
     */
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cocktail_search);

            searchButton = findViewById(R.id.search_button);
            favouritesButton = findViewById(R.id.load_database_button);
            userCocktail = findViewById(R.id.user_cocktail);
            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.help_toolbar);

            setSupportActionBar(toolbar);

            SharedPreferences sharedPrefs = getSharedPreferences("com.cst2335.androidfinalproject.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
            String savedCocktailKey = getString(R.string.saved_user_drink);
            String retrievedCocktail = sharedPrefs.getString(savedCocktailKey, "");
            userCocktail.setText(String.valueOf(retrievedCocktail));

            // Send the user's input cocktail name to another activity
            searchButton.setOnClickListener( v-> {
                // Application cannot search for blank user input. Forces user to enter something into the search box.
                // Learned how to verify if input is blank using trim() and length() in [1].
                if (userCocktail.getText().toString().trim().length() == 0) {
                    Toast.makeText(this, R.string.search_toast, Toast.LENGTH_LONG).show();
                } else {
                    Intent nextActivity = new Intent(CocktailSearch.this, CocktailResultList.class);
                    nextActivity.putExtra(COCKTAIL_NAME, userCocktail.getText().toString());
                    startActivity(nextActivity);
                }
            });

            // Click the button and load the user's favourites from the database
            favouritesButton.setOnClickListener( v -> {
                Intent loadFavouritesActivity = new Intent(CocktailSearch.this, CocktailResultList.class);
                loadFavouritesActivity.putExtra(LOAD_FAVOURITES, LOAD_FAVOURITES);
                startActivity(loadFavouritesActivity);
            });
        }

    /**
     * Saves the user's inputs when the Activity is paused
     */
    @Override
        protected void onPause() {
            super.onPause();
            SharedPreferences sharedPrefs = getSharedPreferences("com.cst2335.androidfinalproject.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(getString(R.string.saved_user_drink), userCocktail.getText().toString());
            editor.apply();
        }

    /**
     * Inflate the menu items for use in the action bar
     * @param menu the menu to be inflated
     * @return true
     */
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.cocktail_menu, menu);
            return true;
        }

    /**
     * Display an alert explaining how this application functions
     * @param menuItem the selected menu item
     * @return true
     */
    @Override
        public boolean onOptionsItemSelected(MenuItem menuItem) {
            AlertDialog.Builder helpAlertBuilder = new AlertDialog.Builder(this);
            helpAlertBuilder.setMessage(R.string.cocktail_menu_help_message)
                            .setTitle(R.string.cocktail_help_menu_title)
                            .setNeutralButton(R.string.alert_message_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}})
                            .create()
                            .show();
            return true;
        }
}


// References cited:
// [1] C. Smotricz, "Answer to 'How do I check that a Java String is not all whitespaces?,'" Stack Overflow, Jul. 14, 2010. https://stackoverflow.com/a/3247081 (accessed Aug. 03, 2022).