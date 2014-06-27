package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;


public class SearchActivity extends Activity {

    private EditText searchET;
    private EditText userET;
    public final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        searchET = (EditText) findViewById(R.id.SearchET);
        userET = (EditText) findViewById(R.id.searchUserET);

        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performShowSearch();
                    return true;
                }
                return false;
            }

            public void performShowSearch() {


                String word2 = searchET.getText().toString();




                String traktWord = word2.replaceAll(" ","-");
                intent = new Intent(SearchActivity.this,InfoPage.class);
                intent.putExtra("trakt", traktWord);

                startActivity(intent);
            }
        });

       userET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
               if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   performUserSearch();
                   return true;
               }
               return false;
           }
           public void performUserSearch() {
               Toast.makeText(getApplicationContext(), "Feature not available yet", Toast.LENGTH_SHORT).show();
               InputMethodManager imm = (InputMethodManager)getSystemService(
                       Context.INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(userET.getWindowToken(), 0);


           }
       });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
