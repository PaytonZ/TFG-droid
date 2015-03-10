package com.bsod.tfg.vista.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.chat.ChatAdapter;
import com.bsod.tfg.controlador.chat.SocketChat;
import com.bsod.tfg.modelo.chat.MessageChat;
import com.bsod.tfg.modelo.sesion.Session;

import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentChat";
    private SocketChat socketChat;
    private View rootView;
    private ListView listViewChat;
    private ArrayList<MessageChat> messageChatList;
    private ChatAdapter chatAdapter;
    private EditText messageBox;
    private Button sendButton;

    public FragmentChat() {
        // Required empty public constructor

    }

    public static Fragment newInstance() {
        return new FragmentChat();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        if (rootView == null) {

            socketChat = new SocketChat();
            socketChat.connect(getActivity(), String.valueOf(Session.getSession().getFacultad().getId()));
            rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            listViewChat = (ListView) rootView.findViewById(R.id.listViewChat);
            messageBox = (EditText) rootView.findViewById(R.id.edittext_chat);
            sendButton = (Button) rootView.findViewById(R.id.send_button_chat);
            sendButton.setOnClickListener(this);
            messageChatList = new ArrayList<>();
/*
            MessageChat mc = new MessageChat();
            mc.setRoom(String.valueOf(Session.getSession().getFacultad().getId()));
            mc.setUserId(Session.getSession().getUser().getIdUser());
            mc.setUserName(Session.getSession().getUser().getName());*/
            //messageChatList.add(mc);
            chatAdapter = new ChatAdapter(getActivity(), Session.getSession().getUser().getIdUser(), messageChatList);
            listViewChat.setAdapter(chatAdapter);
            recieveMessages();

        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        }
        return rootView;
    }


    @Override
    public void onClick(View view) {
        if (view == sendButton) {
            String text = messageBox.getText().toString();
            if (!text.equals("")) {
                MessageChat mc = new MessageChat();
                mc.setMessage(text);
                mc.setRoom(String.valueOf(Session.getSession().getFacultad().getId()));
                mc.setUserId(Session.getSession().getUser().getIdUser());
                mc.setUserName(Session.getSession().getUser().getName());

                chatAdapter.add(mc);
                socketChat.send(text, String.valueOf(Session.getSession().getFacultad().getId()));
                messageBox.setText("");
            }
        }
    }

    private void addMessage() {

    }

    private void recieveMessages() {
        Log.i(TAG, "recieveMessages");

        final MessageChat mc = new MessageChat();
        new Thread(new Runnable() {
            public void run() {

                Log.i(TAG, "recieveMessages RUN ");
                try {
                    //Log.i(TAG, "socket" + socketChat.toString());
                    while (socketChat == null) {
                        sleep(1000);
                    }
                    JSONObject jo = socketChat.recieve();
                    while (jo == null) {
                        sleep(1000);
                        jo = socketChat.recieve();
                    }
                    Log.i(TAG, "Message Recieved" + jo.toString());

                    mc.setRoom(jo.has("room") ? jo.get("room").toString() : "");
                    mc.setMessage(jo.get("message").toString());
                    mc.setUserId(Integer.valueOf(jo.get("user_id").toString()));
                    mc.setUserName(jo.get("name").toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatAdapter.add(mc);
                        }
                    });

                    recieveMessages();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread() {
            public void run() {

                try {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {


                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }

}
