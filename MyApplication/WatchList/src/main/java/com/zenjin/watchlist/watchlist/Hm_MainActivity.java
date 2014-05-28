package com.zenjin.watchlist.watchlist;



import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Hm_MainActivity extends Activity {

    Button BMyWatchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hm_activity);

        BMyWatchList = (Button)findViewById(R.id.BMyWatchList);
        BMyWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hm_MainActivity.this, WatchlistActivity.class);
                startActivity(intent);
            }
        });


    }
}