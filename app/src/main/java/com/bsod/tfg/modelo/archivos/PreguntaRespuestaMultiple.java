package com.bsod.tfg.modelo.archivos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 14/04/2015.
 */
public class  PreguntaRespuestaMultiple extends PreguntaSeleccionable {

    @JsonProperty("respuestasCorrectas")
    private int[] respuestasCorrectas;

    public int[] getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public void setRespuestasCorrectas(int[] respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }
}
