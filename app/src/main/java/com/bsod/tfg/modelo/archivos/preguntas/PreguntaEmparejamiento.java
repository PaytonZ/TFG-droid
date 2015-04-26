package com.bsod.tfg.modelo.archivos.preguntas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 26/04/2015.
 */
public class PreguntaEmparejamiento extends Pregunta implements Serializable {

    @JsonProperty("pareja11")
    private String pareja11;
    @JsonProperty("pareja12")
    private String pareja12;
    @JsonProperty("pareja13")
    private String pareja13;
    @JsonProperty("pareja21")
    private String pareja21;
    @JsonProperty("pareja22")
    private String pareja22;
    @JsonProperty("pareja23")
    private String pareja23;

    public String getPareja11() {
        return pareja11;
    }

    public void setPareja11(String pareja11) {
        this.pareja11 = pareja11;
    }

    public String getPareja12() {
        return pareja12;
    }

    public void setPareja12(String pareja12) {
        this.pareja12 = pareja12;
    }

    public String getPareja13() {
        return pareja13;
    }

    public void setPareja13(String pareja13) {
        this.pareja13 = pareja13;
    }

    public String getPareja21() {
        return pareja21;
    }

    public void setPareja21(String pareja21) {
        this.pareja21 = pareja21;
    }

    public String getPareja22() {
        return pareja22;
    }

    public void setPareja22(String pareja22) {
        this.pareja22 = pareja22;
    }

    public String getPareja23() {
        return pareja23;
    }

    public void setPareja23(String pareja23) {
        this.pareja23 = pareja23;
    }
}
