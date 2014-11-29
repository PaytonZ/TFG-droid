package com.bsod.tfg.modelo.sesion;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 26/10/2014.
 */
public class User implements Serializable {
    @JsonProperty("pk")
    private int idUser;
    @JsonProperty("username")
    private String name;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
