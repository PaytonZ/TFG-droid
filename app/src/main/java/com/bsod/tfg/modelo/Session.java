package com.bsod.tfg.modelo;

import java.util.Date;

/**
 * Created by Payton on 09/10/2014.
 * Represents a singleton Instance for the User Session
 */
public class Session {

    private static Session mSession;

    private String user;
    private String token;
    private Universidad universidad;
    private Date expiration;

    private Session() {

    }

    private static synchronized void createSession() {
        if (mSession == null) {
            mSession = new Session();
        }
    }

    public static Session getSession() {
        createSession();
        return mSession;
    }

    public static synchronized void destroySession() {
        if (mSession != null) {
            mSession = null;
        }
        PreferencesManager.getInstance().clear();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static void loadPreferences() {
        getSession().setUser(PreferencesManager.getInstance().getUser());
        getSession().setToken(PreferencesManager.getInstance().getToken());
        getSession().setUniversidad(PreferencesManager.getInstance().getUniversity());
        //getSession().setUser(PreferencesManager.getInstance().getUser());

    }

    public static void persistPreferences() {
        PreferencesManager.getInstance().setUser(getSession().getUser());
        PreferencesManager.getInstance().setToken(getSession().getToken());
        PreferencesManager.getInstance().setUniversity(getSession().getUniversidad());


    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
