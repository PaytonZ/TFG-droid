package com.bsod.tfg.vista.archivos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.archivos.AdapterExamenPreguntaSeleccionable;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaSeleccionable;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExam;
import com.bsod.tfg.modelo.otros.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPreguntaSeleccionable extends Fragment implements InterfaceCorrectQuestions {

    private View rootView;
    private AdapterExamenPreguntaSeleccionable adapterExamenPreguntaSeleccionable;
    private PreguntaSeleccionable pregunta;

    public static FragmentPreguntaSeleccionable newInstance(PreguntaSeleccionable p) {
        FragmentPreguntaSeleccionable fq = new FragmentPreguntaSeleccionable();
        Bundle args = new Bundle();
        args.putSerializable("pregunta", p);
        fq.setArguments(args);
        return fq;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        pregunta = (PreguntaSeleccionable) getArguments().getSerializable(
                "pregunta");

        final AdapterExamenPreguntaSeleccionable adapter = (adapterExamenPreguntaSeleccionable == null) ? adapterExamenPreguntaSeleccionable = new AdapterExamenPreguntaSeleccionable(getActivity(), pregunta) : adapterExamenPreguntaSeleccionable;

        TextView questiontext = (TextView) rootView.findViewById(R.id.textViewExamQuestion);
        questiontext.setText(pregunta.getPregunta());

        ListView listViewExam = (ListView) rootView.findViewById(R.id.listViewExam);
        listViewExam.setChoiceMode(pregunta.getTipo().equals(Constants.TYPE_OF_QUESTIONS_SHORT[0]) ? AbsListView.CHOICE_MODE_SINGLE : AbsListView.CHOICE_MODE_MULTIPLE);
        listViewExam.setAdapter(adapter);
        listViewExam.setOnItemClickListener(adapter);

        return rootView;
    }

    @Override
    public ResponseExam correctQuestions() {
        return adapterExamenPreguntaSeleccionable.correctQuestions();
    }
}
