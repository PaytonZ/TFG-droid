package com.bsod.tfg.vista.archivos;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaEmparejamiento;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExam;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPreguntasEmparejamiento extends Fragment implements View.OnClickListener, InterfaceCorrectQuestions {


    private View rootView;
    private TextView pareja11;
    private TextView pareja12;
    private TextView pareja13;
    private TextView pareja21;
    private TextView pareja22;
    private TextView pareja23;
    private PreguntaEmparejamiento pregunta;
    private final String TAG = "FragmentPreguntasEm";
    private TextView enunciado;
    private Random rand;
    private boolean firstRow = false;
    private int color;
    private TextView selectedView;
    private int selectedViewValue;

    private int[] respuestasDadas = {-1, -1, -1};
    private int[][] respuestasCorrectas = {{-1, -1, -1}, {-1, -1, -1}};
    private String[] columna2textos;


    public static FragmentPreguntasEmparejamiento newInstance(PreguntaEmparejamiento p) {
        FragmentPreguntasEmparejamiento fq = new FragmentPreguntasEmparejamiento();
        Bundle args = new Bundle();
        args.putSerializable("pregunta", p);
        fq.setArguments(args);
        return fq;
    }

    public FragmentPreguntasEmparejamiento() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_preguntas_emparejamiento, container, false);

            pregunta = (PreguntaEmparejamiento) getArguments().getSerializable(
                    "pregunta");

            if (pregunta == null) {
                Log.e(TAG.substring(0, 23), "Pregunta no se cargo correctamente.");
            } else {

                enunciado = (TextView) rootView.findViewById(R.id.textViewExamQuestion);
                enunciado.setText(pregunta.getPregunta());

                pareja11 = (TextView) rootView.findViewById(R.id.pareja11);
                pareja12 = (TextView) rootView.findViewById(R.id.pareja12);
                pareja13 = (TextView) rootView.findViewById(R.id.pareja13);
                pareja21 = (TextView) rootView.findViewById(R.id.pareja21);
                pareja22 = (TextView) rootView.findViewById(R.id.pareja22);
                pareja23 = (TextView) rootView.findViewById(R.id.pareja23);

                rand = new Random();
                boolean validValues[][] = {{true, true, true}, {true, true, true}};
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 2; j++) {
                        int n;
                        do {
                            n = rand.nextInt(3) + 1;
                            Log.d(TAG, n + "");
                            Log.d(TAG, validValues.toString());
                        } while (!validValues[j][n - 1]);

                        validValues[j][n - 1] = false;
                        respuestasCorrectas[j][i] = n;
                    }

                }

                for (int i = 0; i < respuestasCorrectas.length; i++) {
                    Log.d(TAG, "i:" + i + "respuestascorrectas[]" + respuestasCorrectas[i]);
                }


                configurePareja(pareja11, getTextColumna1(respuestasCorrectas[0][0]));
                configurePareja(pareja12, getTextColumna1(respuestasCorrectas[0][1]));
                configurePareja(pareja13, getTextColumna1(respuestasCorrectas[0][2]));
                configurePareja(pareja21, getTextColumna2(respuestasCorrectas[1][0]));
                configurePareja(pareja22, getTextColumna2(respuestasCorrectas[1][1]));
                configurePareja(pareja23, getTextColumna2(respuestasCorrectas[1][2]));

            }
        }

        return rootView;
    }

    private void configurePareja(TextView tx, String text) {
        tx.setText(text);
        tx.setOnClickListener(this);
        tx.setGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View view) {

        // Intenta elegir una columna de la derecha sin tener algo de la izquierda
        if (!firstRow && ((view == pareja21) || (view == pareja22) || (view == pareja23))) {

            // quiere elegir la segunda columna y ya tiene elegida una de la priemra.
            // Esta respondiendo de forma adecuada
        } else if (firstRow && ((view == pareja21) || (view == pareja22) || (view == pareja23))) {
            Log.d(TAG, "quiere elegir la segunda columna y ya tiene elegida una de la priemra.");
            view.setBackgroundColor(color);
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            selectedView = null;
            firstRow = false;
            color = -1;

            respuestasDadas[selectedViewValue - 1] = (view == pareja21) ? 1 : ((view == pareja22) ? 2 : 3);
            // El usuario quiere deseleccionar la vista que pulso anteriormente
        } else if (selectedView != null && view == selectedView) {

            view.setBackground(getResources().getDrawableForDensity(R.drawable.border_question, getResources().getDisplayMetrics().densityDpi, null));
            ((TextView) view).setTextColor(getResources().getColor(R.color.black));
            selectedView = null;
            firstRow = false;
            selectedViewValue = -1;
        }
        // El usuario seleccionó una de la primera columna y ahora desea elegir otra
        else if (firstRow && (view == pareja11 || view == pareja12 || view == pareja13)) {
            if (selectedView == null) {
                Log.e(TAG, "Error en la lógica de onClick de preguntas emparejamiento.");
            } else {
                selectedView.setBackgroundColor(getResources().getColor(R.color.white));
                selectedView.setTextColor(getResources().getColor(R.color.black));
                // Esto se repite con el otro.
                color = generateRandomColor();
                selectedView = (TextView) view;
                view.setBackgroundColor(color);
                ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                firstRow = true;
                selectedViewValue = (view == pareja11) ? 1 : ((view == pareja12) ? 2 : 3);
            }
        }
        // Esta seleccionando la primera columna por primera vez
        else if ((view == pareja11 || view == pareja12 || view == pareja13) && !firstRow) {
            Log.d(TAG, "Esta seleccionando la primera columna por primera vez");
            color = generateRandomColor();
            selectedView = (TextView) view;
            view.setBackgroundColor(color);
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            firstRow = true;
            selectedViewValue = (view == pareja11) ? 1 : ((view == pareja12) ? 2 : 3);
        } else {

        }

    }

    @Override
    public ResponseExam correctQuestions() {
        return null;
    }

    private String getTextColumna1(int pos) {
        String resultado = "";
        switch (pos) {
            case 1:
                resultado = pregunta.getPareja11();
                break;
            case 2:
                resultado = pregunta.getPareja12();
                break;
            case 3:
                resultado = pregunta.getPareja13();
                break;

        }

        return resultado;

    }

    private String getTextColumna2(int pos) {
        String resultado = "";
        switch (pos) {
            case 1:
                resultado = pregunta.getPareja21();
                break;
            case 2:
                resultado = pregunta.getPareja22();
                break;
            case 3:
                resultado = pregunta.getPareja23();
                break;

        }

        return resultado;

    }

    private int generateRandomColor() {

        return Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
}

