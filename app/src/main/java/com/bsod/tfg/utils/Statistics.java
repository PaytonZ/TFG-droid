package com.bsod.tfg.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Proudly created by Payton on 31/10/2014.
 */
public class Statistics {

    /**
     * Devuelve la resolución de la pantalla en formato ancho x altura
     *
     * @param ct el contexto de la actividad
     * @return El string con la resolución de la pantalla
     */
    public String getResolution(Context ct) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) ct.getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
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
