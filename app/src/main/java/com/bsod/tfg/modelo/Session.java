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
    private University university;
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

    public static void loadPreferences()
    {
        getSession().setUser(PreferencesManager.getInstance().getUser());
        getSession().setToken(PreferencesManager.getInstance().getToken());
        getSession().setUniversity(PreferencesManager.getInstance().getUniversity());
        //getSession().setUser(PreferencesManager.getInstance().getUser());

    }

    public static void persistPreferences()
    {
        PreferencesManager.getInstance().setUser(getSession().getUser());
        PreferencesManager.getInstance().setToken(getSession().getToken());
        PreferencesManager.getInstance().setUniversity(getSession().getUniversity());


    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
