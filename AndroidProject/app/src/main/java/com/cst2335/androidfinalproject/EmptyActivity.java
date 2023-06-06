package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Used to display the Fragment when device is a tablet
 */
public class EmptyActivity extends AppCompatActivity {

    /**
     * Retrieves data passed from previous activity and loads the layout
     * @param savedInstanceState Bundle parameter to be passed to the super-constructor
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras();

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_Layout,detailsFragment)
                .commit();
    }
}