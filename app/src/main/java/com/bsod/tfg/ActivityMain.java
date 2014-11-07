package com.bsod.tfg;


import android.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.controlador.AdapterTab;
import com.bsod.tfg.modelo.Facultad;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.utils.TopBar;
import com.bsod.tfg.vista.ActivitySearchUni;
import com.bsod.tfg.vista.ActivitySettings;
import com.bsod.tfg.vista.FragmentArchivos;
import com.bsod.tfg.vista.FragmentChat;
import com.bsod.tfg.vista.FragmentTablon;
import com.bsod.tfg.vista.ViewPagerNonSwipeable;

/**
 * Actividad Principal que contiene las pestañas
 * <div>Icons made by Designerz Base from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>
 * is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
 */
public class ActivityMain extends FragmentActivity implements
        android.view.View.OnClickListener , TopBar.TabSelectedListener {

    public final static String CURRENT_LOCATION = "com.bsod.tfg.MESSAGE";
    public final static String NEXT_LOCATION = "";
    private static final String TAG = "ActivityMain";
    private String uni_location = null;

    // Maybe Should be in other package

    private ViewPagerNonSwipeable vPager;
    private AdapterTab tAdapter;
    private ActionBar aBar;
    private TextView location;
    private ImageView searchImage;

    private TopBar menu ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uni_location = Session.getSession().getFacultad().getNombre();

        setContentView(R.layout.activity_main);

       // vPager = (ViewPagerNonSwipeable) findViewById(R.id.view_pager);
        //tAdapter = new AdapterTab(getSupportFragmentManager());
        //aBar = getActionBar();

        //Pager.setAdapter(tAdapter);
        // Habilita el modo de navegación por pestañas
       // aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //aBar.setHomeButtonEnabled(true);
        //aBar.setDisplayHomeAsUpEnabled(true);


        //Añadiendo tabs
        //aBar.addTab(aBar.newTab().setIcon(R.drawable.ic_tab_tablon).setTabListener(this));
        //aBar.addTab(aBar.newTab().setIcon(R.drawable.ic_tab_chat).setTabListener(this));
        //aBar.addTab(aBar.newTab().setIcon(R.drawable.ic_tab_archivos).setTabListener(this));

        //vPager.setOnPageChangeListener(this);

        location = (TextView) findViewById(R.id.location);

        //Default page selected
        //onPageSelected(0);
        location.setText(uni_location);

        //Search button Stuff
        searchImage = (ImageView) findViewById(R.id.searchbutton);
        searchImage.setOnClickListener(this);


        menu = (TopBar) findViewById(R.id.topBar);
        menu.setTabListener(this);

        menu.setSelectedTab(TopBar.TAB_TABLON);


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
                /*Intent i = new Intent(this, ActivitySplash.class);
                // Closing all the Activities from stack
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

*/
                Log.d(TAG, "UnLogin OK!");
                //  this.startActivity(i);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Establecer el fragment que se debe mostrar.
        vPager.setCurrentItem(tab.getPosition());
        //invalidateOptionsMenu();
        /* Log.i(TAG, String.valueOf(tab.getPosition()));
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //@Override
    // Método que cuando se cambia a la página position
    //public void onPageSelected(int position) {
        /*String text = "";
        switch (position) {
            case 0:
                text = getString(R.string.tablon);
                break;
            case 1:
                text = getString(R.string.chat);
                break;
            case 2:
                text = getString(R.string.archivos);
                break;
            default:
        }
        location.setText(text);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
*/
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
        if (resultCode == RESULT_OK && requestCode == Constants.UNI_SELECTED) {
            if (data.hasExtra(NEXT_LOCATION)) {
                location.setText(data.getExtras().getString(NEXT_LOCATION));
            }
        }
    }

    public Fragment getCurrentFragment() {
        return tAdapter.getItem(aBar.getSelectedTab().getPosition());
    }


    @Override
    public void tabSelected(int tab) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        Fragment f = null;

        switch (tab) {
            case TopBar.TAB_TABLON:
                f = new FragmentTablon();
                break;
            case TopBar.TAB_CHAT:
                f = new FragmentChat();
                break;
            case TopBar.TAB_ARCHIVOS:
                f = new FragmentArchivos();
                break;
        }
        if (f != null) {

            fragmentTransaction.replace(R.id.fragment, f, "TFGFragment");
            // fragmentTransaction.addToBackStack(null);
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();

        }
    }
}
