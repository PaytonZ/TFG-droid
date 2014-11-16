package com.bsod.tfg.controlador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Proudly created by Payton on 16/11/2014.
 */
public class AdapterFragmentExams extends FragmentPagerAdapter {


    private ArrayList<Fragment> list;

    public AdapterFragmentExams(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);

    }

    @Override
    public int getCount() {
        return list.size();
    }
}
