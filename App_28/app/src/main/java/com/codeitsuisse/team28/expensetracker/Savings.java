package com.codeitsuisse.team28.expensetracker;

/**
 * Created by sarup on 11-09-2015.
 */
public class Savings {
    int month, year;
    float amount;
    Savings(int m,int y,float amt){
        month=m;
        year=y;
        amount=amt;
    }
    int getMonth(){
        return month;
    }
    int getYear(){
        return year;
    }
    float getAmount(){
        return amount;
    }
}
