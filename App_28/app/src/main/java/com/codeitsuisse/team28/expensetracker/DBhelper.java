package com.codeitsuisse.team28.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sarup on 11-09-2015.
 */
public class DBhelper extends SQLiteOpenHelper {

    final static String dbname="tracket";
    final static String budget="budget",expense="expense",savings="savings",month="month",year="year",amount="amount";
    static int count=0;
    int lengthexpense=0;
    public DBhelper(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String buildbudget="create table "+budget+"("+month+" integer,"+year+" integer,"+amount+" real)";
        String buildsaving="create table "+savings+"("+month+" integer,"+year+" integer,"+amount+" real)";
        String buildexpense="create table "+expense+"(day integer,"+"month integer,"+year+" integer,"+amount+" real,type integer,category text,comment text,id int )";
        db.execSQL(buildbudget);
        db.execSQL(buildexpense);
        db.execSQL(buildsaving);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertbudget(int mon,int yr,float amt){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(month,mon);
        values.put(year,yr);
        values.put(amount,amt);
        db.insert(budget, null, values);
        db.close();
    }
    public float sumAllExpense(int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select sum(amount) from "+expense+" where month="+mon+" and year="+yr;
        Cursor cr=db.rawQuery(query,null);
        float ans;
        ans = 0;
        if (cr.moveToFirst()) {
            ans= cr.getFloat(0);
        }
        return ans;
    }
    public float sumPaid(int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select sum(amount) from "+expense+" where month="+mon+" and year="+yr+" and type=1";
        Cursor cr=db.rawQuery(query,null);
        float ans;
        ans = 0;
        if (cr.moveToFirst()) {
            ans= cr.getFloat(0);
        }
        return ans;
    }
    public float sumPayLater(int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select sum(amount) from "+expense+" where month="+mon+" and year="+yr+" and type=0";
        Cursor cr=db.rawQuery(query,null);
        float ans;
        ans = 0;
        if (cr.moveToFirst()) {
            ans= cr.getFloat(0);
        }
        return ans;
    }
    public Expense[] getAllPaid(int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+expense+" where month="+mon+" and year="+year+" and type=1";
        Cursor cr=db.rawQuery(query, null);
        //db.close();
        Expense arr[]=new Expense[100];
        int i=0;
        if (cr.moveToFirst()) {
            do {
                arr[i++] = new Expense(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getFloat(3), cr.getInt(4), cr.getString(5), cr.getString(6), cr.getInt(7));
            }while(cr.moveToNext());
        }

        lengthexpense=i;
        return arr;
    }
    public Expense[] getAllPayLater(int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+expense+" where month="+mon+" and year="+year+" and type=0";
        Cursor cr=db.rawQuery(query, null);
        //db.close();
        Expense arr[]=new Expense[100];
        int i=0;
        if (cr.moveToFirst()) {
            do {
                arr[i++] = new Expense(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getFloat(3), cr.getInt(4), cr.getString(5), cr.getString(6), cr.getInt(7));
            }while(cr.moveToNext());
        }

        lengthexpense=i;
        return arr;
    }
    public Expense[] getAllCat(int mon,int yr,String cat){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+expense+" where month="+mon+" and year="+year+" and category='"+cat+"'";
        Cursor cr=db.rawQuery(query, null);
        //db.close();
        Expense arr[]=new Expense[100];
        int i=0;
        if (cr.moveToFirst()) {
            do {
                arr[i++] = new Expense(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getFloat(3), cr.getInt(4), cr.getString(5), cr.getString(6), cr.getInt(7));
            }while(cr.moveToNext());
        }
        return arr;
    }
    public Expense[] getAllDate(int day,int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+expense+" where day="+day +" and month=" +mon+" and year="+yr;
        Cursor cr=db.rawQuery(query, null);
        //db.close();
        Expense arr[]=new Expense[100];
        int i=0;
        if (cr.moveToFirst()) {
            do {
                arr[i++] = new Expense(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getFloat(3), cr.getInt(4), cr.getString(5), cr.getString(6), cr.getInt(7));
            }while(cr.moveToNext());
        }
        return arr;
    }
    public void insertsavings(int mon,int yr,float amt ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(month,mon);
        values.put(year,yr);
        values.put(amount,amt);
        db.insert(savings,null,values);
        db.close();
    }
    public void insertexpense(int day,int mon,int yr,float amt,int ty,String cat,String com ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("day",day);
        values.put(month,mon);
        values.put(year,yr);
        values.put(amount,amt);
        values.put("type",ty);
        values.put("category",cat);
        values.put("comment",com);
        values.put("id",count);
        count++;
        db.insert(expense, null, values);
        db.close();
    }
    public void deleteexpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(expense, "id" + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public Budget getBudget(int mon,int year){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+budget+" where month="+mon+" and year="+year;
        Cursor cr=db.rawQuery(query, null);
        if (cr.moveToFirst()) {
            return new Budget(mon,year,cr.getFloat(2));
        }
        else return null;
    }

    public Budget getLastBudget(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+budget;
        Cursor cr=db.rawQuery(query, null);
        Budget bg=null;
        if (cr.moveToFirst()) {
            do {
                bg= new Budget(cr.getInt(0), cr.getInt(1), cr.getFloat(2));
            }while(cr.moveToNext());
        }
        return bg;
    }
    public Savings getLastSavings(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+savings;
        Cursor cr=db.rawQuery(query, null);
        Savings bg=null;
        if (cr.moveToFirst()) {
            do {
                bg= new Savings(cr.getInt(0), cr.getInt(1), cr.getFloat(2));
            }while(cr.moveToNext());
        }
        return bg;
    }

    public double getAvgBudget(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select avg(amount) from "+budget;
        Cursor cr=db.rawQuery(query,null);
        double ans;
        ans = 0;
        if (cr.moveToFirst()) {
            ans= cr.getFloat(0);
        }
        return ans;
    }

    public Savings getSavings(int mon,int year){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+savings+" where month="+mon+" and year="+year;
        Cursor cr=db.rawQuery(query,null);
        //db.close();
        if (cr.moveToFirst()) {
            return new Savings(mon,year,cr.getFloat(2));
        }
        else return null;
    }
    public Expense getExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+expense+" where id="+id;
        Cursor cr=db.rawQuery(query,null);
        //db.close();
        if (cr.moveToFirst()) {
            return new Expense(cr.getInt(0),cr.getInt(1),cr.getInt(2),cr.getFloat(3),cr.getInt(4),cr.getString(5),cr.getString(6),id);
        }
        else return null;
    }
    public int getLength(){
        return lengthexpense;
    }
    public Expense[] getAllExpense(int mon,int year){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select * from "+expense+" where month="+mon+" and year="+year;
        Cursor cr=db.rawQuery(query, null);
        //db.close();
        Expense arr[]=new Expense[100];
        int i=0;
        if (cr.moveToFirst()) {
            do {
                arr[i++] = new Expense(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getFloat(3), cr.getInt(4), cr.getString(5), cr.getString(6), cr.getInt(7));
            }while(cr.moveToNext());
        }
        lengthexpense=i;
        return arr;
    }
    public int updateBudget(int mon, int year, float amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(amount, amt);

        // updating row
        return db.update(budget, values, month + " = ? and year =?",
                new String[] { String.valueOf(mon),String.valueOf(year) });
    }
    public int updateSavings(int mon, int year, float amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(amount, amt);

        // updating row
        return db.update(savings, values, month + " = ? and year =?",
                new String[] { String.valueOf(mon),String.valueOf(year) });
    }
    public void updateExpense(Expense exp){
        int key=exp.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="update "+expense+"set day="+exp.getDay()+",month="+exp.getMonth()+",year="+exp.getYear()+",amount="+exp.getAmount()+",type="+exp.getType()+",category='"+exp.getCategory()+"',comment='"+exp.getComment()+"' where id="+key;
        db.rawQuery(query,null);
    }

    public void clearExpense(int mon,int yr){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(expense, "month = ? and year =?",
                new String[] { String.valueOf(mon),String.valueOf(yr) });
        db.close();

    }

    public float sumAllCat(int mon,int yr,String cat){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select(amount) from "+expense+" where month="+mon+" and year="+year+" and category='"+cat+"'";
        Cursor cr=db.rawQuery(query, null);
        //db.close();
        float ans=0;
        int i=0;
        if (cr.moveToFirst()) {
            ans=cr.getFloat(0);
        }
        return ans;
    }
}
