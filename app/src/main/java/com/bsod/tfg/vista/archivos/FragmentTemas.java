package com.bsod.tfg.vista.archivos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bsod.tfg.R;
import com.bsod.tfg.controlador.archivos.AdapterTemas;
import com.bsod.tfg.modelo.archivos.Asignatura;
import com.bsod.tfg.modelo.archivos.preguntas.Pregunta;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaMultiple;
import com.bsod.tfg.modelo.archivos.preguntas.PreguntaRespuestaUnica;
import com.bsod.tfg.modelo.archivos.Tema;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTemas extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final String TAG = "FragmentTemas";
    private ListView listviewTemas;
    private View rootView;
    private AdapterTemas adapterTemas;
    private int idsubject;
    private String nameOfSubject;
    private TextView textViewTema;
    private ImageView favStar;
    private Boolean isFavorited;
    private Context context;

    public static FragmentTemas newInstance(int tema, String nameOfSubject, Boolean is_favorited) {
        FragmentTemas myFragment = new FragmentTemas();

        Bundle args = new Bundle();
        args.putInt("idsubject", tema);
        args.putString("nameOfSubject", nameOfSubject);
        args.putBoolean("isFavorited", is_favorited);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {

            context = getActivity();

            idsubject = getArguments().getInt("idsubject", 0);
            nameOfSubject = getArguments().getString("nameOfSubject", "");
            isFavorited = getArguments().getBoolean("isFavorited", false);
            rootView = inflater.inflate(R.layout.fragment_temas, container, false);

            listviewTemas = (ListView) rootView.findViewById(R.id.listview_temas);
            final AdapterTemas adapter = (adapterTemas == null) ? adapterTemas = new AdapterTemas(getActivity()) : adapterTemas;
            listviewTemas.setAdapter(adapter);
            listviewTemas.setOnItemClickListener(this);
            textViewTema = (TextView) rootView.findViewById(R.id.textViewTema);
            textViewTema.setText(nameOfSubject);

            favStar = (ImageView) rootView.findViewById(R.id.favoriteAsignatura);
            favStar.setImageResource((isFavorited) ? R.drawable.ic_action_important_pushed : R.drawable.ic_action_important);
            favStar.setOnClickListener(this);
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
                        //mapper.registerModule(new JsonOrgModule());

                        List<Tema> list = mapper.readValue(
                                response.get("data").toString(), new TypeReference<List<Tema>>() {
                                });
                        adapterTemas.update(list);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


        new MaterialDialog.Builder(context)
                .title("Elige tipo de pregunta")
                .items(Constants.TYPE_OF_QUESTIONS)
                .itemsCallback(new MaterialDialog.ListCallback() {
                                   @Override
                                   public void onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {

                                       final String[] typeOfQuestions = Constants.TYPE_OF_QUESTIONS_SHORT;
                                       RequestParams params = new RequestParams();
                                       params.put("token", Session.getSession().getToken().getToken());
                                       params.put("subject", idsubject);
                                       params.put("theme", adapterTemas.getItem(position).getid());
                                       params.put("numberofquestions", Constants.NUM_OF_QUESTIONS_IN_EXAM);
                                       params.put("typeofquestion", typeOfQuestions[which]);

                                       if (position >= 0) {
                                           HttpClient.get(Constants.HTTP_GET_EXAMS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
                                               @Override
                                               public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                   final Pregunta p;
                                                   int error;
                                                   try {
                                                       TypeReference tr = null;
                                                       error = Integer.parseInt(response.get("error").toString());
                                                       if (error == 200) {
                                                           ObjectMapper mapper = new ObjectMapper();
                                                           if (typeOfQuestions[which].equals(typeOfQuestions[0])) {
                                                               tr = new TypeReference<List<PreguntaRespuestaUnica>>() {
                                                               };
                                                           } else if (typeOfQuestions[which].equals(typeOfQuestions[1])) {
                                                               tr = new TypeReference<List<PreguntaRespuestaMultiple>>() {
                                                               };
                                                           }
                                                           if (tr != null) {

                                                               ArrayList<?> listOfQuestions = mapper.readValue(
                                                                       response.get("data").toString(), tr);

                                                               int idTest = mapper.readValue(response.get("test").toString(), Integer.class);

                                                               Intent i = new Intent(getActivity(), ActivitySolveExam.class);
                                                               i.putExtra(Constants.INTENT_EXTRA_ARRAY_QUESTIONS, listOfQuestions);
                                                               i.putExtra(Constants.INTENT_ID_TEST, idTest);
                                                               i.putExtra(Constants.INTENT_EXTRA_TYPE_OF_QUESTIONS, which);
                                                               i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                               startActivity(i);
                                                           }
                                                       } else {
                                                           Toast.makeText(context, "No existen preguntas de este tipo para este tema", Toast.LENGTH_SHORT).show();
                                                       }

                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                                   }
                                               }
                                           });
                                       }

                                   }
                               }
                ).show();


    }

    @Override
    public void onClick(View view) {
        if (view == favStar) {
            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("subject", idsubject);
            HttpClient.get(Constants.HTTP_FAV_SUBJECT, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    int error;
                    try {
                        error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            Asignatura a = mapper.readValue(response.get("data").toString(), Asignatura.class);
                            isFavorited = a.getUser_favorited();
                            favStar.setImageResource((isFavorited) ? R.drawable.ic_action_important_pushed : R.drawable.ic_action_important);

                        } else {
                            Toast.makeText(context, "Hubo un error al intentar añadir a favoritos esta asignatura... Inténtalo más tarde", Toast.LENGTH_SHORT).show();
                            favStar.setImageResource((isFavorited) ? R.drawable.ic_action_important_pushed : R.drawable.ic_action_important);
                        }

                    } catch (Exception e) {
                        Toast.makeText(context, "Hubo un error al intentar añadir a favoritos esta asignatura... Inténtalo más tarde", Toast.LENGTH_SHORT).show();
                        favStar.setImageResource((isFavorited) ? R.drawable.ic_action_important_pushed : R.drawable.ic_action_important);
                        Log.i(TAG, e.toString());
                    }

                }

                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(context, "Hubo un error al intentar añadir a favoritos esta asignatura... Inténtalo más tarde", Toast.LENGTH_SHORT).show();
                    favStar.setImageResource((isFavorited) ? R.drawable.ic_action_important_pushed : R.drawable.ic_action_important);
                }

                public void onFailure(int status, Header[] h, String s, Throwable t) {
                    Toast.makeText(context, "Hubo un error al intentar añadir a favoritos esta asignatura... Inténtalo más tarde", Toast.LENGTH_SHORT).show();
                    favStar.setImageResource((isFavorited) ? R.drawable.ic_action_important_pushed : R.drawable.ic_action_important);
                }

            });
        }
    }
}
