package com.bsod.tfg.controlador.archivos;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.otros.AdapterGeneric;
import com.bsod.tfg.modelo.Asignatura;
import com.bsod.tfg.utils.ViewHolder;
import com.bsod.tfg.vista.archivos.FragmentTemas;

/**
 * Proudly created by Payton on 14/11/2014.
 */
public class AdapterAsignaturas extends AdapterGeneric<Asignatura> implements AdapterView.OnItemClickListener {

    public AdapterAsignaturas(Context context) {
        super(context);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (id >= 0) {

            FragmentActivity fa = (FragmentActivity) context;
            FragmentManager fragmentManager = fa.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            Asignatura a = getItem(position);
            Fragment f = FragmentTemas.newInstance(a.getId(), a.toString(), a.getUser_favorited());

            fragmentTransaction.replace(R.id.fragment, f, "TFGFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();

        }


    }
}
