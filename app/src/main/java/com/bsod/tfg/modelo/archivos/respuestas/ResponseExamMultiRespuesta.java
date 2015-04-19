package com.bsod.tfg.modelo.archivos.respuestas;

import java.util.List;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamMultiRespuesta extends ResponseExam {

    private List<Integer> selectedOptions;

    public List<Integer> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<Integer> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
