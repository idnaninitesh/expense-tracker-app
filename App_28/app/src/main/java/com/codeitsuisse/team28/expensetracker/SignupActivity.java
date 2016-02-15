package com.codeitsuisse.team28.expensetracker;

/**
 * Created by sarup on 05-09-2015.
 */
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeitsuisse.team28.expensetracker.R;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    SharedPreferences.Editor editor;
    SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings=getSharedPreferences("preference",0);
                editor=settings.edit();
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent inte=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(inte);
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        class LoginAsync extends AsyncTask<String, Void, String> {

            int succ=1;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[2];
                String pass = params[1];
                String email=params[0];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("emailid", email));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                nameValuePairs.add(new BasicNameValuePair("name", name));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://sarupvjti.net46.net/signup.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                    String line=null;
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("pass 2", "connection success ="+result);
                } catch (ClientProtocolException e) {
                    succ=0;
                } catch (UnsupportedEncodingException e) {
                    succ=0;
                } catch (IOException e) {
                    succ=0;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                int code=0;
                if(succ==0) {
                    _signupButton.setEnabled(true);
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Check internet connection", Toast.LENGTH_LONG).show();
                    //Builder(LoginActivity.this);
                    return;
                }
                try {
                    JSONObject json_data = new JSONObject(result);
                    code = (json_data.getInt("code"));
                }catch(Exception ex){

                }
                if(code==1){
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    onSignupSuccess();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }else {
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    onSignupFailed();
                                }
                            }, 3000);
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(email, password,name);

    }



    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        editor.putString("id", _emailText.getText().toString());
        editor.putString("name", _nameText.getText().toString());
        editor.commit();
        Intent inte=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(inte);
        finish();
    }

    public void onSignupFailed() {
        //Toast.makeText(getBaseContext(), "Email_id already exist", Toast.LENGTH_LONG).show();
        AlertDialog.Builder abd=new AlertDialog.Builder(this);
        abd.setMessage("Email id already exist").setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        abd.show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}