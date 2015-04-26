package com.bsod.tfg.modelo.universidades;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;

import java.io.Serializable;

/**
 * Proudly created by Payton on 14/10/2014.
 */
public class FacultadRegistro extends GenericType implements Serializable {
    @Override
    public String selectOneText() {
        return App.getContext().getResources().getString(R.string.selecciona_facultad);
    }
}
