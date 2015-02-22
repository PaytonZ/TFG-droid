package com.bsod.tfg.modelo;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 06/11/2014.
 */
public class Asignatura {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("abreviatura")
    private String abreviatura;


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
}
