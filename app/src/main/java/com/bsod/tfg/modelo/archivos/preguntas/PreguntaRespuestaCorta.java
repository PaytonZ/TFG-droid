package com.bsod.tfg.modelo.archivos.preguntas;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class PreguntaRespuestaCorta extends Pregunta {

    @JsonProperty("respuestaCorta")
    private String respuesta;

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
