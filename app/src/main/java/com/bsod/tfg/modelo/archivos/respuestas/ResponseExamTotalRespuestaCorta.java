package com.bsod.tfg.modelo.archivos.respuestas;

import java.util.HashMap;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamTotalRespuestaCorta extends ResponseExamTotal {

    private HashMap<Integer, String> questions;

    public HashMap<Integer, String> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, String> questions) {
        this.questions = questions;
    }
}
