package com.bsod.tfg.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Asignatura;
import com.bsod.tfg.utils.ViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Proudly created by Payton on 06/11/2014.
 */
public class AdapterAsignaturas extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final String TAG = "AdapterTablon";
    private final Context context;
    private List<Asignatura> asignaturasList = Collections.emptyList();


    public AdapterAsignaturas(Context context) {
        this.context = context;
    }

    public void updateAsignaturas(List<Asignatura> asignaturasList) {
        this.asignaturasList = asignaturasList;
        notifyDataSetChanged();
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

        if (convertView == null) {

            //holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_listasignaturas, parent, false);
        }

        TextView asignatura = ViewHolder.get(convertView, R.id.asignatura_item);
        Asignatura a = getItem(position);

        asignatura.setText(a.toString());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
