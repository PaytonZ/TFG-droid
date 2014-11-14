package com.bsod.tfg.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Tema;
import com.bsod.tfg.utils.ViewHolder;

/**
 * Proudly created by Payton on 11/11/2014.
 */
public class AdapterTemas extends AdapterGeneric<Tema> implements AdapterView.OnItemClickListener {

    public AdapterTemas(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            //holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_listasignaturas, parent, false);
        }
        TextView tema = ViewHolder.get(convertView, R.id.asignatura_item);
        Tema t = getItem(position);
        tema.setText(t.toString());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
