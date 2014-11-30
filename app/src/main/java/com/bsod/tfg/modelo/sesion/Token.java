package com.bsod.tfg.modelo.sesion;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Proudly created by Payton on 04/11/2014.
 */
public class Token {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("token")
    private String token;
    @JsonProperty("expirationdate")
    private long expirationDateUnix;
    private Date expirationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpirationDateUnix() {
        return expirationDateUnix;
    }

    public void setExpirationDateUnix(long expirationDateUnix) {
        this.expirationDateUnix = expirationDateUnix;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
