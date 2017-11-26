package com.codebrain.minato.tragua;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codebrain.minato.tragua.WebService.UserData;

import java.io.IOException;

/**
 * Created by David on 25/11/2017.
 */

public class LoginActivity extends NavigationDrawerBaseActivity{
    private EditText username, password;
    private TextView singUp;
    private AppCompatButton btLogin;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);
        singUp = (TextView)findViewById(R.id.login_no_account);
        btLogin = (AppCompatButton)findViewById(R.id.login_button);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLogin()
    {
        if (!validate())
        {
            onLoginFailed();
            return;
        }

        CheckLogin checkLogin = new CheckLogin();
        checkLogin.execute(this.username.getText().toString(), this.password.getText().toString());
    }

    private boolean validate()
    {
        boolean valid = true;

        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        if (username.isEmpty())
        {
            this.username.setError(getResources().getString(R.string.empty_username));
            valid = false;
        }
        else
        {
            this.username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20)
        {
            this.password.setError(getResources().getString(R.string.password_incorrect));
            valid = false;
        }
        else
        {
            this.password.setError(null);
        }

        return valid;
    }

    private void onLoginFailed()
    {

    }

    protected class CheckLogin extends AsyncTask<String, String, String>
    {
        private boolean networkAccess = false;
        private boolean loged = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = getResources().getString(R.string.authenticating) + "...";
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            try
            {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                {
                    networkAccess = true;
                    if (UserData.checkLogin(args[0], args[1]))
                    {
                        loged = true;
                        return "Loged";
                    }
                    return "Failed To Login";
                }
            }
            catch (Exception e)
            {
                Log.d("Exception: ", e.getMessage());
                return "Failed to login";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (!networkAccess)
            {
                //no network access
                Toast.makeText(getApplicationContext(), "No network access", Toast.LENGTH_LONG).show();
            }
            else if (!loged)
            {
                Toast.makeText(getApplicationContext(), "Failde login", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Loged", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = getSharedPreferences("MyPref", Context.MODE_PRIVATE).edit();
                editor.putBoolean("logged", true);
                editor.commit();
            }
        }
    }
}
