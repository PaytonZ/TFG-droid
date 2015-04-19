package com.bsod.tfg.modelo.archivos.respuestas;

/**
 * Proudly created by Payton on 17/11/2014.
 */

import java.io.Serializable;

/**
 * Clase que encapusla un resultado final de un examen
 */
public abstract class ResponseExamTotal implements Serializable {

    private int idTest;
    private double finalMark;
    private int numOfQuestions;
    private long time;
    private int typeofQuestions;

    public double getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(double finalMark) {
        this.finalMark = finalMark;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }


    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTypeofQuestions() {
        return typeofQuestions;
    }

    public void setTypeofQuestions(int typeofQuestions) {
        this.typeofQuestions = typeofQuestions;
    }
}
