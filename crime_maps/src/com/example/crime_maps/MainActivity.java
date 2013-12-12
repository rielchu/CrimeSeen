package com.example.crime_maps;

import com.example.tabswipe.adapter.TabsPagerAdapter;
import com.example.crime_maps.R;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
 
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private Button addButton;
    
    private MenuItem refreshMenuItem;
    
    private String[] tabs = {"Recent","Hot","Map"};
    
    private ConnectionDetector cd;
    private Boolean isInternetPresent;
    private Boolean isMobileDataPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cd = new ConnectionDetector(MainActivity.this);        
        isInternetPresent = cd.isConnectingToInternet();  
        isMobileDataPresent = cd.isMobileDataEnabled();
      
        if(!isInternetPresent&&!isMobileDataPresent)
        {	
        //cd.createNetErrorDialog();
        	
        }
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
 
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
                mAdapter.notifyDataSetChanged();
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    
    
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_search:
            // search action
        	LocationFound();
            return true;
            
        case R.id.action_add_report:
            // add report
            AddReport();
            return true;
            
        case R.id.action_refresh:
            refreshMenuItem = item;
            // load the data from server
            new SyncData().execute();
            return true;
            
        case R.id.action_view_profile:
            // view profile
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    private void LocationFound() {
        Intent i = new Intent(MainActivity.this, LocationFound.class);
        
        startActivity(i);
    }
    
    private void AddReport() {
        Intent i = new Intent(MainActivity.this, AddReportIncidentActivity.class);
        startActivityForResult(i, 100);
    }

    
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
        	
         MenuItemCompat.setActionView(refreshMenuItem,R.layout.action_progressbar);
 
         MenuItemCompat.expandActionView(refreshMenuItem);
        
        }
 
        @Override
        protected String doInBackground(String... params) {
            // for now we use a timer to wait for sometime
            try {
            	mAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(String result) {
            MenuItemCompat.collapseActionView(refreshMenuItem);
            // remove the progress bar view
            MenuItemCompat.setActionView(refreshMenuItem,null);
        }
    };
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
	    super.onActivityResult(requestCode, resultCode, data);

	     // Toast.makeText(this,resultCode+" "+requestCode,Toast.LENGTH_LONG).show();

	    if(resultCode ==  200)
	    {

	        //String enteredText = "no action defined for this requestcode :"+requestCode;
	        if(requestCode == 100)
	        {
	            
	  	     // Toast.makeText(this,"test",Toast.LENGTH_LONG).show();
	        	
	            mAdapter.notifyDataSetChanged();
	  	      
	  	    
	        }
	    }
	    else
	    {
	       // Toast.makeText(this,"some exception occurred",Toast.LENGTH_SHORT).show();
	    }
	}
 
}