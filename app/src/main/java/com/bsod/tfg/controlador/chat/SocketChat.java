package com.bsod.tfg.controlador.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.modelo.chat.ChatClientBean;
import com.bsod.tfg.modelo.chat.ChatClientEnum;
import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.chat.ChatServerEnum;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

/**
 * Proudly created by Payton on 04/03/2015.
 */
public class SocketChat {

    private static final String TAG = "SocketChat";

    private boolean connected;
    private PrintWriter socketOutput;
    private BufferedReader socketInput;
    private String state;
    private Socket socket;
    private TextView connectionStatus;

    public SocketChat() {
        socket = null;
        socketOutput = null;
        setConnected(false);
        state = "";
    }


    public void connect(Context context, String room) {
        new ConnectTask(context).execute(Constants.SERVER_IP, String.valueOf(Constants.CHAT_PORT), room);

    }

    public void disconnect(Context context) {
        if (isConnected()) {
            try {
                socketOutput.close();
                socket.close();
                setConnected(false);
            } catch (IOException e) {
                showToast(context, "Couldn't get I/O for the connection");
                Log.e(TAG, e.getMessage());
            }
        }
    }


    public void send(final ChatClientBean csb) {
        new Thread(new Runnable() {
            public void run() {
                int trys = 0;

                while (!isConnected() && !state.equals(Constants.CHAT_STATE_CHAT) && trys < 3) {
                    try {
                        trys++;
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (isConnected()) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Log.d(TAG, "socket.send : ".concat(mapper.writeValueAsString(csb)));
                        socketOutput.println(mapper.writeValueAsString(csb));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    socketOutput.flush();
                }

            }
        }).start();


    }

    public String recieve() throws Exception {
        Log.d(TAG, "Socket.recieve()");
        String response = null;
        if (isConnected()) {
            response = socketInput.readLine();
        }
        return response;
    }

    private void showToast(final Context context, final String message) {
        new Handler(context.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    private class ConnectTask extends AsyncTask<String, Void, Void> {

        private Context context;

        public ConnectTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //showToast(context, "Connecting..");
            //connectionStatus.setText("Conectando...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (isConnected()) {
                //connectionStatus.setText("Conectado");
            } else {
                //connectionStatus.setText("Desconectado");
            }

            super.onPostExecute(result);
        }


        @Override
        protected Void doInBackground(String... params) {
            try {
                String host = params[0];
                int port = Integer.parseInt(params[1]);
                String room = params[2];
                socket = new Socket(host, port);

                if (socket.isConnected()) {
                    socket.setTcpNoDelay(true);
                    socket.setKeepAlive(true);
                    socketOutput = new PrintWriter(socket.getOutputStream(),
                            true);
                    socketInput = new BufferedReader(new InputStreamReader(
                            new BufferedInputStream(new DataInputStream(
                                    socket.getInputStream()))));

                    ChatClientBean clb = new ChatClientBean();
                    clb.setType(ChatClientEnum.LOGIN);
                    clb.setToken(Session.getSession().getToken().getToken());
                    clb.setRoom(room);
                    ObjectMapper mapper = new ObjectMapper();
                    Log.d(TAG, "Mensaje de registro al chat : ".concat(mapper.writeValueAsString(clb)));
                    socketOutput.println(mapper.writeValueAsString(clb));
                    socketOutput.flush();
                    String response = socketInput.readLine();
                    ChatServerBean csb = mapper.readValue(response, ChatServerBean.class);
                    setConnected((csb.getType() == ChatServerEnum.STATUS));
                }

            } catch (UnknownHostException e) {
                showToast(context, "Don't know about host: " + Constants.BASE_URL + ":" + Constants.CHAT_PORT);
                Log.e(TAG, "UnknownHostException" + e.getMessage());
            } catch (IOException e) {
                showToast(context, "Couldn't get I/O for the connection to: " + Constants.BASE_URL + ":" + Constants.CHAT_PORT);
                Log.e(TAG, "IOException" + e.getMessage());

            } catch (Exception e) {
                showToast(context, "Other error connection CHAT SOCKET");
                Log.e(TAG, "Exception " + e.getMessage());
            }

            return null;
        }


    }


}

