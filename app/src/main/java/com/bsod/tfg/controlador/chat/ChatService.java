package com.bsod.tfg.controlador.chat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bsod.tfg.modelo.chat.ChatClientBean;
import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Proudly created by Payton on 14/03/2015.
 */
public class ChatService extends Service {

    private static final String TAG = "ChatService";
    private static boolean isRunning = false;
    // Random number generator
    private static SocketChat socket;
    // Binder given to clients

    public static boolean isConnected() {
        return isRunning;
    }

    public static void sendMessage(ChatClientBean csb) throws Exception {
        if (socket == null) {
            throw new Exception("Attempting to use a invalid socket.");
        } else {
            socket.send(csb);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void reconnectAttempt() {
        Log.d(TAG, "Reconnecting Attempt");
        getApplicationContext().startService(new Intent(this, ChatService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Chat Service started");
        if (socket == null) {
            socket = new SocketChat();
            socket.connect(getApplicationContext(), String.valueOf(Session.getSession().getFacultad().getId()));
            startListening();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startListening() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String result;
                    int tries = 0;
                    while (!socket.isConnected() && tries < 3) {
                        Thread.sleep(500);
                        tries++;
                    }
                    if (socket.isConnected()) {
                        Log.d(TAG, "Servicio de Chat Recibiendo mensajes con normalidad");
                        isRunning = true;
                        while ((result = socket.recieve()) == null) {
                            Log.d(TAG, "Durmiendo ServiceChat 2 seg");
                            Thread.sleep(2000);
                        }
                        Log.d(TAG, "Mensaje recibido: ".concat(result));
                        broadcastIntent(result);
                        startListening();
                    } else {
                        Log.e(TAG, "El servicio de chat no se ha iniciado con normalidad");
                        reconnectAttempt();
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }).start();

    }

    private void broadcastIntent(String message) {
        Intent intent = new Intent(Constants.CHAT_BROADCAST);
        ObjectMapper mapper = new ObjectMapper();
        ChatServerBean csb = null;
        try {
            csb = mapper.readValue(message, ChatServerBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(Constants.CHAT_SERVER_EXTRA, csb);
        sendBroadcast(intent);
    }
}