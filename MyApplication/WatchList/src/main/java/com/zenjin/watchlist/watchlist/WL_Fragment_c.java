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
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
public class WL_Fragment_c extends Fragment {

    private final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";
    private ListView mListView;

    private ArrayList c_titlelist = new ArrayList();                    // empty arrays for titels, massages and images
    private ArrayList c_messagelist = new ArrayList();
    private ArrayList c_imageurl = new ArrayList();
    private static final String TAG_IMAGE = "poster";
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public WL_Fragment_c() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Resources res = getResources();

        Parse.initialize(getActivity(), "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        getvalues();

        View v = inflater.inflate(R.layout.fragment_c_wl, container, false);
        mListView = (ListView) v.findViewById(R.id.wl_c_listview);

        WebView webview = (WebView) v.findViewById(R.id.webViewC);
        webview.loadUrl("file:///android_asset/loadingshows.gif");

        mListView = getListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                if (c_titlelist.get(0) == "No series added") {

                    Intent intent;
                    intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);

                } else if (c_titlelist.get(0) == "No internet connection") {

                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);

                } else {

                    Intent intent;
                    intent = new Intent(getActivity(), InfoPage.class);

                    String titleSerieRaw = (String) c_titlelist.get(i);
                    String titleSerie = java.net.URLEncoder.encode(titleSerieRaw);

                    String word2 = (String) c_titlelist.get(i);
                    String traktWord = word2.replaceAll("[ ]", "-");
                    String traktword2 = traktWord.replaceAll("[' : ( ) ,]", "");
                    intent.putExtra("trakt", traktword2);

                    intent.putExtra(EXTRA_MESSAGE, titleSerie);
                    startActivity(intent);
                    //getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);

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
        watching_query.whereEqualTo("Status", "Plan to watch");
        watching_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> User, com.parse.ParseException e) {
                if (e == null) {
                    int count = User.size();
                    int i = 0;
                    c_titlelist.clear();
                    try {
                        do {
                            ParseObject koppel = User.get(i);
                            c_titlelist.add(i, koppel.getString("Serie"));
                            i++;
                        }
                        while (i < count);
                    } catch (Exception a) {
                        c_titlelist.clear();
                        c_titlelist.add(i, "No series added");
                    }

                    String[] c_title = (String[]) c_titlelist.toArray(new String[c_titlelist.size()]);
                    new getmessages().execute(c_title);     // get messages from API

                } else {
                    c_titlelist.clear();
                    c_titlelist.add(0, "No internet connection");
                    String[] c_title = (String[]) c_titlelist.toArray(new String[c_titlelist.size()]);
                    new getmessages().execute(c_title);     // get messages from API
                }
            }
        });
    }

    public void createview(String[] c_title, String[] c_message, ArrayList<Bitmap> c_images) {

        try {
            WebView webview = (WebView) getActivity().findViewById(R.id.webViewC);
            webview.setVisibility(View.GONE);

        } catch (Exception e) {
        }

        myArrayAdapterc adapter = new myArrayAdapterc(getActivity().getApplicationContext(), c_title, c_images, c_message);
        mListView.setAdapter(adapter);
    }

    public class Pair {
        public String[] message;
        public String[] title;
        public ArrayList<Bitmap> c_images;
    }

    private class getmessages extends AsyncTask<String, Void, Pair> {
        @Override
        protected Pair doInBackground(String... c_title) {
            int count = c_titlelist.size();
            int i = 0;
            String check = (String) c_titlelist.get(0);

            if (check == "No series added") {
                c_messagelist.clear();
                c_messagelist.add(i, "Search for a show to get started");

            } else if (check == "No internet connection") {
                c_messagelist.clear();
                c_messagelist.add(i, "Please connect to the internet to get started");

            } else {
                try {
                    do {
                        String serie = (String) c_titlelist.get(i);
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
                        c_messagelist.add(i, info);
                        i++;
                    }
                    while (i < count);
                } catch (Exception a) {
                    c_messagelist.clear();
                    c_messagelist.add(i, "An error occured. Cannot get serie messages");
                }
            }

            String[] c_message = (String[]) c_messagelist.toArray(new String[c_messagelist.size()]);
            Pair p = new Pair();
            p.message = c_message;
            p.title = c_title;
            return p;
        }

        protected void onPostExecute(Pair p) {
            String[] c_message = p.message;
            String[] c_title = p.title;
            new getimages().execute(c_title, c_message);
        }
    }

    private class getimages extends AsyncTask<Object, Void, Pair> {
        @Override
        protected Pair doInBackground(Object... object) {

            String[] c_title = (String[]) object[0];
            String[] c_message = (String[]) object[1];
            int count = c_titlelist.size();
            int i = 0;
            String check = (String) c_titlelist.get(0);
            ServiceHandler jParser = new ServiceHandler();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .build();

            if (check == "No series added") {
                c_imageurl.clear();
                c_imageurl.add(i, "http://i.imgur.com/ZNt7DXU.png");

            } else if (check == "No internet connection") {
                c_imageurl.clear();
                c_imageurl.add(i, "http://i.imgur.com/ZNt7DXU.png");

            } else {
                try {
                    do {

                        String serie = (String) c_titlelist.get(i);
                        String prep0 = serie.replaceAll("[ ]", "-");
                        String prep = prep0.replaceAll("[' : ( ) ,]", "");
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
                        c_imageurl.add(i, url);
                        i++;
                    }
                    while (i < count);
                } catch (Exception a) {
                    c_imageurl.clear();
                    c_imageurl.add(i, "https://www.google.com/images/srpr/logo11w.png");
                }
            }

            String[] c_images_for_method = (String[]) c_imageurl.toArray(new String[c_imageurl.size()]);
            ArrayList<Bitmap> c_images = null;

            try {
                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                i = 0;
                count = c_images_for_method.length;
                do {
                    Bitmap bmp;
                    try {
                        bmp = imageLoader.loadImageSync(c_images_for_method[i], options);
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
                c_images = images;

            } catch (Exception e) {
                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_launcher);
                images.add(0, icon);
                c_images = images;
            }

            Pair p = new Pair();
            p.message = c_message;
            p.title = c_title;
            p.c_images = c_images;
            return p;
        }

        protected void onPostExecute(Pair p) {

            String[] c_message = p.message;
            String[] c_title = p.title;
            ArrayList<Bitmap> c_images = p.c_images;
            createview(c_title, c_message, c_images);
        }
    }
}

class myArrayAdapterc extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<Bitmap> imagesarray;
    private String[] titlearray;
    private String[] messagearray;

    myArrayAdapterc(Context c, String[] wl_c_title, ArrayList<Bitmap> img, String[] mssg) {
        super(c, R.layout.single_row_wl, R.id.wl_title, wl_c_title);
        this.mContext = c;
        this.imagesarray = img;
        this.titlearray = wl_c_title;
        this.messagearray = mssg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_wl, parent, false);

        ImageView imagec = (ImageView) row.findViewById(R.id.wl_image);
        TextView titlec = (TextView) row.findViewById(R.id.wl_title);
        TextView messagec = (TextView) row.findViewById(R.id.wl_message);

        imagec.setImageBitmap(imagesarray.get(position));
        titlec.setText(titlearray[position]);
        messagec.setText(messagearray[position]);

        return row;
    }
}