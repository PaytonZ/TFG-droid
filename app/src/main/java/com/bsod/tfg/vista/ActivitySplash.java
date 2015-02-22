package com.bsod.tfg.vista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.sesion.Token;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class ActivitySplash extends Activity implements View.OnClickListener {

    private static final String TAG = "ActivitySplash";
    private Button button_registrate;
    private Button button_login;
    private ActivitySplash as;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        as = this;
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        } else {

            // Hack taken from http://stackoverflow.com/questions/2776116/how-do-i-dynamically-choose-which-activity-to-launch-when-opening-an-app
            if (Session.getSession().getUser() != null && Session.getSession().getUser().getName() != "") {
                // Tenemos que comprobar si el token sigue siendo valido , o obligar a volver a logear

                RequestParams params = new RequestParams();
                params.put("token", Session.getSession().getToken().getToken());
                HttpClient.get(Constants.HTTP_RENEW_TOKEN, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int error;
                        try {
                            final Class<? extends Activity> activityClass;
                            error = Integer.parseInt(response.get("error").toString());
                            if (error == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                //mapper.registerModule(new JsonOrgModule());

                                Token t = mapper.readValue(response.get("data").toString(), Token.class);
                                Session.getSession().setToken(t);
                                Session.persistPreferences();

                                //Starting main activity
                                activityClass = ActivityMain.class;
                                Log.d(TAG, "Login OK! User logged on ... " + Session.getSession().getUser().getName());

                            } else // Needs to relog again
                            {
                                activityClass = ActivityLogin.class;
                                Toast.makeText(App.getContext(), getString(R.string.session_expired), Toast.LENGTH_SHORT).show();
                                Session.destroySession();
                                Log.d(TAG, "Session Expired or Invalid");
                            }

                            Intent newActivity = new Intent(App.getContext(), activityClass);
                            // Closing all the Activities from stack
                            newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            App.getContext().startActivity(newActivity);
                        } catch (Exception e) {
                            Toast.makeText(App.getContext(), getString(R.string.splash_connection_error), Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.activity_splash);
                            button_registrate = (Button) findViewById(R.id.splash_registrate_button);
                            button_login = (Button) findViewById(R.id.splash_login_button);
                            button_registrate.setOnClickListener(as);
                            button_login.setOnClickListener(as);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        Toast.makeText(App.getContext(), getString(R.string.splash_connection_error), Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_splash);
                        button_registrate = (Button) findViewById(R.id.splash_registrate_button);
                        button_login = (Button) findViewById(R.id.splash_login_button);
                        button_registrate.setOnClickListener(as);
                        button_login.setOnClickListener(as);
                    }


                });
            } else {
                setContentView(R.layout.activity_splash);
                button_registrate = (Button) findViewById(R.id.splash_registrate_button);
                button_login = (Button) findViewById(R.id.splash_login_button);
                button_registrate.setOnClickListener(this);
                button_login.setOnClickListener(this);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == button_registrate) {
            Intent intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);

        } else if (view == button_login) {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);

        }

    }


}
