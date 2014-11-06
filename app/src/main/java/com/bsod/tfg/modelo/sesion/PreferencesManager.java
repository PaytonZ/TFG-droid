package com.bsod.tfg.modelo.sesion;

import android.content.Context;
import android.content.SharedPreferences;

import com.bsod.tfg.modelo.Facultad;
import com.google.gson.Gson;

/**
 * Created by Payton on 08/10/2014.
 * Taken from http://yakivmospan.wordpress.com/2014/03/11/best-practice-sharedpreferences/
 * Makes the preferences persistence in a file.
 */
public class PreferencesManager {


    private static final String USER = "com.bsod.tfg.USER";
    private static final String TOKEN = "com.bsod.tfg.TOKEN";
    private static final String FACULTAD = "com.bsod.tfg.FACULTAD";

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


    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

    public Token getToken() {
        Gson gson = new Gson();
        String json = mPref.getString(TOKEN, "");
        return gson.fromJson(json, Token.class);
    }

    public void setToken(Token token) {
        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(token);
        prefsEditor.putString(TOKEN, json);
        prefsEditor.commit();
    }

    public Facultad getFacultad() {
        Gson gson = new Gson();
        String json = mPref.getString(FACULTAD, "");
        return gson.fromJson(json, Facultad.class);
    }

    public void setFacultad(Facultad facultad) {

        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(facultad);
        prefsEditor.putString(FACULTAD, json);
        prefsEditor.commit();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = mPref.getString(USER, "");
        return gson.fromJson(json, User.class);
    }

    public void setUser(User user) {
        SharedPreferences.Editor prefsEditor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(USER, json);
        prefsEditor.commit();
    }
}