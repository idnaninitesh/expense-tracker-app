package com.codeitsuisse.team28.expensetracker.drawermenu;

import android.content.Context;

/**
 * Created by idnaninitesh on 08/09/15.
 */

public class Place {

    public String id;
    public String name;
    public String imageName;
    public boolean isFav;

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }
}
