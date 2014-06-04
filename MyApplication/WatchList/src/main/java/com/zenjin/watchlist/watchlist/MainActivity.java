package com.zenjin.watchlist.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    public String username = "test";
    public String password = "test123";
    public static String LogInfile = "LogInData";
    private SharedPreferences LogInData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogInData = getSharedPreferences(LogInfile, 0);
        String LastUser = LogInData.getString("username", "");
        String LastPassword = LogInData.getString("password", "");
        EditText usernameInput = (EditText) findViewById(R.id.Username);
        EditText passwordInput = (EditText) findViewById(R.id.Password);
        usernameInput.setText(LastUser);
        passwordInput.setText(LastPassword);
        Button LogIn = (Button) findViewById(R.id.LogIn);
        LogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText usernameInput = (EditText) findViewById(R.id.Username);
                EditText passwordInput = (EditText) findViewById(R.id.Password);
                if (username.equals(usernameInput.getText().toString()) && password.equals(passwordInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Correct",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, Hm_MainActivity.class);
                    startActivity(intent);


                    CheckBox CBRemember = (CheckBox) findViewById(R.id.Remember);
                    if (CBRemember.isChecked()) {
                        SharedPreferences.Editor editor = LogInData.edit();
                        editor.putString("username", usernameInput.getText().toString());
                        editor.putString("password", passwordInput.getText().toString());
                        editor.commit();
                    } else {
                        SharedPreferences.Editor editor = LogInData.edit();
                        editor.putString("username", "");
                        editor.putString("password", "");
                        editor.commit();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password!",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        TextView Recover = (TextView) findViewById(R.id.Forgot);
        Recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Recovery = new Intent(MainActivity.this, RecoverPasswordActivity.class);
                startActivity(Recovery);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //is dit een branch?

}
