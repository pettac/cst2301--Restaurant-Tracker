package edu.cuny.citytech.restauranttracker;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class EditResFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_res, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.


        Bundle extras = getArguments();

        int row_id = extras.getInt("row_id");


        SQLController sqlcon = new SQLController(getActivity());

        Cursor single = sqlcon.singleEntry(row_id,null);
        single.moveToFirst();
        TextView id = (TextView) getActivity().findViewById(R.id.row_id);
        EditText name = (EditText) getActivity().findViewById(R.id.edit_name);
        RatingBar score = (RatingBar) getActivity().findViewById(R.id.edit_ratingBar);
        EditText address1 = (EditText) getActivity().findViewById(R.id.edit_address1);
        EditText address2 = (EditText) getActivity().findViewById(R.id.edit_address2);
        EditText phone = (EditText) getActivity().findViewById(R.id.edit_phone);



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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (menu != null) {

            menu.findItem(R.id.add).setVisible(false);
            menu.findItem(R.id.delete).setVisible(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
