package com.zenjin.watchlist.watchlist;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class WL_Fragment_a extends Fragment {

    ListView mListView;
    String[] a_title;
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

        a_title= res.getStringArray(R.array.wl_a_title);
        a_message= res.getStringArray(R.array.wl_a_message);

        View v = inflater.inflate(R.layout.fragment_a_wl, container, false);

        mListView = (ListView) v.findViewById(R.id.wl_a_listview);


        mListView = getListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                
                Toast.makeText(getActivity(), "Positie "+i , Toast.LENGTH_SHORT).show();

                if (i == 10){

                    Intent intent;
                    intent = new Intent(getActivity(),InfoPage.class);
                    startActivity(intent);

                }

            }
        });

        myArrayAdaptera adapter = new myArrayAdaptera(getActivity().getApplicationContext(),a_title,a_images,a_message);
        mListView.setAdapter(adapter);



        return v;
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