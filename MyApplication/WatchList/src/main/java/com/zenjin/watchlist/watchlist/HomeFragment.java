package com.zenjin.watchlist.watchlist;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

    Button BMyWatchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hm_activity, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        BMyWatchList = (Button) getView().findViewById(R.id.BMyWatchList);
        BMyWatchList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WatchlistActivity.class);
                startActivity(intent);
            }
        });
    }
}








