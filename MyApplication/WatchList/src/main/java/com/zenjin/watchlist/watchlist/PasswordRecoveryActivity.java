package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;


public class PasswordRecoveryActivity extends Activity {

    private EditText email;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password_recovery);

        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        email = (EditText) findViewById(R.id.Email);
        button = (Button) findViewById(R.id.Recover);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ParseUser.requestPasswordResetInBackground(email.getText().toString().toLowerCase(),
                        new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(PasswordRecoveryActivity.this, "An e-mail has been send to " + email.getText().toString(), Toast.LENGTH_LONG)
                                            .show();
                                    finish();
                                } else {
                                    Toast.makeText(PasswordRecoveryActivity.this, "Not a registered e-mail", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        }
                );


            }
        });


    }

}
