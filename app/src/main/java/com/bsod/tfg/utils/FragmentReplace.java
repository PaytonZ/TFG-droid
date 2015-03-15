package com.bsod.tfg.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;

/**
 * Proudly created by Payton on 15/03/2015.
 */
public class FragmentReplace {

    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.fragment, fragment, Constants.TFG_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
