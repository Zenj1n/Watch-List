package com.zenjin.watchlist.watchlist;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Author :     Rinesh Ramadhin
 */
public class WL_Fragment_b extends Fragment {

    private final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";
    private ListView mListView;

    private ArrayList b_titlelist = new ArrayList();
    private ArrayList b_messagelist = new ArrayList();
    private ArrayList b_imageurl = new ArrayList();
    private static final String TAG_IMAGE = "poster";
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public WL_Fragment_b() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Resources res = getResources();

        // initialiseer Parse
        Parse.initialize(getActivity(), "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        // get values for titels, massages and images
        getvalues();

        View v = inflater.inflate(R.layout.fragment_b_wl, container, false);
        mListView = (ListView) v.findViewById(R.id.wl_b_listview);

        // create loading animation
        WebView webview = (WebView) v.findViewById(R.id.webViewB);
        webview.loadUrl("file:///android_asset/loadingshows.gif");

        mListView = getListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                if (b_titlelist.get(0) == "No series added") {

                    Intent intent;
                    intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);
                } else if (b_titlelist.get(0) == "No internet connection") {

                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);
                } else {
                    Intent intent;
                    intent = new Intent(getActivity(), InfoPage.class);

                    String titleSerieRaw = (String) b_titlelist.get(i);
                    String titleSerie = java.net.URLEncoder.encode(titleSerieRaw);

                    String word2 = (String) b_titlelist.get(i);
                    String traktWord = word2.replaceAll(" ", "-");
                    intent.putExtra("trakt", traktWord);

                    intent.putExtra(EXTRA_MESSAGE, titleSerie);
                    startActivity(intent);
                }

            }
        });
        return v;
    }

    public ListView getListView() {
        return mListView;
    }

    public void getvalues() {
        gettitles();            // get titels from Parse
    }

    public void gettitles() {
        ParseQuery<ParseObject> watching_query = ParseQuery.getQuery("Koppel");
        watching_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
        watching_query.whereEqualTo("Status", "Completed");
        watching_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> User, com.parse.ParseException e) {
                if (e == null) {

                    int count = User.size();                // number of items on Parse
                    int i = 0;
                    b_titlelist.clear();

                    try {
                        do {
                            ParseObject koppel = User.get(i);
                            b_titlelist.add(i, koppel.getString("Serie"));
                            i++;
                        }
                        while (i < count);
                    } catch (Exception a) {
                        b_titlelist.clear();
                        b_titlelist.add(i, "No series added");
                    }
                    String[] b_title = (String[]) b_titlelist.toArray(new String[b_titlelist.size()]);
                    new getmessages().execute(b_title);     // get messages from API
                } else {
                    b_titlelist.clear();
                    b_titlelist.add(0, "No internet connection");
                    String[] b_title = (String[]) b_titlelist.toArray(new String[b_titlelist.size()]);
                    new getmessages().execute(b_title);     // get messages from API
                }
            }
        });
    }

    public void createview(String[] b_title, String[] b_message, ArrayList<Bitmap> b_images) {

        try {
            WebView webview = (WebView) getActivity().findViewById(R.id.webViewB);
            webview.setVisibility(View.GONE);
        } catch (Exception e) {
        }
        myArrayAdapterb adapter = new myArrayAdapterb(getActivity().getApplicationContext(), b_title, b_images, b_message);
        mListView.setAdapter(adapter);
    }

    public class Pair {
        public String[] message;
        public String[] title;
        public ArrayList<Bitmap> b_images;
    }

    private class getmessages extends AsyncTask<String, Void, Pair> {
        @Override
        protected Pair doInBackground(String... b_title) {
            int count = b_titlelist.size();
            int i = 0;
            String check = (String) b_titlelist.get(0);

            if (check == "No series added") {
                b_messagelist.clear();
                b_messagelist.add(i, "Search for a show to get started");

            } else if (check == "No internet connection") {
                b_messagelist.clear();
                b_messagelist.add(i, "Please connect to the internet to get started");

            } else {
                try {
                    do {
                        String serie = (String) b_titlelist.get(i);
                        String prep = serie.replaceAll(" ", "%20");
                        String info = "Next episode is not available or this show has been canceled";
                        try {

                            String showurl = "http://services.tvrage.com/tools/quickinfo.php?show=" + prep;
                            URL tvrage = new URL(showurl);
                            BufferedReader in = new BufferedReader(new InputStreamReader(tvrage.openStream()));
                            String lijn;
                            String fullsite = "";
                            String nextepisode;

                            while ((lijn = in.readLine()) != null) {
                                fullsite = fullsite + lijn;
                            }
                            nextepisode = fullsite.substring(fullsite.indexOf("Next Episode@"), fullsite.indexOf("Country"));
                            nextepisode = nextepisode.replace("^", "    ");
                            nextepisode = nextepisode.substring(13);

                            try {
                                nextepisode = nextepisode.substring(0, nextepisode.indexOf("RFC"));
                            } catch (Exception e) {
                            }

                            try {
                                nextepisode = nextepisode.substring(0, nextepisode.indexOf("GMT"));
                            } catch (Exception e) {
                            }
                            info = nextepisode;

                        } catch (Exception e) {
                            String nonextepisode = "Next episode is not available or this show has been canceled";
                            info = nonextepisode;
                        }
                        b_messagelist.add(i, info);
                        i++;
                    }
                    while (i < count);

                } catch (Exception a) {
                    b_messagelist.clear();
                    b_messagelist.add(i, "An error occured. Cannot get serie messages");
                }
            }
            String[] b_message = (String[]) b_messagelist.toArray(new String[b_messagelist.size()]);
            Pair p = new Pair();
            p.message = b_message;
            p.title = b_title;
            return p;
        }

        protected void onPostExecute(Pair p) {
            String[] b_message = p.message;
            String[] b_title = p.title;
            new getimages().execute(b_title, b_message);
        }
    }

    private class getimages extends AsyncTask<Object, Void, Pair> {
        @Override
        protected Pair doInBackground(Object... object) {
            String[] b_title = (String[]) object[0];
            String[] b_message = (String[]) object[1];
            int count = b_titlelist.size();
            int i = 0;
            String check = (String) b_titlelist.get(0);
            ServiceHandler jParser = new ServiceHandler();

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .build();

            if (check == "No series added") {
                b_imageurl.clear();
                b_imageurl.add(i, "http://i.imgur.com/ZNt7DXU.png");
            } else if (check == "No internet connection") {
                b_imageurl.clear();
                b_imageurl.add(i, "http://i.imgur.com/ZNt7DXU.png");
            } else {
                try {
                    do {

                        String serie = (String) b_titlelist.get(i);
                        String prep = serie.replaceAll(" ", "-");
                        String url;
                        try {
                            String urlTrakt = "http://api.trakt.tv/show/summary.json/390983740f2092270bc0fa267334db88/" + prep;
                            JSONObject jsonTrakt = jParser.getJSONFromUrl(urlTrakt);
                            String Image = jsonTrakt.getString(TAG_IMAGE);
                            url = Image;
                        } catch (Exception e) {
                            String noimage = "http://i.imgur.com/ZNt7DXU.png";
                            url = noimage;
                        }
                        b_imageurl.add(i, url);
                        i++;
                    }
                    while (i < count);

                } catch (Exception a) {
                    b_imageurl.clear();
                    b_imageurl.add(i, "https://www.google.com/images/srpr/logo11w.png");
                }
            }

            String[] b_images_for_method = (String[]) b_imageurl.toArray(new String[b_imageurl.size()]);
            ArrayList<Bitmap> b_images = null;

            try {
                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                i = 0;
                count = b_images_for_method.length;
                do {
                    Bitmap bmp;
                    try {
                        bmp = imageLoader.loadImageSync(b_images_for_method[i], options);
                        images.add(i, bmp);
                    } catch (Exception e) {
                        images = new ArrayList<Bitmap>();
                        Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                                R.drawable.ic_launcher);
                        images.add(0, icon);
                    }
                    i++;
                }
                while (i < count);
                b_images = images;

            } catch (Exception e) {
                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_launcher);
                images.add(0, icon);
                b_images = images;
            }

            Pair p = new Pair();
            p.message = b_message;
            p.title = b_title;
            p.b_images = b_images;
            return p;

        }

        protected void onPostExecute(Pair p) {
            String[] a_message = p.message;
            String[] a_title = p.title;
            ArrayList<Bitmap> a_images = p.b_images;
            createview(a_title, a_message, a_images);
        }
    }
}

class myArrayAdapterb extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<Bitmap> imagesarray;
    private String[] titlearray;
    private String[] messagearray;

    myArrayAdapterb(Context b, String[] wl_b_title, ArrayList<Bitmap> img, String[] mssg) {
        super(b, R.layout.single_row_wl, R.id.wl_title, wl_b_title);
        this.mContext = b;
        this.imagesarray = img;
        this.titlearray = wl_b_title;
        this.messagearray = mssg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_wl, parent, false);

        ImageView imageb = (ImageView) row.findViewById(R.id.wl_image);
        TextView titleb = (TextView) row.findViewById(R.id.wl_title);
        TextView messageb = (TextView) row.findViewById(R.id.wl_message);

        imageb.setImageBitmap(imagesarray.get(position));
        titleb.setText(titlearray[position]);
        messageb.setText(messagearray[position]);

        return row;
    }
}