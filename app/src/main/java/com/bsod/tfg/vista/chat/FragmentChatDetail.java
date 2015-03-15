package com.bsod.tfg.vista.chat;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.bbdd.DataBaseHelper;
import com.bsod.tfg.controlador.chat.ChatAdapter;
import com.bsod.tfg.controlador.chat.ChatService;
import com.bsod.tfg.modelo.chat.ChatClientBean;
import com.bsod.tfg.modelo.chat.ChatClientEnum;
import com.bsod.tfg.modelo.chat.ChatRoom;
import com.bsod.tfg.modelo.chat.ChatServerBean;
import com.bsod.tfg.modelo.chat.MessageChat;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.DateManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChatDetail extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentChatDetail";
    private View rootView;
    private Button sendButton;
    private EditText messageBox;
    private ChatAdapter chatAdapter;
    private ChatRoom roomName;

    public FragmentChatDetail() {
        // Required empty public constructor
    }

    public static FragmentChatDetail newInstance(ChatRoom room) {

        FragmentChatDetail myFragment = new FragmentChatDetail();
        Bundle args = new Bundle();
        args.putSerializable("room", room);
        myFragment.setArguments(args);
        return myFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            roomName = (ChatRoom) getArguments().getSerializable("room");
            rootView = inflater.inflate(R.layout.fragment_chat_detail, container, false);
            ListView listViewChat = (ListView) rootView.findViewById(R.id.listViewChat);
            messageBox = (EditText) rootView.findViewById(R.id.edittext_chat);
            sendButton = (Button) rootView.findViewById(R.id.send_button_chat);

            TextView chat_detail_room_name = (TextView) rootView.findViewById(R.id.chat_detail_room_name);
            chat_detail_room_name.setText(roomName.getName());

            sendButton.setOnClickListener(this);
            chatAdapter = new ChatAdapter(getActivity(), Session.getSession().getUser().getIdUser(), roomName.getListofMessages());
            listViewChat.setAdapter(chatAdapter);
            processMessages();
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
                try {
                    mc.setDate(DateManager.toHoursandMinutes(System.currentTimeMillis()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ChatClientBean ccb = new ChatClientBean();
                ccb.setType(ChatClientEnum.MESSAGE);
                ccb.setRoom(roomName.getIdRoom());
                ccb.setMessage(mc.getMessage());
                chatAdapter.add(mc);
                try {
                    ChatService.sendMessage(ccb);
                } catch (Exception e) {
                    e.printStackTrace();
                    chatAdapter.remove(mc);
                    Toast.makeText(getActivity(), "Hubo un problema al mandar este mensaje...", Toast.LENGTH_SHORT).show();
                }
                messageBox.setText("");
            }
        }
    }

    private void processMessages() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Dao<ChatServerBean, Integer> daochatServer = DataBaseHelper.getInstance().getDAOChatServer();
                try {
                    List<ChatServerBean> messages = daochatServer.query(daochatServer.queryBuilder().where().eq("room", roomName.getIdRoom()).prepare());
                    //Log.i(TAG, "Hay ".concat(String.valueOf(messages.size())).concat(" mensaje por procesar"));
                    ObjectMapper mapper = new ObjectMapper();
                    for (ChatServerBean csb : messages) {
                        final MessageChat mc = new MessageChat();
                        try {
                            Log.d(TAG, "Procesando mensaje :".concat(mapper.writeValueAsString(csb)));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        mc.setUserName(csb.getName());
                        mc.setUserId(csb.getUserId());
                        mc.setRoom(csb.getRoom());
                        mc.setMessage(csb.getMessage());
                        try {
                            mc.setDate(DateManager.toHoursandMinutes(csb.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        chatAdapter.add(mc);
                    }
                    DeleteBuilder<ChatServerBean, Integer> db = daochatServer.deleteBuilder();
                    db.where().eq("room", roomName.getIdRoom());
                    db.delete();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                processMessages();
            }
        }, 1200);
    }
}