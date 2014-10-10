package com.bsod.tfg.vista;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.preference.Preference;

import com.bsod.tfg.modelo.PreferencesManager;
import com.bsod.tfg.modelo.Session;

/**
 * Created by Payton on 09/10/2014.
 * Taken from http://yakivmospan.wordpress.com/2014/04/17/best-practice-application/
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // your application starts from here
        initModel();
    }

    private void initModel() {
        Context applicationContext = getApplicationContext();

        PreferencesManager.initializeInstance(applicationContext);
        Session.loadPreferences();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // This is called when the overall system is running low on memory
        // and actively running processes should trim their memory usage
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Called by the system when the device configuration changes while your
        // component is running. Unlike activities Application doesn't restart when
        // a configuration changes
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        // This method is for use in emulated process environments only.
        // You can simply forget about it because it will never be called on real device
    }
}
