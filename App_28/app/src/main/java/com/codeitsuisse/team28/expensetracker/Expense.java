package com.codeitsuisse.team28.expensetracker;

/**
 * Created by sarup on 11-09-2015.
 */
public class Expense {
    int day,month,year,type,id;
    String category,comment;
    float amount;
    Expense(int d,int mon,int y ,float amt,int ty,String cat,String com,int id){
        day=d;
        month=mon;
        year=y;
        type=ty;
        this.id=id;
        category=cat;
        comment=com;
        amount=amt;
    }
    int getDay(){
        return day;
    }
    int getYear(){
        return year;
    }
    int getMonth(){
        return month;
    }
    int getType(){
        return type;
    }
    int getId(){
        return id;
    }
    String getCategory(){
        return category;
    }
    String getComment(){
        return comment;
    }
    float getAmount(){
        return amount;
    }
}
