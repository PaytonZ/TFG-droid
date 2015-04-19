package com.bsod.tfg.modelo.archivos.preguntas;

import java.util.ArrayList;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class PreguntaRespondidaMultiple extends PreguntaRespondida {

    private ArrayList<Integer> r;

    public ArrayList<Integer> getr() {
        return r;
    }

    public void setr(ArrayList<Integer> respuesta) {
        this.r = respuesta;
    }
}
