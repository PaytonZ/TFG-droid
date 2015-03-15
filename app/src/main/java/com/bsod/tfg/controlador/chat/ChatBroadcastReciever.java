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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
