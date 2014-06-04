package com.JasonLie.galgje.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    String Woorden[] = {"kraan", "vogel", "stoel"};
    int level;
    int fouten = 0;
    boolean verloren = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand = new Random();
        level = rand.nextInt(3);
        char char0 = Woorden[level].charAt(0);
        char char1 = Woorden[level].charAt(1);
        char char2 = Woorden[level].charAt(2);
        char char3 = Woorden[level].charAt(3);
        char char4 = Woorden[level].charAt(4);
        final TextView letter1 = (TextView) findViewById(R.id.letter1);
        final TextView letter2 = (TextView) findViewById(R.id.letter2);
        final TextView letter3 = (TextView) findViewById(R.id.letter3);
        final TextView letter4 = (TextView) findViewById(R.id.letter4);
        final TextView letter5 = (TextView) findViewById(R.id.letter5);
        letter1.setText(String.valueOf(char0));
        letter2.setText(String.valueOf(char1));
        letter3.setText(String.valueOf(char2));
        letter4.setText(String.valueOf(char3));
        letter5.setText(String.valueOf(char4));

        Button LogIn = (Button) findViewById(R.id.Check);
        LogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText Input = (EditText) findViewById(R.id.Input);
                char char0 = Woorden[level].charAt(0);
                char char1 = Woorden[level].charAt(1);
                char char2 = Woorden[level].charAt(2);
                char char3 = Woorden[level].charAt(3);
                char char4 = Woorden[level].charAt(4);
                if(String.valueOf(char0).equals(Input.getText().toString()) && verloren == false)
                {
                    letter1.setVisibility(View.VISIBLE);
                }
                else if(String.valueOf(char1).equals(Input.getText().toString()) && verloren == false)
                {
                    letter2.setVisibility(View.VISIBLE);
                }
                else if(String.valueOf(char2).equals(Input.getText().toString()) && verloren == false)
                {
                    letter3.setVisibility(View.VISIBLE);
                }
                else if(String.valueOf(char3).equals(Input.getText().toString()) && verloren == false)
                {
                    letter4.setVisibility(View.VISIBLE);
                }
                else if(String.valueOf(char4).equals(Input.getText().toString()) && verloren == false)
                {
                    letter5.setVisibility(View.VISIBLE);
                }
                else
                {
                    fouten++;
                }
                ImageView img= (ImageView) findViewById(R.id.imageView);

                switch (fouten) {
                    case 1:  img.setImageResource(R.drawable.hang1);
                        break;
                    case 2:  img.setImageResource(R.drawable.hang2);
                        break;
                    case 3:  img.setImageResource(R.drawable.hang3);
                        break;
                    case 4:  img.setImageResource(R.drawable.hang4);
                        break;
                    case 5: img.setImageResource(R.drawable.hang5);
                        break;
                    case 6:  img.setImageResource(R.drawable.hang6);
                        break;
                    case 7:  img.setImageResource(R.drawable.hang7);
                        break;
                    case 8:  img.setImageResource(R.drawable.hang8);
                        break;
                    case 9:  img.setImageResource(R.drawable.hang9);
                        break;
                    case 10: img.setImageResource(R.drawable.hang10);
                        Toast.makeText(getApplicationContext(), "Verloren!",
                                Toast.LENGTH_SHORT).show();
                        verloren = true;
                        break;
                }

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

}
