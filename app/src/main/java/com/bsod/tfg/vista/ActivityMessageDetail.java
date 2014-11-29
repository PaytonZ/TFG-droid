package com.bsod.tfg.vista;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.bsod.tfg.utils.HttpClient;
import com.fasterxml.jackson.module.jsonorg.JsonOrgModule;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class ActivityMessageDetail extends Activity {

    private MessageBoard mb;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        context = getBaseContext();
        mb = (MessageBoard) getIntent().getSerializableExtra(Constants.INTENT_MESSAGE_DETAIL);

        TextView message = (TextView) findViewById(R.id.message_board_text);
        TextView title = (TextView) findViewById(R.id.message_board_title);
        ImageView image = (ImageView) findViewById(R.id.message_board_image);
        TextView date = (TextView) findViewById(R.id.message_board_date);
        ImageView like = (ImageView) findViewById(R.id.message_board_like);
        final TextView numberOflikes = (TextView) findViewById(R.id.message_board_number_of_likes);


        // Formating Date
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
        date.setText(dt.format(mb.getCreationDate()));

        title.setText(mb.getUser().getName());

        like.setImageResource(mb.isUserFavorited() ? R.drawable.ic_action_favorite_selected : R.drawable.ic_action_favorite);

        title.setTypeface(null, Typeface.BOLD);
        message.setText(mb.getMessage());
        numberOflikes.setText(String.valueOf(mb.getNumOfFavs()));
        if (!mb.getUser().getPicImageUrl().equals("")) {
            ImageLoader im = ImageLoader.getInstance();
            im.displayImage(Constants.MEDIA_URL + mb.getUser().getPicImageUrl(), image);
        } else {
            image.setImageResource(R.drawable.no_image);
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView v = (ImageView) view;
                final MessageBoard message = mb;
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
                                mb = mapper.readValue(response.get("data").toString(), MessageBoard.class);

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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_message_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
