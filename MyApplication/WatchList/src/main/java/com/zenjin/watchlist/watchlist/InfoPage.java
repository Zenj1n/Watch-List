package com.zenjin.watchlist.watchlist;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;


public class InfoPage extends ActionBarActivity {

    Button Baddto;
    Button Brate;
    TextView Title;
    TextView TGenres;
    TextView Tplot;
    TextView TStatus;
    ImageView Image;


    private static final String TAG_TITLE = "Title";
    private static final String TAG_GENRE = "Genre";
    private static final String TAG_PLOT = "Plot";
    private static final String TAG_IMAGE = "Poster";

    private static final String TAG_STATUS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopage);
        getActionBar().setDisplayHomeAsUpEnabled(true);


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
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.watching:
                                ParseQuery<ParseObject> watching_query = ParseQuery.getQuery("Koppel");
                                watching_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                                watching_query.whereEqualTo("Serie", Title.getText());
                                watching_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Status", "Watching");
                                                koppel.saveInBackground();
                                            } catch (Exception e) {
                                                ParseObject watching = new ParseObject("Koppel");
                                                watching.put(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                                                watching.put("Serie", Title.getText());
                                                watching.put("Status", "Watching");
                                                watching.saveInBackground();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.plantowatch:
                                ParseQuery<ParseObject> plantowatch_query = ParseQuery.getQuery("Koppel");
                                plantowatch_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                plantowatch_query.whereEqualTo("Serie", Title.getText());
                                plantowatch_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Status", "Plan to watch");
                                                koppel.saveInBackground();
                                            } catch (Exception e) {
                                                ParseObject watching = new ParseObject("Koppel");
                                                watching.put("User", ParseUser.getCurrentUser().getUsername());
                                                watching.put("Serie", Title.getText());
                                                watching.put("Status", "Plan to watch");
                                                watching.saveInBackground();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.completed:
                                ParseQuery<ParseObject> completed_query = ParseQuery.getQuery("Koppel");
                                completed_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                completed_query.whereEqualTo("Serie", Title.getText());
                                completed_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Status", "Completed");
                                                koppel.saveInBackground();
                                            } catch (Exception e) {
                                                ParseObject watching = new ParseObject("Koppel");
                                                watching.put("User", ParseUser.getCurrentUser().getUsername());
                                                watching.put("Serie", Title.getText());
                                                watching.put("Status", "Completed");
                                                watching.saveInBackground();
                                            }
                                        }
                                    }
                                });
                                return true;
                            default:
                                return false;
                        }

                    }
                });

            }
        });


        Brate = (Button) findViewById(R.id.Brate);
        Brate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(InfoPage.this, Brate);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.rating, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.Remove_rating:
                                ParseQuery<ParseObject> remove_rating_query = ParseQuery.getQuery("Koppel");
                                remove_rating_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                remove_rating_query.whereEqualTo("Serie", Title.getText());
                                remove_rating_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.remove("Rating");
                                                koppel.saveInBackground();
                                                Toast.makeText(InfoPage.this, "Rating removed", Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Toast.makeText(InfoPage.this, "Add to your list first", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.Very_bad:
                                ParseQuery<ParseObject> very_bad_query = ParseQuery.getQuery("Koppel");
                                very_bad_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                very_bad_query.whereEqualTo("Serie", Title.getText());
                                very_bad_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Rating", 1);
                                                koppel.saveInBackground();
                                                Toast.makeText(InfoPage.this, "You rated " + Title.getText(), Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Toast.makeText(InfoPage.this, "Add to your list first ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.Bad:
                                ParseQuery<ParseObject> bad_query = ParseQuery.getQuery("Koppel");
                                bad_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                bad_query.whereEqualTo("Serie", Title.getText());
                                bad_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Rating", 2);
                                                koppel.saveInBackground();
                                                Toast.makeText(InfoPage.this, "You rated " + Title.getText(), Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Toast.makeText(InfoPage.this, "Add to your list first", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.Average:
                                ParseQuery<ParseObject> average_query = ParseQuery.getQuery("Koppel");
                                average_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                average_query.whereEqualTo("Serie", Title.getText());
                                average_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Rating", 3);
                                                koppel.saveInBackground();
                                                Toast.makeText(InfoPage.this, "You rated" + Title.getText(), Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Toast.makeText(InfoPage.this, "Add to your list first", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.Good:
                                ParseQuery<ParseObject> good_query = ParseQuery.getQuery("Koppel");
                                good_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                good_query.whereEqualTo("Serie", Title.getText());
                                good_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Rating", 4);
                                                koppel.saveInBackground();
                                                Toast.makeText(InfoPage.this, "You rated " + Title.getText(), Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Toast.makeText(InfoPage.this, "Add to your list first", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                return true;
                            case R.id.Great:
                                ParseQuery<ParseObject> great_query = ParseQuery.getQuery("Koppel");
                                great_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
                                great_query.whereEqualTo("Serie", Title.getText());
                                great_query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> User, com.parse.ParseException p) {
                                        if (p == null) {
                                            try {
                                                ParseObject koppel = User.get(0);
                                                koppel.put("Rating", 5);
                                                koppel.saveInBackground();
                                                Toast.makeText(InfoPage.this, "You rated " + Title.getText(), Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Toast.makeText(InfoPage.this, "Add to your list first", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                return true;
                            default:
                                return false;
                        }

                    }
                });
            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Title = (TextView) findViewById(R.id.title);
            TGenres = (TextView) findViewById(R.id.Tgenres);
            Tplot = (TextView) findViewById(R.id.plot);
            Image = (ImageView) findViewById(R.id.Image);
            TStatus = (TextView)findViewById(R.id.TStatus);


            pDialog = new ProgressDialog(InfoPage.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {


            Intent intent = getIntent();
            String message = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);
            //String message = intent.getStringExtra(WL_Fragment_a.EXTRA_MESSAGE);

            String url = "http://www.omdbapi.com/?t=" + message + "&plot=full";
            String urlTrakt = "http://api.trakt.tv/show/summary.json/390983740f2092270bc0fa267334db88/"+ message;
            ServiceHandler jParser = new ServiceHandler();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            JSONObject jsonTrakt = jParser.getJSONFromUrl(urlTrakt);

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
                //String Status = jsonTrakt.getString(TAG_STATUS);


                //Set JSON Data in TextView
                Title.setText(TitleMovie);
                TGenres.setText(GenreMovie);
                Tplot.setText(PlotMovie);
               // TStatus.setText(Status);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
