package com.zenjin.watchlist.watchlist;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

/**
 * Created by Rinesh Ramadhin on 16-05-2014 13:00.
 * com.zenjin.watchlist.watchlist
 * Watch List
 */
public class WatchlistActivity extends MyWatchList implements ActionBar.TabListener {

    ViewPager mViewPager;
    ActionBar mActionBar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.activity_watchlist);

        mViewPager= (ViewPager) findViewById(R.id.wl_pager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new WL_myAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = mActionBar.newTab();
        tab1.setText(R.string.wl_tab1);
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = mActionBar.newTab();
        tab2.setText(R.string.wl_tab2);
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = mActionBar.newTab();
        tab3.setText(R.string.wl_tab3);
        tab3.setTabListener(this);
        mActionBar.addTab(tab1);
        mActionBar.addTab(tab2);
        mActionBar.addTab(tab3);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mywatchlist_activity, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search){
            onSearchRequested();
            //Intent intent = new Intent (WatchlistActivity.this,SearchActivity.class);
            //startActivity(intent);
        }
        if (id == R.id.action_refresh){

            int x = mViewPager.getCurrentItem();
            switch (x){
                case 0: // this;
                    break;
                case 1: // this;
                    break;
                case 2: // this;
                    break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}

class WL_myAdapter extends FragmentPagerAdapter
{
    public WL_myAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int fragment_position) {
        Fragment fragment = null;
        switch (fragment_position){
            case 0: fragment = new WL_Fragment_a();
                break;
            case 1: fragment = new WL_Fragment_b();
                break;
            case 2: fragment = new WL_Fragment_c();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }


}
