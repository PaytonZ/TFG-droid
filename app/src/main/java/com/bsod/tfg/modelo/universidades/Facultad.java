package com.bsod.tfg.modelo.universidades;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 04/11/2014.
 */
public class Facultad implements Serializable {

    @JsonProperty("pk")
    private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("universidad")
    private Universidad uni;

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

    public Universidad getUni() {
        return uni;
    }

    public void setUni(Universidad uni) {
        this.uni = uni;
    }
}
