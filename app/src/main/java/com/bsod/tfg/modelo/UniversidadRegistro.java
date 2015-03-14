package com.bsod.tfg.modelo;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;

import java.io.Serializable;

/**
 * Created by Payton on 09/10/2014.
 */
public class UniversidadRegistro extends GenericType implements Serializable {


    @Override
    public String selectOneText() {


        return App.getContext().getResources().getString(R.string.selecciona_universidad);
    }
}
