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

    public void insertData(String resname, Float score, String address1, String address2, String phone) {
        open();
        ContentValues cv = new ContentValues();
        if(address1 == null){
            System.out.println("address232");
        }

        cv.put(MyDbHelper.RESTAURANT_NAME, resname);
        cv.put(MyDbHelper.RESTAURANT_RATING, score);
        cv.put(MyDbHelper.RESTAURANT_ADDRESS1,address1);
        cv.put(MyDbHelper.RESTAURANT_ADDRESS2, address2);
        cv.put(MyDbHelper.RESTAURANT_PHONE, phone);
        database.insert(MyDbHelper.TABLE_RESTAURANT, null, cv);
        close();

    }

    public void editData(int id, String resname, Float score, String address1, String address2, String phone) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.RESTAURANT_NAME, resname);
        cv.put(MyDbHelper.RESTAURANT_RATING, score);
        cv.put(MyDbHelper.RESTAURANT_ADDRESS1, address1);
        cv.put(MyDbHelper.RESTAURANT_ADDRESS2, address2);
        cv.put(MyDbHelper.RESTAURANT_PHONE, phone);
        database.update(MyDbHelper.TABLE_RESTAURANT, cv, "_id=" + id, null);
        close();

    }
    public void deleteData(int id) {
        open();
        database.delete(MyDbHelper.TABLE_RESTAURANT, "_id=" + id, null);
        close();

    }

    public Cursor singleEntry(Integer row, String order) {
        if(order == null){
            order = "";

        }
        Cursor c;
        open();
        String[] allColumns = new String[] { MyDbHelper.RESTAURANT_ID, MyDbHelper.RESTAURANT_NAME,
                MyDbHelper.RESTAURANT_RATING, MyDbHelper.RESTAURANT_ADDRESS1, MyDbHelper.RESTAURANT_ADDRESS2,MyDbHelper.RESTAURANT_PHONE };


        if (order.matches("latest")){

            String orderBy = MyDbHelper.RESTAURANT_ID + " DESC";
            c = database.query(MyDbHelper.TABLE_RESTAURANT, allColumns, null , null, null,
                    null, orderBy);

        }else if (order.matches("highest")){

            String orderBy = MyDbHelper.RESTAURANT_RATING + " DESC";
            c = database.query(MyDbHelper.TABLE_RESTAURANT, allColumns, null, null, null,
                    null, orderBy);

        }else{

            c = database.query(MyDbHelper.TABLE_RESTAURANT, allColumns, "_id=?", new String[] {Integer.toString(row)}, null,
                    null, null);
        }




        if (c != null) {
            c.moveToFirst();
        }
        close();
        return c;

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