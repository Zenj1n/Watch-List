package com.zenjin.watchlist.watchlist;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class WL_Fragment_a extends Fragment {

    public final static String EXTRA_MESSAGE = "com.zenjin.watchlist.watchlist";

    ListView mListView;

    String[] a_title;
    ArrayList a_titlelist = new ArrayList();

    String[] a_message;
    ArrayList a_messagelist = new ArrayList();
    ArrayList a_messageurl = new ArrayList();

    int[] a_images = {R.drawable.gameofthrones,R.drawable.thebigbangtheory,R.drawable.thebigbangtheory,R.drawable.thebigbangtheory,R.drawable.thebigbangtheory};


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
        a_message= res.getStringArray(R.array.wl_a_message);







        getvalues();
        //gettitles();
        //getmessages();








        View v = inflater.inflate(R.layout.fragment_a_wl, container, false);

        mListView = (ListView) v.findViewById(R.id.wl_a_listview);

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

                    Toast.makeText(getActivity(), "An error occured. Cannot get serie names" , Toast.LENGTH_SHORT).show();

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

        } else {


                try {

                    do {

                        String serie = (String) a_titlelist.get(i);

                        new getNextEpisode().execute(serie);

                        //getnextepisode(serie);
                        //getnextepisode.execute((Runnable) a_titlelist);
                        //String serie = (String) a_messagelist.get(i);



                        a_messagelist.add(i, "//voeg ddeze tekst toe. positie is " + i + "serie is ");

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
        createview(a_title, a_message);
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

   class getNextEpisode extends AsyncTask<String, Void, Void> {

    //private Exception exception;

       @Override
       protected Void doInBackground(String... titel) {

           try {


               String showurl = "http://services.tvrage.com/tools/quickinfo.php?show=" + titel[0];
               URL tvrage = new URL(showurl);
               BufferedReader in = new BufferedReader(new InputStreamReader(tvrage.openStream()));

               String lijn;
               String fullsite = "";

               while ((lijn = in.readLine()) != null){

                   fullsite = fullsite + lijn;

               }

               fullsite = fullsite.substring(fullsite.indexOf("Next Episode@") + 1);
               fullsite = fullsite.substring(0, fullsite.indexOf("Country"));

               Log.i("data", fullsite);


           } catch (Exception e) {

               Log.e("ERROR 2", "exception", e);
               return null;

           }

           return null;

       }
   }







    public void createview(String[] a_title, String[] a_message){

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
    int[] imagesarray;
    String[] titlearray;
    String[] messagearray;

    myArrayAdaptera(Context a,String[] wl_a_title,int img[],String[] mssg)
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

        imagea.setImageResource(imagesarray[position]);
        titlea.setText(titlearray[position]);
        messagea.setText(messagearray[position]);

        return row;
    }


}