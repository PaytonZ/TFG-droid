package com.bsod.tfg.modelo.archivos.preguntas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Proudly created by Payton on 14/04/2015.
 */
public class PreguntaRespuestaMultiple extends PreguntaSeleccionable {

    @JsonProperty("respuestasCorrectas")
    private List<Integer> respuestasCorrectas;

    public List<Integer> getRespuestasCorrectas() {
        return respuestasCorrectas;
    }
    public void setRespuestasCorrectas(List<Integer> respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    @Override
    public boolean isMultiAnswer() {
        return true;
    }
}
