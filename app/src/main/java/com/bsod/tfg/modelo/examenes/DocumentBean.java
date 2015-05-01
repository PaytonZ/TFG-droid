package com.bsod.tfg.modelo.examenes;

import com.bsod.tfg.modelo.sesion.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Proudly created by Payton on 28/04/2015.
 */
public class DocumentBean implements Serializable {
    @JsonProperty("pk")
    private int id;
    @JsonProperty("asignatura")
    private int asignatura;
    @JsonProperty("usuario")
    private User usuario;
    @JsonProperty("ano")
    private int anno;
    @JsonProperty("mes")
    private int mes;
    @JsonProperty("tema")
    private int tema;
    @JsonProperty("imagenes")
    private ArrayList<String> imagenes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(int asignatura) {
        this.asignatura = asignatura;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getTema() {
        return tema;
    }

    public void setTema(int tema) {
        this.tema = tema;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }
}
