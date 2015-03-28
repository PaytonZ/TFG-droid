package com.bsod.tfg.vista.archivos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.Asignatura;
import com.bsod.tfg.modelo.archivos.Tema;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.DateManager;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.materialdesign.views.Button;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUploadFile extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialogFragment.DatePickerDialogHandler, View.OnClickListener {

    private static final String TAG = "FragmentUploadFile";
    private View rootView;
    private Spinner spinnersubject;
    private Spinner spinnertheme;
    private Context context;
    private FragmentUploadFile thisfragment = this;
    private Button button_selectyearmonth;
    private TextView textViewMonth;
    private TextView textViewYear;
    private int monthSelected;
    private int yearSelected;

    public FragmentUploadFile() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_upload_file, container, false);
            spinnersubject = (Spinner) rootView.findViewById(R.id.spinnersubject);
            spinnertheme = (Spinner) rootView.findViewById(R.id.spinnertheme);
            button_selectyearmonth = (Button) rootView.findViewById(R.id.button_selectyearmonth);
            textViewMonth = (TextView) rootView.findViewById(R.id.textViewMonth);
            textViewYear = (TextView) rootView.findViewById(R.id.textViewYear);
            button_selectyearmonth.setOnClickListener(this);
            context = getActivity();
            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("idfaculty", Session.getSession().getFacultad().getId());
            HttpClient.get(Constants.HTTP_GET_ALL_SUBJECTS, params, new JsonHttpResponseHandlerCustom(context) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        int error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            List<Asignatura> listofSubjects = mapper.readValue(
                                    response.get("data").toString(), new TypeReference<List<Asignatura>>() {
                                    });
                            spinnersubject.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listofSubjects));
                            spinnersubject.setOnItemSelectedListener(thisfragment);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView == spinnersubject) {
            Asignatura a = (Asignatura) adapterView.getSelectedItem();
            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("subject", a.getId());
            HttpClient.get(Constants.HTTP_GET_THEMES, params, new JsonHttpResponseHandlerCustom(context) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        int error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            List<Tema> listofThemes = mapper.readValue(
                                    response.get("data").toString(), new TypeReference<List<Tema>>() {
                                    });

                            Tema t = new Tema();
                            t.setId(-1);
                            t.setNombre((listofThemes.size() == 0) ? "No existen temas de esta asignatura" : "Elige un tema");
                            listofThemes.add(0, t);

                            spinnertheme.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listofThemes));
                            spinnertheme.setOnItemSelectedListener(thisfragment);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == button_selectyearmonth) {

            DatePickerBuilder dpb = new DatePickerBuilder()
                    .setFragmentManager(getActivity().getSupportFragmentManager())
                    .setTargetFragment(this)
                    .setStyleResId(R.style.BetterPickersDialogFragment_Light);
            dpb.show();
        }
    }

    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        Log.i(TAG, "onDialogExpirationSet");
        monthSelected = monthOfYear;
        yearSelected = year;
        textViewMonth.setText(DateManager.monthToString(monthOfYear + 1));
        textViewYear.setText(String.valueOf(year));
    }
}
