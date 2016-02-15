package com.codeitsuisse.team28.expensetracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.provider.ExpenseCardProvider;
import com.codeitsuisse.team28.expensetracker.card.provider.PaidExpenseSummaryCardProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

import java.util.Calendar;

/**
 * Created by hp1 on 21-01-2015.
 */
public class BasicPaidExpense extends android.support.v4.app.Fragment implements View.OnClickListener{

    private MaterialListView mListView;
    private String title;
    private String description;
    private Button mButton;
    private ListView mExpenseList;
    private DBhelper db;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.fragment_basic_paid_expense, container, false);

        mListView = (MaterialListView) row.findViewById(R.id.material_listview);
        db = new DBhelper(getActivity().getApplicationContext());
        Calendar calendar=Calendar.getInstance();
        int thisMonth=calendar.get(Calendar.MONTH)+1,thisYear=calendar.get(Calendar.YEAR);


        title = "Paid Expense Summary";
        description = "Total Paid expenses : Rs."+db.sumPaid(thisMonth,thisYear);

        mListView.add(getPaidExpenseSummaryCard(0));



        Expense[] arr=db.getAllPaid(thisMonth, thisYear);
        int len=db.getLength();
        for (int i = (len-1); i >=0 ; i--) {
            title = " ";
            description = "Amount : Rs. " + arr[i].getAmount() + "\nCategory : "+arr[i].getCategory()+"\nDate : " + arr[i].getDay()+"/"+arr[i].getMonth()+"/"+arr[i].getYear();
            mListView.add(getExpenseCard(i));
        }

        return row;
    }


    private Card getPaidExpenseSummaryCard(int position) {

        final PaidExpenseSummaryCardProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(PaidExpenseSummaryCardProvider.class)
                .setTitle(title)
                .setDescription(description);

        provider.setDividerVisible(false);

        return provider.endConfig().build();

    }


    public Card getExpenseCard(int position){
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

    }



}