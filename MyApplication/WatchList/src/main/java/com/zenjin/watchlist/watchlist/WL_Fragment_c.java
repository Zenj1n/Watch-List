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
public class WL_Fragment_c extends Fragment {

    ListView mListView;
    String[] c_title;
    String[] c_message;
    int[] c_images = {R.drawable.familyguy,R.drawable.hannibal,R.drawable.bones};


    public WL_Fragment_c() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Resources res = getResources();

        c_title= res.getStringArray(R.array.wl_c_title);
        c_message= res.getStringArray(R.array.wl_c_message);

        View v = inflater.inflate(R.layout.fragment_c_wl, container, false);

        mListView = (ListView) v.findViewById(R.id.wl_c_listview);



        myArrayAdapterc adapter = new myArrayAdapterc(getActivity().getApplicationContext(),c_title,c_images,c_message);
        mListView.setAdapter(adapter);



        return v;
    }

}

class myArrayAdapterc extends ArrayAdapter<String>
{
    Context mContext;
    int[] imagesarray;
    String[] titlearray;
    String[] messagearray;

    myArrayAdapterc(Context c,String[] wl_c_title,int img[],String[] mssg)
    {
        super(c,R.layout.single_row_wl,R.id.wl_title,wl_c_title);
        this.mContext=c;
        this.imagesarray=img;
        this.titlearray=wl_c_title;
        this.messagearray=mssg;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_wl,parent,false);

        ImageView imagec = (ImageView) row.findViewById(R.id.wl_image);
        TextView titlec = (TextView) row.findViewById(R.id.wl_title);
        TextView messagec = (TextView) row.findViewById(R.id.wl_message);

        imagec.setImageResource(imagesarray[position]);
        titlec.setText(titlearray[position]);
        messagec.setText(messagearray[position]);

        return row;
    }


}