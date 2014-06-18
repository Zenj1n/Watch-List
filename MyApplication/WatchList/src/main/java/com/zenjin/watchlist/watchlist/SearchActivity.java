package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class SearchActivity extends Activity {

    private EditText searchET;
    public final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchET = (EditText) findViewById(R.id.SearchET);

        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }

            public void performSearch() {
                String word = java.net.URLEncoder.encode(searchET.getText().toString());
                String word2 = searchET.getText().toString();

                String traktWord = word2.replaceAll(" ","-");
                intent = new Intent(SearchActivity.this,InfoPage.class);
                intent.putExtra(EXTRA_MESSAGE, word);
                intent.putExtra("trakt", traktWord);


                intent = new Intent(SearchActivity.this,InfoPage.class);
                intent.putExtra(EXTRA_MESSAGE, word);
                intent.putExtra("trakt", traktWord);

                startActivity(intent);
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
