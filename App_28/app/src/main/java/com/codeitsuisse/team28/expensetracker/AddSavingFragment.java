package com.codeitsuisse.team28.expensetracker;


import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSavingFragment extends android.support.v4.app.Fragment {

    private View row;
    private FragmentActivity mContext;
    private OnFragmentInteractionListener mListener;

    public AddSavingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        row = inflater.inflate(R.layout.fragment_add_saving,container,false);


        final EditText ed=(EditText)row.findViewById(R.id.edtextamt);
        Button butt=(Button)row.findViewById(R.id.submitbutt);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float amt=new Float(ed.getText().toString());
                Calendar calendar=Calendar.getInstance();
                int thisYear = calendar.get(Calendar.YEAR);

                int thisMonth = calendar.get(Calendar.MONTH)+1;
                DBhelper db=new DBhelper(getActivity().getApplicationContext());
                Savings bug=db.getSavings(thisMonth,thisYear);
                if(bug==null){
                    db.insertsavings(thisMonth,thisYear,amt);
                }else{
                    db.updateSavings(thisMonth,thisYear,amt);
                }

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
