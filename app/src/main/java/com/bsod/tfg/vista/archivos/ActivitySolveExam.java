package com.bsod.tfg.vista.archivos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.archivos.AdapterFragmentExams;
import com.bsod.tfg.modelo.archivos.preguntas.Pregunta;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaEmparejamiento;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaCorta;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaMultiple;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaUnica;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamEmparejamientos;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamMultiRespuesta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamRespuestaCorta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamTotal;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamTotalEmparejamientos;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamTotalMultiRespuesta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamTotalRespuestaCorta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamTotalUnicaRespuesta;
import com.bsod.tfg.modelo.archivos.respuestas.ResponseExamUnicaRespuesta;
import com.bsod.tfg.modelo.otros.Constants;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActivitySolveExam extends FragmentActivity implements FragmentFinalExam.CorrectExam {

    private static final String TAG = "ActivitySolveExam";
    private ViewPager pager;
    private AdapterFragmentExams adapterFragmentExams;
    private ArrayList<Pregunta> listOfQuestions;
    private ArrayList<Fragment> fragmentList;
    private Integer idTest;
    private Boolean finished = false;
    private long startTime;
    private int typeOfTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listOfQuestions = (ArrayList<Pregunta>) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_ARRAY_QUESTIONS);
        idTest = getIntent().getIntExtra(Constants.INTENT_ID_TEST, -1);
        typeOfTest = getIntent().getIntExtra(Constants.INTENT_EXTRA_TYPE_OF_QUESTIONS, -1);
        Log.i(TAG, "ID TEST ...:" + String.valueOf(idTest));
        setContentView(R.layout.activity_solve_exam);
        //Set the pager with an adapter
        pager = (ViewPager) findViewById(R.id.pager);
        fragmentList = new ArrayList<>();
        PagerAdapter pagerAdapter;
        switch (typeOfTest) {
            case 0: //Respuesta única ( NA )
                for (Pregunta p : listOfQuestions) {

                    fragmentList.add(FragmentPreguntaSeleccionable.newInstance((PreguntaRespuestaUnica) p));
                }
                break;

            case 1://Respuesta Múltiple ( MA )
                for (Pregunta p : listOfQuestions) {

                    fragmentList.add(FragmentPreguntaSeleccionable.newInstance((PreguntaRespuestaMultiple) p));
                }
                break;
            case 2: // Preguntas Cortas
                for (Pregunta p : listOfQuestions) {

                    fragmentList.add(FragmentPreguntaCorta.newInstance((PreguntaRespuestaCorta) p));
                }
                break;
            case 3: // Preguntas Emparejamiento

                for (Pregunta p : listOfQuestions) {

                    fragmentList.add(FragmentPreguntasEmparejamiento.newInstance((PreguntaEmparejamiento) p));
                }
                break;
            default:
                break;
        }
        fragmentList.add(FragmentFinalExam.newInstance());

        //adapterFragmentExams = new AdapterFragmentExams(getSupportFragmentManager());
        //adapterFragmentExams.setList(fragmentList);
        pagerAdapter = new AdapterFragmentExams(getSupportFragmentManager(), fragmentList);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);

        //Bind the title indicator to the adapter
        LinePageIndicator titleIndicator = (LinePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(pager);

        startTime = System.nanoTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity_solve_exam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    /**
     * Método que itera cada fragment y obtiene los datos como que respuesta se obtuvo y si se acertó
     */
    public ResponseExamTotal correctQuestions() {
        ResponseExamTotal ret = null;
        long convert = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        int size = fragmentList.size() - 1;
        double valuePerQuestions = 10.0 / size;
        switch (typeOfTest) {

            case 0: //Respuesta única ( NA )
                ret = new ResponseExamTotalUnicaRespuesta();

                int correct = 0;
                int failed = 0;
                SparseIntArray questions = new SparseIntArray();

                for (int i = 0; i < size; i++) {
                    FragmentPreguntaSeleccionable f = (FragmentPreguntaSeleccionable) fragmentList.get(i);
                    ResponseExamUnicaRespuesta res = (ResponseExamUnicaRespuesta) f.correctQuestions();
                    questions.put(res.getId(), res.getSelectedOption());
                    if (res.getValue() < 0) {
                        failed += 1;
                    } else if (res.getValue() > 0) {
                        correct += 1;
                    }
                }
                ((ResponseExamTotalUnicaRespuesta) ret).setQuestions(questions);
                ret.setFinalMark(Math.max((correct * valuePerQuestions) - (failed * (valuePerQuestions / 2)), 0.0));
                Log.i(TAG, "Examen completado TIPO RESPUESTA ÚNICA");
                Log.i(TAG, "Correct answers :" + String.valueOf(correct));
                Log.i(TAG, "Failed answers :" + String.valueOf(failed));
                break;
            case 1: //Respuesta Múltiple ( MA )
                HashMap<Integer, List<Integer>> questions1 = new HashMap<>();
                ret = new ResponseExamTotalMultiRespuesta();
                double correct1 = 0.0;
                double failed1 = 0.0;
                for (int i = 0; i < size; i++) {
                    FragmentPreguntaSeleccionable f = (FragmentPreguntaSeleccionable) fragmentList.get(i);
                    ResponseExamMultiRespuesta res = (ResponseExamMultiRespuesta) f.correctQuestions();
                    questions1.put(res.getId(), res.getSelectedOptions());
                    if (res.getValue() < 0) {
                        failed1 += 1;
                    } else if (res.getValue() > 0) {
                        correct1 += res.getValue();
                    }
                }
                ((ResponseExamTotalMultiRespuesta) ret).setQuestions(questions1);
                Log.i(TAG, "Examen completado TIPO RESPUESTA MULTIPLE");
                ret.setFinalMark(Math.max((correct1 * valuePerQuestions) - (failed1 * (valuePerQuestions / 2)), 0.0));
                break;
            case 2: // Respuesta Corta (SA)

                HashMap<Integer, String> questions2 = new HashMap<>();
                ret = new ResponseExamTotalRespuestaCorta();
                double correct2 = 0.0;
                double failed2 = 0.0;
                for (int i = 0; i < size; i++) {

                    FragmentPreguntaCorta f = (FragmentPreguntaCorta) fragmentList.get(i);
                    ResponseExamRespuestaCorta res = (ResponseExamRespuestaCorta) f.correctQuestions();
                    questions2.put(res.getId(), res.getRespuesta());
                    if (res.getValue() < 0) {
                        failed2 += 1;
                    } else if (res.getValue() > 0) {
                        correct2 += res.getValue();
                    }
                }
                ((ResponseExamTotalRespuestaCorta) ret).setQuestions(questions2);
                Log.i(TAG, "Examen completado TIPO RESPUESTA CORTA");
                ret.setFinalMark(Math.max((correct2 * valuePerQuestions) - (failed2 * (valuePerQuestions / 2)), 0.0));

                break;
            case 3: //Empajeramiento
                ret = new ResponseExamTotalEmparejamientos();
                double correct3 = 0.0;
                double failed3 = 0.0;
                HashMap<Integer, int[][]> questions3 = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    FragmentPreguntasEmparejamiento f = (FragmentPreguntasEmparejamiento) fragmentList.get(i);
                    ResponseExamEmparejamientos res = (ResponseExamEmparejamientos) f.correctQuestions();
                    questions3.put(res.getId(), res.getRespuesta());
                    if (res.getValue() > 0.0) {
                        correct3 += 1.0;
                    } else {
                        failed3 += 1.0;
                    }
                }
                ((ResponseExamTotalEmparejamientos) ret).setQuestions(questions3);
                ret.setFinalMark(Math.max((correct3 * valuePerQuestions) - (failed3 * (valuePerQuestions / 2)), 0.0));
                break;
        }
        if (ret != null) {

            ret.setNumOfQuestions(size);
            ret.setIdTest(idTest);
            finished = true;
            ret.setTime(convert);
            ret.setTypeofQuestions(typeOfTest);
        }
        return ret;
    }

    @Override
    public void onBackPressed() {
        if (!finished) {
            new AlertDialog.Builder(this)
                    .setTitle("¿ Quieres salir ?")
                    .setMessage("¿ Estás seguro de quieres salir de este test ? \n No se guardará ningún progreso")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            ActivitySolveExam.super.onBackPressed();

                        }
                    }).create().show();
        } else {
            super.onBackPressed();
        }
    }
}
