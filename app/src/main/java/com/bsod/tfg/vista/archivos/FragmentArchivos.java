package com.bsod.tfg.vista.archivos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.archivos.AdapterAsignaturasDetail;
import com.bsod.tfg.modelo.archivos.Asignatura;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.FragmentReplace;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.bsod.tfg.vista.examenes.FragmentUploadFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import static com.bsod.tfg.utils.MeasureUtils.setListViewHeightBasedOnChildren;

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
    private View rootView;
    private boolean documents;
    private Button buttonUploadExam;


    public static FragmentArchivos newInstance(boolean documents) {
        FragmentArchivos fg = new FragmentArchivos();
        Bundle args = new Bundle();
        args.putBoolean("documents", documents);
        fg.setArguments(args);
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*if (rootView == null) {
            // Inflate the layout for this fragment*/
        rootView = inflater.inflate(R.layout.fragment_archivos, container,
                false);
        documents = getArguments().getBoolean("documents");
        buttonFirst = (Button) rootView.findViewById(R.id.archivos_boton_primero);
        buttonSecond = (Button) rootView.findViewById(R.id.archivos_boton_segundo);
        buttonThird = (Button) rootView.findViewById(R.id.archivos_boton_tercero);
        buttonFourth = (Button) rootView.findViewById(R.id.archivos_boton_cuarto);

        buttonUploadExam = (Button) rootView.findViewById(R.id.archivos_subir_examen);
        if (!documents) {
            buttonUploadExam.setVisibility(View.INVISIBLE);
        }
        listViewAsignaturasFavoritas = (ListView) rootView.findViewById(R.id.listview_fav_asignaturas);

        adapter = new AdapterAsignaturasDetail(getActivity());
        listViewAsignaturasFavoritas.setAdapter(adapter);
        listViewAsignaturasFavoritas.setOnItemClickListener(adapter);

        getFavSubjects();
        setListViewHeightBasedOnChildren(listViewAsignaturasFavoritas);

        buttonFirst.setOnClickListener(this);
        buttonSecond.setOnClickListener(this);
        buttonThird.setOnClickListener(this);
        buttonFourth.setOnClickListener(this);
        buttonUploadExam.setOnClickListener(this);
        /*} else {
            /*getFavSubjects();
            setListViewHeightBasedOnChildren(listViewAsignaturasFavoritas);

            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }*/
        return rootView;
    }

    @Override
    public void onClick(View view) {

        int curso = 0;

        if (view == buttonUploadExam) {
            FragmentReplace.replaceFragment(getActivity(), new FragmentUploadFile());
        } else if (view == buttonFirst) {
            curso = 1;
        } else if (view == buttonSecond) {
            curso = 2;
        } else if (view == buttonThird) {
            curso = 3;
        } else if (view == buttonFourth) {
            curso = 4;
        }
        if (curso != 0) {
            FragmentReplace.replaceFragment(getActivity(), FragmentAsignaturas.newInstance(curso, documents));
        }
    }

    private void getFavSubjects() {
        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken().getToken());

        HttpClient.get(Constants.HTTP_GET_FAV_SUBJECTS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        ObjectMapper mapper = new ObjectMapper();
                        //mapper.registerModule(new JsonOrgModule());
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
