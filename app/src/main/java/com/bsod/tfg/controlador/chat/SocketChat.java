package com.bsod.tfg.controlador.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;

import org.json.JSONException;
import org.json.JSONObject;

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


    public SocketChat() {
        socket = null;
        socketOutput = null;
        connected = false;
        state = "";
    }


    public void connect(Context context, String room) {
        new ConnectTask(context).execute(Constants.SERVER_IP, String.valueOf(Constants.CHAT_PORT), room);
    }

    public void disconnect(Context context) {
        if (connected) {
            try {
                socketOutput.close();
                socket.close();
                connected = false;
            } catch (IOException e) {
                showToast(context, "Couldn't get I/O for the connection");
                Log.e(TAG, e.getMessage());
            }
        }
    }


    public void send(final String message, final String room) {
        new Thread(new Runnable() {
            public void run() {
                int trys = 0;
                JSONObject jo = new JSONObject();
                try {
                    jo.put("message", message);
                    jo.put("room", room);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                while (!connected && !state.equals(Constants.CHAT_STATE_CHAT) && trys < 3) {
                    try {
                        trys++;
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (connected && state.equals(Constants.CHAT_STATE_CHAT)) {
                    socketOutput.println(jo.toString());
                }

            }
        }).start();


    }

    public JSONObject recieve() throws Exception {
        Log.d(TAG, "Socket.Recieve");
        String response = null;
        if (connected && state.equals(Constants.CHAT_STATE_CHAT)) {
            response = socketInput.readLine();
        }
        return (response == null) ? null : new JSONObject(response);
    }

    private void showToast(final Context context, final String message) {
        new Handler(context.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class ConnectTask extends AsyncTask<String, Void, Void> {

        private Context context;

        public ConnectTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            showToast(context, "Connecting..");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (connected) {
                showToast(context, "Connection successfull");
            } else {
                showToast(context, "Connection NOT SUCESFUL ;X");
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
                socket.setTcpNoDelay(true);
                socket.setKeepAlive(true);
                socketOutput = new PrintWriter(socket.getOutputStream(),
                        true);
                socketInput = new BufferedReader(new InputStreamReader(
                        new BufferedInputStream(new DataInputStream(
                                socket.getInputStream()))));
                JSONObject jo;
                String response;
                response = socketInput.readLine();
                jo = new JSONObject(response);
                // Log.i(TAG, "jo.get(\"state\") : " + jo.get("state").toString());
                if (jo.get("state").toString().equals(Constants.CHAT_STATE_REGISTER)) {
                    Log.i(TAG, "SI estas en modo de registro.");
                    jo = new JSONObject();
                    jo.put("token", Session.getSession().getToken().getToken());
                    jo.put("room", room);

                    // Log.i(TAG, "REGISTRO :" + jo.toString());
                    socketOutput.println(jo.toString());

                    response = socketInput.readLine();

                    jo = new JSONObject(response);
                    Log.i(TAG, "REGISTRO RESPUESTA:" + jo.toString());
                    if (Integer.valueOf(jo.get("error").toString()) == 200) {
                        connected = true;
                        state = String.valueOf(jo.get("state"));
                        Log.i(TAG, "REGISTRO RESPUESTA: state " + jo.toString());
                    }
                } else {
                    Log.i(TAG, "No estas en modo de registro.");
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

