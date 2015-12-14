package edu.cuny.citytech.restauranttracker;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingleRes fr = new SingleRes();
        Bundle args = new Bundle();
        args.putString("order", "highest");
        fr.setArguments(args);
        getFragmentManager().beginTransaction()
                .add(R.id.higest_rating, fr).commit();



    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void listActivity(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, RestaurantList.class);
        startActivity(intent);
        System.out.println("yes");
    }

    //action bar creation
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //this nested if checks to see which action bar item was pressed based on the id assigned in xml page
        //it then creates an intent which will specify which page to go to
        //finally it starts the intent and takes the user to the specified page


        if (id == R.id.add) {
            Intent addIntent = new Intent(this, MainActivity.class); //replace Main.Activity.class with add activity
            startActivity(addIntent);
            return true;
        }
        else if (id == R.id.delete) {
            Intent deleteIntent = new Intent(this, page1.class); //replace page1.class with delete activity
            startActivity(deleteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}