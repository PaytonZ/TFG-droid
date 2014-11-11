package com.bsod.tfg.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Tema;
import com.bsod.tfg.utils.ViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Proudly created by Payton on 11/11/2014.
 */
public class AdapterTemas extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final String TAG = "AdapterTablon";
    private final Context context;
    private List<Tema> temaList = Collections.emptyList();


    public AdapterTemas(Context context) {
        this.context = context;
    }

    public void updateTemas(List<Tema> temaList) {
        this.temaList = temaList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return temaList.size();
    }

    @Override
    public Tema getItem(int position) {
        return temaList.get(position);
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

        TextView tema = ViewHolder.get(convertView, R.id.asignatura_item);
        Tema t = getItem(position);

        tema.setText(t.toString());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
