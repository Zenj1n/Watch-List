package com.zenjin.watchlist.watchlist;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.zenjin.watchlist.watchlist.adapter.NavDrawerListAdapter;
import com.zenjin.watchlist.watchlist.model.NavDrawerItem;

import java.util.ArrayList;



public class MyWatchList extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    //Context context = getApplicationContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mTitle = mDrawerTitle = getTitle();

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

        // MyWatchList
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // My Profile
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // News Feed
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Browse
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // Log Out
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }


    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent launchIntent = null;
            switch (position) {
                case 0:
                    launchIntent = new Intent(getApplicationContext(), WatchlistActivity.class);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;

                case 1:
                    /*launchIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);*/
                    Toast.makeText(getApplicationContext(),"Feature not available yet",Toast.LENGTH_SHORT).show();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;

                case 2:
                    /*launchIntent = new Intent(getApplicationContext(), NewsFeedActivity.class);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);*/
                        Toast.makeText(getApplicationContext(),"Feature not available yet",Toast.LENGTH_SHORT).show();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;

                case 3:
                    launchIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;


                case 4:
                    /*launchIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);*/
                    Toast.makeText(getApplicationContext(),"Feature not available yet",Toast.LENGTH_SHORT).show();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;

                case 5:
                    ParseUser.logOut();
                    Intent intent3 = new Intent(MyWatchList.this, MainActivity.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent3);
                    break;


            }
            if (launchIntent == null) {
                mDrawerLayout.closeDrawer(mDrawerList);
                return;
            }
            startActivity(launchIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }




    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}