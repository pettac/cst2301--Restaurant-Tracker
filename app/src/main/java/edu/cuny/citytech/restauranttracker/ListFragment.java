package edu.cuny.citytech.restauranttracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class ListFragment extends Fragment {

    DetailsListener mListener;

    TableLayout table_layout;
    SQLController sqlcon;

    public ListFragment() {
        // Required empty public constructor
    }


    public interface DetailsListener {

        void detailsInteraction(int ints);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.list_fragment, container, false);
        table_layout =(TableLayout) view.findViewById(R.id.TableLayout01);
        sqlcon = new SQLController(getActivity());
        BuildTable();

        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (DetailsListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void BuildTable() {
        table_layout.removeAllViews();
        sqlcon.open();
        Cursor c = sqlcon.readEntry();

        int rows = c.getCount();
        int cols = c.getColumnCount();

        c.moveToFirst();


        // outer for loop

        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.setTag(R.string.id, c.getInt(0));
            row.setOnClickListener(detailsListener);
            // inner for loop
            for (int j = 1; j < cols; j++) {

                if(j==2){
                    RatingBar score = new RatingBar(getActivity());
                    score.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    score.setNumStars(5);
                    score.setIsIndicator(true);
                    score.setRating(c.getFloat(j));
                    row.addView(score);
                }else{
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(16,16,16,16);

                    tv.setText(c.getString(j));

                    row.addView(tv);
                }



            }
            c.moveToNext();

            table_layout.addView(row);

        }
        sqlcon.close();
    }

    View.OnClickListener detailsListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){

            if (mListener != null) {
                System.out.println(v.getTag(R.string.id));
                mListener.detailsInteraction(Integer.parseInt(v.getTag(R.string.id).toString()));
                System.out.println(v.getTag(R.string.id).toString());
            }
        }
    };

}
