package com.bsod.tfg.modelo.archivos.respuestas;

import android.util.SparseIntArray;

import java.io.Serializable;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamTotalUnicaRespuesta extends ResponseExamTotal implements Serializable {

    private SparseIntArray questions;

    public SparseIntArray getQuestions() {
        return questions;
    }

    public void setQuestions(SparseIntArray questions) {
        this.questions = questions;
    }
}
