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


public class HomeActivity extends MyWatchList {

    private Button BMyWatchList;
    private static final String TAG_TITLE = "title";
    private static final String TAG_IMAGE = "poster";

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

    ImageView Image11;


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

            Image11 = (ImageView) findViewById(R.id.image11);


            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            String urlTraktTrending = "http://api.trakt.tv/shows/trending.json/390983740f2092270bc0fa267334db88/";
            ServiceHandler jParser = new ServiceHandler();
            // Getting JSON from URL
            JSONObject jsonTrakt = jParser.getJSONFromUrl(urlTraktTrending);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonTrakt);
            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            try {
                String TrendingTitle = jsonArray.getJSONObject(0).getString(TAG_TITLE);
                tTrendingTitle11.setText(TrendingTitle);
                new DownloadImageTask((ImageView) findViewById(R.id.image11))
                        .execute(jsonArray.getJSONObject(0).getString(TAG_IMAGE));
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








