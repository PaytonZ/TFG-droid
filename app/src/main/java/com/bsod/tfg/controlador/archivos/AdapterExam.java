package com.bsod.tfg.controlador.archivos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.Pregunta;
import com.bsod.tfg.modelo.archivos.ResponseExamStats;
import com.bsod.tfg.utils.ViewHolder;

/**
 * Proudly created by Payton on 16/11/2014.
 */
public class AdapterExam extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final String TAG = "AdapterExam";
    private final Context context;
    private Pregunta pregunta;
    private int selecteditem = -1;
    private TextView respuestaCorrecta;
    private boolean correctionMode = false;

    public AdapterExam(Context context, Pregunta p) {
        this.context = context;
        pregunta = p;
    }


    @Override
    public int getCount() {
        // TODO: fix this Horrible hack!!
        int count = 0;
        if (!pregunta.getRespuesta1().equals("")) count++;
        if (!pregunta.getRespuesta2().equals("")) count++;
        if (!pregunta.getRespuesta3().equals("")) count++;
        if (!pregunta.getRespuesta4().equals("")) count++;
        if (!pregunta.getRespuesta5().equals("")) count++;
        return count;
    }

    @Override
    public String getItem(int position) {

        String response = "";
        switch (position) {
            case 0:
                response = pregunta.getRespuesta1();
                break;
            case 1:
                response = pregunta.getRespuesta2();
                break;
            case 2:
                response = pregunta.getRespuesta3();
                break;
            case 3:
                response = pregunta.getRespuesta4();
                break;
            case 4:
                response = pregunta.getRespuesta5();
        }
        return response;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.question_layout, parent, false);
        }
        TextView respuesta = ViewHolder.get(convertView, R.id.textViewresponseQuestion);

        if ((respuestaCorrecta == null) && (position == (pregunta.getRespuestaCorrecta() - 1))) {
            respuestaCorrecta = respuesta;
        }

        if (!correctionMode)
            respuesta.setTextColor((position == selecteditem) ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.black));

        String a = getItem(position);
        respuesta.setText(a);

        return convertView;
    }

    public void setPregunta(Pregunta p) {
        this.pregunta = p;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        ListView lw = ((ListView) adapterView);
        // Entering correction mode
        // Little hack for making the list unvariable in correction mode
        if (correctionMode) {
            lw.setItemChecked(position, false);
            if (selecteditem != -1)
                lw.setItemChecked(selecteditem, true);
        } else {
            // Se esta eligiendo una posición que no era la que anteriormente disponiamos
            // Esto se utiliza para poder 'desmarcar una opción'
            if (position != selecteditem) {
                lw.setItemChecked(position, true);
                selecteditem = lw.getCheckedItemPosition();
            } else // Se indica que no hay elemento elegido.
            {
                lw.setItemChecked(position, false);
                selecteditem = -1;
            }
        }
    }

    public ResponseExamStats correctQuestions() {

        correctionMode = true;

        ResponseExamStats stats = new ResponseExamStats();
        if (respuestaCorrecta != null) {
            respuestaCorrecta.setBackgroundColor(context.getResources().getColor((R.color.green_test)));
            respuestaCorrecta.setTextColor(context.getResources().getColor(R.color.white));
        }

        stats.setId(pregunta.getId());
        // No se seleccionó ninguna opcion
        if (selecteditem == -1) {
            stats.setSelectedOption(0);
            stats.setValue(0.0);
        } else { // Se seleccionó un valor.
            stats.setSelectedOption(selecteditem + 1);
            Log.i(TAG, String.valueOf(stats.getSelectedOption()));
            stats.setValue((selecteditem == pregunta.getRespuestaCorrecta() - 1) ? +1.0 : -1.0);
        }
        return stats;
    }
}
