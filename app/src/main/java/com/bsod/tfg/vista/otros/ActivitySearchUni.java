package com.bsod.tfg.vista.otros;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bsod.tfg.ActivityMain;
import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterBuscarUni;

import java.util.ArrayList;

public class ActivitySearchUni extends Activity {

    private static final String TAG = "ActivitySearchUni";
    private TextView text;
    private ListView listview;
    private AdapterBuscarUni adapter;
    private String itemSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_uni);
        Intent intent = getIntent();
        String message = intent.getStringExtra(ActivityMain.CURRENT_LOCATION);
        //TextView textView = new TextView(this);
        //textView.setTextSize(40);
        //textView.setText(message);

        // Set the text view as the activity layout
        //setContentView(textView);
        text = (TextView) findViewById(R.id.search_location);
        text.setText(message);

        listview = (ListView) findViewById(R.id.listview_buscar_uni);

        String[] values = new String[]{

                "Unv.Complutense-Informática",
                "Unv.Complutense-Historia",
                "Unv.Complutense-Matemáticas",
                "Unv.Complutense-Químicas",
                "Unv.Complutense-Derecho",
                "Unv.Politénica-Telecomunicaciones",
                "Unv.Politénica-Aeroespacial",

        };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        adapter = new AdapterBuscarUni(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(100).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                /*list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);*/
                                itemSelected = item;
                                // Prepare data intent
                                Intent data = new Intent();
                                data.putExtra(ActivityMain.NEXT_LOCATION, itemSelected);
                                // Activity finished ok, return the data
                                setResult(RESULT_OK, data);

                            }
                        });
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_buscar_uni, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

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
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {

        super.finish();
    }


}
