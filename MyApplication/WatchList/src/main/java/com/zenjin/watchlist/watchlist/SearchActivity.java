package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends Activity {

    protected ListView list;
    protected TextView showTitle;
    protected TextView overview;
    protected ImageView poster;
    protected EditText searchShowET;
    ArrayList<HashMap<String, String>> searchlist = new ArrayList<HashMap<String, String>>();
    protected ImageLoader imageLoader = ImageLoader.getInstance();



    private static final String TAG_TITLE = "title";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_POSTER = "poster";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
        searchlist = new ArrayList<HashMap<String, String>>();

        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        searchShowET = (EditText) findViewById(R.id.searchShowET);
        searchShowET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchlist.clear();
                    new JSONParse().execute();
                }

                return true;
            }
        });
    }
    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showTitle = (TextView) findViewById(R.id.showTitle);
            overview = (TextView) findViewById(R.id.overview);
            //poster = (Imageview)findViewById(R.id.poster);
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        protected JSONArray doInBackground(String... args) {
            ServiceHandler jParser = new ServiceHandler();
            String searchword = searchShowET.getText().toString();
            String searchword2 = searchword.replaceAll(" ","+");
            String url = "http://api.trakt.tv/search/shows/390983740f2092270bc0fa267334db88/20140627/"+searchword2;
            JSONArray jsonSearch = jParser.getJsonArray(url);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonSearch);

            return jsonArray;
        }

        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            try {
                JSONArray jsonSearch = jsonArray.getJSONArray(0);
                for (int i = 0; i < jsonSearch.length(); i++) {
                    JSONObject e;
                    e = jsonSearch.getJSONObject(i);
                    String name = e.getString("title");
                    String overview = e.getString("overview");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_TITLE, name);
                    map.put(TAG_OVERVIEW, overview);
                    searchlist.add(map);
                    list = (ListView) findViewById(R.id.list);
                    ListAdapter adapter = new SimpleAdapter(SearchActivity.this, searchlist,
                            R.layout.search_listview,
                            new String[]{TAG_TITLE, TAG_OVERVIEW}, new int[]{
                            R.id.showTitle, R.id.overview}
                    );
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent intent;
                            intent = new Intent(SearchActivity.this, InfoPage.class);
                            String word2 = searchlist.get(+position).get("title");
                            String traktWord = word2.replaceAll(" ", "-");
                            intent.putExtra("trakt", traktWord);

                            startActivity(intent);
                        }
                    });

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
