package com.bsod.tfg.modelo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 06/11/2014.
 */
public class Asignatura implements Serializable {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("abreviatura")
    private String abreviatura;
    @JsonProperty("user_favorited")
    private Boolean user_favorited;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String toString() {
        return getNombre() + " (" + getAbreviatura() + ')';
    }

    public Boolean getUser_favorited() {
        return user_favorited;
    }

    public void setUser_favorited(Boolean user_favorited) {
        this.user_favorited = user_favorited;
    }
}
