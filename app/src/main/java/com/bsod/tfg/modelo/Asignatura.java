package com.bsod.tfg.modelo;

import org.codehaus.jackson.annotate.JsonProperty;

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


}
