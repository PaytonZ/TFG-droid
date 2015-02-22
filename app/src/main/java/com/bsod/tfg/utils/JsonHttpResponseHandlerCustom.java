package com.bsod.tfg.utils;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

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
}
