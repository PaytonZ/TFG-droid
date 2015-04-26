package com.bsod.tfg;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.controlador.chat.ChatService;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.Statistics;
import com.bsod.tfg.utils.TopBar;
import com.bsod.tfg.vista.archivos.FragmentArchivos;
import com.bsod.tfg.vista.archivos.FragmentEstadisticas;
import com.bsod.tfg.vista.archivos.FragmentUploadFile;
import com.bsod.tfg.vista.chat.FragmentChat;
import com.bsod.tfg.vista.login.ActivitySplash;
import com.bsod.tfg.vista.otros.ActivitySearchUni;
import com.bsod.tfg.vista.otros.ActivitySettings;
import com.bsod.tfg.vista.tablon.FragmentTablon;

/**
 * Actividad Principal que contiene las pestañas
 * <div>Icons made by Designerz Base from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>
 * is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
 */
public class ActivityMain extends FragmentActivity implements
        android.view.View.OnClickListener, TopBar.TabSelectedListener {

    public final static String CURRENT_LOCATION = "com.bsod.tfg.MESSAGE";
    public final static String NEXT_LOCATION = "";
    private static final String TAG = "ActivityMain";

    private String uni_location = null;
    // Maybe Should be in other package
    private TextView location;
    private ImageView searchImage;
    private TopBar menu;
    private FragmentTablon fTablon;
    private FragmentChat fChat;
    private FragmentArchivos fArchivos;
    private Fragment fExamenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Statistics.startProfiling(TAG);
        startService(new Intent(this, ChatService.class));
        uni_location = Session.getSession().getFacultad().getNombre();
        setContentView(R.layout.activity_main);
        location = (TextView) findViewById(R.id.location);
        //Default page selected
        location.setText(uni_location);
        //Search button Stuff
        searchImage = (ImageView) findViewById(R.id.searchbutton);
        searchImage.setOnClickListener(this);

        menu = (TopBar) findViewById(R.id.topBar);
        menu.setTabListener(this);

        fTablon = new FragmentTablon();
        fChat = new FragmentChat();
        fArchivos = new FragmentArchivos();
        fExamenes = new FragmentUploadFile();

        menu.setSelectedTab(TopBar.TAB_TABLON);
        Statistics.stopProfiling(TAG, "ActivityMain Start");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, ActivitySettings.class);
                this.startActivity(i);
                return true;

            case R.id.action_logout:
                Session.destroySession();
                Toast.makeText(this, R.string.desconectado, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Unlogin OK!");
                Intent intent = new Intent(getApplicationContext(), ActivitySplash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("EXIT", true);
                startActivity(intent);

                return true;

            case R.id.action_statistics:
                menu.setTab(-1);
                replaceFragment(new FragmentEstadisticas());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == searchImage) {
            //Toast.makeText(this, "SEARCH BUTTON DISABLED", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ActivitySearchUni.class);
            intent.putExtra(CURRENT_LOCATION, location.getText().toString());
            startActivityForResult(intent, Constants.UNI_SELECTED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        /* Llamando al activityresult del fragment */
        getCurrentFragment().onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.UNI_SELECTED) {
            if (data.hasExtra(NEXT_LOCATION)) {
                location.setText(data.getExtras().getString(NEXT_LOCATION));
            }
        }

    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public void tabSelected(int tab) {

        Fragment f = null;

        switch (tab) {
            case TopBar.TAB_TABLON:
                f = fTablon;
                break;
            case TopBar.TAB_CHAT:
                f = fChat;
                break;
            case TopBar.TAB_ARCHIVOS:
                f = fArchivos;
                break;
            case TopBar.TAB_EXAMENES:
                f = fExamenes;
                break;
        }
        if (f != null) {
            replaceFragment(f);

        }
    }

    public void replaceFragment(Fragment f) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Cleaning Stack taken from SO -->http://stackoverflow.com/questions/5802141/is-this-the-right-way-to-clean-up-fragment-back-stack-when-leaving-a-deeply-nest
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentTransaction.replace(R.id.fragment, f, Constants.TFG_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(FragmentTransaction.TRANSIT_ENTER_MASK, FragmentTransaction.TRANSIT_EXIT_MASK);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.want_exit_question))
                    .setMessage(getString(R.string.want_exit_app_string))
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            //Session.destroySession();
                            Intent intent = new Intent(getApplicationContext(), ActivitySplash.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            stopService(new Intent(ActivityMain.this, ChatService.class));
                            startActivity(intent);

                        }
                    }).create().show();
        }
    }

}
