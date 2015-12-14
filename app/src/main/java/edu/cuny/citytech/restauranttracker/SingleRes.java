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


public class SingleRes extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.single_res, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.

        Bundle extras = getArguments();

        String section = extras.getString("order");


        SQLController sqlcon = new SQLController(getActivity());

        Cursor single = sqlcon.singleEntry(null, section);
        single.moveToFirst();

        TextView id = (TextView) getActivity().findViewById(R.id.hr_res_id);
        TextView name = (TextView) getActivity().findViewById(R.id.hr_res_name);
        RatingBar score = (RatingBar) getActivity().findViewById(R.id.hr_res_score);
        TextView address1 = (TextView) getActivity().findViewById(R.id.hr_res_add1);
        TextView address2 = (TextView) getActivity().findViewById(R.id.hr_res_add2);
        TextView phone = (TextView) getActivity().findViewById(R.id.hr_res_phone);

        id.setText(Integer.toString(single.getInt(0)));

        name.setText(single.getString(1));


        score.setRating(single.getFloat(2));


        address1.setText(single.getString(3));


        address2.setText(single.getString(4));


        phone.setText(single.getString(5));


        System.out.println(single.getInt(0));


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

