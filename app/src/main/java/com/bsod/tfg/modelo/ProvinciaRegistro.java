package com.bsod.tfg.modelo;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;

/**
 * Created by Payton on 13/10/2014.
 */
public class ProvinciaRegistro extends GenericType {
    @Override
    public String selectOneText() {
        return App.getContext().getResources().getString(R.string.selecciona_provincia);
    }


}
