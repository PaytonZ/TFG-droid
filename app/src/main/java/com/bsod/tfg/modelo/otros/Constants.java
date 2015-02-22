package com.bsod.tfg.modelo.otros;

/**
 * Created by Payton on 08/10/2014.
 */
public class Constants {

    public final static int UNI_SELECTED = 201;
    public final static String USER = "user";

    /*
     * HTTP FUNCTIONS NAMES
     */
    // Login and register
    public final static String HTTP_LOGIN_USER = "loginuser";
    public final static String HTTP_GET_PROVINCIAS = "getprovincias";
    public final static String HTTP_GET_UNIS = "getunis";
    public final static String HTTP_REGISTER_USER = "registeruser";
    public static final String HTTP_GET_FACULTYS = "getfacultades";
    public static final String HTTP_CHECK_USER = "checkuser";
    public static final String HTTP_CHECK_EMAIL = "checkemail";
    //User
    public static final String HTTP_UPLOAD_IMAGE = "uploadimageuser";
    //Tablon
    public static final String HTTP_GET_MESSAGES_BOARD = "getmensajestablon";
    public static final String HTTP_POST_MESSAGES_BOARD = "sendmensajetablon";
    public static final String HTTP_FAV_MESSAGE = "favoritemessage";
    public static final String HTTP_UPDATE_MESSAGES = "updatemessages";
    // Exams
    public static final String HTTP_FAV_SUBJECT = "addfavsubject";
    public static final String HTTP_GET_FAV_SUBJECTS = "getfavsubjects";
    public static final String HTTP_GET_SUBJECTS = "getsubjects";
    public static final String HTTP_GET_THEMES = "getthemes";
    public static final String HTTP_GET_EXAMS = "getexams";
    public static final String HTTP_SEND_EXAMS = "sendresults";
    //others
    public final static String HTTP_DOWNLOAD_MEDIA = "media/";
    public final static String HTTP_RENEW_TOKEN = "renewtoken";
    public static final String INTENT_EXTRA_ARRAY_QUESTIONS = "questions";
    public static final String INTENT_ID_TEST = "idtest";


    /* Intent Extras */
    public static final String INTENT_MESSAGE_DETAIL = "MESSAGE";

    /* Application Settings */
    public static final int NUM_OF_QUESTIONS_IN_EXAM = 20;

    /* Connection Settings - Deploy - Testing */
    public static String BASE_URL = "http://192.168.1.12/";
    //public static String BASE_URL = "http:/bsodsoftware.me/";
    //public static String BASE_URL = "http://178.62.194.33/";
    public static String MEDIA_URL = BASE_URL + "media/";


}
