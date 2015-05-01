package com.bsod.tfg.vista.examenes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsod.tfg.R;
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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDocuments extends Fragment {

    private View rootView;
    private int idSubject;
    private String type;

    public static FragmentDocuments newInstance(int idSubject, String type) {
        FragmentDocuments myFragment = new FragmentDocuments();
        Bundle args = new Bundle();
        args.putInt("idSubject", idSubject);
        args.putString("type", type);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_documents, container, false);
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

                            List<DocumentBean> listOfDocuments = mapper.readValue(
                                    response.get("data").toString(), new TypeReference<List<DocumentBean>>() {
                                    });

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
