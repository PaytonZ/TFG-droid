package com.bsod.tfg.vista;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Payton on 23/09/2014.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private static int NUM_OF_TABS = 3;
    private static final String TAG = "TabsAdapter";

    private Fragment[] fragmentList;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new Fragment[3];
        fragmentList[0] = FragmentTablon.newInstance();
        fragmentList[1] = FragmentChat.newInstance();
        fragmentList[2] = FragmentArchivos.newInstance();

    }

    @Override
    public Fragment getItem(int index) {

        Log.i(TAG, String.valueOf(index));
        Fragment f = null;
        if (index < NUM_OF_TABS) {
            f = fragmentList[index];
        }
        return f;
    }

    @Override
    public int getCount() {
        return NUM_OF_TABS;
    }
}
