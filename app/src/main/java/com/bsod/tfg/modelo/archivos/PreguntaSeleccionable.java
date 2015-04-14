package com.bsod.tfg.modelo.archivos;

import com.bsod.tfg.modelo.archivos.Pregunta;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 14/04/2015.
 */
public class PreguntaSeleccionable extends Pregunta {


    @JsonProperty("respuesta1")
    private String respuesta1;
    @JsonProperty("respuesta2")
    private String respuesta2;
    @JsonProperty("respuesta3")
    private String respuesta3;
    @JsonProperty("respuesta4")
    private String respuesta4;
    @JsonProperty("respuesta5")
    private String respuesta5;

    public String getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    public String getRespuesta4() {
        return respuesta4;
    }

    public void setRespuesta4(String respuesta4) {
        this.respuesta4 = respuesta4;
    }

    public String getRespuesta5() {
        return respuesta5;
    }

    public void setRespuesta5(String respuesta5) {
        this.respuesta5 = respuesta5;
    }

}
