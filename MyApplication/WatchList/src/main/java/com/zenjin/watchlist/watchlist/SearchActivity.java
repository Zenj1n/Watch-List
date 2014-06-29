package com.zenjin.watchlist.watchlist;

import android.app.ActionBar;
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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.parse.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends Activity {

    private static final String TAG_TITLE = "title";
    private EditText searchET;
    private EditText userET;
    private Intent intent;
    private List<String> searchResults = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        handleIntent(getIntent());

        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        //userET = (EditText) findViewById(R.id.searchUserET);
        //searchET = (EditText) findViewById(R.id.SearchET);


        /*searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }

            public void performSearch() {
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
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userET.getWindowToken(), 0);
            }
        });
        */

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            String query1 = query.replace(" ", "+");
            new JSONParse().execute(query1);
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
        protected JSONArray doInBackground(String... query) {


            //String word2 = searchET.getText().toString();
            //String searchWord = word2.replaceAll(" ", "+");
            String query1 = Arrays.toString(query);
            String querySearch = query1.replace("[", "");
            String urlSearch = "http://api.trakt.tv/search/shows.json/2c0bdfbdb92cb55e844c997757180341?query=" + querySearch;
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

                if (jsonSearch != null) {
                    for (int i = 0; i < jsonSearch.length(); i++) {
                        JSONObject s;
                        s = jsonSearch.getJSONObject(i);
                        String test1 = s.getString(TAG_TITLE);
                        String title = test1.replace("[", "");
                        searchResults.add(title);
                    }

                    showResults();

                } else {
                    Toast.makeText(getApplicationContext(), "No Information Available", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public void showResults(){

        final ListView listview = (ListView) findViewById(R.id.listview);

        String[] values = searchResults.toArray(new String[searchResults.size()]);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                String search = item.replace(" ","-");

                intent = new Intent(SearchActivity.this, InfoPage.class);
                intent.putExtra("trakt", search);
                startActivity(intent);

            }

        });

    }

}
