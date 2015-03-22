package com.bsod.tfg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Proudly created by Payton on 02/12/2014.
 */
public class DateManager {

    private static final String TAG = "DateManager";
    private static int SECONDS = 60;
    private static int HOUR = SECONDS * 60;
    private static int DAY = HOUR * 24;

    /**
     * Convierte una fecha dada en una diferencia en texto 'humano' , indicando la diferencia entre la fecha y la actual en segundos
     * minutos , horas o días , según sea la diferencia.
     *
     * @param date
     * @return
     */
    public static String convertUnixToHumanDate(long date) {

        /**
         String sent_ago = "Enviado hace ";
         String segundo = " segundo";
         String segundos = " segundos";
         String minuto = " minuto";
         String minutos = " minutos";
         String hora = " hora";
         String horas = " horas";
         String dia = " día";
         String dias = " días";
         **/
        //TODO: internacinalizar
        String sent_ago = "";
        String segundo = " seg";
        String segundos = " segs";
        String minuto = " min";
        String minutos = " mins";
        String hora = " hora";
        String horas = " horas";
        String dia = " d";
        String dias = " d";
        //long actualUNIXTime =
        //Date actualDate = new Date();
        long seconds = Math.abs(date - (System.currentTimeMillis() / 1000L));
        //Log.i(TAG, "SERVERUNIXTIME : " + String.valueOf(date));
        //Log.i(TAG, "ACTUALUNIXTIME : " + String.valueOf(actualUNIXTime));
        //Log.i(TAG, String.valueOf(seconds));

        if (seconds == 1) {
            return sent_ago + seconds + segundo;
        } else if (seconds > 1 && seconds <= SECONDS) {
            return sent_ago + seconds + segundos;
        } else if (seconds > SECONDS && seconds < SECONDS * 2) {
            return sent_ago + Math.round(seconds / SECONDS) + minuto;
        } else if (seconds >= SECONDS * 2 && seconds < HOUR) {
            return sent_ago + Math.round(seconds / SECONDS) + minutos;
        } else if (seconds >= HOUR && seconds < HOUR * 2) {
            return sent_ago + Math.round(seconds / (HOUR)) + hora;
        } else if (seconds >= HOUR && seconds < DAY) {
            return sent_ago + Math.round(seconds / (HOUR)) + horas;
        } else if (seconds >= DAY && seconds < DAY * 2) {
            return sent_ago + Math.round(seconds / (DAY)) + dia;
        } else {
            return sent_ago + Math.round(seconds / (DAY)) + dias;
        }

    }

    /**
     * Dado un timestamp , lo transforma a formato HH:ss
     *
     * @param time
     * @return
     */
    public static String toHoursandMinutes(Long time) throws ParseException {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static String toDayMonthYear(Date d) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return format.format(d);
    }

    public static String timeStamptoDayMonthYear(long date) {
        return toDayMonthYear(new Date(date * 1000L));
    }

}

