package edu.cuny.citytech.restauranttracker;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class RestaurantList extends Activity implements ListFragment.DetailsListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        //checks if its in single pane mode
        if (findViewById(R.id.container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            //Generates Fragment and adds to container
            ListFragment fr = new ListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fr).commit();

        }

    }



    @Override
    public void detailsInteraction(int ints) {

        //Creates Details fragment
        DetailsFragment detailsFrag = (DetailsFragment)
                getFragmentManager().findFragmentById(R.id.article_fragment);

        //checks if article frag is avibable and also double checks the orientation
        if (detailsFrag != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // Call a method in the DetailsFragment to update its con

                detailsFrag.updateDetailsView(ints);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            DetailsFragment newFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt(DetailsFragment.ARG_POSITION, ints);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }

    }

    //action bar creation
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //this nested if checks to see which action bar item was pressed based on the id assigned in xml page
        //it then creates an intent which will specify which page to go to
        //finally it starts the intent and takes the user to the specified page


        if (id == R.id.add) {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            AddResFragment addFragment = new AddResFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, addFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }else if(id == R.id.delete){
            DeleteResFragment deleteFragment = new DeleteResFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, deleteFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }


        return super.onOptionsItemSelected(item);
    }

    public void addRes(View view){
        EditText name = (EditText) findViewById(R.id.add_name);
        RatingBar score = (RatingBar) findViewById(R.id.add_ratingBar);
        EditText address1 = (EditText) findViewById(R.id.add_address1);
        EditText address2 = (EditText) findViewById(R.id.add_address2);
        EditText phone = (EditText) findViewById(R.id.add_phone);


        String res_name, res_addy1, res_addy2, res_phone;
        Float res_score;


        // if statements checks if edit text boxes are empty, if so it assigns null to them
        if(name.getText().toString().matches("")){
            res_name = null;
        }else{
            res_name = name.getText().toString();
        }

        if(score.getRating() == 0){
            res_score = null;
        }else{
            res_score = score.getRating();
        }

        if(address1.getText().toString().matches("")){
            res_addy1 = null;
        }else{
            res_addy1 = address1.getText().toString();
        }

        if(address2.getText().toString().matches("")){
            res_addy2 = null;
        }else{
            res_addy2 = address2.getText().toString();
        }
        if(phone.getText().toString().matches("")){
            res_phone = null;
        }else{
            res_phone = phone.getText().toString();
        }



        // Makes the Name edit box and the rating mandatory or if addy 1 is filled and addy is not
        if(res_name == null && res_score == null ){

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cannot Add");
            alertDialog.setMessage("You need to at least add a Name and Rating.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }else if (res_addy1 == null && res_addy2 != null || res_addy1 != null && res_addy2 == null){

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cannot Add");
            alertDialog.setMessage("Both or neither of the address boxes need to be populated.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }else {
            SQLController sqlcon = new SQLController(this);

            sqlcon.insertData(res_name,res_score,res_addy1,res_addy2,res_phone);


            System.out.println("Name: " + name.getText().toString() + " Score: " + score.getRating());

            getFragmentManager().popBackStack();

        }



    }

    public void publicDirections (View view){
        TextView name = (TextView) findViewById(R.id.name);
        TextView address1 = (TextView) findViewById(R.id.address1); //street address
        TextView address2 = (TextView) findViewById(R.id.address2); //city,state zip

        if (address1.getText().toString().matches("") || address2.getText().toString().matches("")){
            // search directions by name

        }else{
            //search directions by address

        }

    }

    public void carDirections (View view){
        TextView name = (TextView) findViewById(R.id.name);
        TextView address1 = (TextView) findViewById(R.id.address1); //street address
        TextView address2 = (TextView) findViewById(R.id.address2); //city,state zip

        if (address1.getText().toString().matches("") || address2.getText().toString().matches("")){
            // search directions by name

        }else{
            //search directions by address

        }

    }

}