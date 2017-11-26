package com.codebrain.minato.tragua;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by David on 25/11/2017.
 */

public class LoginActivity extends NavigationDrawerBaseActivity{
    private EditText username, password;
    private AppCompatButton btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);
        btLogin = (AppCompatButton)findViewById(R.id.login_button);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
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
}
