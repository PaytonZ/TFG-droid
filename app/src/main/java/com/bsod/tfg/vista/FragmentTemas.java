package com.bsod.tfg.vista;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterTemas;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTemas extends Fragment {

    private ListView listviewTemas;
    private View rootView;
    private AdapterTemas adapterTemas;

    public FragmentTemas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_temas, container, false);

            listviewTemas = (ListView) rootView.findViewById(R.id.listview_temas);
            final AdapterTemas adapter = (adapterTemas == null) ? adapterTemas = new AdapterTemas(getActivity()) : adapterTemas;
            listviewTemas.setAdapter(adapter);
            listviewTemas.setOnItemClickListener(adapter);

        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }


}
