package com.bsod.tfg.modelo.sesion;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Proudly created by Payton on 26/10/2014.
 */
@DatabaseTable(tableName = "user")
public class User implements Serializable {

    @DatabaseField(columnName = "idUser", index = true , id=true)
    @JsonProperty("pk")
    private int idUser;

    @DatabaseField(columnName = "name")
    @JsonProperty("username")
    private String name;

    @DatabaseField(columnName = "image")
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
