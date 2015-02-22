package com.bsod.tfg.modelo.sesion;

import com.bsod.tfg.modelo.Facultad;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Payton on 09/10/2014.
 * Represents a singleton Instance for the User Session
 */
public class Session {

    private static Session mSession;

    private User user;
    private Token token;
    private Facultad facultad;
    private ConcurrentHashMap<Integer, User> mapUsers;

    private Session() {
        mapUsers = new ConcurrentHashMap<Integer, User>();
    }

    private static synchronized void createSession() {
        if (getmSession() == null) {
            setmSession(new Session());
        }
    }

    public static Session getSession() {
        createSession();
        return getmSession();
    }

    public static synchronized void destroySession() {
        if (getmSession() != null) {
            setmSession(null);
        }
        PreferencesManager.getInstance().clear();
    }

    public static void loadPreferences() {
        getSession().setUser(PreferencesManager.getInstance().getUser());
        getSession().setToken(PreferencesManager.getInstance().getToken());
        getSession().setFacultad(PreferencesManager.getInstance().getFacultad());
        //getSession().setUser(PreferencesManager.getInstance().getUser());
    }


    public static void persistPreferences() {
        PreferencesManager.getInstance().setUser(getSession().getUser());
        PreferencesManager.getInstance().setToken(getSession().getToken());
        PreferencesManager.getInstance().setFacultad(getSession().getFacultad());
    }


    private static Session getmSession() {
        return mSession;
    }

    private static void setmSession(Session mSession) {
        Session.mSession = mSession;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad f) {
        facultad = f;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConcurrentHashMap<Integer, User> getMapUsers() {
        return mapUsers;
    }

    public void setMapUsers(ConcurrentHashMap<Integer, User> mapUsers) {
        this.mapUsers = mapUsers;
    }
}
