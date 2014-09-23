package com.bsod.tfg.vista;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Payton on 23/09/2014.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        if (index < 3) {
            switch (index) {
                case 0:
                    return FragmentTablon.newInstance();
                case 1:
                    return FragmentChat.newInstance();
                case 2:
                    return FragmentArchivos.newInstance();

            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
