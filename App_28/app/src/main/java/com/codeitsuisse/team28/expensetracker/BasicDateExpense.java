package com.codeitsuisse.team28.expensetracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.provider.ExpenseCardProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

/**
 * Created by hp1 on 21-01-2015.
 */
public class BasicDateExpense extends android.support.v4.app.Fragment implements View.OnClickListener{
    private MaterialListView mListView;
    private String title;
    private String description;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.fragment_basic_date_expense, container, false);

        mListView = (MaterialListView) row.findViewById(R.id.material_listview);

        title = "Expense Card";

        title=" ";
        description = "Amount : Rs. " + (100) + "\nCategory : Food \nDate :  1/10/2015 \nType : Paid";
        mListView.add(getExpenseCard(0));

        return row;
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