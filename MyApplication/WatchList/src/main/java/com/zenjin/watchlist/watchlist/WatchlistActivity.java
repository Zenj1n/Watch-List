package com.zenjin.watchlist.watchlist;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Rinesh Ramadhin on 16-05-2014 13:00.
 * com.zenjin.watchlist.watchlist
 * Watch List
 */
public class WatchlistActivity extends FragmentActivity implements ActionBar.TabListener {

    ViewPager mViewPager;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        mViewPager= (ViewPager) findViewById(R.id.wl_pager);
        mViewPager.setAdapter(new myAdapter(getSupportFragmentManager()));
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

        //TODO: add non nard coded texts.

        ActionBar.Tab tab1 = mActionBar.newTab();
        tab1.setText("Watching");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = mActionBar.newTab();
        tab2.setText("Completed");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = mActionBar.newTab();
        tab3.setText("Plan to watch");
        tab3.setTabListener(this);

        mActionBar.addTab(tab1);
        mActionBar.addTab(tab2);
        mActionBar.addTab(tab3);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        // do something

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        // do something

    }
}

class myAdapter extends FragmentPagerAdapter
{

    public myAdapter(FragmentManager fm) {
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
