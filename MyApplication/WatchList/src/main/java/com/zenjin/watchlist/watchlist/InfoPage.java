package com.zenjin.watchlist.watchlist;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


import java.io.InputStream;
import java.net.URL;


public class InfoPage extends ActionBarActivity {

    Button Baddto;
    Button Brate;
    TextView Title;
    TextView TGenres;
    TextView Tplot;
    ImageView Image;





    private static final String TAG_TITLE = "Title";
    private static final String TAG_GENRE = "Genre";
    private static final String TAG_PLOT = "Plot";
    private static final String TAG_IMAGE = "Poster";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopage);

        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");
        new JSONParse().execute();



            Baddto = (Button) findViewById(R.id.Baddto);
            Baddto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(InfoPage.this, Baddto);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.popup_menu, popup.getMenu());
                    popup.show();
                }
            });

        Brate = (Button) findViewById(R.id.Brate);
        Brate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent intent = new Intent(InfoPage.this, DispatchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        }



    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Title = (TextView)findViewById(R.id.title);
            TGenres = (TextView)findViewById(R.id.Tgenres);
            Tplot = (TextView)findViewById(R.id.plot);
            Image = (ImageView)findViewById(R.id.Image);

            pDialog = new ProgressDialog(InfoPage.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {

            Intent intent = getIntent();
            String message = intent.getStringExtra(WL_Fragment_a.EXTRA_MESSAGE);

            String url = "http://www.omdbapi.com/?t="+message+"&plot=full";
            ServiceHandler jParser = new ServiceHandler();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);

            return json;

        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {


                // Storing  JSON item in a Variable
                String TitleMovie = json.getString(TAG_TITLE);
                String PlotMovie = json.getString(TAG_PLOT);
                String GenreMovie = json.getString(TAG_GENRE);



                //Set JSON Data in TextView
                Title.setText(TitleMovie);
                TGenres.setText(GenreMovie);
                Tplot.setText(PlotMovie);

                new DownloadImageTask((ImageView) findViewById(R.id.Image))
                        .execute(json.getString(TAG_IMAGE));
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


}