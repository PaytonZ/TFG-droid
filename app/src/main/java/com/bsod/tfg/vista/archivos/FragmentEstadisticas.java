package com.bsod.tfg.vista.archivos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.archivos.estadisticas.EstadisticasGlobalTest;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bsod.tfg.utils.DateManager.toDayMonthYear;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEstadisticas extends Fragment {

    private Context context;
    private View rootView;

    public FragmentEstadisticas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_estadisticas, container, false);
            final LinearLayout l = (LinearLayout) rootView.findViewById(R.id.fragment_estadisticas_layout);
            final PieChart chart_stats_answers = (PieChart) rootView.findViewById(R.id.chart_answers);
            final LineChart chart_mark = (LineChart) rootView.findViewById(R.id.chart_mark);
            context = getActivity();
            RequestParams params = new RequestParams();


            params.put("token", Session.getSession().getToken().getToken());

            HttpClient.get(Constants.HTTP_GENERATE_EXAM_STATS, params, new JsonHttpResponseHandlerCustom(getActivity()) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    int error;
                    try {
                        error = Integer.parseInt(response.get("error").toString());
                        if (error == 200) {
                            //swipeLayout.setRefreshing(true);
                            ObjectMapper mapper = new ObjectMapper();
                            EstadisticasGlobalTest egt = mapper.readValue(response.get("data").toString(), EstadisticasGlobalTest.class);
                            ArrayList<Entry> responseData = new ArrayList<>();
                            responseData.add(new Entry(egt.getAverageCorrect(), 0));
                            responseData.add(new Entry(egt.getAverageFailed(), 1));
                            responseData.add(new Entry(egt.getAverageNotAnswered(), 2));
                            ArrayList<String> label = new ArrayList<>();
                            label.add("Correctas");
                            label.add("Falladas");
                            label.add("Sin Contestar");
                            PieDataSet pds = new PieDataSet(responseData, "");
                            ArrayList<Integer> colors = new ArrayList<>();
                            colors.add(getResources().getColor(R.color.green_test));
                            colors.add(getResources().getColor(R.color.red_test));
                            colors.add(getResources().getColor(R.color.orange));
                            pds.setColors(colors);
                            PieData pd = new PieData(label, pds);
                            pd.setValueFormatter(new ValueFormatter() {
                                @Override
                                public String getFormattedValue(float value) {
                                    return String.valueOf(value).concat(" %");
                                }
                            });
                            pd.setValueTextColor(getResources().getColor(R.color.white));
                            chart_stats_answers.setData(pd);
                            chart_stats_answers.invalidate();

                            LineData ld = new LineData();
                            responseData = new ArrayList<>();
                            ArrayList<String> xVals = new ArrayList<>();
                            int lenght = egt.getEstadisticasTests().length;
                            for (int i = 0; i < lenght; i++) {
                                responseData.add(new Entry(egt.getEstadisticasTests()[i].getNota(), i));
                                xVals.add(toDayMonthYear(egt.getEstadisticasTests()[i].getDate()));
                            }
                            LineDataSet setComp1 = new LineDataSet(responseData, "Notas");
                            LineData data = new LineData(xVals, setComp1);
                            chart_mark.setData(data);
                            chart_mark.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });
                            chart_mark.invalidate(); // refresh


                            TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, 150);
                            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                            TableLayout tableLayout = new TableLayout(context);
                            tableLayout.setLayoutParams(tableParams);

                            TableRow tableRow = new TableRow(context);
                            tableRow.setLayoutParams(tableParams);

                            TextView textView = new TextView(context);
                            textView.setLayoutParams(rowParams);
                            textView.setText("WTG");
                            tableRow.addView(textView);
                            l.addView(tableLayout);
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


}
