package edu.cuny.citytech.restauranttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    // TABLE INFORMATTION
    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String RESTAURANT_ID = "_id";
    public static final String RESTAURANT_NAME = "resname";
    public static final String RESTAURANT_RATING = "score";
    public static final String RESTAURANT_ADDRESS1 = "address1";
    public static final String RESTAURANT_ADDRESS2 = "address2";
    public static final String RESTAURANT_PHONE = "phone";
    // DATABASE INFORMATION
    static final String DB_NAME = "MEMBER.DB";
    static final int DB_VERSION = 6;

    // TABLE CREATION STATEMENT

    private static final String CREATE_TABLE = "create table " + TABLE_RESTAURANT
            + "("
            + RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RESTAURANT_NAME + " TEXT NOT NULL, "
            + RESTAURANT_RATING + " FLOAT, "
            + RESTAURANT_ADDRESS1 + " TEXT, "
            + RESTAURANT_ADDRESS2 + " TEXT, "
            + RESTAURANT_PHONE + " TEXT"
            +");";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        ContentValues value = new ContentValues();
        value.put(RESTAURANT_NAME,"Tanpopo Ramen");
        value.put(RESTAURANT_RATING, 5);
        db.insert(TABLE_RESTAURANT, null, value);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        onCreate(db);

    }

}
