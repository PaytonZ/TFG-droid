package com.bsod.tfg.modelo.archivos.respuestas;

/**
 * Proudly created by Payton on 17/11/2014.
 */

import java.io.Serializable;

/**
 * Clase encargada de encapulsar las respuestas de las distintas preguntas
 */
public abstract class ResponseExam implements Serializable {
    /**
     * Valor de la respuesta
     * 0 si no respondida
     * -1 si respondida , pero erronea
     * +1 si respondida correcta
     */
    protected double value;
    /**
     * Identificador de la pregunta
     */
    protected int id;


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Id de la pregunta fallada
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
