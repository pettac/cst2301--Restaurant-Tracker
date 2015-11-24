package edu.cuny.citytech.restauranttracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

    private MyDbHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbhelper = new MyDbHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbhelper.close();
    }

    public void insertData(String resname, float score) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.RESTAURANT_NAME, resname);
        cv.put(MyDbHelper.RESTAURANT_RATING, score);
        database.insert(MyDbHelper.TABLE_RESTAURANT, null, cv);
        close();

    }

    public void editData(int id, String resname, float score) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.RESTAURANT_NAME, resname);
        cv.put(MyDbHelper.RESTAURANT_RATING, score);
        database.update(MyDbHelper.TABLE_RESTAURANT, cv, "_id=" + id, null);
        close();

    }
    public void deleteData(int id) {
        open();
        database.delete(MyDbHelper.TABLE_RESTAURANT,"_id=" + id, null);
        close();

    }

    public Cursor readEntry() {

        String[] allColumns = new String[] { MyDbHelper.RESTAURANT_ID, MyDbHelper.RESTAURANT_NAME,
                MyDbHelper.RESTAURANT_RATING };

        Cursor c = database.query(MyDbHelper.TABLE_RESTAURANT, allColumns, null, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

}