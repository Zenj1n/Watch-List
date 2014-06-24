package com.zenjin.watchlist.watchlist;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class WL_Fragment_a extends Fragment {

    public final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";

    ListView mListView;

    //String[] a_title;
    ArrayList a_titlelist = new ArrayList();

    //String[] a_message;
    ArrayList a_messagelist = new ArrayList();
    //ArrayList a_messageurl = new ArrayList();

    ArrayList a_imageurl = new ArrayList();

    //int[] a_images = {R.drawable.gameofthrones,R.drawable.thebigbangtheory,R.drawable.thebigbangtheory,R.drawable.thebigbangtheory,R.drawable.thebigbangtheory};


    public WL_Fragment_a() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Resources res = getResources();

        // TODO: build method to create int array "a_images"
        // TODO: build method to create string array and put it in "a_title"
        // TODO: build method to create string array and put it in "a_message"
        // TODO: same things with fragment "b" and "c"
        // TODO: add values from toast and parse to strings file and parsestring file

        Parse.initialize(getActivity(), "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        //a_title= res.getStringArray(R.array.wl_a_title);
        //a_message= res.getStringArray(R.array.wl_a_message);







        getvalues();
        //gettitles();
        //getmessages();








        View v = inflater.inflate(R.layout.fragment_a_wl, container, false);

        mListView = (ListView) v.findViewById(R.id.wl_a_listview);

        // create loading aniation


        WebView webview = (WebView) v.findViewById(R.id.webViewA);
        webview.loadUrl("file:///android_asset/loadingshows.gif");

        //ProgressBar progressbar = (ProgressBar) v.findViewById(R.id.progressBarA);
        //progressbar.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.MULTIPLY);


        mListView = getListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                //Toast.makeText(getActivity(), "Positie "+i +"titel is" + a_title[i], Toast.LENGTH_SHORT).show();

                Intent intent;
                intent = new Intent(getActivity(),InfoPage.class);

                String titleSerieRaw = (String) a_titlelist.get(i);
                String titleSerie = java.net.URLEncoder.encode(titleSerieRaw);

                String word2 = (String) a_titlelist.get(i);
                String traktWord = word2.replaceAll(" ","-");
                intent.putExtra("trakt", traktWord);

                intent.putExtra(EXTRA_MESSAGE,titleSerie );
                startActivity(intent);
                getActivity().overridePendingTransition (R.anim.shrink_and_rotate_entrance, R.anim.shrink_and_rotate_exit);



            }
        });

        //createview();
        //updateview();





        return v;

    }


    public void getvalues(){

        gettitles();            // haal titels op.

    }

    public void gettitles() {

        ParseQuery<ParseObject> watching_query = ParseQuery.getQuery("Koppel");
        watching_query.whereEqualTo("User", ParseUser.getCurrentUser().getUsername());
        watching_query.whereEqualTo("Status", "Watching");
        watching_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> User, com.parse.ParseException e) {
                if (e == null) {

                    int count = User.size();                // aantal items op parse
                    int i = 0;
                    a_titlelist.clear();


                    try {

                        do {

                            ParseObject koppel = User.get(i);
                            a_titlelist.add(i, koppel.getString("Serie"));

                            i++;

                        }
                        while (i < count);

                    }catch (Exception a){

                        a_titlelist.clear();
                        a_titlelist.add(i, "No series added");
                        //Toast.makeText(getActivity(), "An error occured. Cannot get serie names" , Toast.LENGTH_SHORT).show();

                    }


                    String[] a_title = (String[]) a_titlelist.toArray(new String[a_titlelist.size()]);

                    getmessages(a_title);

                    //createview(a_title);

                } else {

                    //Toast.makeText(getActivity(), "An error occured. Cannot get serie names" , Toast.LENGTH_SHORT).show();
                    a_titlelist.clear();
                    a_titlelist.add(0, "No internet connection");

                    String[] a_title = (String[]) a_titlelist.toArray(new String[a_titlelist.size()]);

                    getmessages(a_title);

                      //error

                }
            }
        });


    }

    public void getmessages(String[] a_title) {


        int count = a_titlelist.size();
        int i = 0;
        String check = (String) a_titlelist.get(0);

        if (check == "No series added") {

            a_messagelist.clear();
            a_messagelist.add(i, "Search for a show to get started");

        } else if (check == "No internet connection")
        {

            a_messagelist.clear();
            a_messagelist.add(i, "Please connect to the internet to get started");

        }else {


                try {

                    do {

                        String serie = (String) a_titlelist.get(i);
                        String prep = serie.replaceAll(" ","%20");

                        String info = new getNextEpisode().execute(prep).get();

                        //getnextepisode(serie);
                        //getnextepisode.execute((Runnable) a_titlelist);
                        //String serie = (String) a_messagelist.get(i);



                        a_messagelist.add(i, info);

                        i++;

                    }
                    while (i < count);

                } catch (Exception a) {

                    a_messagelist.clear();
                    a_messagelist.add(i, "An error occured. Cannot get serie messages");
                    //Toast.makeText(getActivity(), "An error occured. Cannot get serie names" , Toast.LENGTH_SHORT).show();

                }





            }

        String[] a_message = (String[]) a_messagelist.toArray(new String[a_messagelist.size()]);

        //createview(a_title, a_message);

        getimages(a_title,a_message);



        //ImageView imageview = (ImageView) getActivity().findViewById(R.id.imageViewFragmentA);
        //imageview.setVisibility(View.INVISIBLE);
        //ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBarA);
        //progressBar.setVisibility(View.INVISIBLE);

        //WebView webview = (WebView) getActivity().findViewById(R.id.webViewA);
        //webview.setVisibility(View.GONE);

    }


    public void getimages(String[] a_title, String[] a_message){

        int count = a_titlelist.size();
        int i = 0;
        String check = (String) a_titlelist.get(0);

        if (check == "No series added") {

            a_imageurl.clear();
            a_imageurl.add(i, "http://i.imgur.com/EwgfZTv.png");

        } else if (check == "No internet connection")
        {

            a_imageurl.clear();
            a_imageurl.add(i, "https://www.google.com/images/srpr/logo11w.png");

        }else {


            try {

                do {

                    String serie = (String) a_titlelist.get(i);
                    String prep = serie.replaceAll(" ","%20");

                    String url;
                    url = new getimagesonline().execute(prep).get();

                    //getnextepisode(serie);
                    //getnextepisode.execute((Runnable) a_titlelist);
                    //String serie = (String) a_messagelist.get(i);



                    a_imageurl.add(i, url);

                    i++;

                }
                while (i < count);

            } catch (Exception a) {

                a_imageurl.clear();
                a_imageurl.add(i, "https://www.google.com/images/srpr/logo11w.png");
                //Toast.makeText(getActivity(), "An error occured. Cannot get serie names" , Toast.LENGTH_SHORT).show();

            }





        }

        String[] a_images_for_method = (String[]) a_imageurl.toArray(new String[a_imageurl.size()]);

        ArrayList<Bitmap> a_images = null;

        try {
            a_images = new imagetoarray().execute(a_images_for_method).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //ArrayList<Bitmap> a_images = imagetoarray(a_images_for_method);

        createview(a_title, a_message,a_images);

        //getimages(a_title,a_message);



        //ImageView imageview = (ImageView) getActivity().findViewById(R.id.imageViewFragmentA);
        //imageview.setVisibility(View.INVISIBLE);
        //ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBarA);
        //progressBar.setVisibility(View.INVISIBLE);

        //WebView webview = (WebView) getActivity().findViewById(R.id.webViewA);
        //webview.setVisibility(View.GONE);

        WebView webview = (WebView) getActivity().findViewById(R.id.webViewA);
        webview.setVisibility(View.GONE);


    }


/*

public void getnextepisode (String titel){

    try {

        String showurl = "http://services.tvrage.com/tools/quickinfo.php?show=" + titel;
        URL tvrage = new URL(showurl);
        BufferedReader in = new BufferedReader(new InputStreamReader(tvrage.openStream()));

        String lijn;
        String fullsite = "";

        while ((lijn = in.readLine()) != null){

            fullsite = fullsite + lijn;

        }

        Toast.makeText(getActivity(), lijn , Toast.LENGTH_SHORT).show();



    }catch (Exception e){

        //errors
        //Toast.makeText(getActivity(), e , Toast.LENGTH_SHORT).show();
        Log.e("ERROR 1", "exception", e);

    }

}

*/

   class getNextEpisode extends AsyncTask<String, Void, String> {

    //private Exception exception;

       @Override
       protected String doInBackground(String... titel) {

           try {


               String showurl = "http://services.tvrage.com/tools/quickinfo.php?show=" + titel[0];
               URL tvrage = new URL(showurl);
               BufferedReader in = new BufferedReader(new InputStreamReader(tvrage.openStream()));

               String lijn;
               String fullsite = "";
               String nextepisode;

               while ((lijn = in.readLine()) != null){

                   fullsite = fullsite + lijn;

               }


               nextepisode = fullsite.substring(fullsite.indexOf("Next Episode@"),fullsite.indexOf("Country"));
               //nextepisode = nextepisode.replaceAll("","  ");
               nextepisode = nextepisode.replace("^","    ");
               nextepisode = nextepisode.substring(13);
               try {

                   nextepisode = nextepisode.substring(0,nextepisode.indexOf("RFC"));

               }catch (Exception e){

                   //nothing

               }

               try {

                   nextepisode = nextepisode.substring(0,nextepisode.indexOf("GMT"));

               }catch (Exception e){

                   //nothing

               }



               //Log.i("data", nextepisode);

               return nextepisode;

           } catch (Exception e) {

               String nonextepisode = "Next episode is not available or this show has been canceled";
               //Log.e("ERROR 2", "exception", e);

               return nonextepisode;

           }

           //return null;

       }
   }




    class getimagesonline extends AsyncTask<String, Void, String> {

        //private Exception exception;

        @Override
        protected String doInBackground(String... titel) {

            try {


                String omdbapi = "http://www.omdbapi.com/?t=" + titel[0];
                //JSONObject(html).getString("name");




                URL omdb = new URL(omdbapi);
                BufferedReader in = new BufferedReader(new InputStreamReader(omdb.openStream()));

                String lijn;
                String fullsite = "";
                String imageurl;

                while ((lijn = in.readLine()) != null){

                    fullsite = fullsite + lijn;

                }


                imageurl = fullsite.substring(fullsite.indexOf("http://"),fullsite.indexOf("\",\"Metascore"));

                //nextepisode = nextepisode.replaceAll("^","  ");
                //imageurl = imageurl.substring(13);



                //Log.i("data1", fullsite);

                //Log.i("data2", imageurl);

                return imageurl;




            } catch (Exception e) {

                String noimage = "https://www.google.com/images/srpr/logo11w.png";
                //Log.e("ERROR 2", "exception", e);

                return noimage;

            }

            //return null;

        }
    }


    class imagetoarray extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        //private Exception exception;

        @Override
        protected ArrayList<Bitmap> doInBackground(String... imageurl) {

            try {


                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                int i = 0;
                int count = imageurl.length;

                do {



                    Bitmap bitmap;
                    URL imageURL = null;

                    try {
                        imageURL = new URL(imageurl[i]);
                    }

                    catch (Exception e) {
                        Log.e("ERROR 1", "exception", e);
                    }

                    try {
                        HttpURLConnection connection= (HttpURLConnection)imageURL.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();

                        bitmap = BitmapFactory.decodeStream(inputStream);//Convert to bitmap
                        images.add(i,bitmap);
                    }
                    catch (Exception e) {

                        Log.e("ERROR 2", "exception", e);
                    }

                    i++;

                }
                while (i < count);


                return images;






            } catch (Exception e) {


                //String noimage = "https://www.google.com/images/srpr/logo11w.png";
                //Log.e("ERROR 2", "exception", e);


                ArrayList<Bitmap> images = new ArrayList<Bitmap>();

                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_launcher);

                images.add(0,icon);

                return images;

                //return noimage;


            }


        }
    }

    /*

    public ArrayList<Bitmap> imagetoarray(String[] imageurl) {


        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        int i = 0;
        int count = imageurl.length;

        do {



            Bitmap bitmap;
            URL imageURL = null;

            try {
                imageURL = new URL(imageurl[i]);
            }

            catch (Exception e) {
                Log.e("ERROR 1", "exception", e);
            }

            try {
                HttpURLConnection connection= (HttpURLConnection)imageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);//Convert to bitmap
               images.add(i,bitmap);
            }
            catch (Exception e) {

                Log.e("ERROR 2", "exception", e);
            }

            i++;

        }
        while (i < count);


        return images;


    }

        */


    public void createview(String[] a_title, String[] a_message,ArrayList<Bitmap> a_images){

        //Log.i("MyActivity", "string is" + Arrays.toString(a_title));

        myArrayAdaptera adapter = new myArrayAdaptera(getActivity().getApplicationContext(),a_title,a_images,a_message);
        mListView.setAdapter(adapter);

    }


    public ListView getListView() {
        return mListView;
    }

}

class myArrayAdaptera extends ArrayAdapter<String>
{
    Context mContext;
    ArrayList<Bitmap> imagesarray;
    String[] titlearray;
    String[] messagearray;

    myArrayAdaptera(Context a,String[] wl_a_title,ArrayList<Bitmap> img,String[] mssg)
    {
        super(a,R.layout.single_row_wl,R.id.wl_title,wl_a_title);
        this.mContext=a;
        this.imagesarray=img;
        this.titlearray=wl_a_title;
        this.messagearray=mssg;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_wl,parent,false);

        ImageView imagea = (ImageView) row.findViewById(R.id.wl_image);
        TextView titlea = (TextView) row.findViewById(R.id.wl_title);
        TextView messagea = (TextView) row.findViewById(R.id.wl_message);

        imagea.setImageBitmap(imagesarray.get(position));
        titlea.setText(titlearray[position]);
        messagea.setText(messagearray[position]);

        return row;
    }


}