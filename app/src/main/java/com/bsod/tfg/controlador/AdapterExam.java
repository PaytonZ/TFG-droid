package com.bsod.tfg.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsod.tfg.utils.ViewHolder;

/**
 * Proudly created by Payton on 16/11/2014.
 */
public class AdapterExam extends AdapterGeneric<String> {


    public AdapterExam(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_1, parent, false);
        }
        TextView respuesta = ViewHolder.get(convertView, android.R.id.text1);

        String a = getItem(position);

        respuesta.setText(a);

        return convertView;
    }
}
