package com.codeitsuisse.team28.expensetracker.drawermenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codeitsuisse.team28.expensetracker.BudgetFragment;
import com.codeitsuisse.team28.expensetracker.ExpenseFragment;
import com.codeitsuisse.team28.expensetracker.HomePageFragment;
import com.codeitsuisse.team28.expensetracker.SavingFragment;
import com.codeitsuisse.team28.expensetracker.SplashActivity;
import com.codeitsuisse.team28.expensetracker.LoginActivity;
import com.codeitsuisse.team28.expensetracker.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    public static FragmentManager fm;

    public static Activity a;
    public static String getMonth(int mon){
        String[] ans={"January","February","March","April","May","June","July","August","September","October","November","December"};
        return ans[mon-1];
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        SharedPreferences.Editor editor;
        final SharedPreferences settings;
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        settings= SplashActivity.a.getSharedPreferences("preference",0);
        editor=settings.edit();
        String user_email=settings.getString("id","null");
        String user_name=settings.getString("name","null");
        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        String fbid=settings.getString("fbid","null");
        Boolean custlog=settings.getBoolean("Cutomlog",false);
        if(!fbid.equals("null") ){
            if(custlog)
            {
                mNavigationDrawerFragment.setUserData(user_name, user_email, BitmapFactory.decodeResource(getResources(), R.drawable.images_2));
            }
            else {
                class task extends AsyncTask<String, Void, String> {
                    String user_email;
                    String user_name;
                    String fb_id;
                    Bitmap bitmap;
                    int succ = 1;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        SharedPreferences.Editor editor;
                        SharedPreferences settings;
                        settings = SplashActivity.a.getSharedPreferences("preference", 0);
                        editor = settings.edit();
                        user_email = settings.getString("id", "null");
                        user_name = settings.getString("name", "null");
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        try {
                            mNavigationDrawerFragment.setUserData(user_name, user_email, bitmap);
                        }catch(Exception ex){
                            mNavigationDrawerFragment.setUserData(user_name, user_email, BitmapFactory.decodeResource(getResources(), R.drawable.images_2));
                        }
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        try {

                            URL imageURL = new URL("https://graph.facebook.com/" + settings.getString("fbid", "null") + "/picture?type=large"); //new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
                            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                            connection.setDoInput(true);
                            connection.setInstanceFollowRedirects(true);
                            connection.connect();
                            InputStream inputStream = connection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(inputStream);
                        } catch (MalformedURLException ex) {
                            succ = 0;
                        } catch (IOException e) {
                            succ = 0;
                        }
                        return null;
                    }
                }
                task tk = new task();
                tk.execute();
            }
        }else {
            mNavigationDrawerFragment.setUserData(user_name, user_email, BitmapFactory.decodeResource(getResources(), R.drawable.images_2));
        }

        a=this;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putInt("position", position);
        switch (position)
        {
            case 0 :    //Toast.makeText(this, "Menu item selected -> 0", Toast.LENGTH_SHORT).show();
                fragment = new HomePageFragment();
                break;
            case 1 :    //Toast.makeText(this, "Menu item selected -> 1", Toast.LENGTH_SHORT).show();
                fragment = new BudgetFragment();
                break;
            case 2 :    fragment = new ExpenseFragment();
                //Toast.makeText(this, "Menu item selected -> 2", Toast.LENGTH_SHORT).show();
                break;
            case 3 :    fragment = new SavingFragment();
                break;
            /*case 3 : break;
            case 4 : break;
            case 5 : break;
            case 6 : break;
            */
            default:    //Toast.makeText(this, "Menu item selected -> default", Toast.LENGTH_SHORT).show();
                break;
        }
        fragment.setArguments(args);
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, fragment,"MY_FRAGMENT"+position).addToBackStack(null)
                .commit();

    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            class tsk extends AsyncTask<String,Void,String>{
                ProgressDialog progressDialog ;
                tsk() {
                    progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Log Out ...");
                }
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Intent inte=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(inte);
                    progressDialog.dismiss();
                    finish();
                }

                @Override
                protected String doInBackground(String... params) {
                    SharedPreferences.Editor editor;
                    SharedPreferences settings;
                    settings=SplashActivity.a.getSharedPreferences("preference",0);
                    editor=settings.edit();
                    editor.remove("logged");
                    editor.remove("facebook");
                    editor.commit();
                    try{
                        Thread.sleep(1000);
                    }
                    catch(Exception ex){

                    }
                    return null;
                }
            }
            tsk th=new tsk();
            th.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
