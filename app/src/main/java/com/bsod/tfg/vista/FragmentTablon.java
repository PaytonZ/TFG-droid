package com.bsod.tfg.vista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterTablon;

import java.util.ArrayList;

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

        final ListView listview = (ListView) rootView.findViewById(R.id.list_tablon);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final AdapterTablon adapter = new AdapterTablon(getActivity(),
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // Devolvemos la vista para que se muestre en pantalla.
        return rootView;
    }


}
