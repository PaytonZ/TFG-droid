package com.bsod.tfg.modelo.archivos;

/**
 * Proudly created by Payton on 17/11/2014.
 */

import java.io.Serializable;

/**
 * Clase encargada de encapulsar las respuestas de las distintas preguntas
 */
public class ResponseExamStats implements Serializable {
    private double value;
    private int id;
    private int selectedOption;

    /**
     * Valor de la respuesta
     * 0 si no respondida
     * -1 si respondida , pero erronea
     * +1 si respondida correcta
     */
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

    /**
     * En caso de ser erronea,  opción que se eligio o -1 si no se eligio ninguna
     */
    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }
}
