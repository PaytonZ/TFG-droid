package com.bsod.tfg.modelo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Proudly created by Payton on 04/11/2014.
 */
public class Universidad {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("provincia")
    private Provincia provincia;

}
