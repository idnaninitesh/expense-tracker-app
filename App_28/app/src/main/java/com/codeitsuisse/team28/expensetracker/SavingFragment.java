package com.codeitsuisse.team28.expensetracker;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.provider.SavingDetailCardProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavingFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private DBhelper dbhelper;
    private int month, year;
    private Savings saving;
    private Calendar calendar;
    private View row;
    private MaterialListView mListView;
    private String title;
    private String description;
    private Button mButton;


    public SavingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbhelper = new DBhelper(getActivity().getApplicationContext());

        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;

        saving = dbhelper.getSavings(month, year);

        if (saving == null) {
            //add,reset button
            row = inflater.inflate(R.layout.fragment_saving_addres, container, false);

            mListView = (MaterialListView) row.findViewById(R.id.material_listview);
            Savings sv=dbhelper.getLastSavings();
            if(sv!=null) {
                title = "Previous savings goal ("+MainActivity.getMonth(sv.getMonth())+","+sv.getYear()+")";

                description = "Savings Goal : Rs "+sv.getAmount();
            }
            else{
                title = "Previous savings goal";
                description = "No previous information avialable,start saving now!!";

            }
            mListView.add(getSavingDetailCard(0, month, year));

            mButton = (Button) row.findViewById(R.id.addbutton);
            mButton.setOnClickListener(this);

            mButton = (Button) row.findViewById(R.id.resetbutton);
            mButton.setOnClickListener(this);


        } else {
            //update button

            row = inflater.inflate(R.layout.fragment_saving, container, false);

            mListView = (MaterialListView) row.findViewById(R.id.material_listview);

            title = MainActivity.getMonth(month) + " " + String.valueOf(year);
            description = "Savings Goal : Rs "+saving.getAmount();

            mListView.add(getSavingDetailCard(0, month, year));

            mButton = (Button) row.findViewById(R.id.updatebutton);
            mButton.setOnClickListener(this);

        }
        Button but=(Button)row.findViewById(R.id.chartbutton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(getActivity(),SavingChart.class);
                startActivity(inte);
            }
        });

        // Inflate the layout for this fragment
        return row;
    }

    private Card getSavingDetailCard(int position, int month, int year) {

        //title = String.valueOf(month) + " " + String.valueOf(year);
        //description = "Current Month details";

        final SavingDetailCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(SavingDetailCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);

        return provider.endConfig().build();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.addbutton:
                AddSavingFragment newFragment = new AddSavingFragment();


                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                break;

            case R.id.updatebutton:
                UpdateSavingFragment newFragment1 = new UpdateSavingFragment();


                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();

                transaction1.replace(R.id.container, newFragment1);
                transaction1.addToBackStack(null);

                // Commit the transaction
                transaction1.commit();
                break;

            case R.id.resetbutton:
                AlertDialog.Builder abd = new AlertDialog.Builder(MainActivity.a);
                abd.setTitle("Reset Saving Goal").setMessage("Confirm to set Saving Goal to Zero").setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper db = new DBhelper(getActivity().getApplicationContext());
                        Calendar calendar = Calendar.getInstance();
                        final int thisYear = calendar.get(Calendar.YEAR);

                        final int thisMonth = calendar.get(Calendar.MONTH) + 1;

                        db.insertsavings(thisMonth,thisYear,new Float(0.0));
                        BudgetFragment newFragment1 = new BudgetFragment();


                        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();

                        transaction1.replace(R.id.container, newFragment1);
                        transaction1.addToBackStack(null);

                        // Commit the transaction
                        transaction1.commit();

                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                abd.show();
                break;

        }

    }
}
