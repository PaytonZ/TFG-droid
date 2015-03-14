package com.bsod.tfg.modelo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 04/11/2014.
 */
public class Provincia implements Serializable {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("nombre")
    private String nombre;

}
