package com.codeitsuisse.team28.expensetracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.codeitsuisse.team28.expensetracker.R;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.parse.ParseInstallation;

/**
 * Created by sarup on 08-09-2015.
 */
public class SplashActivity extends Activity {
    public static Activity a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        a=this;
        ParseInstallation.getCurrentInstallation().saveInBackground();

        SharedPreferences settings=this.getSharedPreferences("preference", 0);
        final SharedPreferences.Editor editor=settings.edit();
        Boolean user;
        user=settings.getBoolean("logged",false);
        class th extends Thread{
            Boolean b;
            th(Boolean str){b=str;}
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    if(b==false){
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
        th timerThread=new th(user);
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
