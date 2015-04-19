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
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaMultiple;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaUnica;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaSeleccionable;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExam;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamMultiRespuesta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamUnicaRespuesta;
import com.bsod.tfg.utils.ViewHolder;
import com.bsod.tfg.vista.archivos.InterfaceCorrectQuestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Proudly created by Payton on 16/11/2014.
 */
public class AdapterExamenPreguntaSeleccionable extends BaseAdapter implements AdapterView.OnItemClickListener, InterfaceCorrectQuestions {

    private static final String TAG = "AdapterExamenPreguntaSeleccionable";
    private final Context context;
    private PreguntaSeleccionable pregunta;
    private int selecteditem = -1;
    private TextView respuestaCorrecta;
    private boolean correctionMode = false;
    private List<Integer> selectedItems;


    public AdapterExamenPreguntaSeleccionable(Context context, PreguntaSeleccionable p) {
        this.context = context;
        pregunta = p;
        if (p.isMultiAnswer()) {
            selectedItems = new ArrayList<>();
        }
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

        if (pregunta.isMultiAnswer()) // Lógica para preguntas múltirespuesta
        {
            if (selectedItems.contains(position)) {
                respuesta.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                respuesta.setTextColor(context.getResources().getColor(R.color.black));
            }
            if (correctionMode && (((PreguntaRespuestaMultiple) pregunta).getRespuestasCorrectas().contains(position + 1))) {
                respuesta.setBackgroundResource(R.color.green_test);
                respuesta.setTextColor(context.getResources().getColor(R.color.white));
            }

        } else { // Lógica para preguntas de una sola respuesta
            int respuestaCorrectaValue = (((PreguntaRespuestaUnica) pregunta).getRespuestaCorrecta() - 1);
            if ((respuestaCorrecta == null) && (position == respuestaCorrectaValue)) {
                respuestaCorrecta = respuesta;
            }
            if (correctionMode && position == respuestaCorrectaValue) {
                respuesta.setBackgroundResource(R.color.green_test);
                respuesta.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                respuesta.setTextColor((position == selecteditem) ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.black));
            }
        }
        String a = getItem(position);
        respuesta.setText(a);

        return convertView;
    }

    public void setPregunta(PreguntaSeleccionable p) {
        this.pregunta = p;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        ListView lw = ((ListView) adapterView);
        if (pregunta.isMultiAnswer()) { // Lógica para preguntas múltirespuesta
            if (correctionMode) {
                lw.setItemChecked(position, false);
                if (selectedItems.contains(position))
                    lw.setItemChecked(position, true);
            } else {
                lw.setItemChecked(position, false);
                if (selectedItems.contains(position)) {
                    lw.setItemChecked(position, false);
                    selectedItems.remove((Integer) position);
                } else {
                    selectedItems.add(position);
                    lw.setItemChecked(position, true);
                }
            }

        } else// Lógica para preguntas de una sola respuesta
        {
             /*
        Entrando en modo corrección
        Hacer la lista no disponible en modo correci&oacute;n
        */
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
    }

    @Override
    public ResponseExam correctQuestions() {

        correctionMode = true;
        ResponseExam stats;
        if (pregunta.isMultiAnswer()) { // Lógica para preguntas múltirespuesta
            stats = new ResponseExamMultiRespuesta();
            int relativeValue = 0;
            // Se itera por los valores seleccionados y se suma 1 , ya que, p.e, respuesta correcta 1 indica que
            // la respuesta correcta es la primera , con respecto a las listas es la 0.
            List<Integer> selectedItemsAux = new ArrayList<>(selectedItems);
            double resultado = 0.0d;
            for (int i = 0; i < selectedItemsAux.size(); i++) {

                selectedItemsAux.set(i, selectedItems.get(i) + 1);
                if (((PreguntaRespuestaMultiple) pregunta).getRespuestasCorrectas().contains(selectedItemsAux.get(i))) {
                    relativeValue += 1;
                }
            }
            if (selectedItemsAux.size() == 0) // No selecciono ninguna opcion
            {
                selectedItemsAux.add(0);
                stats.setValue(0.0);
            } else {

                stats.setValue(relativeValue == ((PreguntaRespuestaMultiple) pregunta).getRespuestasCorrectas().size() ? +1.0 : -1.0);
            }

            ((ResponseExamMultiRespuesta) stats).setSelectedOptions(selectedItemsAux);

        } else// Lógica para preguntas de una sola respuesta
        {
            stats = new ResponseExamUnicaRespuesta();
            if (respuestaCorrecta != null) {
                respuestaCorrecta.setBackgroundResource(R.color.green_test);
                respuestaCorrecta.setTextColor(context.getResources().getColor(R.color.white));
            }
            // No se seleccionó ninguna opcion
            if (selecteditem == -1) {
                ((ResponseExamUnicaRespuesta) stats).setSelectedOption(0);
                stats.setValue(0.0);
            } else { // Se seleccionó un valor.
                ((ResponseExamUnicaRespuesta) stats).setSelectedOption(selecteditem + 1);
                Log.i(TAG.substring(0, 23), "Opcion seleccionada por el usuario :".concat(String.valueOf(((ResponseExamUnicaRespuesta) stats).getSelectedOption())));
                stats.setValue((selecteditem == ((PreguntaRespuestaUnica) pregunta).getRespuestaCorrecta() - 1) ? +1.0 : -1.0);
            }
        }
        stats.setId(pregunta.getId());
        notifyDataSetChanged();
        return stats;
    }

}
