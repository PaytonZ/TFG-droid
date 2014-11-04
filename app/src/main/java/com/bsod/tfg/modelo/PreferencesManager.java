package com.bsod.tfg.modelo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Payton on 08/10/2014.
 * Taken from http://yakivmospan.wordpress.com/2014/03/11/best-practice-sharedpreferences/
 * Makes the preferences persistence in a file.
 */
public class PreferencesManager {


    private static final String USER = "com.bsod.tfg.USER";
    private static final String TOKEN = "com.bsod.tfg.TOKEN";
    private static final String UNIVERSITY_ID = "com.bsod.tfg.UNIVERSITY_ID";
    private static final String UNIVERSITY_NAME = "com.bsod.tfg.UNIVERSITY_NAME";
    private static PreferencesManager sInstance;
    private final String SHARED_PREFERENCES_FILE = "tfg";
    private final SharedPreferences mPref;


    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public String getUser() {

        return mPref.getString(USER, "");
    }

    public void setUser(String value) {
        mPref.edit()
                .putString(USER, value)
                .commit();
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

    public String getToken() {
        return mPref.getString(TOKEN, "");
    }

    public void setToken(String token) {
        mPref.edit()
                .putString(TOKEN, token)
                .commit();
    }

    public FacultadRegistro getFacultad() {
        FacultadRegistro i = new FacultadRegistro();
        i.setId(mPref.getInt(UNIVERSITY_ID, 0));
        i.setName(mPref.getString(UNIVERSITY_NAME, ""));
        return i;
    }

    public void setFacultad(FacultadRegistro universidad) {
        mPref.edit().putString(UNIVERSITY_NAME, universidad.getName()).commit();
        mPref.edit().putInt(UNIVERSITY_ID, universidad.getId()).commit();
    }
}