package com.bsod.tfg.vista.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment implements View.OnClickListener {

    SocketChat socketChat;
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
            socketChat.connect(getActivity(), "room");
            socketChat.send("wtf", "room");

            rootView = inflater.inflate(R.layout.fragment_chat, container, false);

            listViewChat = (ListView) rootView.findViewById(R.id.listViewChat);

            messageBox = (EditText) rootView.findViewById(R.id.edittext_chat);
            sendButton = (Button) rootView.findViewById(R.id.send_button_chat);
            sendButton.setOnClickListener(this);
            messageChatList = new ArrayList<>();
            MessageChat mc = new MessageChat();
            mc.setMessage("ESTO ES UNA PRUEBA");
            mc.setRoom("room");
            mc.setUserId(1);
            mc.setUserName("pepe");
            messageChatList.add(mc);
            chatAdapter = new ChatAdapter(getActivity(), Session.getSession().getUser().getIdUser(), messageChatList);
            listViewChat.setAdapter(chatAdapter);

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
                mc.setRoom("room");
                mc.setUserId(Session.getSession().getUser().getIdUser());
                mc.setUserName(Session.getSession().getUser().getName());

                chatAdapter.add(mc);

                socketChat.send(text, "room");

                messageBox.setText("");
            }
        }
    }
}
