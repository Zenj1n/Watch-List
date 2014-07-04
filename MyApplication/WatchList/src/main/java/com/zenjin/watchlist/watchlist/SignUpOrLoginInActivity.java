package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SignUpOrLoginInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_or_login);

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignUpOrLoginInActivity.this, RegistreerActivity.class));
            }
        });
    }

    public void onClickLogin(View v) {
        startActivity(new Intent(SignUpOrLoginInActivity.this, LoginActivity.class));
    }

}
