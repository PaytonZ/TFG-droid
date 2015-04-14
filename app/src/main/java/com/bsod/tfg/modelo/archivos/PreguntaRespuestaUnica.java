package com.bsod.tfg.modelo.archivos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 14/04/2015.
 */
public class PreguntaRespuestaUnica extends PreguntaSeleccionable {

    @JsonProperty("respuestaCorrecta")
    private int respuestasCorrecta;

    public int getRespuestasCorrecta() {
        return respuestasCorrecta;
    }

    public void setRespuestasCorrecta(int respuestasCorrecta) {
        this.respuestasCorrecta = respuestasCorrecta;
    }
}
