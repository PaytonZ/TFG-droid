package com.bsod.tfg.utils;

import java.util.Date;

/**
 * Proudly created by Payton on 02/12/2014.
 */
public class DateManager {

    private static int SECONDS = 60;
    private static int HOUR = SECONDS * 60;
    private static int DAY = HOUR * 24;

    /**
     * Convierte una fecha dada en una diferencia en texto 'humano' , indicando la diferencia entre la fecha y la actual en segundos
     * minutos , horas o días , según sea la diferencia.
     *
     * @param d
     * @return
     */
    public static String convertToHumanDate(Date d) {

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

        String sent_ago = "";
        String segundo = " seg";
        String segundos = " segs";
        String minuto = " min";
        String minutos = " mins";
        String hora = " hora";
        String horas = " horas";
        String dia = " d";
        String dias = " d";

        Date actualDate = new Date();
        long seconds = (actualDate.getTime() - d.getTime()) / 1000;
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


}

