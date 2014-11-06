package com.bsod.tfg.controlador;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.bsod.tfg.modelo.Asignatura;

import java.util.Collections;
import java.util.List;

/**
 * Proudly created by Payton on 06/11/2014.
 */
public class AdapterAsignaturas extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final String TAG = "AdapterTablon";
    private final Context context;
    private List<Asignatura> asignaturasList = Collections.emptyList();


    public void updateAsignaturas(List<Asignatura> messageList) {
        this.asignaturasList = messageList;
        notifyDataSetChanged();
    }

    public AdapterAsignaturas(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return asignaturasList.size();
    }

    @Override
    public Asignatura getItem(int position) {
        return asignaturasList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
