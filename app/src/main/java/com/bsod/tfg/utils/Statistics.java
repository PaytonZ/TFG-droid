package com.bsod.tfg.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.bsod.tfg.BuildConfig;
import com.bsod.tfg.controlador.bbdd.DataBaseHelper;
import com.bsod.tfg.modelo.estadisticas.EstadisticasBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Proudly created by Payton on 31/10/2014.
 */
public class Statistics {

    public static final boolean PROFILING_ACTIVE = (BuildConfig.DEBUG);
    private static final String TAG = "Statistics";
    private static HashMap<String, Long> timeMap = new HashMap<>();

    public static void startProfiling(String TAG) {
        if (PROFILING_ACTIVE) {
            timeMap.put(TAG, System.nanoTime());
        }

    }

    public static void stopProfiling(String TAG, @Nullable String other) {
        if (PROFILING_ACTIVE) {
            if (timeMap.containsKey(TAG)) {
                Dao<EstadisticasBean, Integer> daoEstadisticas = DataBaseHelper.getInstance().getDAOEstadisticas();
                long timeElapsed = (System.nanoTime() - timeMap.get(TAG)) / 1000000;
                timeMap.remove(TAG);
                String other1 = (other == null) ? "" : other;
                Log.d("Stadistics", TAG.concat(" class take ").concat(String.valueOf(timeElapsed)).concat(" milliseconds to do : ").concat(other1));
                EstadisticasBean eb = new EstadisticasBean();
                eb.setSeconds(timeElapsed);
                eb.setTag(TAG);
                eb.setOther(other1);

                try {
                    daoEstadisticas.create(eb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Log.e("Stadistics", "The class".concat(TAG).concat(" dont start profiling."));
        }
    }

    public static void showStadisticsViaLog() {

        Log.v(TAG, "=========================================================");
        Log.v(TAG, "GENERATED STATS ");
        Dao<EstadisticasBean, Integer> daoEstadisticas = DataBaseHelper.getInstance().getDAOEstadisticas();
        try {
            List<EstadisticasBean> stats =
                    daoEstadisticas.query(
                            daoEstadisticas.queryBuilder().groupBy("tag")
                                    .prepare());
            for (EstadisticasBean stat : stats) {
                Log.v(TAG, stat.getTag() + " - " + stat.getSeconds() + " OTHER: " + stat.getOther());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "=========================================================");
    }


    /**
     * Devuelve la resolución de la pantalla en formato ancho x altura
     *
     * @param ct el contexto de la actividad
     * @return El string con la resolución de la pantalla
     */
    public String getResolution(Context ct) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) ct.getSystemService(Context.WINDOW_SERVICE);
        // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        return screenWidth + "x" + screenHeight;
    }

    /**
     * Devuelve el nombre del modelo del dispositivo
     *
     * @return El nombre del modelo del dispositivo
     */
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public String getAndroidVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


}
