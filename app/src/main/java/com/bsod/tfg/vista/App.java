package com.bsod.tfg.vista;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.bsod.tfg.modelo.sesion.PreferencesManager;
import com.bsod.tfg.modelo.sesion.Session;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Payton on 09/10/2014.
 * Taken from http://yakivmospan.wordpress.com/2014/04/17/best-practice-application/
 */
public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // your application starts from here
        mContext = this;
        initModel();
    }

    private void initModel() {
        Context applicationContext = getApplicationContext();
        PreferencesManager.initializeInstance(applicationContext);
        Session.loadPreferences();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)

                .build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(41943040)
                .threadPoolSize(10)
                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        //TODO: Cuando la cache funcione bien , quitar eso
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
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
