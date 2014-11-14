package com.bsod.tfg.modelo;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 14/11/2014.
 */
public class Pregunta implements Serializable {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("enunciado")
    private String pregunta;
    @JsonProperty("respuesta1")
    private String respuesta1;
    @JsonProperty("respuesta2")
    private String respuesta2;
    @JsonProperty("respuesta3")
    private String respuesta3;
    @JsonProperty("respuesta4")
    private String respuesta4;
    @JsonProperty("respuestaCorrecta")
    private int respuestaCorrecta;

}
