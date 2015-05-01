package com.bsod.tfg.modelo.archivos.respuestas;

import java.util.HashMap;

/**
 * Proudly created by Payton on 01/05/2015.
 */
public class ResponseExamTotalEmparejamientos extends ResponseExamTotal {

    private HashMap<Integer, int[][]> questions = new HashMap<>();

    public HashMap<Integer, int[][]> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, int[][]> questions) {
        this.questions = questions;
    }
}
