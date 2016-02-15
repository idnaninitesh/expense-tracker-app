package com.codeitsuisse.team28.expensetracker;

import com.parse.Parse;

/**
 * Created by sarup on 09-09-2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this,
                "4F8HyYlfxLDJUraer1NeKL9nVk8F00LwRYUD7cH4",
                "6BwlPSdQ5GqJgFXbILPCrUM7U40Mh5TrrKxRs1rn");
    }
}
