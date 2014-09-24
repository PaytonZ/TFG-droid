package com.bsod.tfg.vista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentArchivos extends Fragment {

    private TextView mainText;

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
        View rootView = inflater.inflate(R.layout.fragment_tablon, container,
                false);
        mainText = (TextView) rootView.findViewById(R.id.maintextarchivos);
        if (mainText == null)

            Toast.makeText(getActivity(), "NULLPOINTER TO MAJO", Toast.LENGTH_LONG).show();

        else
            mainText.setText("ARCHIVOS LOCATION!");


        return rootView;
    }


}
