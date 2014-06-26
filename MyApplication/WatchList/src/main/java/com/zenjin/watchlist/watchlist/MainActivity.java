package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
            Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");
            // Check if there is current user info
            if (ParseUser.getCurrentUser() != null) {
                // Start an intent for the logged in activity
                startActivity(new Intent(this, WatchlistActivity.class));
            } else {
                // Start and intent for the logged out activity
                startActivity(new Intent(this, SignUpOrLoginInActivity.class));
            }
        }
 }
