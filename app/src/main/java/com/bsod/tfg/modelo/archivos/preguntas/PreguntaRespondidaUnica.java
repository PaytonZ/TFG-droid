package com.bsod.tfg.modelo.archivos.preguntas;

import java.io.Serializable;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class PreguntaRespondidaUnica extends PreguntaRespondida implements Serializable {
    private int r;

    public int getr() {
        return r;
    }

    public void setr(int respuesta) {
        this.r = respuesta;
    }
}
