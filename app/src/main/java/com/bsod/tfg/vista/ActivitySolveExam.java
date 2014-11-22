package com.bsod.tfg.vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterFragmentExams;
import com.bsod.tfg.modelo.Pregunta;
import com.bsod.tfg.modelo.ResponseExamStats;
import com.bsod.tfg.modelo.ResponseExamTotal;
import com.bsod.tfg.modelo.otros.Constants;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listOfQuestions = (ArrayList<Pregunta>) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_ARRAY_QUESTIONS);
        idTest = getIntent().getIntExtra(Constants.INTENT_ID_TEST, -1);
        Log.i(TAG, "ID TEST ...:" + String.valueOf(idTest));
        setContentView(R.layout.activity_solve_exam);
        //Set the pager with an adapter
        pager = (ViewPager) findViewById(R.id.pager);


        //ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList = new ArrayList<Fragment>();
        for (Pregunta p : listOfQuestions) {
            //fragmentList.add(FragmentQuestion.newInstance(p));
            fragmentList.add(FragmentQuestion.newInstance(p));
        }
        fragmentList.add(FragmentFinalExam.newInstance());

        //adapterFragmentExams = new AdapterFragmentExams(getSupportFragmentManager());
        //adapterFragmentExams.setList(fragmentList);

        pager.setAdapter(new AdapterFragmentExams(getSupportFragmentManager(), fragmentList));
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
        ResponseExamTotal ret = new ResponseExamTotal();
        long convert = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        ret.setTime(convert);
        int size = fragmentList.size() - 1;
        ret.setFinalMark(0.0);
        ret.setNumOfQuestions(size);
        ret.setIdTest(idTest);

        SparseIntArray questions = new SparseIntArray();
        int correct = 0;
        int failed = 0;

        for (int i = 0; i < size; i++) {
            FragmentQuestion f = (FragmentQuestion) fragmentList.get(i);
            ResponseExamStats res = f.correctQuestions();
            questions.put(res.getId(), res.getSelectedOption());
            if (res.getValue() < 0) {
                failed += 1;
            } else if (res.getValue() > 0) {
                correct += 1;
            }
        }
        Log.i(TAG, "Correct answers :" + String.valueOf(correct));
        Log.i(TAG, "Failed answers :" + String.valueOf(failed));
        // Log.i(TAG, "Transcurred Time:" + String.valueOf(convert));
        double valuePerQuestions = 10.0 / size;
        // Log.i(TAG, "valuePerQuestions val :" + String.valueOf(valuePerQuestions));

        finished = true;

        ret.setFinalMark(Math.max((correct * valuePerQuestions) - (failed * (valuePerQuestions / 2)), 0.0));
        ret.setQuestions(questions);
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
