package com.zenjin.watchlist.watchlist;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.Arrays;
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
    int[] a_images = {R.drawable.gameofthrones,R.drawable.thebigbangtheory,R.drawable.truebloodimage,R.drawable.ncis,R.drawable.criminalminds,R.drawable.prettylittleliars,R.drawable.fallingskies,R.drawable.familyguy,R.drawable.hannibal,R.drawable.bones,R.drawable.arrow};


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

        Parse.initialize(getActivity(), "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");

        //a_title= res.getStringArray(R.array.wl_a_title);
        a_message= res.getStringArray(R.array.wl_a_message);

        gettitles();

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

                /*
                intent = new Intent(getActivity(),InfoPage.class);

                switch(i) {

                    case 0:
                        intent.putExtra(EXTRA_MESSAGE, "Game+of+thrones");
                        startActivity(intent);
                        break;

                    case 1:
                        intent.putExtra(EXTRA_MESSAGE, "The+big+bang+theory");
                        startActivity(intent);
                        break;

                    case 2:
                        intent.putExtra(EXTRA_MESSAGE, "True+blood");
                        startActivity(intent);
                        break;

                    case 3:
                        intent.putExtra(EXTRA_MESSAGE, "NCIS");
                        startActivity(intent);
                        break;

                    case 4:
                        intent.putExtra(EXTRA_MESSAGE, "Criminal+minds");
                        startActivity(intent);
                        break;

                    case 5:
                        intent.putExtra(EXTRA_MESSAGE, "Pretty+little+liars");
                        startActivity(intent);
                        break;

                    case 6:
                        intent.putExtra(EXTRA_MESSAGE, "Falling+skies");
                        startActivity(intent);
                        break;

                    case 7:
                        intent.putExtra(EXTRA_MESSAGE, "Family+guy");
                        startActivity(intent);
                        break;

                    case 8:
                        intent.putExtra(EXTRA_MESSAGE, "Hannibal");
                        startActivity(intent);
                        break;

                    case 9:
                        intent.putExtra(EXTRA_MESSAGE, "Bones");
                        startActivity(intent);
                        break;

                    case 10:
                        intent.putExtra(EXTRA_MESSAGE, "Arrow");
                        startActivity(intent);
                        break;

                }

                */

            }
        });

        //createview();
        //updateview();



        return v;

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

                    do {

                        ParseObject koppel = User.get(i);
                        a_titlelist.add(i, koppel.getString("Serie"));

                        i++;

                    }
                    while (i < count);

                    String[] a_title = (String[]) a_titlelist.toArray(new String[a_titlelist.size()]);

                    createview(a_title);

                } else {

                    Toast.makeText(getActivity(), "An error occured. Cannot get serie names" , Toast.LENGTH_SHORT).show();

                      //error

                }
            }
        });


    }

    public void createview(String[] a_title){

        Log.i("MyActivity", "string is" + Arrays.toString(a_title));

        myArrayAdaptera adapter = new myArrayAdaptera(getActivity().getApplicationContext(),a_title,a_images,a_message);
        mListView.setAdapter(adapter);

    }

    public void updateview(){


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