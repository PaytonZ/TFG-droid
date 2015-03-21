package com.bsod.tfg.utils;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Payton on 14/10/2014.
 */
public class JsonHttpResponseHandlerCustom extends JsonHttpResponseHandler {

    private Context mContext;

    public JsonHttpResponseHandlerCustom(Context context) {
        mContext = context;
    }

    @Override
    public void onStart() {
        // called before request is started
        ProgressDialogCustom.makeDialogLoading(mContext);
    }

    @Override
    public void onFinish() {
        ProgressDialogCustom.dissmissDialog();
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Toast.makeText(mContext, "Hubo un problema al procesar tu petición... Inténtalo más tarde", Toast.LENGTH_SHORT).show();
    }


}
