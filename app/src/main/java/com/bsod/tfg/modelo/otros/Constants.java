package com.bsod.tfg.modelo.otros;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;

/**
 * Created by Payton on 08/10/2014.
 */
public class Constants {

    public final static int UNI_SELECTED = 201;
    public static final int INTENT_MESSAGE_DELETED = 202;
    public static final int INTENT_CHANGE_PASSWORD = 203;

    public final static String USER = "user";

    /*
     * HTTP FUNCTIONS NAMES
     */

    /* LOGIN AND REGISTER*/
    public final static String HTTP_LOGIN_USER = "loginuser";
    public final static String HTTP_GET_PROVINCIAS = "getprovincias";
    public final static String HTTP_GET_UNIS = "getunis";
    public final static String HTTP_REGISTER_USER = "registeruser";
    public static final String HTTP_GET_FACULTYS = "getfacultades";
    public static final String HTTP_GET_ALL_FACULTYS = "getallfacultys";
    public static final String HTTP_CHECK_USER = "checkuser";
    public static final String HTTP_CHECK_EMAIL = "checkemail";
    /* USER STUFF */
    public static final String HTTP_UPLOAD_IMAGE = "uploadimageuser";
    public static final String HTTP_CHANGE_PASSWORD = "changepassword";
    public static final String HTTP_CHANGE_FACULTY = "changefaculty";

    /* TABLON */
    public static final String HTTP_GET_MESSAGES_BOARD = "getmensajestablon";
    public static final String HTTP_POST_MESSAGES_BOARD = "sendmensajetablon";
    public static final String HTTP_FAV_MESSAGE = "favoritemessage";
    public static final String HTTP_UPDATE_MESSAGES = "updatemessages";
    public static final String HTTP_DELETE_MESSAGE = "deletemessage";
    /* ARCHIVOS */
    public static final String HTTP_FAV_SUBJECT = "favoritesubject";
    public static final String HTTP_GET_FAV_SUBJECTS = "getfavsubjects";
    public static final String HTTP_GET_SUBJECTS = "getsubjects";
    public static final String HTTP_GET_ALL_SUBJECTS = "getallsubjects";
    public static final String HTTP_GET_THEMES = "getthemes";
    public static final String HTTP_GET_EXAMS = "getexams";
    public static final String HTTP_SEND_EXAMS = "sendresults";
    public static final String HTTP_CREATE_DOCUMENT = "createdocument";
    public static final String HTTP_GET_DOCUMENTS = "getdocuments";

    /* STATS */
    public static final String HTTP_GENERATE_EXAM_STATS = "generateuserstats";

    /* OTHERS */
    public final static String HTTP_DOWNLOAD_MEDIA = "media/";
    public final static String HTTP_RENEW_TOKEN = "renewtoken";
    public static final String INTENT_EXTRA_ARRAY_QUESTIONS = "questions";
    public static final String INTENT_ID_TEST = "idtest";
    /* Intent Extras */
    public static final String INTENT_MESSAGE_DETAIL = "MESSAGE";
    public static final String INTENT_CHAT_ROOM = "INTENT_CHAT_ROOM";
    public static final String INTENT_EXTRA_DELETED_MESSAGE_ID = "INTENT_EXTRA_DELETED_MESSAGE_ID";
    public static final String TFG_FRAGMENT = "TFGFragment";
    public static final String CHAT_BROADCAST = "CHAT_BROADCAST";
    public static final String CHAT_SERVER_EXTRA = "CHAT_SERVER_EXTRA";
    public static final String INTENT_USER_IMAGE_DETAIL = "INTENT_USER_IMAGE_DETAIL";
    public static final String INTENT_EXTRA_TYPE_OF_QUESTIONS = "INTENT_EXTRA_TYPE_OF_QUESTIONS";
    public static final String INTENT_EXTRA_IMAGELIST_DOCUMENTS = "INTENT_EXTRA_IMAGELIST_DOCUMENTS";
    /* Application Settings */
    public static final int NUM_OF_QUESTIONS_IN_EXAM = 3;
    /* Local SQLITE */
    public static final String DB_NAME = "database.sqlite";
    public static final int DATABASE_VERSION = 1;

    public static final String[] TYPE_OF_QUESTIONS = {App.getContext().getString(R.string.unique_answer), App.getContext().getString(R.string.multiple_answer), App.getContext().getString(R.string.short_answer), App.getContext().getString(R.string.pairing_answer)};
    public static final String[] TYPE_OF_QUESTIONS_SHORT = {"na", "ma", "sa", "pa"};

    public static final String[] TYPE_OF_DOCUMENTS = {App.getContext().getString(R.string.exam), App.getContext().getString(R.string.notes)};
    public static final String[] TYPE_OF_DOCUMENTS_SHORT = {"exam", "notes"};

    /* CHAT STUFF */
    public static final String CHAT_STATE_CHAT = "CHAT";
    public static final int CHAT_PORT = 8000;
    /* Connection Settings - Deploy - Testing */
    //public static String BASE_URL = "http://192.168.1.12/";
    //public static String BASE_URL = "http:/bsodsoftware.me/";
    public static final String SERVER_IP = "192.168.1.12";
    //public static String SERVER_IP = "178.62.194.33";
    public static final String BASE_URL = "http://".concat(SERVER_IP).concat("/");
    public static final String MEDIA_URL = BASE_URL.concat("media/");
    public static final String ADMIN_URL = BASE_URL.concat("admin/");


}
