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
import com.bsod.tfg.controlador.bbdd.DataBaseHelper;
import com.bsod.tfg.modelo.chat.MessageChat;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.utils.ViewHolder;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.sql.SQLException;
import java.util.List;

/**
 * Proudly created by Payton on 04/03/2015.
 */
public class ChatAdapter extends ArrayAdapter<MessageChat> {

    private int mUserId;
    private Dao<User, Integer> daoUsers;
    private String ownPhotoURL;

    public ChatAdapter(Context context, int userId, List<MessageChat> list) {
        super(context, 0, list);
        this.mUserId = userId;
        daoUsers = DataBaseHelper.getInstance().getDAOUser();
        try {
            ownPhotoURL = Session.getSession().getUser().getPicImageUrl();
            //ownPhotoURL = daoUsers.queryBuilder().selectColumns(User.IMAGE_FIELD_NAME).where().eq(User.IDUSER_FIELD_NAME, userId).queryForFirst().getPicImageUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
        }
        TextView name = ViewHolder.get(convertView, R.id.chat_name);
        ImageView imageLeft = ViewHolder.get(convertView, R.id.ivProfileLeft);
        ImageView imageRight = ViewHolder.get(convertView, R.id.ivProfileRight);
        TextView body = ViewHolder.get(convertView, R.id.tvBody);

        final MessageChat message = getItem(position);
        final boolean isMe = message.getUserId() == mUserId;
        final ImageView profileView = isMe ? imageRight : imageLeft;
        ImageLoader im = ImageLoader.getInstance();
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            imageRight.setVisibility(View.VISIBLE);
            imageLeft.setVisibility(View.GONE);
            body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            name.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            im.displayImage(Constants.BASE_URL.concat(ownPhotoURL), profileView);

        } else {
            imageLeft.setVisibility(View.VISIBLE);
            imageRight.setVisibility(View.GONE);
            body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            name.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            try {
                User u;
                if ((u = daoUsers.queryBuilder().selectColumns(User.IMAGE_FIELD_NAME).where().eq(User.IDUSER_FIELD_NAME, message.getUserId()).queryForFirst()) != null) {
                    im.displayImage(Constants.BASE_URL.concat(u.getPicImageUrl()), profileView);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        body.setText(message.getMessage());
        name.setText("<".concat(message.getUserName()).concat("> ").concat(message.getDate()));
        return convertView;
    }
}
