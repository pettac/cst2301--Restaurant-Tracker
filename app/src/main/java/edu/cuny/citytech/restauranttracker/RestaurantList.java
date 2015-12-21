package edu.cuny.citytech.restauranttracker;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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

        //Creates Details fragment to check
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

            DetailsFragment detailsFrag = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.article_fragment);
            Fragment foundFrag = getFragmentManager().findFragmentById(R.id.article_fragment);


            //checks if article frag is avibable and also double checks the orientation
            if (detailsFrag != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                // Call a method in the DetailsFragment to update its con
                transaction.remove(foundFrag);
                transaction.replace(R.id.article_fragment, addFragment);


            }else{
                transaction.replace(R.id.container, addFragment);
            }

            transaction.addToBackStack(null);


            // Commit the transaction
            transaction.commit();
        }else if(id == R.id.delete){

            TextView row_id = (TextView) findViewById(R.id.row_id);
            final int res_id = Integer.parseInt(row_id.getText().toString());

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Are you sure?");
            alertDialog.setMessage("You are about to delete a restaurant, are you sure?");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            deleteRes(res_id);


                        }
                    });
            alertDialog.show();

        }else if (id == R.id.edit){
            TextView row_id = (TextView) findViewById(R.id.row_id);

            int res_id = Integer.parseInt(row_id.getText().toString());
            EditResFragment editFragment = new EditResFragment();


            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            Bundle data = new Bundle();
            data.putInt("row_id", res_id);
            editFragment.setArguments(data);


            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, editFragment);
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
        if(res_name == null || res_score == null ){

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

    public void editRes(View view){
        TextView row_id = (TextView) findViewById(R.id.row_id);
        EditText name = (EditText) findViewById(R.id.edit_name);
        RatingBar score = (RatingBar) findViewById(R.id.edit_ratingBar);
        EditText address1 = (EditText) findViewById(R.id.edit_address1);
        EditText address2 = (EditText) findViewById(R.id.edit_address2);
        EditText phone = (EditText) findViewById(R.id.edit_phone);


        String res_name, res_addy1, res_addy2, res_phone;
        Float res_score;
        int res_id = Integer.parseInt(row_id.getText().toString());



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
            alertDialog.setTitle("Cannot Edit");
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


            sqlcon.editData(res_id, res_name, res_score, res_addy1, res_addy2, res_phone);


            System.out.println("Name: " + name.getText().toString() + " Score: " + score.getRating());

            getFragmentManager().popBackStack();

        }



    }

    public void deleteRes (int row_id){

        SQLController sqlcon = new SQLController(this);


        sqlcon.deleteData(row_id);

        getFragmentManager().popBackStack();

    }


    public void cancelRes (View view){
        getFragmentManager().popBackStack();
    }

    public void publicDirections (View view){
        TextView name = (TextView) findViewById(R.id.name);
        TextView address1 = (TextView) findViewById(R.id.address1); //street address
        TextView address2 = (TextView) findViewById(R.id.address2); //city,state zip
        if (address1.getText().toString().matches("") || address2.getText().toString().matches("")){
            // search directions by name
            Search(name.getText().toString(),"r");
        }else{
            //search directions by address
            Search(address1.getText().toString()+ " " + address2.getText().toString(),"r");
        }

    }

    public void carDirections (View view){
        TextView name = (TextView) findViewById(R.id.name);
        TextView address1 = (TextView) findViewById(R.id.address1); //street address
        TextView address2 = (TextView) findViewById(R.id.address2); //city,state zip
        if (address1.getText().toString().matches("") || address2.getText().toString().matches("")){
            // search directions by name
            Search(name.getText().toString(), "d");
        }else{
            //search directions by address
            Search(address1.getText().toString()+ " " + address2.getText().toString(),"d");
        }

    }
    public void bikeDirections (View view){
        TextView name = (TextView) findViewById(R.id.name);
        TextView address1 = (TextView) findViewById(R.id.address1); //street address
        TextView address2 = (TextView) findViewById(R.id.address2); //city,state zip
        if (address1.getText().toString().matches("") || address2.getText().toString().matches("")){
            // search directions by name
            Search(name.getText().toString(), "b");
        }else{
            //search directions by address
            Search(address1.getText().toString()+ " " + address2.getText().toString(),"b");
        }

    }
    public void walkingDirections (View view){
        TextView name = (TextView) findViewById(R.id.name);
        TextView address1 = (TextView) findViewById(R.id.address1); //street address
        TextView address2 = (TextView) findViewById(R.id.address2); //city,state zip
        if (address1.getText().toString().matches("") || address2.getText().toString().matches("")){
            // search directions by name
            Search(name.getText().toString(),"w");
        }else{
            //search directions by address
            Search(address1.getText().toString()+ " " + address2.getText().toString(),"w");
        }

    }
    //method to create google maps intent
    public void Search (String Destination, String Mode){

        //submits search criteria
        String mapUrl =  "http://maps.google.com/maps?daddr=" + Destination +  "&dirflg=" + Mode;
        //creates intent
        Intent i  = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
        //makes sure google maps is used and not another compatible app
        i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(i);

    }

    public void callPhone (View view){
        TextView phone = (TextView) findViewById(R.id.phone);

        String uri = "tel:" +  phone.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
        System.out.println("phone called");
    }

}