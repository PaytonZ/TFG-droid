package com.bsod.tfg.modelo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 11/11/2014.
 */
public class Tema implements Serializable {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("nombre")
    private String nombre;

    public String toString() {
        return nombre;
    }

    public int getid() {
        return id;
    }
}
