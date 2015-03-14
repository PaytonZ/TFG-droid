package com.bsod.tfg.vista.tablon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

public class ActivityMessageDetail extends Activity implements View.OnClickListener {

    private static final String TAG = "ActivityMessageDetail";
    private MessageBoard mb;
    private Context context;
    private ImageView borrado;

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
        borrado = (ImageView) findViewById(R.id.message_board_delete);
        if (mb.getUser().getIdUser() == Session.getSession().getUser().getIdUser()) {
            borrado.setVisibility(View.VISIBLE);
            borrado.setOnClickListener(this);
        }

        date.setText(mb.getHumanReadableDate());
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
                                //mapper.registerModule(new JsonOrgModule());
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
        //getMenuInflater().inflate(R.menu.menu_activity_message_detail, menu);
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

    @Override
    public void onClick(View view) {
        if (view == borrado) {


            new AlertDialog.Builder(this)
                    .setTitle("¿Quieres borrar este mensaje?")
                    .setMessage("¿Estás seguro de que quieres borrar este mensaje?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            RequestParams params = new RequestParams();
                            params.put("token", Session.getSession().getToken().getToken());
                            params.put("idmessage", mb.getId());
                            HttpClient.get(Constants.HTTP_DELETE_MESSAGE, params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    int error;
                                    try {
                                        error = Integer.parseInt(response.get("error").toString());
                                        if (error == 200) {
                                            Intent i = new Intent();
                                            //i.putExtra("result",Constants.INTENT_MESSAGE_DELETED);
                                            //i.putExtra(Constants.INTENT_EXTRA_DELETED_MESSAGE, true);
                                            i.putExtra(Constants.INTENT_EXTRA_DELETED_MESSAGE_ID, mb.getId());
                                            setResult(Activity.RESULT_OK, i);
                                            finish();

                                        } else {
                                            Log.d(TAG, "error genérico al borrar el mensaje".concat(". Respuesta del servidor:").concat(response.toString()));
                                            Toast.makeText(context, "Hubo un error al borrar el mensaje, inténtelo más tarde.", Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (Exception e) {
                                        Log.d(TAG, e.toString());
                                        Toast.makeText(context, "Hubo un error al borrar el mensaje, inténtelo más tarde.", Toast.LENGTH_SHORT).show();


                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    Log.d(TAG, errorResponse.toString());
                                    Toast.makeText(context, "Hubo un error al borrar el mensaje, inténtelo más tarde.", Toast.LENGTH_SHORT).show();


                                }

                            });

                        }
                    }).create().show();


        }
    }


}
