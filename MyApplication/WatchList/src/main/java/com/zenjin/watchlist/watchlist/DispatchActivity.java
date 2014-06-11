package com.zenjin.watchlist.watchlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseUser;


/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class DispatchActivity extends Activity {

    public DispatchActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "cbrzBhn5G4akqqJB5bXOF6X1zCMfbRQsce7knkZ6", "Z6VQMULpWaYibP77oMzf0p2lgcWsxmhbi8a0tIs6");
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MyWatchList.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SignUpOrLoginInActivity.class));
        }
    }

}

