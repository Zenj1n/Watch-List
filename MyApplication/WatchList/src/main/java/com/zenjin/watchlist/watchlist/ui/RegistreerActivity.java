package com.zenjin.watchlist.watchlist.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.zenjin.watchlist.watchlist.R;


public class RegistreerActivity extends Activity {
    private EditText usernameregister;
    private EditText passwordregister;
    private EditText passwordAgainView;
    private Button registreer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(this, getString(R.string.app_key), getString(R.string.client_key));

        usernameregister = (EditText) findViewById(R.id.UsernameRegister);
        passwordregister = (EditText) findViewById(R.id.PasswordRegister);
        passwordAgainView = (EditText) findViewById(R.id.PasswordRegisterAgain);

        findViewById(R.id.RegistreerButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(usernameregister)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
                }
                if (isEmpty(passwordregister)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
                }
                if (!isMatching(passwordregister, passwordAgainView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(
                            R.string.error_mismatched_passwords));
                }
                validationErrorMessage.append(getResources().getString(R.string.error_end));
                if (validationError) {
                    Toast.makeText(RegistreerActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                final ProgressDialog dlg = new ProgressDialog(RegistreerActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();

                ParseUser user = new ParseUser();
                user.setUsername(usernameregister.getText().toString().toLowerCase());
                user.setPassword(passwordregister.getText().toString());

                user.signUpInBackground(new SignUpCallback() {


                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            Toast.makeText(RegistreerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(RegistreerActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }

            private boolean isEmpty(EditText etText) {
                return etText.getText().toString().trim().length() <= 0;
            }

            private boolean isMatching(EditText etText1, EditText etText2) {
                return etText1.getText().toString().equals(etText2.getText().toString());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
