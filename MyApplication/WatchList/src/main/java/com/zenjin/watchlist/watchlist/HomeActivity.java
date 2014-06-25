package com.zenjin.watchlist.watchlist;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeActivity extends MyWatchList {

    private Button BMyWatchList;
    private static final String TAG_TITLE = "title";
    private static final String TAG_IMAGE = "poster";

    List<String>trendingTitles = new ArrayList<String>();
    List<String>todayTitles = new ArrayList<String>();

    TextView tTrendingTitle11;
    TextView tTrendingTitle12;
    TextView tTrendingTitle13;
    TextView tTrendingTitle14;
    TextView tTrendingTitle15;
    TextView tTrendingTitle16;
    TextView tTrendingTitle17;
    TextView tTrendingTitle18;
    TextView tTrendingTitle19;
    TextView tTrendingTitle20;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.hm_activity, R.id.frame_container);
        new JSONParse().execute();


   /*     if(BMyWatchList == null){
            populate();
        }
    }

    private void populate(){
        BMyWatchList = (Button) findViewById(R.id.BMyWatchList);
        BMyWatchList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WatchlistActivity.class);
                startActivity(intent);
            }
        });*/
    }
    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tTrendingTitle11 = (TextView) findViewById(R.id.trending11);
            tTrendingTitle12 = (TextView) findViewById(R.id.trending12);
            tTrendingTitle13 = (TextView) findViewById(R.id.trending13);
            tTrendingTitle14 = (TextView) findViewById(R.id.trending14);
            tTrendingTitle15 = (TextView) findViewById(R.id.trending15);
            tTrendingTitle16 = (TextView) findViewById(R.id.trending16);
            tTrendingTitle17 = (TextView) findViewById(R.id.trending17);
            tTrendingTitle18 = (TextView) findViewById(R.id.trending18);
            tTrendingTitle19 = (TextView) findViewById(R.id.trending19);
            tTrendingTitle20 = (TextView) findViewById(R.id.trending20);

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
            System.out.println(formattedDate);

            String urlTraktTrending = "http://api.trakt.tv/shows/trending.json/390983740f2092270bc0fa267334db88/";

            String urlToday    = "http://api.trakt.tv/calendar/shows.json/390983740f2092270bc0fa267334db88/"+formattedDate;
            ServiceHandler jParser = new ServiceHandler();

            System.out.println(urlToday);

            // Getting JSON from URL
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
            try {
                JSONArray jsonTrakt = jsonArray.getJSONArray(1);
                JSONArray jsonTraktToday = jsonArray.getJSONArray(0);


                for(int i=0;i<jsonTrakt.length();i++){
                    JSONObject e;
                    e = jsonTrakt.getJSONObject(i);
                    String name = e.getString("title");
                    trendingTitles.add(name);
                }


                for(int i=0;i<jsonTraktToday.length();i++){
                    JSONObject e;
                    e = jsonTraktToday.getJSONObject(i);
                    JSONArray shows = e.getJSONArray("episodes");
                    String Allshows =  shows.getJSONObject(i).getJSONObject("show").getString("title");
                    todayTitles.add(Allshows);
                }



                System.out.println("trending"+trendingTitles);
                System.out.println("today"+ todayTitles);


                TextView [] tvTrendTitles = new TextView[10];
                tvTrendTitles[0]=(TextView)findViewById(R.id.trending11);
                tvTrendTitles[1]=(TextView)findViewById(R.id.trending12);
                tvTrendTitles[2]=(TextView)findViewById(R.id.trending13);
                tvTrendTitles[3]=(TextView)findViewById(R.id.trending14);
                tvTrendTitles[4]=(TextView)findViewById(R.id.trending15);
                tvTrendTitles[5]=(TextView)findViewById(R.id.trending16);
                tvTrendTitles[6]=(TextView)findViewById(R.id.trending17);
                tvTrendTitles[7]=(TextView)findViewById(R.id.trending18);
                tvTrendTitles[8]=(TextView)findViewById(R.id.trending19);
                tvTrendTitles[9]=(TextView)findViewById(R.id.trending20);

                for(int i=0;i<10;i++){
                   tvTrendTitles[i].setText(trendingTitles.get(i));
                }

               new DownloadImageTask((ImageView) findViewById(R.id.image11))
                        .execute(jsonTrakt.getJSONObject(0).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image12))
                        .execute(jsonTrakt.getJSONObject(1).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image13))
                        .execute(jsonTrakt.getJSONObject(2).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image14))
                        .execute(jsonTrakt.getJSONObject(3).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image15))
                        .execute(jsonTrakt.getJSONObject(4).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image16))
                        .execute(jsonTrakt.getJSONObject(5).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image17))
                        .execute(jsonTrakt.getJSONObject(6).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image18))
                        .execute(jsonTrakt.getJSONObject(7).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image19))
                        .execute(jsonTrakt.getJSONObject(8).getString(TAG_IMAGE));
               new DownloadImageTask((ImageView) findViewById(R.id.image20))
                        .execute(jsonTrakt.getJSONObject(9).getString(TAG_IMAGE));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }
}








