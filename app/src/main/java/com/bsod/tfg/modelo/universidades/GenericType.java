package com.bsod.tfg.modelo.universidades;

/**
 * Created by Payton on 13/10/2014.
 */
public abstract class GenericType {

    protected Integer id;
    protected String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract String selectOneText();

}
