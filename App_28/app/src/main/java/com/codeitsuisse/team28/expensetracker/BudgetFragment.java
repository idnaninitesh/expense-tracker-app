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
import com.codeitsuisse.team28.expensetracker.card.provider.BudgetDetailCardProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private DBhelper dbhelper;
    private Budget budget, prev_budget;
    private int month, year;
    private Calendar calendar;
    private double avgBudget;
    private View row;
    private MaterialListView mListView;
    private String title;
    private String description;
    private Button mButton;
    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;

    public BudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        dbhelper = new DBhelper(getActivity().getApplicationContext());

        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;

        budget = dbhelper.getBudget(month, year);

        if (budget == null) {
            //add,reset button

            row = inflater.inflate(R.layout.fragment_budget_updel, container, false);

            mListView = (MaterialListView) row.findViewById(R.id.material_listview);

            prev_budget = dbhelper.getLastBudget();
            if (prev_budget == null) {
                title = MainActivity.getMonth(month) + " " + String.valueOf(year);
                description = "No budget transactions found till date";

            } else {
                title =MainActivity.getMonth(prev_budget.getMonth()) + " " + String.valueOf(prev_budget.getYear());
                description = "Rs " + prev_budget.getAmount();
            }
            mListView.add(getBudgetDetailCard(0, month - 1, year));

            //query for average budget
            title = "Average Budget";
            avgBudget = dbhelper.getAvgBudget();
            if (avgBudget == 0.0)
                description = "No data available. \nStart managing your budget and experience fun !!!";
            else
                description = "Rs " + avgBudget;

            mListView.add(getAverageBudgetDetailCard(1));

            mButton = (Button) row.findViewById(R.id.addbutton);
            mButton.setOnClickListener(this);

            mButton = (Button) row.findViewById(R.id.resetbutton);
            mButton.setOnClickListener(this);

        } else {
            //update button


            row = inflater.inflate(R.layout.fragment_budget, container, false);

            mListView = (MaterialListView) row.findViewById(R.id.material_listview);

            title = MainActivity.getMonth(month)  + " " + String.valueOf(year);
            description = "Rs " + budget.getAmount();

            mListView.add(getBudgetDetailCard(0, month, year));

            //query for average budget
            title = "Average Budget";
            avgBudget = dbhelper.getAvgBudget();
            if (avgBudget == 0.0)
                description = "No data available. \nStart managing your budget and experience fun !!!";
            else
                description = "Rs " + avgBudget;

            mListView.add(getAverageBudgetDetailCard(1));

            mButton = (Button) row.findViewById(R.id.updatebutton);
            mButton.setOnClickListener(this);


        }

        Button but=(Button)row.findViewById(R.id.chartbutton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(getActivity(),BudgetChart.class);
                startActivity(inte);
            }
        });
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        // Inflate the layout for this fragment
        return row;

    }

    private Card getBudgetDetailCard(int position, int month, int year) {

        //title = String.valueOf(month) + " " + String.valueOf(year);
        //description = "Current Month details";

        final BudgetDetailCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BudgetDetailCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);

        return provider.endConfig().build();

    }

    private Card getAverageBudgetDetailCard(int position) {

        //title = "Average Budget";
        //description = "Average budget details";

        final BudgetDetailCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(BudgetDetailCardProvider.class)
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
                AddBudgetFragment newFragment = new AddBudgetFragment();


                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                break;

            case R.id.updatebutton:
                UpdateBudgetFragment newFragment1 = new UpdateBudgetFragment();


                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();

                transaction1.replace(R.id.container, newFragment1);
                transaction1.addToBackStack(null);

                // Commit the transaction
                transaction1.commit();
                break;

            case R.id.resetbutton:
                AlertDialog.Builder abd=new AlertDialog.Builder(MainActivity.a);
                abd.setTitle("Reset Budget").setMessage("Confirm to set Budget to Average Budget").setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper db=new DBhelper(getActivity().getApplicationContext());
                        Calendar calendar=Calendar.getInstance();
                        final int thisYear = calendar.get(Calendar.YEAR);

                        final int thisMonth = calendar.get(Calendar.MONTH)+1;
                        double avg=db.getAvgBudget();
                        if(avg!=0.0){
                            Budget bug=db.getBudget(thisMonth,thisYear);
                            if(bug==null){
                                db.insertbudget(thisMonth,thisYear,new Float(avg));
                            }else{
                                db.updateBudget(thisMonth, thisYear, new Float(avg));
                            }
                        }
                        BudgetFragment newFragment1 = new BudgetFragment();


                        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();

                        transaction1.replace(R.id.container, newFragment1);
                        transaction1.addToBackStack(null);

                        // Commit the transaction
                        transaction1.commit();

                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                abd.show();
                break;

        }

    }
}
