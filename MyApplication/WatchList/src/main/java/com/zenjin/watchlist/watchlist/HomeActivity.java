package com.zenjin.watchlist.watchlist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeActivity extends MyWatchList {

    public final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";


    private static final String TAG_IMAGE = "poster";
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    Intent intent;
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Wij hadden te weining tijd om dit te wijzigen naar een loop
            switch (v.getId()) {
                case R.id.todayImage1:
                    String todayTitle1 = todayTitles.get(0).replaceAll(" ", "-");
                    String todayTitle2 = todayTitle1.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle2);
                    startActivity(intent);
                    break;
                case R.id.todayImage2:
                    String todayTitle3 = todayTitles.get(1).replaceAll(" ", "-");
                    String todayTitle4 = todayTitle3.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle4);
                    startActivity(intent);
                    break;
                case R.id.todayImage3:
                    String todayTitle5 = todayTitles.get(2).replaceAll(" ", "-");
                    String todayTitle6 = todayTitle5.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle6);
                    startActivity(intent);
                    break;
                case R.id.todayImage4:
                    String todayTitle7 = todayTitles.get(3).replaceAll(" ", "-");
                    String todayTitle8 = todayTitle7.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle8);
                    startActivity(intent);
                    break;
                case R.id.todayImage5:
                    String todayTitle9 = todayTitles.get(4).replaceAll(" ", "-");
                    String todayTitle10 = todayTitle9.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle10);
                    startActivity(intent);
                    break;
                case R.id.todayImage6:
                    String todayTitle11 = todayTitles.get(5).replaceAll(" ", "-");
                    String todayTitle12 = todayTitle11.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle12);
                    startActivity(intent);
                    break;
                case R.id.todayImage7:
                    String todayTitle13 = todayTitles.get(6).replaceAll(" ", "-");
                    String todayTitle14 = todayTitle13.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle14);
                    startActivity(intent);
                    break;
                case R.id.todayImage8:
                    String todayTitle15 = todayTitles.get(7).replaceAll(" ", "-");
                    String todayTitle16 = todayTitle15.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle16);
                    startActivity(intent);
                    break;
                case R.id.todayImage9:
                    String todayTitle17 = todayTitles.get(8).replaceAll(" ", "-");
                    String todayTitle18 = todayTitle17.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle18);
                    startActivity(intent);
                    break;
                case R.id.todayImage10:
                    String todayTitle19 = todayTitles.get(9).replaceAll(" ", "-");
                    String todayTitle20 = todayTitle19.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", todayTitle20);
                    startActivity(intent);
                    break;
                case R.id.trendingImage1:
                    String trendingTitle1 = trendingTitles.get(0).replaceAll(" ", "-");
                    String trendingTitle2 = trendingTitle1.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle2);
                    startActivity(intent);
                    break;
                case R.id.trendingImage2:
                    String trendingTitle3 = trendingTitles.get(1).replaceAll(" ", "-");
                    String trendingTitle4 = trendingTitle3.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle4);
                    startActivity(intent);
                    break;
                case R.id.trendingImage3:
                    String trendingTitle5 = trendingTitles.get(2).replaceAll(" ", "-");
                    String trendingTitle6 = trendingTitle5.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle6);
                    startActivity(intent);
                    break;
                case R.id.trendingImage4:
                    String trendingTitle7 = trendingTitles.get(3).replaceAll(" ", "-");
                    String trendingTitle8 = trendingTitle7.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle8);
                    startActivity(intent);
                    break;
                case R.id.trendingImage5:
                    String trendingTitle9 = trendingTitles.get(4).replaceAll(" ", "-");
                    String trendingTitle10 = trendingTitle9.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle10);
                    startActivity(intent);
                    break;
                case R.id.trendingImage6:
                    String trendingTitle11 = trendingTitles.get(5).replaceAll(" ", "-");
                    String trendingTitle12 = trendingTitle11.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle12);
                    startActivity(intent);
                    break;
                case R.id.trendingImage7:
                    String trendingTitle13 = trendingTitles.get(6).replaceAll(" ", "-");
                    String trendingTitle14 = trendingTitle13.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle14);
                    startActivity(intent);
                    break;
                case R.id.trendingImage8:
                    String trendingTitle15 = trendingTitles.get(7).replaceAll(" ", "-");
                    String trendingTitle16 = trendingTitle15.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle16);
                    startActivity(intent);
                    break;
                case R.id.trendingImage9:
                    String trendingTitle17 = trendingTitles.get(8).replaceAll(" ", "-");
                    String trendingTitle18 = trendingTitle17.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle18);
                    startActivity(intent);
                    break;
                case R.id.trendingImage10:
                    String trendingTitle19 = trendingTitles.get(9).replaceAll(" ", "-");
                    String trendingTitle20 = trendingTitle19.replaceAll("'", "");
                    intent = new Intent(HomeActivity.this, InfoPage.class);
                    intent.putExtra("trakt", trendingTitle20);
                    startActivity(intent);
                    break;
            }
        }
    };
    private List<String> trendingTitles = new ArrayList<String>();
    private List<String> todayTitles = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.hm_activity);
        new JSONParse().execute();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

        //Veel te laat om in een array te stoppen / te weining tijd
        ImageView todayImage1 = (ImageView) findViewById(R.id.todayImage1);
        ImageView todayImage2 = (ImageView) findViewById(R.id.todayImage2);
        ImageView todayImage3 = (ImageView) findViewById(R.id.todayImage3);
        ImageView todayImage4 = (ImageView) findViewById(R.id.todayImage4);
        ImageView todayImage5 = (ImageView) findViewById(R.id.todayImage5);
        ImageView todayImage6 = (ImageView) findViewById(R.id.todayImage6);
        ImageView todayImage7 = (ImageView) findViewById(R.id.todayImage7);
        ImageView todayImage8 = (ImageView) findViewById(R.id.todayImage8);
        ImageView todayImage9 = (ImageView) findViewById(R.id.todayImage9);
        ImageView todayImage10 = (ImageView) findViewById(R.id.todayImage10);

        ImageView trendingImage1 = (ImageView) findViewById(R.id.trendingImage1);
        ImageView trendingImage2 = (ImageView) findViewById(R.id.trendingImage2);
        ImageView trendingImage3 = (ImageView) findViewById(R.id.trendingImage3);
        ImageView trendingImage4 = (ImageView) findViewById(R.id.trendingImage4);
        ImageView trendingImage5 = (ImageView) findViewById(R.id.trendingImage5);
        ImageView trendingImage6 = (ImageView) findViewById(R.id.trendingImage6);
        ImageView trendingImage7 = (ImageView) findViewById(R.id.trendingImage7);
        ImageView trendingImage8 = (ImageView) findViewById(R.id.trendingImage8);
        ImageView trendingImage9 = (ImageView) findViewById(R.id.trendingImage9);
        ImageView trendingImage10 = (ImageView) findViewById(R.id.trendingImage10);

        todayImage1.setOnClickListener(onClickListener);
        todayImage2.setOnClickListener(onClickListener);
        todayImage3.setOnClickListener(onClickListener);
        todayImage4.setOnClickListener(onClickListener);
        todayImage5.setOnClickListener(onClickListener);
        todayImage6.setOnClickListener(onClickListener);
        todayImage7.setOnClickListener(onClickListener);
        todayImage8.setOnClickListener(onClickListener);
        todayImage9.setOnClickListener(onClickListener);
        todayImage10.setOnClickListener(onClickListener);

        trendingImage1.setOnClickListener(onClickListener);
        trendingImage2.setOnClickListener(onClickListener);
        trendingImage3.setOnClickListener(onClickListener);
        trendingImage4.setOnClickListener(onClickListener);
        trendingImage5.setOnClickListener(onClickListener);
        trendingImage6.setOnClickListener(onClickListener);
        trendingImage7.setOnClickListener(onClickListener);
        trendingImage8.setOnClickListener(onClickListener);
        trendingImage9.setOnClickListener(onClickListener);
        trendingImage10.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
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
        if (id == R.id.action_search) {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            String formattedDate = df.format(c.getTime());
            String urlTraktTrending = "http://api.trakt.tv/shows/trending.json/390983740f2092270bc0fa267334db88/";
            String urlToday = "http://api.trakt.tv/calendar/shows.json/390983740f2092270bc0fa267334db88/" + formattedDate;
            ServiceHandler jParser = new ServiceHandler();


            JSONArray jsonTrakt = jParser.getJsonArray(urlTraktTrending);
            JSONArray jsonTraktToday = jParser.getJsonArray(urlToday);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonTraktToday);
            jsonArray.put(jsonTrakt);

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .build();

            try {
                JSONArray jsonTrakt = jsonArray.getJSONArray(1);
                JSONArray jsonTraktToday = jsonArray.getJSONArray(0);

                for (int i = 0; i < jsonTrakt.length(); i++) {
                    JSONObject e;
                    e = jsonTrakt.getJSONObject(i);
                    String name = e.getString("title");
                    trendingTitles.add(name);
                }
                for (int i = 0; i < 10; i++) {
                    JSONObject e;
                    e = jsonTraktToday.getJSONObject(0);
                    JSONArray shows = e.getJSONArray("episodes");
                    String todayShows = shows.getJSONObject(i).getJSONObject("show").getString("title");
                    todayTitles.add(todayShows);
                }

                TextView[] tvTodayTitles = new TextView[10];
                tvTodayTitles[0] = (TextView) findViewById(R.id.todayTextView1);
                tvTodayTitles[1] = (TextView) findViewById(R.id.todayTextView2);
                tvTodayTitles[2] = (TextView) findViewById(R.id.todayTextView3);
                tvTodayTitles[3] = (TextView) findViewById(R.id.todayTextView4);
                tvTodayTitles[4] = (TextView) findViewById(R.id.todayTextView5);
                tvTodayTitles[5] = (TextView) findViewById(R.id.todayTextView6);
                tvTodayTitles[6] = (TextView) findViewById(R.id.todayTextView7);
                tvTodayTitles[7] = (TextView) findViewById(R.id.todayTextView8);
                tvTodayTitles[8] = (TextView) findViewById(R.id.todayTextView9);
                tvTodayTitles[9] = (TextView) findViewById(R.id.todayTextView10);

                TextView[] tvTrendTitles = new TextView[10];
                tvTrendTitles[0] = (TextView) findViewById(R.id.trendingText1);
                tvTrendTitles[1] = (TextView) findViewById(R.id.trendingText2);
                tvTrendTitles[2] = (TextView) findViewById(R.id.trendingText3);
                tvTrendTitles[3] = (TextView) findViewById(R.id.trendingText4);
                tvTrendTitles[4] = (TextView) findViewById(R.id.trendingText5);
                tvTrendTitles[5] = (TextView) findViewById(R.id.trendingText6);
                tvTrendTitles[6] = (TextView) findViewById(R.id.trendingText7);
                tvTrendTitles[7] = (TextView) findViewById(R.id.trendingText8);
                tvTrendTitles[8] = (TextView) findViewById(R.id.trendingText9);
                tvTrendTitles[9] = (TextView) findViewById(R.id.trendingText10);

                for (int i = 0; i < 10; i++) {
                    tvTrendTitles[i].setText(trendingTitles.get(i));
                }
                for (int i = 0; i < todayTitles.size(); i++) {
                    tvTodayTitles[i].setText(todayTitles.get(i));
                }

                ImageView[] tvTrendImages = new ImageView[10];
                tvTrendImages[0] = (ImageView) findViewById(R.id.trendingImage1);
                tvTrendImages[1] = (ImageView) findViewById(R.id.trendingImage2);
                tvTrendImages[2] = (ImageView) findViewById(R.id.trendingImage3);
                tvTrendImages[3] = (ImageView) findViewById(R.id.trendingImage4);
                tvTrendImages[4] = (ImageView) findViewById(R.id.trendingImage5);
                tvTrendImages[5] = (ImageView) findViewById(R.id.trendingImage6);
                tvTrendImages[6] = (ImageView) findViewById(R.id.trendingImage7);
                tvTrendImages[7] = (ImageView) findViewById(R.id.trendingImage8);
                tvTrendImages[8] = (ImageView) findViewById(R.id.trendingImage9);
                tvTrendImages[9] = (ImageView) findViewById(R.id.trendingImage10);

                ImageView[] tvTodayImages = new ImageView[10];
                tvTodayImages[0] = (ImageView) findViewById(R.id.todayImage1);
                tvTodayImages[1] = (ImageView) findViewById(R.id.todayImage2);
                tvTodayImages[2] = (ImageView) findViewById(R.id.todayImage3);
                tvTodayImages[3] = (ImageView) findViewById(R.id.todayImage4);
                tvTodayImages[4] = (ImageView) findViewById(R.id.todayImage5);
                tvTodayImages[5] = (ImageView) findViewById(R.id.todayImage6);
                tvTodayImages[6] = (ImageView) findViewById(R.id.todayImage7);
                tvTodayImages[7] = (ImageView) findViewById(R.id.todayImage8);
                tvTodayImages[8] = (ImageView) findViewById(R.id.todayImage9);
                tvTodayImages[9] = (ImageView) findViewById(R.id.todayImage10);

                for (int i = 0; i < 10; i++) {
                    JSONObject e;
                    e = jsonTraktToday.getJSONObject(0);
                    JSONArray shows = e.getJSONArray("episodes");
                    imageLoader.displayImage(shows.getJSONObject(i).getJSONObject("show").getJSONObject("images").getString(TAG_IMAGE), tvTodayImages[i], options);
                }

                for (int i = 0; i < 10; i++) {
                    imageLoader.displayImage(jsonTrakt.getJSONObject(i).getString(TAG_IMAGE), tvTrendImages[i], options);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}








