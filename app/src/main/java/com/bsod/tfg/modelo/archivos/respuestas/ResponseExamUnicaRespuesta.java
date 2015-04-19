package com.bsod.tfg.modelo.archivos.respuestas;

/**
 * Proudly created by Payton on 19/04/2015.
 */
public class ResponseExamUnicaRespuesta extends ResponseExam {
    private int selectedOption;

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
