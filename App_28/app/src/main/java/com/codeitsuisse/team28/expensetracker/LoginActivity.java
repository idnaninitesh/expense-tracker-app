package com.codeitsuisse.team28.expensetracker;

/**
 * Created by sarup on 05-09-2015.
 */
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.*;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeitsuisse.team28.expensetracker.R;
import com.codeitsuisse.team28.expensetracker.drawermenu.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public String pic=null;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    SharedPreferences.Editor editor;
    SharedPreferences settings;
    CallbackManager callbackManager;
    String name=null;
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        try {
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }
        catch(Exception ex){
            Toast.makeText(getBaseContext(), "Check internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        settings=SplashActivity.a.getSharedPreferences("preference", 0);
        editor=settings.edit();
        Boolean fb=settings.getBoolean("facebook",false);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.authButton);
        List<String> permissionNeeds = Arrays.asList("user_photos", "email", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);
        if(fb==false){
            LoginManager.getInstance().logOut();
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                GraphRequest request = GraphRequest.newMeRequest
                        (loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            String id,email,name,gender,birthday;
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Application code
                                Log.v("LoginActivity", response.toString());
                                //System.out.println("Check: " + response.toString());
                                try {

                                    id = object.getString("id");
                                    name = object.getString("name");
                                    email = object.getString("email");
                                    Log.d("login succ", id + ", " + name + ", " + email );
                                    editor.clear();
                                    editor.commit();
                                    editor.putString("id", email);
                                    editor.putString("name", name);
                                    editor.putString("fbid", id);
                                    editor.putBoolean("facebook", true);
                                    editor.putBoolean("logged", true);
                                    editor.putBoolean("Cutomlog", false);
                                    editor.commit();
                                    Intent inte = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(inte);
                                    finish();
                                } catch (JSONException e) {
                                    Log.e("catch", "Json exception");
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.e("cancel", "onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        // TODO: Implement your own authentication logic here.
        class LoginAsync extends AsyncTask<String, Void, String> {

            int succ=1;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://sarupvjti.net46.net/login.php");
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
                if(succ==0){
                    _loginButton.setEnabled(true);
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Check internet connection", Toast.LENGTH_LONG).show();
                    //Builder(LoginActivity.this);
                    return;
                }
                try {
                    JSONObject json_data = new JSONObject(result);
                    code = (json_data.getInt("code"));

                    if (code == 1) {
                        name = (json_data.getString("name"));
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        onLoginSuccess();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                    } else {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        onLoginFailed();

                                    }
                                }, 3000);
                    }
                }catch(Exception ex){

                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(email, password);

    }




    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        editor.putString("id", _emailText.getText().toString());
        editor.putString("name", name);
        editor.putBoolean("facebook", false);
        editor.putBoolean("logged",true);
        editor.putBoolean("Cutomlog",true);
        editor.commit();
        Intent inte=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(inte);
        finish();
    }

    public void onLoginFailed() {
       // Toast.makeText(getBaseContext(), "Invalid email_id or password", Toast.LENGTH_LONG).show();
        AlertDialog.Builder abd=new AlertDialog.Builder(LoginActivity.this);
        abd.setMessage("Incorrect email or password").setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        abd.show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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
