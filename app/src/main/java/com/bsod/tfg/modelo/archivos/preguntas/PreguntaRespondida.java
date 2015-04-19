package com.bsod.tfg.modelo.archivos.preguntas;

import java.io.Serializable;

/**
 * Proudly created by Payton on 22/11/2014.
 */
public abstract class PreguntaRespondida implements Serializable {

    protected int i;

    public int geti() {
        return i;
    }

    public void seti(int id) {
        this.i = id;
    }


}
