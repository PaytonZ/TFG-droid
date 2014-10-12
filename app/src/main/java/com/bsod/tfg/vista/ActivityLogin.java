package com.bsod.tfg.vista;

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
import com.bsod.tfg.modelo.Session;
import com.bsod.tfg.modelo.University;
import com.bsod.tfg.utils.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bsod.tfg.modelo.Constants;


public class ActivityLogin extends Activity implements View.OnClickListener {

    private static final String TAG = "ActivityLogin";
    private Button logButton;
    private TextView user;
    private TextView password;
    private ActivityLogin thisactivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logButton = (Button) findViewById(R.id.login_log_button);
        logButton.setOnClickListener(this);

        user = (TextView) findViewById(R.id.login_usuario);
        password = (TextView) findViewById(R.id.login_password);

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

            RequestParams params = new RequestParams();
            params.put("user", username);
            params.put("pass", pass);

            // HARDCODING for making root toor always accesible
            if (username.equals("root") && pass.equals("toor")) {
                Session.getSession().setUser(username);
                Session.getSession().setToken("asihjdajshdjasd");
                University i = new University();
                i.setId(1);
                i.setName("Root Access!");
                Session.getSession().setUniversity(i);
                Session.persistPreferences();
                Intent intent = new Intent(thisactivity, ActivityMain.class);
                // Closing all the Activities from stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {


                HttpClient.get(Constants.LOGIN_USER_HTTP, params, new JsonHttpResponseHandler() {
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

                                // Takes more data from the server and put it here
                                Session.getSession().setUser(username);
                                Session.getSession().setToken(response.get("token").toString());
                                University i = new University();
                                i.setId(1);
                                i.setName("Unv. Complutensis Madritensis.");
                                Session.getSession().setUniversity(i);
                                Session.persistPreferences();

                                Intent intent = new Intent(thisactivity, ActivityMain.class);
                                // Closing all the Activities from stack
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    }
                });
            }

        } else {

        }
    }


}
