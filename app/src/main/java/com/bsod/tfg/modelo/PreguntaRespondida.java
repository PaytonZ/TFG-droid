package com.bsod.tfg.modelo;

import java.io.Serializable;

/**
 * Proudly created by Payton on 22/11/2014.
 */
public class PreguntaRespondida implements Serializable {

    private int i;
    private int r;

    public int geti() {
        return i;
    }

    public void seti(int id) {
        this.i = id;
    }

    public int getr() {
        return r;
    }

    public void setr(int respuesta) {
        this.r = respuesta;
    }
}
