package com.codeitsuisse.team28.expensetracker;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
/**
 * Created by sarup on 13-09-2015.
 */
public class UpdateBudgetFragment extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Toolbar mToolbar;
    private FragmentActivity mContext;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateBudgetFragment newInstance(String param1, String param2) {
        UpdateBudgetFragment fragment = new UpdateBudgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UpdateBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View row;

        row = inflater.inflate(R.layout.fragment_update_budget,container,false);
        Calendar calendar=Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);

        final int thisMonth = calendar.get(Calendar.MONTH)+1;
        final DBhelper db=new DBhelper(getActivity().getApplicationContext());
        TextView tv1=(TextView)row.findViewById(R.id.textv1);
        tv1.setText("Current Buget = Rs "+db.getBudget(thisMonth,thisYear).getAmount());
        final EditText ed=(EditText)row.findViewById(R.id.edtextamt);
        Button butt=(Button)row.findViewById(R.id.submitbutt);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float amt=new Float(ed.getText().toString());
                db.updateBudget(thisMonth,thisYear,amt);

                // onBackPressed();
                BudgetFragment newFragment = new BudgetFragment();


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
