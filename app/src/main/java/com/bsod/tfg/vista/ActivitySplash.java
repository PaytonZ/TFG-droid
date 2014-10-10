package com.bsod.tfg.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;
import com.bsod.tfg.modelo.Session;

public class ActivitySplash extends Activity implements View.OnClickListener {

    private static final String TAG = "ActivitySplash";
    private Button button_registrate;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Hack taken from http://stackoverflow.com/questions/2776116/how-do-i-dynamically-choose-which-activity-to-launch-when-opening-an-app
        final Class<? extends Activity> activityClass;

        if (Session.getSession().getUser() != null && Session.getSession().getUser() != "") {
            activityClass = ActivityMain.class;
            Intent newActivity = new Intent(this, activityClass);
            // Closing all the Activities from stack
            newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Log.d(TAG, "Login OK! User logged on ... " + Session.getSession().getUser());
            this.startActivity(newActivity);

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        button_registrate = (Button) findViewById(R.id.splash_registrate_button);
        button_login = (Button) findViewById(R.id.splash_login_button);

        button_registrate.setOnClickListener(this);
        button_login.setOnClickListener(this);

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
