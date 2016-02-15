package com.codeitsuisse.team28.expensetracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Calendar;

public class PieChartActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        mToolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        DBhelper db=new DBhelper(getApplicationContext());
        Calendar calendar=Calendar.getInstance();
        int thisMonth=calendar.get(Calendar.MONTH)+1,thisYear=calendar.get(Calendar.YEAR);
        float food,travel,shopping,rent,loan;
        food=db.sumAllCat(thisMonth,thisYear,"Food");
        travel=db.sumAllCat(thisMonth,thisYear,"Travel");
        shopping=db.sumAllCat(thisMonth,thisYear,"Shopping");
        rent=db.sumAllCat(thisMonth,thisYear,"Rent");
        loan=db.sumAllCat(thisMonth,thisYear,"Loan");
        if(food!=0 || travel!=0 || shopping!=0 || rent!=0 || loan!=0) {
            if(food!=0)
            mPieChart.addPieSlice(new PieModel("Food", food, Color.parseColor("#FE6DA8")));
            if(travel!=0)
            mPieChart.addPieSlice(new PieModel("Travel", travel, Color.parseColor("#56B7F1")));
            if(shopping!=0)
            mPieChart.addPieSlice(new PieModel("Shopping", shopping, Color.parseColor("#CDA67F")));
            if(rent!=0)
            mPieChart.addPieSlice(new PieModel("Rent", rent, Color.parseColor("#FED70E")));
            if(loan!=0)
            mPieChart.addPieSlice(new PieModel("Loan", loan, Color.parseColor("#5ae413")));
            mPieChart.startAnimation();
        }
        else{
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
