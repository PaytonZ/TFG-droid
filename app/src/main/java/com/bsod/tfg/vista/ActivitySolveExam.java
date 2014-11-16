package com.bsod.tfg.vista;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterFragmentExams;
import com.bsod.tfg.modelo.Pregunta;
import com.bsod.tfg.modelo.otros.Constants;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;

public class ActivitySolveExam extends FragmentActivity implements FragmentFinalExam.CorrectExam {

    private ViewPager pager;
    private AdapterFragmentExams adapterFragmentExams;
    private ArrayList<Pregunta> listOfQuestions;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listOfQuestions = (ArrayList<Pregunta>) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_ARRAY_QUESTIONS);


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
    public double correctQuestions() {
        double d = 0.0;
        int size = fragmentList.size() - 1;
        for (int i = 0; i < size; i++) {
            FragmentQuestion f = (FragmentQuestion) fragmentList.get(i);
            d += f.correctQuestions();
        }
        if (d < 0) {
            d = 0.0;
        }
        return (d / size) * 10;
    }
}
