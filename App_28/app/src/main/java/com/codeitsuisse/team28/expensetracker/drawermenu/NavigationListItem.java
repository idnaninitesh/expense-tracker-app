package com.codeitsuisse.team28.expensetracker.drawermenu;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codeitsuisse.team28.expensetracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationListItem extends android.support.v4.app.Fragment {



    public NavigationListItem() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        int myInt = bundle.getInt("position", 0);
        Toast.makeText(MainActivity.a,"In item " + (myInt+1),Toast.LENGTH_SHORT).show();



        return inflater.inflate(R.layout.fragment_navigation_list_item, container, false);

        }


}
