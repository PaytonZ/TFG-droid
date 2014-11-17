package com.bsod.tfg.vista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.ResponseExamTotal;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFinalExam extends Fragment implements View.OnClickListener {


    private View rootView;
    private Button buttonSendResults;
    private RatingBar examRating;
    private TextView textViewMark;


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
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }


    @Override
    public void onClick(View view) {

        if (view == buttonSendResults) {


            ResponseExamTotal ret = ((CorrectExam) getActivity()).correctQuestions();

            examRating.setRating((float) ret.getFinalMark() / 2.0f);
            examRating.setVisibility(View.VISIBLE);
            examRating.setIsIndicator(true);
            buttonSendResults.setVisibility(View.INVISIBLE);
            textViewMark.setText(String.valueOf(ret.getFinalMark()));


        }
    }

    public interface CorrectExam {
        ResponseExamTotal correctQuestions();
    }
}


