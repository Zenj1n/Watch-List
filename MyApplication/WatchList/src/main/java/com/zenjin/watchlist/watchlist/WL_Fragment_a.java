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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
public class WL_Fragment_a extends Fragment {


    private ListView mListView;
    private ArrayList a_titlelist = new ArrayList();                    // empty arrays for titels, massages and images
    private ArrayList a_messagelist = new ArrayList();
    private ArrayList a_imageurl = new ArrayList();
    private static final String TAG_IMAGE = "poster";
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public WL_Fragment_a() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Resources res = getResources();


        Parse.initialize(getActivity(), "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");
        getvalues();

        View v = inflater.inflate(R.layout.fragment_a_wl, container, false);
        mListView = (ListView) v.findViewById(R.id.wl_a_listview);

        WebView webview = (WebView) v.findViewById(R.id.webViewA);
        webview.loadUrl("file:///android_asset/loadingshows.gif");

        mListView = getListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                if (a_titlelist.get(0) == "No series added") {
                    Intent intent;
                    intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);

                } else if (a_titlelist.get(0) == "No internet connection") {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);
                } else {
                    Intent intent;
                    intent = new Intent(getActivity(), InfoPage.class);
                    String word2 = (String) a_titlelist.get(i);
                    String traktWord = word2.replaceAll(" ", "-");
                    intent.putExtra("trakt", traktWord);

                    startActivity(intent);

                    ParseQuery<ParseObject> watching_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
                    watching_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
                    watching_query.whereEqualTo(ParseUtil.SERIE, word2);
                    watching_query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> User, com.parse.ParseException e) {
                            if (e == null) {
                                ParseObject koppel = User.get(0);
                                InfoPage.PROGRESS = koppel.getInt(ParseUtil.PROGRESS);
                            } else {
                            }
                        }
                    });
                }
                getActivity().overridePendingTransition(R.anim.push_in, R.anim.push_out);
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

        ParseQuery<ParseObject> watching_query = ParseQuery.getQuery(ParseUtil.KOPPEL);
        watching_query.whereEqualTo(ParseUtil.PARSE_USER, ParseUser.getCurrentUser().getUsername());
        watching_query.whereEqualTo(ParseUtil.STATUS, ParseUtil.WATCHING);
        watching_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> User, com.parse.ParseException e) {
                if (e == null) {
                    int count = User.size();                // number of items on Parse
                    int i = 0;
                    a_titlelist.clear();
                    try {
                        do {
                            ParseObject koppel = User.get(i);
                            a_titlelist.add(i, koppel.getString(ParseUtil.SERIE));
                            i++;
                        }
                        while (i < count);
                    } catch (Exception a) {
                        a_titlelist.clear();
                        a_titlelist.add(i, "No series added");
                    }
                    String[] a_title = (String[]) a_titlelist.toArray(new String[a_titlelist.size()]);
                    new getmessages().execute(a_title);     // get messages from API
                } else {
                    a_titlelist.clear();
                    a_titlelist.add(0, "No internet connection");
                    String[] a_title = (String[]) a_titlelist.toArray(new String[a_titlelist.size()]);
                    new getmessages().execute(a_title);     // get messages from API
                }
            }
        });
    }

    public void createview(String[] a_title, String[] a_message, ArrayList<Bitmap> a_images) {
        try {
            WebView webview = (WebView) getActivity().findViewById(R.id.webViewA);
            webview.setVisibility(View.GONE);
        } catch (Exception e) {
        }

        myArrayAdaptera adapter = new myArrayAdaptera(getActivity().getApplicationContext(), a_title, a_images, a_message);
        mListView.setAdapter(adapter);
    }

    public class Pair {
        public String[] message;
        public String[] title;
        public ArrayList<Bitmap> a_images;
    }

    private class getmessages extends AsyncTask<String, Void, Pair> {
        @Override
        protected Pair doInBackground(String... a_title) {
            int count = a_titlelist.size();
            int i = 0;
            String check = (String) a_titlelist.get(0);

            if (check == "No series added") {
                a_messagelist.clear();
                a_messagelist.add(i, "Search for a show to get started");
            } else if (check == "No internet connection") {
                a_messagelist.clear();
                a_messagelist.add(i, "Please connect to the internet to get started");
            } else {
                try {
                    do {
                        String serie = (String) a_titlelist.get(i);
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

                        a_messagelist.add(i, info);
                        i++;
                    }
                    while (i < count);
                } catch (Exception a) {
                    a_messagelist.clear();
                    a_messagelist.add(i, "An error occured. Cannot get serie messages");
                }
            }

            String[] a_message = (String[]) a_messagelist.toArray(new String[a_messagelist.size()]);
            Pair p = new Pair();
            p.message = a_message;
            p.title = a_title;
            return p;
        }

        protected void onPostExecute(Pair p) {

            String[] a_message = p.message;
            String[] a_title = p.title;
            new getimages().execute(a_title, a_message);
        }
    }

    private class getimages extends AsyncTask<Object, Void, Pair> {
        @Override
        protected Pair doInBackground(Object... object) {

            String[] a_title = (String[]) object[0];
            String[] a_message = (String[]) object[1];
            int count = a_titlelist.size();
            int i = 0;
            String check = (String) a_titlelist.get(0);
            ServiceHandler jParser = new ServiceHandler();

            if (check == "No series added") {
                a_imageurl.clear();
                a_imageurl.add(i, "http://i.imgur.com/ZNt7DXU.png");

            } else if (check == "No internet connection") {
                a_imageurl.clear();
                a_imageurl.add(i, "http://i.imgur.com/ZNt7DXU.png");

            } else {
                try {
                    do {
                        String serie = (String) a_titlelist.get(i);
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
                        a_imageurl.add(i, url);
                        i++;
                    }
                    while (i < count);
                } catch (Exception a) {
                    a_imageurl.clear();
                    a_imageurl.add(i, "https://www.google.com/images/srpr/logo11w.png");
                }
            }

            String[] a_images_for_method = (String[]) a_imageurl.toArray(new String[a_imageurl.size()]);
            ArrayList<Bitmap> a_images = null;
            try {
                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                i = 0;
                count = a_images_for_method.length;
                do {
                    Bitmap bmp;
                    try {
                        bmp = imageLoader.loadImageSync(a_images_for_method[i]);
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
                a_images = images;
            } catch (Exception e) {

                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_launcher);
                images.add(0, icon);
                a_images = images;
            }

            Pair p = new Pair();
            p.message = a_message;
            p.title = a_title;
            p.a_images = a_images;
            return p;
        }

        protected void onPostExecute(Pair p) {

            String[] a_message = p.message;
            String[] a_title = p.title;
            ArrayList<Bitmap> a_images = p.a_images;
            createview(a_title, a_message, a_images);

        }
    }

}


class myArrayAdaptera extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<Bitmap> imagesarray;
    private String[] titlearray;
    private String[] messagearray;

    myArrayAdaptera(Context a, String[] wl_a_title, ArrayList<Bitmap> img, String[] mssg) {
        super(a, R.layout.single_row_wl, R.id.wl_title, wl_a_title);
        this.mContext = a;
        this.imagesarray = img;
        this.titlearray = wl_a_title;
        this.messagearray = mssg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_wl, parent, false);

        ImageView imagea = (ImageView) row.findViewById(R.id.wl_image);
        TextView titlea = (TextView) row.findViewById(R.id.wl_title);
        TextView messagea = (TextView) row.findViewById(R.id.wl_message);

        //imagea.setImageBitmap(imagesarray.get(position));
        titlea.setText(titlearray[position]);
        messagea.setText(messagearray[position]);

        return row;
    }
}