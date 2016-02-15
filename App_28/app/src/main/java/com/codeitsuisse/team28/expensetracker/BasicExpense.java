package com.codeitsuisse.team28.expensetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.provider.ExpenseCardProvider;
import com.codeitsuisse.team28.expensetracker.card.provider.ExpenseSummaryCardProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

import java.util.Calendar;

/**
 * Created by hp1 on 21-01-2015.
 */
public class BasicExpense extends android.support.v4.app.Fragment implements View.OnClickListener {

    private MaterialListView mListView;
    private String title;
    private String description;
    private Button mButton;
    private ListView mExpenseList;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.fragment_basic_expense, container, false);

        mListView = (MaterialListView) row.findViewById(R.id.material_listview);

        DBhelper db=new DBhelper(getActivity().getApplicationContext());
        Calendar calendar=Calendar.getInstance();
        int thisMonth=calendar.get(Calendar.MONTH)+1,thisYear=calendar.get(Calendar.YEAR);

        title = "Expense Summary";
        description = "Total expenses : Rs." + db.sumAllExpense(thisMonth,thisYear)+ " \nPaid expenses : Rs." +db.sumPaid(thisMonth,thisYear) + "\nLiabilities : Rs." + db.sumPayLater(thisMonth,thisYear) ;

        mListView.add(getExpenseSummaryCard(0));

        mButton = (Button) row.findViewById(R.id.addbutton);
        mButton.setOnClickListener(this);

        mButton = (Button) row.findViewById(R.id.resetbutton);
        mButton.setOnClickListener(this);

        Expense[] arr=db.getAllExpense(thisMonth, thisYear);
        int len=db.getLength();
        for (int i = (len-1); i >=0 ; i--) {
            title = " ";
            String ty;
            switch(arr[i].getType()){
                case 0:ty="Pay Later";break;
                default :ty="Paid";break;
            }
            description = "Amount : Rs. " + arr[i].getAmount() + "\nCategory : "+arr[i].getCategory()+"\nDate : " + arr[i].getDay()+"/"+arr[i].getMonth()+"/"+arr[i].getYear()+ "\nType : "+ty;
            mListView.add(getExpenseCard(i));
        }



        return row;
    }


    private Card getExpenseSummaryCard(int position) {

        final ExpenseSummaryCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(ExpenseSummaryCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);

        return provider.endConfig().build();

    }


    public Card getExpenseCard(int position) {
        final ExpenseCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(ExpenseCardProvider.class)
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
                AddExpenseFragment newFragment = new AddExpenseFragment();


                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                break;

            case R.id.resetbutton:
                AlertDialog.Builder abd = new AlertDialog.Builder(MainActivity.a);
                abd.setTitle("Reset Expenses").setMessage("Confirm to reset the expenses").setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper db = new DBhelper(getActivity().getApplicationContext());
                        Calendar calendar = Calendar.getInstance();
                        final int thisYear = calendar.get(Calendar.YEAR);

                        final int thisMonth = calendar.get(Calendar.MONTH) + 1;

                        db.clearExpense(thisMonth,thisYear);
                        BasicExpense newFragment1 = new BasicExpense();


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