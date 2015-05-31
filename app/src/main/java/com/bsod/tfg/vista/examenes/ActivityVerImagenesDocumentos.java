package com.bsod.tfg.vista.examenes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.archivos.AdapterFragmentSimple;
import com.bsod.tfg.modelo.otros.Constants;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;

public class ActivityVerImagenesDocumentos extends FragmentActivity {
    private ViewPager pager;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagenes_documentos);


        ArrayList<String> imagenes = (ArrayList<String>) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_IMAGELIST_DOCUMENTS);
        fragmentList = new ArrayList<>();
        for (String imagen : imagenes) {
            fragmentList.add(FragmentSimpleImagen.newInstance(imagen));
        }


        pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter;
        pagerAdapter = new AdapterFragmentSimple(getSupportFragmentManager(), fragmentList);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);

        //Bind the title indicator to the adapter
        LinePageIndicator titleIndicator = (LinePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(pager);
    }


}
