package com.bsod.tfg.vista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bsod.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentArchivos extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentArchivos";
    private TextView mainText;

    private Button buttonFirst;
    private Button buttonSecond;
    private Button buttonThird;
    private Button buttonFourth;

    public FragmentArchivos() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new FragmentArchivos();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_archivos, container,
                false);

        buttonFirst = (Button) rootView.findViewById(R.id.archivos_boton_primero);
        buttonSecond = (Button) rootView.findViewById(R.id.archivos_boton_segundo);
        buttonThird = (Button) rootView.findViewById(R.id.archivos_boton_tercero);
        buttonFourth = (Button) rootView.findViewById(R.id.archivos_boton_cuarto);


        buttonFirst.setOnClickListener(this);
        buttonSecond.setOnClickListener(this);
        buttonThird.setOnClickListener(this);
        buttonFourth.setOnClickListener(this);


        return rootView;
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "CLICKING ASIGNATURA BUTTON");

// Create new fragment and transaction

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.layout_archivos1, new FragmentAsignaturas());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }
}
