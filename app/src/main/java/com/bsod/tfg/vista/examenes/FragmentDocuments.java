package com.bsod.tfg.vista.examenes;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.examenes.AdapterDocuments;
import com.bsod.tfg.modelo.examenes.DocumentBean;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDocuments extends Fragment {

    private View rootView;
    private int idSubject;
    private String type;
    private AdapterDocuments adapterDocuments;
    private ListView listview_documents;
    private TextView textViewAsignatura;
    private Context context;

    public static FragmentDocuments newInstance(int idSubject, String name, String type) {
        FragmentDocuments myFragment = new FragmentDocuments();
        Bundle args = new Bundle();
        args.putInt("idSubject", idSubject);
        args.putString("type", type);
        args.putString("name", name);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {

            context = getActivity();
            rootView = inflater.inflate(R.layout.fragment_documents, container, false);
            listview_documents = (ListView) rootView.findViewById(R.id.listview_documents);
            textViewAsignatura = (TextView) rootView.findViewById(R.id.textViewAsignatura);
            textViewAsignatura.setText(getArguments().getString("name"));

            idSubject = getArguments().getInt("idSubject", -1);
            type = getArguments().getString("type");
            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("type", type);
            params.put("idsubject", idSubject);
            HttpClient.get(Constants.HTTP_GET_DOCUMENTS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    int error;
                    try {
                        error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            //mapper.registerModule(new JsonOrgModule());

                            ArrayList<DocumentBean> listOfDocuments = mapper.readValue(
                                    response.get("data").toString(), new TypeReference<List<DocumentBean>>() {
                                    });
                            adapterDocuments = new AdapterDocuments(getActivity(), listOfDocuments);
                            listview_documents.setAdapter(adapterDocuments);
                            listview_documents.setOnItemClickListener(adapterDocuments);
                            if (adapterDocuments.getCount() == 0) {
                                Toast.makeText(context, "No existe material disponible", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
        return rootView;

    }


}
