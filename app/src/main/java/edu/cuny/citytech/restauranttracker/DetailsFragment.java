package edu.cuny.citytech.restauranttracker;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentRow = -1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentRow = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment


        View v;


        v = inflater.inflate(R.layout.details_fragment, null);


        return v;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (menu != null) {

            menu.findItem(R.id.add).setVisible(false);


            //checks if article frag is avibable and also double checks the orientation
                menu.findItem(R.id.add).setVisible(true);
                menu.findItem(R.id.edit).setVisible(true);
                menu.findItem(R.id.delete).setVisible(true);

        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentRow = savedInstanceState.getInt(ARG_POSITION);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateDetailsView(args.getInt(ARG_POSITION));
        } else if (mCurrentRow != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateDetailsView(mCurrentRow);
        }
    }

    public void updateDetailsView(int row) {
        System.out.println("this is position " + row);

        SQLController sqlcon = new SQLController(getActivity());

        Cursor single = sqlcon.singleEntry(row, null);
        single.moveToFirst();


        TextView id = (TextView) getActivity().findViewById(R.id.row_id);
        TextView name = (TextView) getActivity().findViewById(R.id.name);
        RatingBar score = (RatingBar) getActivity().findViewById(R.id.score);
        TextView address1 = (TextView) getActivity().findViewById(R.id.address1);
        TextView address2 = (TextView) getActivity().findViewById(R.id.address2);
        TextView phone = (TextView) getActivity().findViewById(R.id.phone);

        id.setText(Integer.toString(single.getInt(0)));

        name.setText(single.getString(1));
        name.setVisibility(View.VISIBLE);

        score.setRating(single.getFloat(2));
        score.setVisibility(View.VISIBLE);

        address1.setText(single.getString(3));
        address1.setVisibility(View.VISIBLE);

        address2.setText(single.getString(4));
        address2.setVisibility(View.VISIBLE);

        phone.setText(single.getString(5));
        phone.setVisibility(View.VISIBLE);



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentRow);
    }

}