package com.bsod.tfg.modelo.archivos.preguntas;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 14/11/2014.
 */

public abstract class Pregunta implements Serializable {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("enunciado")
    private String pregunta;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("tema")
    private int tema;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTema() {
        return tema;
    }

    public void setTema(int tema) {
        this.tema = tema;
    }
}
