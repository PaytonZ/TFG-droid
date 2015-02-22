package com.bsod.tfg.utils;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Proudly created by Payton on 09/11/2014.
 */
public class ManagerFragment {

    private final ArrayList<Fragment> fragments;

    public ManagerFragment() {
        this.fragments = new ArrayList<Fragment>();
    }

    public ArrayList<Fragment> getFragments() {
        return this.fragments;
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    public void removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
    }

    public void removeLastFragment() {
        this.fragments.remove(fragments.size() - 1);
    }

    public Fragment getLastFragment() {
        return this.fragments.get(fragments.size() - 1);
    }

    public Fragment getFragmentAtIndex(int i) {
        return this.fragments.get(i);
    }

    public int size() {
        return fragments.size();
    }
}
