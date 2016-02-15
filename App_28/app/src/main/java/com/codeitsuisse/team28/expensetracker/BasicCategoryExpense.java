package com.codeitsuisse.team28.expensetracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codeitsuisse.team28.expensetracker.card.Card;
import com.codeitsuisse.team28.expensetracker.card.OnButtonClickListener;
import com.codeitsuisse.team28.expensetracker.card.provider.CategoryExpenseProvider;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.codeitsuisse.team28.expensetracker.view.MaterialListView;

import java.util.Calendar;

/**
 * Created by hp1 on 21-01-2015.
 */
public class BasicCategoryExpense extends android.support.v4.app.Fragment implements OnButtonClickListener{

    private MaterialListView mListView;
    private DBhelper db;
    private String title;
    private String description;

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View row = inflater.inflate(R.layout.fragment_basic_category_expense, container, false);

        mListView = (MaterialListView) row.findViewById(R.id.material_listview);
        db = new DBhelper(getActivity().getApplicationContext());
        Calendar calendar=Calendar.getInstance();
        int thisMonth=calendar.get(Calendar.MONTH)+1,thisYear=calendar.get(Calendar.YEAR);
        String category[] = {"Food","Travel","Shopping","Rent","Loan"};
        for(int i=0;i<5;i++){
            float amt = db.sumAllCat(thisMonth,thisYear,category[i]);
            description = "Rs." + String.valueOf(amt);
            title = category[i];
            mListView.add(getCategoryExpenseCard(i));
        }
        Button but=(Button)row.findViewById(R.id.piebutton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(getActivity(),PieChartActivity.class);
                startActivity(inte);
            }
        });

        return row;
    }

    public Card getCategoryExpenseCard(int position){
        final CategoryExpenseProvider provider = new Card.Builder(MainActivity.a)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(CategoryExpenseProvider.class)
                .setTitle(title)
                .setDescription(description)
                .setDescriptionResourceColor(R.color.white);

        provider.setDividerVisible(false);

        return provider.endConfig().build();

    }

    @Override
    public void onButtonClicked(View view, Card card) {

    }
}