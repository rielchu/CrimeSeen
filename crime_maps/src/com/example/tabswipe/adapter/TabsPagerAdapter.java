package com.example.tabswipe.adapter;

import com.example.crime_maps.RecentFragment;
import com.example.crime_maps.HotFragment;
import com.example.crime_maps.MapFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new RecentFragment();
        case 1:
            // Games fragment activity
            return new HotFragment();
        case 2:
            // Movies fragment activity
            return new MapFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
    
    @Override
    public int getItemPosition(Object object) {
         return POSITION_NONE;
    }
 
}