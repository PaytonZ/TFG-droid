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
import com.bsod.tfg.controlador.archivos.AdapterExam;
import com.bsod.tfg.modelo.archivos.Pregunta;
import com.bsod.tfg.modelo.archivos.ResponseExamStats;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentQuestion extends Fragment {

    private View rootView;
    private AdapterExam adapterExam;
    private Pregunta pregunta;

    public static FragmentQuestion newInstance(Pregunta p) {
        FragmentQuestion fq = new FragmentQuestion();
        Bundle args = new Bundle();
        args.putSerializable("pregunta", p);
        fq.setArguments(args);
        return fq;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_question, container, false);
            pregunta = (Pregunta) getArguments().getSerializable(
                    "pregunta");

            final AdapterExam adapter = (adapterExam == null) ? adapterExam = new AdapterExam(getActivity(), pregunta) : adapterExam;

            TextView questiontext = (TextView) rootView.findViewById(R.id.textViewExamQuestion);
            questiontext.setText(pregunta.getPregunta());

            ListView listViewExam = (ListView) rootView.findViewById(R.id.listViewExam);
            listViewExam.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listViewExam.setAdapter(adapter);
            listViewExam.setOnItemClickListener(adapter);


            //adapter.setPregunta(pregunta);


        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }

    public ResponseExamStats correctQuestions() {
        return adapterExam.correctQuestions();
    }
}
