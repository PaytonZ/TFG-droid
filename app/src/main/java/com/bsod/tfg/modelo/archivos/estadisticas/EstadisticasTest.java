package com.bsod.tfg.modelo.archivos.estadisticas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Proudly created by Payton on 21/03/2015.
 */
public class EstadisticasTest implements Serializable {

    @JsonProperty("fecha")
    private long fecha;
    @JsonProperty("answers")
    private int preguntas;
    @JsonProperty("mark")
    private float nota;
    @JsonProperty("time")
    private float tiempo;
    @JsonProperty("correct")
    private int acertadas;
    @JsonProperty("failed")
    private int falladas;
    @JsonProperty("not_answered")
    private int noRespondidas;

    @JsonIgnore
    private Date date;

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public int getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(int preguntas) {
        this.preguntas = preguntas;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public float getTiempo() {
        return tiempo;
    }

    public void setTiempo(float tiempo) {
        this.tiempo = tiempo;
    }

    public int getAcertadas() {
        return acertadas;
    }

    public void setAcertadas(int acertadas) {
        this.acertadas = acertadas;
    }

    public int getFalladas() {
        return falladas;
    }

    public void setFalladas(int falladas) {
        this.falladas = falladas;
    }

    public int getNoRespondidas() {
        return noRespondidas;
    }

    public void setNoRespondidas(int noRespondidas) {
        this.noRespondidas = noRespondidas;
    }


    public Date getDate() {
        return (date == null) ? date = new Date(fecha * 1000L) : date; // *1000 is to convert seconds to milliseconds ;
    }

    public void setDate(Date creationDate) {
        this.date = creationDate;
    }
}
