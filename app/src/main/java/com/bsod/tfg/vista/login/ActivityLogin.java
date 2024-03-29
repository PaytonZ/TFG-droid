package com.bsod.tfg.vista.login;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.sesion.Token;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.universidades.Facultad;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


public class ActivityLogin extends Activity implements View.OnClickListener {

    private static final String TAG = "ActivityLogin";
    private Button logButton;
    private TextView user;
    private TextView password;
    private ActivityLogin thisactivity = this;
    private ActionBar aBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logButton = (Button) findViewById(R.id.login_log_button);
        logButton.setOnClickListener(this);

        user = (TextView) findViewById(R.id.login_usuario);
        password = (TextView) findViewById(R.id.login_password);

        aBar = getActionBar();

        if (aBar != null) {
            aBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_login, menu);
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

        // The user wants to log in
        if (view == logButton) {

            // Stackoverflow hack http://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

            final String username = user.getText().toString();
            final String pass = password.getText().toString();

            // HARDCODING for making root toor always accesible
            if (username.equals("root") && pass.equals("toor")) {
                User u = new User();
                u.setName("root");
                Session.getSession().setUser(u);
                Token t = new Token();
                t.setToken("");
                Session.getSession().setToken(t);
                Facultad i = new Facultad();
                i.setId(-1);
                i.setNombre("Root Access!");
                Session.getSession().setFacultad(i);
                Session.persistPreferences();
                Intent intent = new Intent(thisactivity, ActivityMain.class);
                // Closing all the Activities from stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                RequestParams params = new RequestParams();
                params.put("user", username);
                params.put("pass", pass);
                HttpClient.get(Constants.HTTP_LOGIN_USER, params, new JsonHttpResponseHandlerCustom(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        try {
                            // Usuario y contraseña no validos
                            int error = Integer.parseInt(response.get("error").toString());
                            if (error != 200) {
                                Toast.makeText(thisactivity, R.string.invalid_user_password, Toast.LENGTH_SHORT).show();
                                password.setText("");
                            } else {
                                // TODO: Takes more data from the server and put it here
                                ObjectMapper mapper = new ObjectMapper();
                                // mapper.registerModule(new JsonOrgModule());

                                Token t = mapper.readValue(response.get("token").toString(), Token.class);
                                Facultad facultad = mapper.readValue(response.get("faculty").toString(), Facultad.class);
                                Session.getSession().setUser(mapper.readValue(response.get("user").toString(), User.class));
                                Session.getSession().setToken(t);
                                Session.getSession().setFacultad(facultad);
                                Session.persistPreferences();

                                Intent intent = new Intent(thisactivity, ActivityMain.class);
                                // Closing all the Activities from stack
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        }
    }


}
