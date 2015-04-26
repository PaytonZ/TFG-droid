package com.bsod.tfg.modelo.archivos.respuestas;

import java.io.Serializable;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamRespuestaCorta extends ResponseExam implements Serializable {

    private String respuesta;

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
