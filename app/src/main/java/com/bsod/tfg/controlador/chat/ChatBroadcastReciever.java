package com.bsod.tfg.controlador.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bsod.tfg.controlador.bbdd.DataBaseHelper;
import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.otros.Constants;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Proudly created by Payton on 15/03/2015.
 */
public class ChatBroadcastReciever extends BroadcastReceiver {
    private static final String TAG = "ChatBroadcastReciever";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Dao<ChatServerBean, Integer> daochatServer = DataBaseHelper.getInstance().getDAOChatServer();
            daochatServer.create((ChatServerBean) intent.getSerializableExtra(Constants.CHAT_SERVER_EXTRA));
            Log.i(TAG, "Mensaje persistido en BBDD");
/*
            Context ctx = App.getContext();
            NotificationManager mNotificationManager = (NotificationManager)
                    App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                    new Intent(ctx, ActivityMain.class), 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(App.getContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Esto es una prueba")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(App.getContext().getString(R.string.app_name)))
                    .setContentText(App.getContext().getString(R.string.app_name))
                    .setAutoCancel(true);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(1, mBuilder.build());
*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
