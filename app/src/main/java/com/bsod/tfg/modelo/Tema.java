package com.bsod.tfg.modelo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Proudly created by Payton on 11/11/2014.
 */
public class Tema {
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
