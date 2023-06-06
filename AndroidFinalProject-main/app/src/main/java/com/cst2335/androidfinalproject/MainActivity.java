package com.cst2335.androidfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * Application landing page. Contains a Toolbar, a NavigationView, and a DrawerLayout. Clicking the
 * Cocktail icon sends user to CocktailSearch activity. Used [1], [2], and [3] to understand how Toolbar,
 * DrawerLayout, and NavigationView work together.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Loads the View, Toolbar, DrawerLayout, and NavigationView.
     * @param savedInstanceState Bundle parameter to be passed to the super-constructor
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle hamburger = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(hamburger);
        hamburger.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Learned how to use BuildConfig to dynamically retrieve the version in [4].
        TextView versionView = (TextView)findViewById(R.id.version_view);
       versionView.setText(getText(R.string.version) + ": " + BuildConfig.VERSION_NAME);

        Button cocktailButton = findViewById(R.id.cocktailButton);
        cocktailButton.setOnClickListener( v -> {
            Intent cocktailActivity = new Intent(MainActivity.this, CocktailSearch.class);
            startActivity(cocktailActivity);
        });
    }

    /**
     * Inflates the menu
     * @param menu the menu where layout is inflated
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    /**
     * When menu icon is clicked, user is sent to CocktailSearch activity
     * @param item the icon being clicked
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setOnMenuItemClickListener(item1 -> {
            Intent cocktailActivity = new Intent(MainActivity.this, CocktailSearch.class);
            startActivity(cocktailActivity);
            return true;
        });
        return true;
    }

    /**
     * When an item is clicked in the NavigationView, the user is sent to CocktailSearch Activity.
     * @param item the icon being clicked.
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setOnMenuItemClickListener(item1 -> {
            Intent cocktailActivity = new Intent(MainActivity.this, CocktailSearch.class);
            startActivity(cocktailActivity);
            return true;
        });
        return true;
    }
}

// Reference cited:
// [1] E. Torunski, "Week8_Toolbar - 22S_CST2335_010 Mobile Graphical Interface Prog.," Algonquin College, Jul. 28, 2022. https://brightspace.algonquincollege.com/d2l/le/content/444410/viewContent/7030236/View (accessed Aug. 06, 2022).
// [2] E. Torunski, "etorunski/InClassExamples_W21 at week8_NavigationDrawer," Github, 2021. https://github.com/etorunski/InClassExamples_W21/tree/week8_NavigationDrawer (accessed Aug. 06, 2022).
// [3] E. Torunski, "etorunski/InClassExamples_W21 at week8_toolbar," Github, 2021. https://github.com/etorunski/InClassExamples_W21/tree/week8_toolbar (accessed Aug. 06, 2022).
// [4] S. Dozor, "Answer to 'How can you get the build/version number of your Android application?,'" Stack Overflow, Jan. 14, 2014. https://stackoverflow.com/a/21119027 (accessed Aug. 06, 2022).