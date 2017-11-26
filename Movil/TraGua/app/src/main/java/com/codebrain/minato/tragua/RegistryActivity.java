package com.codebrain.minato.tragua;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codebrain.minato.tragua.WebService.UserData;

/**
 * Created by David on 25/11/2017.
 */

public class RegistryActivity extends AppCompatActivity {

    private EditText username, firstname, lastname, email, password, password_2;
    private AppCompatButton btRegister;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        //inicialize
        username = (EditText)findViewById(R.id.register_username);
        firstname = (EditText)findViewById(R.id.register_firstname);
        lastname = (EditText)findViewById(R.id.register_lastname);
        email = (EditText)findViewById(R.id.register_email);
        password = (EditText)findViewById(R.id.register_password);
        password_2 = (EditText) findViewById(R.id.register_password_again);

        btRegister = (AppCompatButton)findViewById(R.id.register_signup_button);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewUser();
            }
        });
    }

    private void insertNewUser()
    {
        if (!validate())
        {
            onRegisterFailed();
            return;
        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute(this.username.getText().toString(),
                this.password.getText().toString(),
                this.firstname.getText().toString(),
                this.lastname.getText().toString(),
                this.email.getText().toString());
    }

    private boolean validate()
    {
        boolean valid = true;

        if (this.username.getText().toString().isEmpty())
        {
            this.username.setError("user needed");
            valid = false;
        }
        else if (this.firstname.getText().toString().isEmpty())
        {
            this.firstname.setError("user needed");
            valid = false;
        }
        else if (this.lastname.getText().toString().isEmpty())
        {
            this.lastname.setError("user needed");
            valid = false;
        }
        else if (this.email.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this.email.getText().toString()).matches())
        {
            this.email.setError("user needed");
            valid = false;
        }
        else if (this.password.getText().toString().isEmpty())
        {
            this.password.setError("user needed");
            valid = false;
        }
        else if (this.password_2.getText().toString().isEmpty())
        {
            this.password_2.setError("eeoe");
            valid = false;
        }
        else if ((this.password.getText().toString().compareTo(this.password_2.getText().toString())) != 0)
        {
            this.password.setError("Not the same password");
            valid = false;
        }

        return valid;
    }

    private void onRegisterFailed()
    {

    }

    protected class RegisterUser extends AsyncTask<String, String, String>
    {
        private boolean networkAccess = false;
        private boolean registerSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = getResources().getString(R.string.authenticating) + "...";
            progressDialog = new ProgressDialog(RegistryActivity.this);
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
                    if (UserData.insertNewUser(args[0], args[1], args[2], args[3], args[4]))
                    {
                        registerSuccess = true;
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
            if (!registerSuccess)
            {
                if (UserData.codeResult != -1)
                {
                    switch (UserData.codeResult)
                    {
                        case 1:
                            //usuario ya existe
                            Toast.makeText(getApplicationContext(), "Usuario existe", Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            //error en el servidor intentelo mas tarde
                            Toast.makeText(getApplicationContext(), "Error....Intentelo mas tarde", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Success register", Toast.LENGTH_LONG).show();
            }
        }
    }

}
