package com.cst2335.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Accesses the database containing a user's favourite Cocktail recipes.
 * Taken from [1] and informed by [2].
 */
public class MyOpener extends SQLiteOpenHelper {

    // Instance variables representing the database's column names and information
    /** {@value} The SQLite database's name **/
    protected final static String DATABASE_NAME = "CocktailsDB";
    /** {@value} The database's version number **/
    protected final static int VERSION_NUM = 1;
    /** {@value} The Cocktail table's name **/
    public final static String TABLE_NAME = "COCKTAILS";
    /** {@value} A Cocktail instance's unique database id (primary key) **/
    public final static String COL_ID = "_id";
    /** {@value} A Cocktail instance's name **/
    public final static String COL_NAME = "COCKTAIL_NAME";
    /** {@value} A Cocktail instance's instructions **/
    public final static String COL_INSTRUCTIONS = "INSTRUCTIONS";
    /** {@value} A Cocktail instance's first ingredient **/
    public final static String COL_INGREDIENT1 = "INGREDIENT1";
    /** {@value} A Cocktail instance's second ingredient **/
    public final static String COL_INGREDIENT2 = "INGREDIENT2";
    /** {@value} A Cocktail instance's third ingredient **/
    public final static String COL_INGREDIENT3 = "INGREDIENT3";
    /** {@value} A Cocktail instance's picture name. Used to retrieve picture from local storage **/
    public final static String COL_PICTURE = "PICTURE";

    /**
     * Parameterized constructor passes parameters to its super-constructor
     * @param ctx the context passed to the super-constructor
     */
    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Gets called if no database file exists.
     * @param db the local SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " text,"
                + COL_INSTRUCTIONS  + " text,"
                + COL_INGREDIENT1 + " text,"
                + COL_INGREDIENT2 + " text,"
                + COL_INGREDIENT3 + " text,"
                + COL_PICTURE + " text);"); // add or remove columns
    }

    /**
     * Gets called if the database version on device is lower than VERSION_NUM
     * @param db the local SQLite database
     * @param oldVersion the database's old version
     * @param newVersion the database's new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create the new table:
        onCreate(db);
    }

    /**
     * Gets called if the database version on device is higher than VERSION_NUM.
     * @param db the local SQLite database
     * @param oldVersion the database's old version
     * @param newVersion the database's new version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create the new table:
        onCreate(db);
    }
}

// Reference cited:
// [1] E. Torunski, "etorunski/InClassExamples_W21 at week5_database," Github, 2020. https://github.com/etorunski/InClassExamples_W21/tree/week5_database (accessed Jul. 13, 2022).
// [2] E. Torunski, "Week5_Android_SQLLite - 22S_CST2335_010 Mobile Graphical Interface Prog." Algonquin College, Jul. 05, 2022. Accessed: Jul. 13, 2022. [Online]. Available: https://brightspace.algonquincollege.com/d2l/le/content/444410/viewContent/7030230/View
