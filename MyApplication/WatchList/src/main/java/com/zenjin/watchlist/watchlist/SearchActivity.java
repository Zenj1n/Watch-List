package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.parse.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity {

    private EditText searchET;
    private EditText userET;
    private Intent intent;
    private static final String TAG_TITLE = "title";
    private List<String> searchResults = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        userET = (EditText) findViewById(R.id.searchUserET);
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
            public void performSearch(){
                new JSONParse().execute();
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
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    public class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }



        @Override
        protected JSONArray doInBackground(String... args) {


            String word2 = searchET.getText().toString();
            String searchWord = word2.replaceAll(" ", "+");

            String urlSearch = "http://api.trakt.tv/search/shows.json/2c0bdfbdb92cb55e844c997757180341?query="+ searchWord;
            ServiceHandler jParser = new ServiceHandler();

            // Getting JSON from URL
            JSONArray jsonSearch = jParser.getJsonArray(urlSearch);
            return jsonSearch;

        }

        @Override
        protected void onPostExecute(JSONArray jsonSearch) {
            pDialog.dismiss();
            try {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .build();
                // Storing  JSON item in a Variable

                if(jsonSearch != null){
                    for(int i=0;i<jsonSearch.length();i++){
                        JSONObject s;
                        s = jsonSearch.getJSONObject(i);
                        String test1 = s.getString(TAG_TITLE);
                        String title = test1.replace("[", "");
                        searchResults.add(title);
                    }
                    System.out.println(searchResults);
                }
                else{
                    Toast.makeText(getApplicationContext(), "No Information Available", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
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
