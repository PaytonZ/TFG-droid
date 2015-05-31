package com.bsod.tfg.vista.examenes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Proudly created by Payton on 31/05/2015.
 */
public class FragmentSimpleImagen extends Fragment {


    private View rootView;
    private Context context;
    private String urlImagen;
    private ImageView imagen;

    public static Fragment newInstance(String imagen) {
        FragmentSimpleImagen myFragment = new FragmentSimpleImagen();
        Bundle args = new Bundle();
        args.putString("imagen", imagen);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {

            context = getActivity();
            rootView = inflater.inflate(R.layout.fragment_simple_imagen, container, false);
            urlImagen = getArguments().getString("imagen");
            imagen = (ImageView) rootView.findViewById(R.id.imagen);
            ImageLoader im = ImageLoader.getInstance();
            im.displayImage(Constants.BASE_URL.concat(urlImagen), imagen);

        }
        return rootView;

    }


}

