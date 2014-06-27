package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SignUpOrLoginInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_or_login);

       /* // Log in button click handler
        ((Button) findViewById(R.id.logInButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(SignUpOrLoginInActivity.this, LoginActivity.class));
            }
        });*/

        // Sign up button click handler
        ((Button) findViewById(R.id.signUpButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(SignUpOrLoginInActivity.this, RegistreerActivity.class));
            }
        });


    }
    public void onClickLogin(View v){
        startActivity(new Intent(SignUpOrLoginInActivity.this, LoginActivity.class));
    }

}
