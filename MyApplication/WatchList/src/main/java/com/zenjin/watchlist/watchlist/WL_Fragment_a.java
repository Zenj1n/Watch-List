package com.zenjin.watchlist.watchlist;



import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class WL_Fragment_a extends Fragment {


    public WL_Fragment_a() {
        // Required empty public constructor
    }


    String[] watching_title;
    String[] watching_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_wl, container, false);

        Resources res;
        res = getResources();

        watching_title= res.getStringArray(R.array.wl_watching_title);
        watching_message= res.getStringArray(R.array.wl_watching_message);



    }


}
