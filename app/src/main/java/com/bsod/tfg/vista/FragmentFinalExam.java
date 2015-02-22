package com.bsod.tfg.vista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.PreguntaRespondida;
import com.bsod.tfg.modelo.ResponseExamTotal;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFinalExam extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentFinalExam";
    private View rootView;
    private Button buttonSendResults;
    private RatingBar examRating;
    private TextView textViewMark;
    private TextView textViewRating;
    private TextView textViewYourMark;


    public static Fragment newInstance() {
        return new FragmentFinalExam();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_final_exam, container, false);
            buttonSendResults = (Button) rootView.findViewById(R.id.send_results_button);
            buttonSendResults.setOnClickListener(this);
            examRating = (RatingBar) rootView.findViewById(R.id.exam_rating);
            textViewMark = (TextView) rootView.findViewById(R.id.textViewMark);
            textViewRating = (TextView) rootView.findViewById(R.id.textView_your_rating);
            textViewYourMark = (TextView) rootView.findViewById(R.id.your_mark);

        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {

        if (view == buttonSendResults) {

            final ResponseExamTotal ret = ((CorrectExam) getActivity()).correctQuestions();
            RequestParams params = new RequestParams();
            params.put("token", Session.getSession().getToken().getToken());
            params.put("time", ret.getTime());
            params.put("idtest", ret.getIdTest());
            params.put("score", ret.getFinalMark());
            SparseIntArray sa = ret.getQuestions();
            List<PreguntaRespondida> list = new ArrayList<PreguntaRespondida>();

            for (int i = 0; i < sa.size(); i++) {
                PreguntaRespondida pr = new PreguntaRespondida();
                int key = sa.keyAt(i);
                pr.seti(key);
                pr.setr(sa.get(key));
                list.add(pr);
            }
            ObjectMapper mapper = new ObjectMapper();
            //mapper.registerModule(new JsonOrgModule());
            try {
                params.put("questions", mapper.writeValueAsString(list));
                Log.i(TAG, mapper.writeValueAsString(list));
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpClient.get(Constants.HTTP_SEND_EXAMS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                int error;
                                error = Integer.parseInt(response.get("error").toString());
                                if (error == 200) {

                                    Toast.makeText(getActivity(), getString(R.string.exam_send_succesful), Toast.LENGTH_SHORT).show();
                                    examRating.setRating((float) ret.getFinalMark() / 2.0f);
                                    examRating.setStepSize(0.25f);
                                    examRating.setVisibility(View.VISIBLE);
                                    examRating.setIsIndicator(true);
                                    buttonSendResults.setVisibility(View.INVISIBLE);
                                    textViewMark.setVisibility(View.VISIBLE);
                                    textViewMark.setText(String.valueOf(ret.getFinalMark()));
                                    textViewRating.setVisibility(View.VISIBLE);
                                    textViewYourMark.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), getString(R.string.exam_send_error), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(getActivity(), getString(R.string.exam_send_error), Toast.LENGTH_SHORT).show();
                        }
                    }

            );

        }
    }

    public interface CorrectExam {
        ResponseExamTotal correctQuestions();
    }
}


