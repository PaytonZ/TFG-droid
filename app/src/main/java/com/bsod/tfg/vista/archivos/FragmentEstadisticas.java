package com.bsod.tfg.vista.archivos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
            final PieChart chart_stats_answers = (PieChart) rootView.findViewById(R.id.chart_answers);
            chart_stats_answers.setDescription("");
            final LineChart chart_mark = (LineChart) rootView.findViewById(R.id.chart_mark);
            chart_mark.setDescription("");
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
                            /* Tabla de Nota media y Tiempo Medio */
                            TableLayout table_average = (TableLayout) rootView.findViewById(R.id.table_average);
                            createRowTableAverage(table_average, "Nota Media", String.valueOf(egt.getAverageMark()));
                            createRowTableAverage(table_average, "Tiempo Medio (segundos)", String.valueOf(egt.getAverageTime() / 1000));
                            /* Gráfico circular de estadísticas de acierto */
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
                            /* Gráfico lineal de fecha y nota */
                            ArrayList<Entry> notasEntry = new ArrayList<>();
                            ArrayList<Entry> aciertosEntry = new ArrayList<>();
                            ArrayList<Entry> fallosEntry = new ArrayList<>();
                            ArrayList<Entry> noContestadasEntry = new ArrayList<>();
                            ArrayList<String> xVals = new ArrayList<>();


                            int lenght = egt.getEstadisticasTests().length;
                            for (int i = 0; i < lenght; i++) {
                                notasEntry.add(new Entry(egt.getEstadisticasTests()[i].getNota(), i));
                                aciertosEntry.add(new Entry(egt.getEstadisticasTests()[i].getAcertadas(), i));
                                fallosEntry.add(new Entry(egt.getEstadisticasTests()[i].getFalladas(), i));
                                noContestadasEntry.add(new Entry(egt.getEstadisticasTests()[i].getNoRespondidas(), i));
                                xVals.add(toDayMonthYear(egt.getEstadisticasTests()[i].getDate()));
                            }
                            LineDataSet notasLDS = new LineDataSet(notasEntry, "Notas");
                            LineDataSet aciertosLDS = new LineDataSet(aciertosEntry, "Aciertos");
                            LineDataSet fallosLDS = new LineDataSet(fallosEntry, "Fallos");
                            LineDataSet nocontestadasLDS = new LineDataSet(noContestadasEntry, "No Contestadas");

                            aciertosLDS.setColor(getResources().getColor(R.color.green_test));
                            fallosLDS.setColor(getResources().getColor(R.color.red_test));
                            nocontestadasLDS.setColor(getResources().getColor(R.color.orange));

                            ArrayList<LineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(notasLDS);
                            dataSets.add(aciertosLDS);
                            dataSets.add(fallosLDS);
                            dataSets.add(nocontestadasLDS);


                            LineData data = new LineData(xVals, dataSets);
                            chart_mark.setData(data);

                            chart_mark.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });
                            chart_mark.invalidate(); // refresh


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


    private void createRowTableAverage(TableLayout tb, String text1, String text2) {
        TableRow tR = new TableRow(context);
        tR.setPadding(5, 5, 5, 5);
        TextView tV_txt1 = new TextView(context);
        tV_txt1.setText(text1);
        TextView tV_txt2 = new TextView(context);
        tV_txt2.setText(text2);
        tR.addView(tV_txt1);
        tR.addView(tV_txt2);
        tb.addView(tR);
    }


    private void createRowTest(TableLayout tb, String fecha, String nota, String acertadas, String falladas, String noContestadas, String tiempo) {
        TableRow tR = new TableRow(context);

        //Drawable background = getResources().getDrawable(R.drawable.border_question);
        tR.setPadding(5, 5, 5, 5);
        TextView tV_txt1 = new TextView(context);
        TextView tV_txt2 = new TextView(context);
        TextView tV_txt3 = new TextView(context);
        TextView tV_txt4 = new TextView(context);
        TextView tV_txt5 = new TextView(context);
        TextView tV_txt6 = new TextView(context);

        tV_txt1.setText(fecha);
        tV_txt2.setText(nota);
        tV_txt3.setText(acertadas);
        tV_txt4.setText(falladas);
        tV_txt5.setText(noContestadas);
        tV_txt6.setText(tiempo);

        tV_txt1.setPadding(0, 0, 6, 0);
        tV_txt2.setPadding(0, 0, 6, 0);

        tR.addView(tV_txt1);
        tR.addView(tV_txt2);
        tR.addView(tV_txt3);
        tR.addView(tV_txt4);
        tR.addView(tV_txt5);
        tR.addView(tV_txt6);

        tb.addView(tR);
    }

}
