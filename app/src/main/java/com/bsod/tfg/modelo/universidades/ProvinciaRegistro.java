package com.bsod.tfg.modelo.universidades;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;

import java.io.Serializable;

/**
 * Created by Payton on 13/10/2014.
 */
public class ProvinciaRegistro extends GenericType implements Serializable {
    @Override
    public String selectOneText() {
        return App.getContext().getResources().getString(R.string.selecciona_provincia);
    }


}
