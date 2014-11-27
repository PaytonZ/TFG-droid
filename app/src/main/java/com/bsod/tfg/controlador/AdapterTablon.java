package com.bsod.tfg.controlador;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.fasterxml.jackson.module.jsonorg.JsonOrgModule;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Proudly created by Payton on 25/09/2014.
 */
public class AdapterTablon extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final String TAG = "AdapterTablon";
    private final Context context;

    private List<MessageBoard> messageList = Collections.emptyList();

    public AdapterTablon(Context context) {
        this.context = context;

    }

    public void updateOneMessage(int id, MessageBoard mb) {
        messageList.set(id, mb);
        notifyDataSetChanged();
    }

    public void addMessages(List<MessageBoard> messageList) {
        this.messageList = messageList;
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


        TextView message = ViewHolder.get(convertView, R.id.message_board_text);
        TextView title = ViewHolder.get(convertView, R.id.message_board_title);
        ImageView image = ViewHolder.get(convertView, R.id.message_board_image);
        TextView date = ViewHolder.get(convertView, R.id.message_board_date);
        ImageView like = ViewHolder.get(convertView, R.id.message_board_like);
        final TextView numberOflikes = ViewHolder.get(convertView, R.id.message_board_number_of_likes);


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView v = (ImageView) view;
                final MessageBoard message = getItem(position);
                // Trick for making it more fluid
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
                                mapper.registerModule(new JsonOrgModule());
                                MessageBoard mb = mapper.readValue(response.get("data").toString(), MessageBoard.class);
                                updateOneMessage(position, mb);
                            } else {
                                Toast.makeText(context, context.getString(R.string.error_cannot_favorited), Toast.LENGTH_SHORT).show();
                                v.setImageResource(message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                                numberOflikes.setText(message.getNumOfFavs());
                            }
                        } catch (Exception e) {
                            v.setImageResource(message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                            Toast.makeText(context, context.getString(R.string.error_cannot_favorited), Toast.LENGTH_SHORT).show();
                            numberOflikes.setText(message.getNumOfFavs());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        v.setImageResource(message.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);
                        Toast.makeText(context, context.getString(R.string.error_cannot_favorited), Toast.LENGTH_SHORT).show();
                        numberOflikes.setText(message.getNumOfFavs());

                    }

                });
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         //Toast.makeText(context, "No toques la imagen!", Toast.LENGTH_SHORT).show();
                                     }
                                 }

        );

        MessageBoard mb = getItem(position);

        // Formating Date
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
        date.setText(dt.format(mb.getCreationDate()));

        title.setText(mb.getUser().getName());

        //Log.i(TAG, "message" + mb.getId() + "isfaved" + mb.isUserFavorited());


        like.setImageResource(mb.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);


        title.setTypeface(null, Typeface.BOLD);
        message.setText(mb.getMessage());
        numberOflikes.setText(String.valueOf(mb.getNumOfFavs()));
        // change the icon for Windows and iPhone
        String s = String.valueOf(getItem(position));
        int wtf = s.hashCode() % 3;
        if (wtf == 0)
            image.setImageResource(R.drawable.ic_owned_fire);
        else if (wtf == 1)
            image.setImageResource(R.drawable.ic_cthulhu_president);
        else

        {
            image.setImageResource(R.drawable.ic_trololol);
        }

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }


}



