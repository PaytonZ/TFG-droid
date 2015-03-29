package com.bsod.tfg.controlador.tablon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.bsod.tfg.modelo.tablon.MessageBoardUpdate;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.ViewHolder;
import com.bsod.tfg.vista.tablon.ActivityImageUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bsod.tfg.utils.DateManager.convertUnixToHumanDate;

/**
 * Proudly created by Payton on 25/09/2014.
 */
public class AdapterTablon extends BaseAdapter {

    private static final String TAG = "AdapterTablon";
    private final Context context;
    private List<MessageBoard> messageList = new ArrayList<>();

    public AdapterTablon(Context context) {
        this.context = context;
    }

    public void updateOneMessage(int id, MessageBoard mb) {
        messageList.set(id, mb);
        notifyDataSetChanged();
    }

    /**
     * Añade una lista de mensajes pasada por parámetro
     *
     * @param messageList
     */
    public void addMessages(List<MessageBoard> messageList) {
        this.messageList.addAll(0, messageList);
        notifyDataSetChanged();
    }

    public void updateMessages(List<MessageBoardUpdate> messages) {
        for (MessageBoardUpdate mbu : messages) {
            int id = searchMessage(mbu.getId());
            if (id != -1) {
                if (mbu.isBorrado()) {
                    messageList.remove(id);
                } else {
                    MessageBoard mb = getItem(id);
                    mb.setNumOfFavs(mbu.getNumOfFavs());
                    mb.setUserFavorited(mbu.isUserFavorited());
                }
            }
        }
        notifyDataSetChanged();
    }

    public int searchMessage(int pk) {
        for (int i = 0; i < messageList.size(); i++) {
            if (messageList.get(i).getId() == pk) return i;
        }
        return -1;
    }


    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public MessageBoard getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.tablonlayout, parent, false);
        }

        final TextView message = ViewHolder.get(convertView, R.id.message_board_text);
        TextView title = ViewHolder.get(convertView, R.id.message_board_title);
        final ImageView image = ViewHolder.get(convertView, R.id.message_board_image);
        TextView date = ViewHolder.get(convertView, R.id.message_board_date);
        ImageView like = ViewHolder.get(convertView, R.id.message_board_like);
        final TextView numberOflikes = ViewHolder.get(convertView, R.id.message_board_number_of_likes);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView v = (ImageView) view;
                final MessageBoard message = getItem(position);
                /* Trick for making it more fluid */
                v.setImageResource(!message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                numberOflikes.setText(String.valueOf(!message.isUserFavorited() ? message.getNumOfFavs() + 1 : message.getNumOfFavs() - 1));

                RequestParams params = new RequestParams();
                params.put("token", Session.getSession().getToken().getToken());
                params.put("idmessage", message.getId());
                HttpClient.get(Constants.HTTP_FAV_MESSAGE, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int error;
                        try {
                            error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                //mapper.registerModule(new JsonOrgModule());
                                MessageBoard mb = mapper.readValue(response.get("data").toString(), MessageBoard.class);
                                updateOneMessage(position, mb);
                            } else {
                                Toast.makeText(context, context.getString(R.string.error_cannot_favorited), Toast.LENGTH_SHORT).show();
                                v.setImageResource(message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                                numberOflikes.setText(String.valueOf(message.getNumOfFavs()));
                            }
                        } catch (Exception e) {
                            v.setImageResource(message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                            Toast.makeText(context, context.getString(R.string.error_cannot_favorited), Toast.LENGTH_SHORT).show();
                            numberOflikes.setText(String.valueOf(message.getNumOfFavs()));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        v.setImageResource(message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                        Toast.makeText(context, context.getString(R.string.error_cannot_favorited), Toast.LENGTH_SHORT).show();
                        numberOflikes.setText(String.valueOf(message.getNumOfFavs()));
                    }

                });
            }
        });
        final MessageBoard mb = getItem(position);
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         //Toast.makeText(context, "No toques la imagen!", Toast.LENGTH_SHORT).show();
                                         Intent i = new Intent(context, ActivityImageUserDetail.class);
                                         i.putExtra(Constants.INTENT_USER_IMAGE_DETAIL, mb.getUser().getPicImageUrl());
                                         context.startActivity(i);

                                     }
                                 }

        );


        // Formating Date
        //SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
        //date.setText(dt.format(mb.getCreationDate()));
        date.setText(mb.getHumanReadableDate());

        title.setText(mb.getUser().getName());

        like.setImageResource(mb.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);

        title.setTypeface(null, Typeface.BOLD);
        message.setText(mb.getMessage());
        numberOflikes.setText(String.valueOf(mb.getNumOfFavs()));

        ImageLoader im = ImageLoader.getInstance();
        //if (!mb.getUser().getPicImageUrl().equals("")) {
        im.displayImage(Constants.BASE_URL.concat(mb.getUser().getPicImageUrl()), image);
        //} else {
        // image.setImageResource(R.drawable.no_image);
        //}


        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        for (MessageBoard mb : messageList) {
            mb.setHumanReadableDate(convertUnixToHumanDate(mb.getCreationDateUnix()));
        }
        super.notifyDataSetChanged();
    }


    public void deleteMessage(int pk) {
        int id = searchMessage(pk);
        if (id != -1) {
            messageList.remove(id);
            notifyDataSetChanged();

        }

    }

    public List<MessageBoard> getMessageList() {
        return messageList;
    }
}



