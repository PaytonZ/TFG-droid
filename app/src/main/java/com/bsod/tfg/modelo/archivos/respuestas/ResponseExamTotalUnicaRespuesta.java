package com.bsod.tfg.modelo.archivos.respuestas;

import android.util.SparseIntArray;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamTotalUnicaRespuesta extends ResponseExamTotal {

    private SparseIntArray questions;

    public SparseIntArray getQuestions() {
        return questions;
    }

    public void setQuestions(SparseIntArray questions) {
        this.questions = questions;
    }
}
