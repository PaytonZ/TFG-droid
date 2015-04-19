package com.bsod.tfg.modelo.archivos.preguntas;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 14/04/2015.
 */
public class PreguntaRespuestaUnica extends PreguntaSeleccionable {

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
