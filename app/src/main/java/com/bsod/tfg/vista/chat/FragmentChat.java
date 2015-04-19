package com.bsod.tfg.vista.chat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.chat.ChatRoomAdapter;
import com.bsod.tfg.controlador.chat.ChatService;
import com.bsod.tfg.modelo.chat.ChatClientBean;
import com.bsod.tfg.modelo.chat.ChatClientEnum;
import com.bsod.tfg.modelo.chat.ChatRoom;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.FragmentReplace;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.HashMap;

import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "FragmentChat";
    private View rootView;
    private TextView connectionStatus;
    private ChatRoomAdapter chatRoomAdapter;
    private ListView listOfChats;
    private HashMap<String, Fragment> fragmentList = new HashMap<>();
    private ButtonFloat buttonFloat;
    private boolean isPaused;

    public FragmentChat() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new FragmentChat();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment
        if (rootView == null) {*/

        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        connectionStatus = (TextView) rootView.findViewById(R.id.chat_connection_status);
        listOfChats = (ListView) rootView.findViewById(R.id.listViewChatRooms);
        chatRoomAdapter = new ChatRoomAdapter(getActivity(), 0);
        listOfChats.setAdapter(chatRoomAdapter);
        listOfChats.setOnItemClickListener(this);
        buttonFloat = (ButtonFloat) rootView.findViewById(R.id.buttonFloat);
        buttonFloat.setBackgroundColor(getResources().getColor(R.color.red));
        buttonFloat.setOnClickListener(this);
        final String startingRoom = String.valueOf(Session.getSession().getFacultad().getId());
        isPaused = false;

        new Handler().postDelayed(new Runnable() {
            public void run() {

                int tries = 0;
                while (!ChatService.isConnected() && tries < 3) {
                    try {
                        sleep(5000);
                        tries += 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (ChatService.isConnected()) {

                    final ChatRoom cr = new ChatRoom();
                    cr.setIdRoom(startingRoom);
                    StringBuilder sb = new StringBuilder();
                    sb.append(Session.getSession().getFacultad().getUni().getNombre());
                    sb.append(" - ");
                    sb.append(Session.getSession().getFacultad().getNombre());
                    cr.setName(sb.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectionStatus.setText(getActivity().getString(R.string.connected));
                            chatRoomAdapter.add(cr);
                            Fragment f = FragmentChatDetail.newInstance(cr);
                            fragmentList.put(cr.getIdRoom(), f);
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectionStatus.setText(getActivity().getString(R.string.disconnected));
                        }
                    });

                }
                checkConnection();
            }
        }, 200);

        /*} else {

            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }*/
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
    }

    public void onResume() {
        super.onResume();
        isPaused = false;
    }

    private void checkConnection() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!isPaused) {
                    Log.d(TAG, "Comprobando la conexión con el sistema de chat...");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectionStatus.setText(ChatService.isConnected() ? getActivity().getString(R.string.connected) : getActivity().getString(R.string.disconnected));
                        }
                    });

                }
                checkConnection();
            }
        }, 10000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView == listOfChats) {
            Fragment f;
            ChatRoom cr = chatRoomAdapter.getItem(position);
            if (fragmentList.containsKey(cr.getIdRoom())) {
                f = fragmentList.get(cr.getIdRoom());
            } else {
                f = FragmentChatDetail.newInstance(cr);
                fragmentList.put(cr.getIdRoom(), f);
            }
            FragmentReplace.replaceFragment(getActivity(), f);

        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonFloat) {

            final EditText input = new EditText(getActivity());
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.crear_nueva_sala)
                            //.setMessage("s")
                    .setView(input)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (!value.equalsIgnoreCase("")) {

                                final ChatRoom cr = new ChatRoom();
                                cr.setIdRoom(value);
                                cr.setName(value);
                                chatRoomAdapter.add(cr);
                                Fragment f = FragmentChatDetail.newInstance(cr);
                                fragmentList.put(cr.getIdRoom(), f);

                                ChatClientBean ccb = new ChatClientBean();
                                ccb.setRoom(value);
                                ccb.setType(ChatClientEnum.ROOM);
                                try {
                                    ChatService.sendMessage(ccb);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "Hubo un problema al crear la sala ", Toast.LENGTH_SHORT).show();
                                    chatRoomAdapter.remove(cr);
                                    fragmentList.remove(cr.getIdRoom());
                                }

                            }
                        }


                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Do nothing.
                }
            }).show();

        }

    }
}