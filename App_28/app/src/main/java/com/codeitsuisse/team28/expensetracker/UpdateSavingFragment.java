package com.codeitsuisse.team28.expensetracker;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateSavingFragment extends android.support.v4.app.Fragment {

    private FragmentActivity mContext;
    private AddSavingFragment.OnFragmentInteractionListener mListener;

    public UpdateSavingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View row;

        row = inflater.inflate(R.layout.fragment_update_saving,container,false);
        Calendar calendar=Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);

        final int thisMonth = calendar.get(Calendar.MONTH)+1;
        final DBhelper db=new DBhelper(getActivity().getApplicationContext());
        TextView tv1=(TextView)row.findViewById(R.id.textv1);
        tv1.setText("Current Saving goals = Rs "+db.getSavings(thisMonth,thisYear).getAmount());
        final EditText ed=(EditText)row.findViewById(R.id.edtextamt);
        Button butt=(Button)row.findViewById(R.id.submitbutt);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float amt=new Float(ed.getText().toString());
                db.updateSavings(thisMonth, thisYear, amt);

                // onBackPressed();
                SavingFragment newFragment = new SavingFragment();


                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        return row;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
