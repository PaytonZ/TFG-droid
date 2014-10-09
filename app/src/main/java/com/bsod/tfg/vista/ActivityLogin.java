package com.bsod.tfg.vista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.bsod.tfg.modelo.Constants;
import com.bsod.tfg.modelo.PreferencesManager;

public class ActivityLogin extends Activity implements View.OnClickListener {

    private Button logButton;
    private TextView user;
    private TextView password;


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
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

            if (validateUserPassword(user.getText().toString(), password.getText().toString())) {

                PreferencesManager.getInstance().setUser(user.getText().toString());

                Intent intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.invalid_user_password, Toast.LENGTH_LONG).show();
                password.setText("");

            }
        } else {

        }
    }

    private boolean validateUserPassword(String user, String password) {
        return user.equals(password);
    }
}
