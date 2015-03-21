package com.bsod.tfg.modelo.archivos;

/**
 * Proudly created by Payton on 17/11/2014.
 */

import android.util.SparseIntArray;

import java.io.Serializable;

/**
 * Clase que encapusla un resultado final de un examen
 */
public class ResponseExamTotal implements Serializable {

    private int idTest;
    private double finalMark;
    private int numOfQuestions;
    private SparseIntArray questions;
    private long time;


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


    public SparseIntArray getQuestions() {
        return questions;
    }

    public void setQuestions(SparseIntArray questions) {
        this.questions = questions;
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
}
