package com.zenjin.watchlist.watchlist;



import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;


public class Hm_MainActivity extends FragmentActivity implements ActionBar.TabListener {

    ViewPager viewPager;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ViewPager om te swipen
        viewPager= (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new Hm_MyAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Actionbars / Tabs
        actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab overview_tab=actionBar.newTab();
        overview_tab.setText("Overview");
        overview_tab.setTabListener(this);

        ActionBar.Tab discover_tab=actionBar.newTab();
        discover_tab.setText("Discover");
        discover_tab.setTabListener(this);

        ActionBar.Tab agenda_tab=actionBar.newTab();
        agenda_tab.setText("Agenda");
        agenda_tab.setTabListener(this);

        actionBar.addTab(overview_tab);
        actionBar.addTab(discover_tab);
        actionBar.addTab(agenda_tab);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

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
}



class Hm_MyAdapter extends FragmentPagerAdapter
{

    public Hm_MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if (position==0)
        {
            fragment=new Overview();
        }
        if (position==1)
        {
            fragment=new Discover();
        }
        if (position==2)
        {
            fragment=new Agenda();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}