package com.zenjin.watchlist.watchlist;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Rinesh Ramadhin on 16-05-2014 13:00.
 * com.zenjin.watchlist.watchlist
 * Watch List
 */
public class WatchlistActivityFragment extends Fragment implements FragmentTabHost {

    ViewPager mViewPager;
    ActionBar mActionBar;

    private LinearLayout ll;
    private FragmentActivity fa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fa = super.getActivity();
        ll = (LinearLayout) inflater.inflate(R.layout.activity_main_main, container, false);

        return ll;

        mViewPager= (ViewPager) fa.findViewById(R.id.wl_pager);
        mViewPager.setAdapter(new WL_myAdapter(fa.getSupportFragmentManager()));
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



        mActionBar = fa.getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        ActionBar.Tab tab1 = mActionBar.newTab();
        tab1.setText(R.string.wl_tab1);
        tab1.setTabListener((ActionBar.TabListener) this);

        ActionBar.Tab tab2 = mActionBar.newTab();
        tab2.setText(R.string.wl_tab2);
        tab2.setTabListener((ActionBar.TabListener) this);

        ActionBar.Tab tab3 = mActionBar.newTab();
        tab3.setText(R.string.wl_tab3);
        tab3.setTabListener((ActionBar.TabListener) this);

        mActionBar.addTab(tab1);
        mActionBar.addTab(tab2);
        mActionBar.addTab(tab3);


    }



    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        fa.getMenuInflater().inflate(R.menu.main, menu);
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


    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());

    }


    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        // do something

    }


    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        // do something

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
