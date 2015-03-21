package com.bsod.tfg.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bsod.tfg.modelo.otros.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Payton on 10/10/2014.
 */
public class HttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void get(String url, FileAsyncHttpResponseHandler responseHandler) {

        client.get(getAbsoluteUrl(url), responseHandler);

    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Constants.BASE_URL + relativeUrl;
    }

    public static boolean isOnline(Context ct) {
        ConnectivityManager cm =
                (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
