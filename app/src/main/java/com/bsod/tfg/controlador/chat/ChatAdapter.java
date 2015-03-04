package com.bsod.tfg.controlador.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.chat.MessageChat;
import com.bsod.tfg.utils.ViewHolder;

import java.util.List;

/**
 * Proudly created by Payton on 04/03/2015.
 */
public class ChatAdapter extends ArrayAdapter<MessageChat> {

    private int mUserId;
    private ImageView imageLeft;
    private ImageView imageRight;
    private TextView body;

    public ChatAdapter(Context context, int userId, List<MessageChat> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
        }


        imageLeft = ViewHolder.get(convertView, R.id.ivProfileLeft);
        imageRight = ViewHolder.get(convertView, R.id.ivProfileRight);
        body = ViewHolder.get(convertView, R.id.tvBody);

        final MessageChat message = getItem(position);

        final boolean isMe = message.getUserId() == mUserId;
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            imageRight.setVisibility(View.VISIBLE);
            imageLeft.setVisibility(View.GONE);
            body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            imageLeft.setVisibility(View.VISIBLE);
            imageRight.setVisibility(View.GONE);
            body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        //final ImageView profileView = isMe ? imageRight : imageLeft;

        body.setText(message.getMessage());
        return convertView;
    }
}
