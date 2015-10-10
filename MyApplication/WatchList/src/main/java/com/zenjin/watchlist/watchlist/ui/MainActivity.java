package com.zenjin.watchlist.watchlist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseUser;
import com.zenjin.watchlist.watchlist.R;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        Parse.initialize(this, getString(R.string.app_key), getString(R.string.client_key));

        if (ParseUser.getCurrentUser() != null) {
            startActivity(new Intent(this, WatchlistActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, SignUpOrLoginInActivity.class));
        }
    }
}




