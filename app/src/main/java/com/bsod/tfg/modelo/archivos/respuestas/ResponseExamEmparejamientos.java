package com.bsod.tfg.modelo.archivos.respuestas;

/**
 * Proudly created by Payton on 28/04/2015.
 */
public class ResponseExamEmparejamientos extends ResponseExam {

    private int respuesta[][] = new int[3][2];

    public int[][] getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int[][] respuesta) {
        this.respuesta = respuesta;
    }
}
