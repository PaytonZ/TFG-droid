package com.bsod.tfg.vista.archivos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaCorta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExam;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamRespuestaCorta;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPreguntaCorta extends Fragment implements InterfaceCorrectQuestions {
    private PreguntaRespuestaCorta pregunta;
    private View rootView;
    private EditText respuesta;
    private TextView respuestaCorrecta;
    private TextView respuestaCorrectaInformativeText;

    public static FragmentPreguntaCorta newInstance(PreguntaRespuestaCorta p) {
        FragmentPreguntaCorta fq = new FragmentPreguntaCorta();
        Bundle args = new Bundle();
        args.putSerializable("pregunta", p);
        fq.setArguments(args);
        return fq;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pregunta_corta, container, false);
            pregunta = (PreguntaRespuestaCorta) getArguments().getSerializable(
                    "pregunta");
            TextView textoPregunta = (TextView) rootView.findViewById(R.id.textview_titulo_pregunta_corta);
            textoPregunta.setText(pregunta.getPregunta());
            respuesta = (EditText) rootView.findViewById(R.id.edittext_pregunta_corta);

            respuestaCorrecta = (TextView) rootView.findViewById(R.id.textview_respuesta_correcta);
            respuestaCorrectaInformativeText = (TextView) rootView.findViewById(R.id.textview_respuesta_correcta_text);
        }

        return rootView;
    }


    @Override
    public ResponseExam correctQuestions() {
        ResponseExam ret = new ResponseExamRespuestaCorta();
        ret.setId(pregunta.getId());
        respuesta.setKeyListener(null);
        respuesta.setHint("");
        respuesta.setTextColor(getActivity().getResources().getColor(R.color.white));
        double value;
        if (respuesta.getText().toString().equalsIgnoreCase(pregunta.getRespuesta())) {
            value = 1.0;
            respuesta.setBackgroundResource(R.color.green_test);
        } else {
            value = -1.0;
            respuestaCorrecta.setText(pregunta.getRespuesta());
            respuestaCorrecta.setVisibility(View.VISIBLE);
            respuestaCorrectaInformativeText.setVisibility(View.VISIBLE);
            respuesta.setBackgroundResource(R.color.red_test);
        }
        ret.setValue(value);
        ((ResponseExamRespuestaCorta) ret).setRespuesta(respuesta.getText().toString());

        return ret;
    }
}
