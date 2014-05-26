package com.zenjin.watchlist.watchlist;



import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class WL_Fragment_b extends Fragment {

    ListView mListView;
    String[] b_title;
    String[] b_message;
    int[] b_images = {R.drawable.gameofthrones,R.drawable.thebigbangtheory,R.drawable.ncis};


    public WL_Fragment_b() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Resources res = getResources();

        b_title= res.getStringArray(R.array.wl_b_title);
        b_message= res.getStringArray(R.array.wl_b_message);

        View v = inflater.inflate(R.layout.fragment_b_wl, container, false);

        mListView = (ListView) v.findViewById(R.id.wl_b_listview);



        myArrayAdapterb adapter = new myArrayAdapterb(getActivity().getApplicationContext(),b_title,b_images,b_message);
        mListView.setAdapter(adapter);



        return v;
    }

}

class myArrayAdapterb extends ArrayAdapter<String>
{
    Context mContext;
    int[] imagesarray;
    String[] titlearray;
    String[] messagearray;

    myArrayAdapterb(Context b,String[] wl_b_title,int img[],String[] mssg)
    {
        super(b,R.layout.single_row_wl,R.id.wl_title,wl_b_title);
        this.mContext=b;
        this.imagesarray=img;
        this.titlearray=wl_b_title;
        this.messagearray=mssg;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_wl,parent,false);

        ImageView imageb = (ImageView) row.findViewById(R.id.wl_image);
        TextView titleb = (TextView) row.findViewById(R.id.wl_title);
        TextView messageb = (TextView) row.findViewById(R.id.wl_message);

        imageb.setImageResource(imagesarray[position]);
        titleb.setText(titlearray[position]);
        messageb.setText(messagearray[position]);

        return row;
    }


}