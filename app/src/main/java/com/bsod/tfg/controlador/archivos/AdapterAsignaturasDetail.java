package com.bsod.tfg.controlador.archivos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.Asignatura;
import com.bsod.tfg.utils.ViewHolder;

/**
 * Proudly created by Payton on 29/11/2014.
 */
public class AdapterAsignaturasDetail extends AdapterAsignaturas {
    public AdapterAsignaturasDetail(Context context) {
        super(context, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_listasignaturas, parent, false);
        }
        TextView asignatura = ViewHolder.get(convertView, R.id.asignatura_item);
        Asignatura a = getItem(position);

        asignatura.setText(a.toString());

        return convertView;
    }
}
