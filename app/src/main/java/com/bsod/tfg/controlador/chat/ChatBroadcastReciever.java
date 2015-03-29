package com.bsod.tfg.controlador.chat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;
import com.bsod.tfg.controlador.bbdd.DataBaseHelper;
import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.vista.App;
import com.j256.ormlite.dao.Dao;

import static java.lang.Math.min;

/**
 * Proudly created by Payton on 15/03/2015.
 */
public class ChatBroadcastReciever extends BroadcastReceiver {
    private static final String TAG = "ChatBroadcastReciever";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Dao<ChatServerBean, Integer> daochatServer = DataBaseHelper.getInstance().getDAOChatServer();
            ChatServerBean csb = (ChatServerBean) intent.getSerializableExtra(Constants.CHAT_SERVER_EXTRA);
            csb = daochatServer.createIfNotExists(csb);
            Log.i(TAG, "Mensaje persistido en BBDD");
            Thread.sleep(2000);
            /* El mensaje no ha sido consumido por el cliente , notificamos al usuario de existencia de nuevo mensaje */
            if (daochatServer.queryBuilder().where().eq("id_db", csb.getId_db()).countOf() > 0) {

                Context ctx = App.getContext();
                NotificationManager mNotificationManager = (NotificationManager)
                        ctx.getSystemService(Context.NOTIFICATION_SERVICE);

                PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                        new Intent(ctx, ActivityMain.class), 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
                mBuilder.setSmallIcon(R.drawable.posibleicon);
                mBuilder.setContentTitle(ctx.getString(R.string.app_name));
                mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(ctx.getString(R.string.app_name)));
                mBuilder.setContentText(csb.getMessage().substring(0, min(csb.getMessage().length(), 30)));
                mBuilder.setAutoCancel(true).setContentIntent(contentIntent);

                mNotificationManager.notify(1, mBuilder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
