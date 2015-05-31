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
    public static final String ID_FIELD_NAME = "id";
    public static final String IDUSER_FIELD_NAME = "idUser";
    public static final String NAME_FIELD_NAME = "name";
    public static final String IMAGE_FIELD_NAME = "image";
    public static final String IS_TEACHER_FIELD_NAME = "is_teacher";
    @DatabaseField(columnName = ID_FIELD_NAME, generatedId = true)
    private int id;
    @DatabaseField(columnName = IDUSER_FIELD_NAME, index = true)
    @JsonProperty("pk")
    private int idUser;
    @DatabaseField(columnName = NAME_FIELD_NAME)
    @JsonProperty("username")
    private String name;
    @DatabaseField(columnName = IMAGE_FIELD_NAME)
    @JsonProperty("image")
    private String image;
    @DatabaseField(columnName = IS_TEACHER_FIELD_NAME)
    @JsonProperty("is_teacher")
    private boolean isTeacher = false;

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
        return (image == null) ? "" : image;
    }

    public void setPicImageUrl(String picImageUrl) {
        this.image = picImageUrl;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
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
