package com.bsod.tfg.modelo;

import java.util.Date;

/**
 * Created by Payton on 09/10/2014.
 */
public class Session {



    private String user;
    private String token;
    private University university;
    private Date expiration;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
