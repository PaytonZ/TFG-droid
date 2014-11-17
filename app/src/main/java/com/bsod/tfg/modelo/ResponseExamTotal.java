package com.bsod.tfg.modelo;

/**
 * Proudly created by Payton on 17/11/2014.
 */

import java.util.Map;

/**
 * Clase que encapusla un resultado final de un examen
 */
public class ResponseExamTotal {

    private double finalMark;
    private int numOfQuestions;
    private Map<Integer, Integer> questions;

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

    public Map<Integer, Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Integer, Integer> questions) {
        this.questions = questions;
    }
}
