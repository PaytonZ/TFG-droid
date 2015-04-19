package com.bsod.tfg.modelo.archivos.respuestas;

import java.util.HashMap;
import java.util.List;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamTotalMultiRespuesta extends ResponseExamTotal {
    private HashMap<Integer, List<Integer>> questions;

    public HashMap<Integer, List<Integer>> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, List<Integer>> questions) {
        this.questions = questions;
    }
}
