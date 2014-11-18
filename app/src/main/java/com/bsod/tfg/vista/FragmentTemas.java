package com.bsod.tfg.vista;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterTemas;
import com.bsod.tfg.modelo.Pregunta;
import com.bsod.tfg.modelo.Tema;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.module.jsonorg.JsonOrgModule;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTemas extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listviewTemas;
    private View rootView;
    private AdapterTemas adapterTemas;
    private int idsubject;
    private String nameOfSubject;
    private TextView textViewTema;

    public static FragmentTemas newInstance(int tema, String nameOfSubject) {
        FragmentTemas myFragment = new FragmentTemas();

        Bundle args = new Bundle();
        args.putInt("idsubject", tema);
        args.putString("nameOfSubject", nameOfSubject);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {

            idsubject = getArguments().getInt("idsubject", 0);
            nameOfSubject = getArguments().getString("nameOfSubject", "");
            rootView = inflater.inflate(R.layout.fragment_temas, container, false);

            listviewTemas = (ListView) rootView.findViewById(R.id.listview_temas);
            final AdapterTemas adapter = (adapterTemas == null) ? adapterTemas = new AdapterTemas(getActivity()) : adapterTemas;
            listviewTemas.setAdapter(adapter);
            listviewTemas.setOnItemClickListener(this);
            textViewTema = (TextView) rootView.findViewById(R.id.textViewTema);
            textViewTema.setText(nameOfSubject);

            getTemas();

        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    private void getTemas() {
        RequestParams params = new RequestParams();
        Session s = Session.getSession();
        params.put("token", s.getToken().getToken());
        params.put("subject", idsubject);

        HttpClient.get(Constants.HTTP_GET_THEMES, params, new JsonHttpResponseHandlerCustom(getActivity()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        //swipeLayout.setRefreshing(true);
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new JsonOrgModule());

                        List<Tema> list = mapper.readValue(
                                response.get("data").toString(),
                                TypeFactory.collectionType(
                                        List.class, Tema.class));
                        adapterTemas.update(list);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken().getToken());
        params.put("subject", idsubject);
        params.put("theme", adapterTemas.getItem(position).getid());
        params.put("numberofquestions", 10);

        if (position >= 0) {
            HttpClient.get(Constants.HTTP_GET_EXAMS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    int error;
                    try {
                        error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.registerModule(new JsonOrgModule());

                            ArrayList<Pregunta> listOfQuestions = mapper.readValue(
                                    response.get("data").toString(),
                                    TypeFactory.collectionType(
                                            List.class, Pregunta.class));

                            Intent i = new Intent(getActivity(), ActivitySolveExam.class);
                            i.putExtra(Constants.INTENT_EXTRA_ARRAY_QUESTIONS, listOfQuestions);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

        }
    }
}
