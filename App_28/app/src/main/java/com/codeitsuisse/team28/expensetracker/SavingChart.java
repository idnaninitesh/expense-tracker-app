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
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.Calendar;

public class SavingChart extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savingchart);
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
        DBhelper db=new DBhelper(getApplicationContext());
        Calendar calendar=Calendar.getInstance();
        int thisMonth=calendar.get(Calendar.MONTH)+1,thisYear=calendar.get(Calendar.YEAR);
        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        Savings bug;
        float mon[]=new float[12];
        for(int i=0;i<12;i++) {
            bug = db.getSavings(i + 1, thisYear);
            if (bug!=null)mon[i]=bug.getAmount();
            else mon[i]=0;
        }
        if(thisMonth>=1)
            series.addPoint(new ValueLinePoint("Jan", mon[0]));
        if(thisMonth>=2)
            series.addPoint(new ValueLinePoint("Feb", mon[1]));
        if(thisMonth>=3)
            series.addPoint(new ValueLinePoint("Mar", mon[2]));
        if(thisMonth>=4)
            series.addPoint(new ValueLinePoint("Apr", mon[3]));
        if(thisMonth>=5)
            series.addPoint(new ValueLinePoint("Mai", mon[4]));
        if(thisMonth>=6)
            series.addPoint(new ValueLinePoint("Jun", mon[5]));
        if(thisMonth>=7)
            series.addPoint(new ValueLinePoint("Jul", mon[6]));
        if(thisMonth>=8)
            series.addPoint(new ValueLinePoint("Aug", mon[7]));
        if(thisMonth>=9)
            series.addPoint(new ValueLinePoint("Sep", mon[8]));
        if(thisMonth>=10)
            series.addPoint(new ValueLinePoint("Oct", mon[9]));
        if(thisMonth>=11)
            series.addPoint(new ValueLinePoint("Nov", mon[10]));
        if(thisMonth>=12)
            series.addPoint(new ValueLinePoint("Dec", mon[11]));

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();

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
