package edu.cuny.citytech.restauranttracker;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.FragmentTransaction;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RestaurantList extends Activity implements ListFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        if (findViewById(R.id.container) != null) {

            if (savedInstanceState != null) {
                return;
            }


            ListFragment fr = new ListFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.container, fr).commit();

        }





    }


    @Override
    public void onFragmentInteraction(int ints) {


        ArticleFragment articleFrag = (ArticleFragment)
                getFragmentManager().findFragmentById(R.id.article_fragment);


        if (articleFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(ints);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, ints);
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


}