package com.codeitsuisse.team28.expensetracker;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends android.support.v4.app.Fragment implements MaterialTabListener {

    private Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager pager;
    private FragmentActivity myContext;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"ALL","PAID","LIABLE","CATEGORY"};
    int Numboftabs = 4;


    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View row;

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        row = inflater.inflate(R.layout.fragment_expense,container,false);

        FragmentManager fragManager = myContext.getSupportFragmentManager();

        adapter =  new ViewPagerAdapter(fragManager,Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) row.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) row.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


        return row;
    }


    @Override
    public void onTabSelected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());
        Toast.makeText(getActivity().getApplicationContext(),"In tab " + (materialTab.getPosition()+1),Toast.LENGTH_SHORT).show();
        Log.d("E","in onTabSelected");
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());
        Toast.makeText(getActivity().getApplicationContext(),"In tab " + (materialTab.getPosition()+1),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

}
