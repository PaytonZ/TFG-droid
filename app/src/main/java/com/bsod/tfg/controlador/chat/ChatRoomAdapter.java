package com.bsod.tfg.controlador.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.chat.ChatRoom;
import com.bsod.tfg.modelo.chat.MessageChat;
import com.bsod.tfg.utils.ViewHolder;

/**
 * Proudly created by Payton on 14/03/2015.
 */
public class ChatRoomAdapter extends ArrayAdapter<ChatRoom> {

    public ChatRoomAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_room, parent, false);
        }
        ChatRoom cr = getItem(pos);
        TextView chatNameTitle = ViewHolder.get(convertView, R.id.chat_name_title);
        TextView chatLastMessage = ViewHolder.get(convertView, R.id.chat_last_message);
        TextView chatUsername = ViewHolder.get(convertView, R.id.chat_username);
        TextView chatRoomShort = ViewHolder.get(convertView, R.id.chat_room_short);

        if (!cr.getListofMessages().isEmpty()) {
            MessageChat mc = cr.getListofMessages().get(cr.getListofMessages().size() - 1);
            chatUsername.setText(mc.getUserName());
            chatRoomShort.setText(mc.getMessage());
            chatLastMessage.setText(mc.getDate());

        } else {
            chatUsername.setText("");
            chatRoomShort.setText("");
            chatLastMessage.setText("");
        }

        chatNameTitle.setText(cr.getName());

        return convertView;
    }
}
