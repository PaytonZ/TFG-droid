package com.bsod.tfg.modelo;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;

/**
 * Proudly created by Payton on 14/10/2014.
 */
public class FacultadRegistro extends GenericType {
    @Override
    public String selectOneText() {
        return App.getContext().getResources().getString(R.string.selecciona_facultad);
    }
}
