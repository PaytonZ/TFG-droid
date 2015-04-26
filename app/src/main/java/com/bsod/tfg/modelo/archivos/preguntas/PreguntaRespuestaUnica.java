package com.bsod.tfg.modelo.archivos.preguntas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 14/04/2015.
 */
public class PreguntaRespuestaUnica extends PreguntaSeleccionable implements Serializable {

    @JsonProperty("respuestaCorrecta")
    private int respuestaCorrecta;

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(int respuestasCorrecta) {
        this.respuestaCorrecta = respuestasCorrecta;
    }

    @Override
    public boolean isMultiAnswer() {
        return false;
    }
}
