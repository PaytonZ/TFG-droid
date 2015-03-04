package com.bsod.tfg.vista.archivos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.archivos.AdapterAsignaturas;
import com.bsod.tfg.modelo.Asignatura;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAsignaturas extends Fragment {


    private ListView listviewAsignaturas;
    private AdapterAsignaturas adapterAsignaturas;
    private TextView textViewCurso;
    private int curso;
    //http://stackoverflow.com/questions/10716571/avoid-recreating-same-view-when-perform-tab-switching/11914078#11914078
    private View rootView;

    public static FragmentAsignaturas newInstance(int curso) {
        FragmentAsignaturas myFragment = new FragmentAsignaturas();

        Bundle args = new Bundle();
        args.putInt("curso", curso);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {

            curso = getArguments().getInt("curso", 0);
            rootView = inflater.inflate(R.layout.fragment_asignaturas, container, false);

            listviewAsignaturas = (ListView) rootView.findViewById(R.id.listview_asignaturas);

            final AdapterAsignaturas adapter = (adapterAsignaturas == null) ? adapterAsignaturas = new AdapterAsignaturas(getActivity()) : adapterAsignaturas;
            listviewAsignaturas.setAdapter(adapter);
            listviewAsignaturas.setOnItemClickListener(adapter);
            textViewCurso = (TextView) rootView.findViewById(R.id.textViewCurso);
            switch (curso) {
                case 1:
                    textViewCurso.setText(R.string.primero);
                    break;
                case 2:
                    textViewCurso.setText(R.string.segundo);
                    break;
                case 3:
                    textViewCurso.setText(R.string.tercero);
                    break;
                case 4:
                    textViewCurso.setText(R.string.cuarto);
                    break;
            }

            getAsignaturas();


        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }


    private void getAsignaturas() {
        RequestParams params = new RequestParams();
        Session s = Session.getSession();
        params.put("token", s.getToken().getToken());
        params.put("idfaculty", s.getFacultad().getId());
        params.put("year", curso);

        HttpClient.get(Constants.HTTP_GET_SUBJECTS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        //swipeLayout.setRefreshing(true);
                        ObjectMapper mapper = new ObjectMapper();
                        //mapper.registerModule(new JsonOrgModule());

                        List<Asignatura> listofSubjects = mapper.readValue(
                                response.get("data").toString(), new TypeReference<List<Asignatura>>() {
                                });
                        adapterAsignaturas.update(listofSubjects);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}









