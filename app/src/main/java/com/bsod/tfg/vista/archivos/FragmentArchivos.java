package com.bsod.tfg.vista.archivos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterAsignaturasDetail;
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
public class FragmentArchivos extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentArchivos";
    private TextView mainText;

    private Button buttonFirst;
    private Button buttonSecond;
    private Button buttonThird;
    private Button buttonFourth;

    private ListView listViewAsignaturasFavoritas;
    private AdapterAsignaturasDetail adapter;

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
/*
        listViewAsignaturasFavoritas = (ListView) rootView.findViewById(R.id.listview_fav_asignaturas);

        adapter = new AdapterAsignaturasDetail(getActivity());
        listViewAsignaturasFavoritas.setAdapter(adapter);
        listViewAsignaturasFavoritas.setOnItemClickListener(adapter);

        getAsignaturas();
*/
        buttonFirst.setOnClickListener(this);
        buttonSecond.setOnClickListener(this);
        buttonThird.setOnClickListener(this);
        buttonFourth.setOnClickListener(this);


        return rootView;
    }


    @Override
    public void onClick(View view) {

        int curso = 0;
        if (view == buttonFirst) {
            curso = 1;
        } else if (view == buttonSecond) {
            curso = 2;
        } else if (view == buttonThird) {
            curso = 3;
        } else if (view == buttonFourth) {
            curso = 4;
        }

        if (curso != 0) {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            Fragment f = FragmentAsignaturas.newInstance(curso);

            fragmentTransaction.replace(R.id.fragment, f, "TFGFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    private void getFavSubjects() {
        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken().getToken());

/*
        HttpClient.get(Constants.HTTP_GET_FAV_SUBJECTS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {

                    }
                } catch (Exception e) {

                }

            }
        });
*/

    }

    private void getAsignaturas() {
        RequestParams params = new RequestParams();
        Session s = Session.getSession();
        params.put("token", s.getToken().getToken());
        params.put("idfaculty", s.getFacultad().getId());
        params.put("year", 2);

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
                        adapter.update(listofSubjects);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}