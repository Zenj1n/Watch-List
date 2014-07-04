package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InfoPage extends Activity {

    private static final String YOU_RATED = "You rated ";
    private static final String ADD_TO_YOUR_LIST_FIRST = "Add to your list first";
    private static final String RATING_REMOVED = "Rating removed";
    private static final String TAG_TITLE = "title";
    private static final String TAG_GENRE = "genres";
    private static final String TAG_PLOT = "overview";
    private static final String TAG_IMAGE = "poster";
    private static final String TAG_STATUS = "status";
    public static int PROGRESS = 0;
    private static String INFOTITLE;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Button b_addto;
    private Button b_rate;
    private TextView title;
    private TextView t_genres;
    private TextView t_plot;
    private TextView t_status;
    private ArrayList<Integer> ratings = new ArrayList<Integer>();
    private double avgRating;
    private String stringRating;
    private int count;
    private int i;
    private int ratings_size;
    private List<Integer> allEpisodes = new ArrayList<Integer>();
    private int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infopage);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

        new JSONParse().execute();

        b_addto = (Button) findViewById(R.id.Baddto);
        b_addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTo();
            }
        });


        b_rate = (Button) findViewById(R.id.Brate);
        b_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rate();
            }
        });

        Button bepisode = (Button) findViewById(R.id.addepisode);
        bepisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addepisode();
            }
        });


    }


    /*
    // CODE VOOR ANIMATIE VAN WATCHLIST
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        //overridePendingTransition (R.anim.shrink_and_rotate_entrance, R.anim.shrink_and_rotate_exit);
    }

*/

    void sumEpisodes() {

        for (int a : allEpisodes) {
            sum += a;
            getProgress();
            getRating();

        }
        System.out.println(sum);
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

    private void AddTo() {
        PopupMenu popup = new PopupMenu(InfoPage.this, b_addto);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.watching:
                        ParseQuery<ParseObject> watching_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        watching_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        watching_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        watching_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.STATUS, ParseUtil.WATCHING);
                                        koppel.saveInBackground();
                                    } catch (Exception e) {
                                        ParseObject watching = new ParseObject(ParseUtil.KOPPEL);
                                        watching.put(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                                        watching.put(ParseUtil.SERIE, title.getText());
                                        watching.put(ParseUtil.STATUS, ParseUtil.WATCHING);
                                        watching.saveInBackground();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.plantowatch:
                        ParseQuery<ParseObject> plan_to_watch_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        plan_to_watch_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        plan_to_watch_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        plan_to_watch_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.STATUS, ParseUtil.PLAN_TO_WATCH);
                                        koppel.saveInBackground();
                                    } catch (Exception e) {
                                        ParseObject koppel = new ParseObject(ParseUtil.KOPPEL);
                                        koppel.put(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                                        koppel.put(ParseUtil.SERIE, title.getText());
                                        koppel.put(ParseUtil.STATUS, ParseUtil.PLAN_TO_WATCH);
                                        koppel.saveInBackground();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.completed:
                        final TextView progress = (TextView) findViewById(R.id.TProgress);
                        ParseQuery<ParseObject> completed_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        completed_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        completed_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        completed_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.STATUS, ParseUtil.COMPLETED);
                                        koppel.put(ParseUtil.PROGRESS, sum);
                                        progress.setText(sum + "/" + sum);
                                        PROGRESS = sum;
                                        koppel.saveInBackground();
                                    } catch (Exception e) {
                                        ParseObject koppel = new ParseObject(ParseUtil.KOPPEL);
                                        koppel.put(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                                        koppel.put(ParseUtil.SERIE, title.getText());
                                        koppel.put(ParseUtil.STATUS, ParseUtil.COMPLETED);
                                        koppel.put(ParseUtil.PROGRESS, sum);
                                        progress.setText(sum + "/" + sum);
                                        PROGRESS = sum;
                                        koppel.saveInBackground();
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

    private void Rate() {
        PopupMenu popup = new PopupMenu(InfoPage.this, b_rate);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.rating, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Remove_rating:
                        ParseQuery<ParseObject> remove_rating_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        remove_rating_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        remove_rating_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        remove_rating_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.remove(ParseUtil.RATING);
                                        koppel.saveInBackground();
                                        Toast.makeText(InfoPage.this, RATING_REMOVED, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(InfoPage.this, ADD_TO_YOUR_LIST_FIRST, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.Very_bad:
                        ParseQuery<ParseObject> very_bad_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        very_bad_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        very_bad_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        very_bad_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.RATING, 1);
                                        koppel.saveInBackground();
                                        Toast.makeText(InfoPage.this, YOU_RATED + title.getText(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(InfoPage.this, ADD_TO_YOUR_LIST_FIRST, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.Bad:
                        ParseQuery<ParseObject> bad_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        bad_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        bad_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        bad_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.RATING, 2);
                                        koppel.saveInBackground();
                                        Toast.makeText(InfoPage.this, YOU_RATED + title.getText(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(InfoPage.this, ADD_TO_YOUR_LIST_FIRST, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.Average:
                        ParseQuery<ParseObject> average_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        average_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        average_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        average_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.RATING, 3);
                                        koppel.saveInBackground();
                                        Toast.makeText(InfoPage.this, YOU_RATED + title.getText(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(InfoPage.this, ADD_TO_YOUR_LIST_FIRST, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.Good:
                        ParseQuery<ParseObject> good_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        good_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        good_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        good_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.RATING, 4);
                                        koppel.saveInBackground();
                                        Toast.makeText(InfoPage.this, YOU_RATED + title.getText(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(InfoPage.this, ADD_TO_YOUR_LIST_FIRST, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        return true;
                    case R.id.Great:
                        ParseQuery<ParseObject> great_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        great_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        great_query.whereEqualTo(ParseUtil.SERIE, title.getText());
                        great_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException p) {
                                if (p == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.RATING, 5);
                                        koppel.saveInBackground();
                                        Toast.makeText(InfoPage.this, YOU_RATED + title.getText(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(InfoPage.this, ADD_TO_YOUR_LIST_FIRST, Toast.LENGTH_SHORT).show();
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

    private void getRating() {
        ParseQuery<ParseObject> rating_query = ParseQuery.getQuery("Koppel");
        rating_query.whereEqualTo(ParseUtil.SERIE, INFOTITLE);
        rating_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> User, com.parse.ParseException e) {
                if (e == null) {
                    count = User.size();
                    i = 0;
                    double rating = 0;
                    ratings.clear();
                    try {
                        while (i < count) {
                            ParseObject koppel = User.get(i);
                            ratings.add(i, koppel.getInt(ParseUtil.RATING));
                            rating = rating + ratings.get(i);
                            i++;
                        }

                    } catch (Exception a) {
                        Toast.makeText(InfoPage.this, "An error occured. Cannot get serie names", Toast.LENGTH_SHORT).show();
                    }

                    i = 0;
                    ratings_size = ratings.size();
                    while (i < ratings.size()) {
                        Log.i("", " " + ratings + i);
                        if (ratings.get(i) == 0) {
                            ratings.remove(i);
                        } else {
                            i++;
                        }
                    }

                    avgRating = rating / ratings.size();
                    TextView rating_view = (TextView) findViewById(R.id.TRating);
                    if (Double.isNaN(avgRating)) {
                        stringRating = "No rating available";
                        rating_view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
                    } else {
                        stringRating = Double.toString(avgRating);
                    }
                    TextView ratingView = (TextView) findViewById(R.id.TRating);
                    ratingView.setText(stringRating);

                }
            }
        });
    }

    private void addepisode() {
        final AlertDialog.Builder errorBuilder = new AlertDialog.Builder(this);
        errorBuilder.setTitle("Invalid Episode!");
        final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        final TextView progress = (TextView) findViewById(R.id.TProgress);
        helpBuilder.setTitle("Episode " + progress.getText());
        final EditText input = new EditText(this);
        input.setSingleLine();
        input.setText(Integer.toString(PROGRESS));
        helpBuilder.setView(input);
        helpBuilder.setNeutralButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Integer.parseInt(input.getText().toString()) > sum || Integer.parseInt(input.getText().toString()) < 0) {
                        AlertDialog helpDialog = errorBuilder.create();
                        helpDialog.show();
                    } else {
                        progress.setText(input.getText() + "/" + sum);
                        ParseQuery<ParseObject> rating_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                        rating_query.whereEqualTo(ParseUtil.SERIE, INFOTITLE);
                        rating_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                        rating_query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> User, com.parse.ParseException e) {
                                if (e == null) {
                                    try {
                                        ParseObject koppel = User.get(0);
                                        koppel.put(ParseUtil.PROGRESS, Integer.parseInt(input.getText().toString()));
                                        koppel.saveInBackground();
                                        PROGRESS = Integer.parseInt(input.getText().toString());
                                    } catch (Exception d) {
                                        AlertDialog helpDialog = errorBuilder.create();
                                        helpDialog.show();

                                    }
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    AlertDialog helpDialog = errorBuilder.create();
                    helpDialog.show();
                }


            }
        });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private void getProgress() {
        final TextView progress = (TextView) findViewById(R.id.TProgress);
        ParseQuery<ParseObject> rating_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
        rating_query.whereEqualTo(ParseUtil.SERIE, INFOTITLE);
        rating_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
        rating_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> User, com.parse.ParseException e) {
                try {
                    if (e == null) {
                        ParseObject koppel = User.get(0);
                        progress.setText(koppel.getInt(ParseUtil.PROGRESS) + "/" + sum);
                    }
                } catch (Exception c) {
                    c.printStackTrace();
                }
            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            title = (TextView) findViewById(R.id.title);
            t_genres = (TextView) findViewById(R.id.Tgenres);
            t_plot = (TextView) findViewById(R.id.plot);
            t_status = (TextView) findViewById(R.id.TStatus);


            pDialog = new ProgressDialog(InfoPage.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected JSONArray doInBackground(String... args) {


            Intent intent = getIntent();

            String message2 = intent.getStringExtra("trakt");


            String urlTrakt = "http://api.trakt.tv/show/summary.json/390983740f2092270bc0fa267334db88/" + message2;
            String urlTraktSeasons = "http://api.trakt.tv/show/seasons.json/390983740f2092270bc0fa267334db88/" + message2;
            ServiceHandler jParser = new ServiceHandler();

            // Getting JSON from URL

            JSONObject jsonTrakt = jParser.getJSONFromUrl(urlTrakt);
            JSONArray jsonEpisodes = jParser.getJsonArray(urlTraktSeasons);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonTrakt);
            jsonArray.put(jsonEpisodes);

            return jsonArray;

        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            try {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .build();
                // Storing  JSON item in a Variable
                //int Seasons = allSeasons.getInt(TAG_SEASONS);
                String TitleMovie = jsonArray.getJSONObject(0).getString(TAG_TITLE);
                String PlotMovie = jsonArray.getJSONObject(0).getString(TAG_PLOT);
                String GenreMovie = jsonArray.getJSONObject(0).getString(TAG_GENRE);
                String Status = jsonArray.getJSONObject(0).getString(TAG_STATUS);
                String Image = jsonArray.getJSONObject(0).getString(TAG_IMAGE);
                INFOTITLE = TitleMovie;
                JSONArray episodes = jsonArray.getJSONArray(1);

                if (jsonArray != null) {
                    for (int i = 0; i < episodes.length(); i++) {
                        JSONObject e;
                        e = episodes.getJSONObject(i);
                        int test1 = e.getInt("episodes");
                        allEpisodes.add(test1);
                    }


                    sumEpisodes();
                    String test3 = GenreMovie.replaceAll("[\"\\[\\]]", "");
                    String test4 = test3.replaceAll(",(\\d|\\w)", ", $1");

                    //Set JSON Data in TextView
                    title.setText(TitleMovie);
                    t_genres.setText(test4);
                    t_plot.setText(PlotMovie);
                    t_status.setText(Status);

                    imageLoader.displayImage(Image, (ImageView) findViewById(R.id.Image), options);
                } else {
                    Toast.makeText(getApplicationContext(), "No Information Available", Toast.LENGTH_SHORT).show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


