package com.zenjin.watchlist.watchlist;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeActivity extends MyWatchList {


    private static final String TAG_IMAGE = "poster";

    List<String>trendingTitles = new ArrayList<String>();
    List<String>todayTitles = new ArrayList<String>();

    Intent intent;
    public final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.hm_activity, R.id.frame_container);
        new JSONParse().execute();

        ImageView todayImage1 = (ImageView) findViewById(R.id.image1);
        ImageView todayImage2 = (ImageView) findViewById(R.id.image2);
        ImageView todayImage3 = (ImageView) findViewById(R.id.image3);
        ImageView todayImage4 = (ImageView) findViewById(R.id.image4);
        ImageView todayImage5 = (ImageView) findViewById(R.id.image5);
        ImageView todayImage6 = (ImageView) findViewById(R.id.image6);
        ImageView todayImage7 = (ImageView) findViewById(R.id.image7);
        ImageView todayImage8 = (ImageView) findViewById(R.id.image8);
        ImageView todayImage9 = (ImageView) findViewById(R.id.image9);
        ImageView todayImage10 = (ImageView) findViewById(R.id.image10);

        ImageView trendingImage1 = (ImageView) findViewById(R.id.image11);
        ImageView trendingImage2 = (ImageView) findViewById(R.id.image12);
        ImageView trendingImage3 = (ImageView) findViewById(R.id.image13);
        ImageView trendingImage4 = (ImageView) findViewById(R.id.image14);
        ImageView trendingImage5 = (ImageView) findViewById(R.id.image15);
        ImageView trendingImage6 = (ImageView) findViewById(R.id.image16);
        ImageView trendingImage7 = (ImageView) findViewById(R.id.image17);
        ImageView trendingImage8 = (ImageView) findViewById(R.id.image18);
        ImageView trendingImage9 = (ImageView) findViewById(R.id.image19);
        ImageView trendingImage10 = (ImageView) findViewById(R.id.image20);

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

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.image1:
                    String word1 = java.net.URLEncoder.encode(todayTitles.get(0));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word1);
                    startActivity(intent);
                    break;
                case R.id.image2:
                    String word2 = java.net.URLEncoder.encode(todayTitles.get(1));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word2);
                    startActivity(intent);
                    break;
                case R.id.image3:
                    String word3 = java.net.URLEncoder.encode(todayTitles.get(2));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word3);
                    startActivity(intent);
                    break;
                case R.id.image4:
                    String word4 = java.net.URLEncoder.encode(todayTitles.get(3));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word4);
                    startActivity(intent);
                    break;
                case R.id.image5:
                    String word5 = java.net.URLEncoder.encode(todayTitles.get(4));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word5);
                    startActivity(intent);
                    break;
                case R.id.image6:
                    String word6 = java.net.URLEncoder.encode(todayTitles.get(5));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word6);
                    startActivity(intent);
                    break;
                case R.id.image7:
                    String word7 = java.net.URLEncoder.encode(todayTitles.get(6));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word7);
                    startActivity(intent);
                    break;
                case R.id.image8:
                    String word8 = java.net.URLEncoder.encode(todayTitles.get(7));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word8);
                    startActivity(intent);
                    break;
                case R.id.image9:
                    String word9 = java.net.URLEncoder.encode(todayTitles.get(8));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word9);
                    startActivity(intent);
                    break;
                case R.id.image10:
                    String word10 = java.net.URLEncoder.encode(todayTitles.get(9));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word10);
                    startActivity(intent);
                    break;
                case R.id.image11:
                    String word11 = java.net.URLEncoder.encode(trendingTitles.get(0));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word11);
                    startActivity(intent);
                    break;
                case R.id.image12:
                    String word12 = java.net.URLEncoder.encode(trendingTitles.get(1));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word12);
                    startActivity(intent);
                    break;
                case R.id.image13:
                    String word13 = java.net.URLEncoder.encode(trendingTitles.get(2));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word13);
                    startActivity(intent);
                    break;
                case R.id.image14:
                    String word14 = java.net.URLEncoder.encode(trendingTitles.get(3));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word14);
                    startActivity(intent);
                    break;
                case R.id.image15:
                    String word15 = java.net.URLEncoder.encode(trendingTitles.get(4));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word15);
                    startActivity(intent);
                    break;
                case R.id.image16:
                    String word16 = java.net.URLEncoder.encode(trendingTitles.get(5));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word16);
                    startActivity(intent);
                    break;
                case R.id.image17:
                    String word17 = java.net.URLEncoder.encode(trendingTitles.get(6));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word17);
                    startActivity(intent);
                    break;
                case R.id.image18:
                    String word18 = java.net.URLEncoder.encode(trendingTitles.get(7));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word18);
                    startActivity(intent);
                    break;
                case R.id.image19:
                    String word19 = java.net.URLEncoder.encode(trendingTitles.get(8));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word19);
                    startActivity(intent);
                    break;
                case R.id.image20:
                    String word20 = java.net.URLEncoder.encode(trendingTitles.get(9));
                    intent = new Intent(HomeActivity.this,InfoPage.class);
                    intent.putExtra(EXTRA_MESSAGE, word20);
                    startActivity(intent);
                    break;
            }
        }
    };
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



                for(int i=0;i<10;i++){
                    JSONObject e ;
                    e = jsonTraktToday.getJSONObject(0);
                    JSONArray shows = e.getJSONArray("episodes");
                    String todayShows =  shows.getJSONObject(i).getJSONObject("show").getString("title");
                    todayTitles.add(todayShows);
                }


                System.out.println("trending"+trendingTitles);
                System.out.println("today"+ todayTitles);

                TextView [] tvTodayTitles = new TextView[10];
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
                for(int i=0;i<todayTitles.size();i++){
                    tvTodayTitles[i].setText(todayTitles.get(i));
                }

                ImageView [] tvTrendImages = new ImageView[10];
                tvTrendImages[0]=(ImageView)findViewById(R.id.image11);
                tvTrendImages[1]=(ImageView)findViewById(R.id.image12);
                tvTrendImages[2]=(ImageView)findViewById(R.id.image13);
                tvTrendImages[3]=(ImageView)findViewById(R.id.image14);
                tvTrendImages[4]=(ImageView)findViewById(R.id.image15);
                tvTrendImages[5]=(ImageView)findViewById(R.id.image16);
                tvTrendImages[6]=(ImageView)findViewById(R.id.image17);
                tvTrendImages[7]=(ImageView)findViewById(R.id.image18);
                tvTrendImages[8]=(ImageView)findViewById(R.id.image19);
                tvTrendImages[9]=(ImageView)findViewById(R.id.image20);

                ImageView [] tvTodayImages = new ImageView[10];
                tvTodayImages[0]=(ImageView)findViewById(R.id.image1);
                tvTodayImages[1]=(ImageView)findViewById(R.id.image2);
                tvTodayImages[2]=(ImageView)findViewById(R.id.image3);
                tvTodayImages[3]=(ImageView)findViewById(R.id.image4);
                tvTodayImages[4]=(ImageView)findViewById(R.id.image5);
                tvTodayImages[5]=(ImageView)findViewById(R.id.image6);
                tvTodayImages[6]=(ImageView)findViewById(R.id.image7);
                tvTodayImages[7]=(ImageView)findViewById(R.id.image8);
                tvTodayImages[8]=(ImageView)findViewById(R.id.image9);
                tvTodayImages[9]=(ImageView)findViewById(R.id.image10);

                for (int i=0;i<10;i++){
                    JSONObject e ;
                    e = jsonTraktToday.getJSONObject(0);
                    JSONArray shows = e.getJSONArray("episodes");
                    new DownloadImageTask(tvTodayImages[i]).execute(shows.getJSONObject(i).getJSONObject("show").getJSONObject("images").getString(TAG_IMAGE));
                }

               for (int i=0;i<10;i++){
                   new DownloadImageTask(tvTrendImages[i]).execute(jsonTrakt.getJSONObject(i).getString(TAG_IMAGE));
               }


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








