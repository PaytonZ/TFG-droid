package com.bsod.tfg;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.vista.NonSwipeableViewPager;
import com.bsod.tfg.vista.TabsAdapter;


public class ActivityMain extends FragmentActivity implements
        ActionBar.TabListener, ViewPager.OnPageChangeListener, android.view.View.OnClickListener {

    private NonSwipeableViewPager vPager;
    private TabsAdapter tAdapter;
    private ActionBar aBar;

    private TextView location;
    private ImageView searchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = (NonSwipeableViewPager) findViewById(R.id.view_pager);
        tAdapter = new TabsAdapter(getSupportFragmentManager());
        aBar = getActionBar();

        vPager.setAdapter(tAdapter);
        aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Añadiendo tabs
        aBar.addTab(aBar.newTab().setIcon(R.drawable.ic_tab_tablon).setTabListener(this));
        aBar.addTab(aBar.newTab().setIcon(R.drawable.ic_tab_chat).setTabListener(this));
        aBar.addTab(aBar.newTab().setIcon(R.drawable.ic_tab_archivos).setTabListener(this));

        vPager.setOnPageChangeListener(this);

        location = (TextView) findViewById(R.id.location);

        //Default page selected
        onPageSelected(0);


        //Search button Stuff
        searchImage = (ImageView) findViewById(R.id.searchbutton);
        searchImage.setOnClickListener(this);



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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Establecer el fragment que se debe mostrar.
        vPager.setCurrentItem(tab.getPosition());
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
    // Método que cuando se cambia a la página position
    public void onPageSelected(int position) {
        String text = "";
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

    @Override
    public void onClick(View view) {
        if (view == searchImage) {
            Toast.makeText(this, "SEARCH BUTTON DISABLED", Toast.LENGTH_LONG).show();
        }
    }
}
