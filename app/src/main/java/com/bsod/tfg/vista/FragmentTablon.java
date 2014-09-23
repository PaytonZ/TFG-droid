package com.bsod.tfg.vista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsod.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTablon extends Fragment {


    public FragmentTablon() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new FragmentTablon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos la Vista que se debe mostrar en pantalla.
        View rootView = inflater.inflate(R.layout.fragment_tablon, container,
                false);
        // Devolvemos la vista para que se muestre en pantalla.
        return rootView;
    }


}
