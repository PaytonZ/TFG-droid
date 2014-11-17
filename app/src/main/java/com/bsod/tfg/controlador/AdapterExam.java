package com.bsod.tfg.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Pregunta;
import com.bsod.tfg.modelo.ResponseExamStats;
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

    public AdapterExam(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 4;
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

        respuestaCorrecta.setBackgroundColor(context.getResources().getColor((R.color.green_test)));
        respuestaCorrecta.setTextColor(context.getResources().getColor(R.color.white));
        stats.setId(pregunta.getId());
        // No se seleccionó ninguna opcion
        if (selecteditem == -1) {
            stats.setSelectedOption(-1);
            stats.setValue(0.0);
        }
        // La respuesta es la correcta
        if (selecteditem == pregunta.getRespuestaCorrecta() - 1) {
            stats.setValue(+1.0);
        } else {//Respuesta incorrecta
            stats.setSelectedOption(selecteditem + 1);
            stats.setValue(-1.0);
        }
        return stats;
    }
}
