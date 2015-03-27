package com.bsod.tfg.utils;


import android.content.Context;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.vista.App;
import com.gc.materialdesign.widgets.ProgressDialog;

/**
 * Created by Payton on 14/10/2014.
 */
public class ProgressDialogCustom {


    private static ProgressDialog progressDialog;

    /**
     * Genera un mensaje de dialogo con los textos que se añaden por parametro
     *
     * @param context
     * @param message
     * @param title
     */
    public static void makeDialog(Context context, String message, String title) {

        //progressDialog = ProgressDialog.show(context, title, message);

        progressDialog.setContentView(R.layout.progress_custom);
        TextView tv = (TextView) progressDialog.findViewById(R.id.progressdialogtext);
        tv.setText(message);
        progressDialog.setCancelable(true);
    }

    /**
     * Genera un mensaje de dialogo con el texto 'Cargando'
     *
     * @param context El contexto para que pueda generar el mensaje
     */
    public static void makeDialogLoading(Context context) {

        progressDialog = new ProgressDialog(context, App.getContext().getResources().getString(R.string.loading));
        //progressDialog = ProgressDialog.show(context, "", App.getContext().getResources().getString(R.string.loading));
        progressDialog.requestWindowFeature(R.layout.progress_custom);
        //progressDialog.setContentView(R.layout.progress_custom);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * Descarta el mensaje de dialogo anteriormente creado. Es necesario que estuviese creado antes
     */
    public static void dissmissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


}
