package com.bsod.tfg.modelo.sesion;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 26/10/2014.
 */
public class User implements Serializable {
    @JsonProperty("pk")
    private int idUser;
    @JsonProperty("username")
    private String name;
    @JsonProperty("image")
    private String image;

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

    public String getPicImageUrl() {
        return image;
    }

    public void setPicImageUrl(String picImageUrl) {
        this.image = picImageUrl;
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof User)) return false;
        User otherMyClass = (User) other;
        return otherMyClass.idUser == this.idUser;
    }

}
