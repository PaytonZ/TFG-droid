package com.bsod.tfg.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bsod.tfg.R;

/**
 * Proudly created by Payton on 07/11/2014.
 */
public class TopBar extends RelativeLayout implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    public static final int TAB_TABLON = 1;
    public static final int TAB_CHAT = 2;
    public static final int TAB_ARCHIVOS = 3;
    private static final String TAG = "TopBar";

    private int selectedTab;

    private ImageView tablon;
    private ImageView chat;
    private ImageView archivos;

    private long timeLastTabChange;
    private Context context;
    private TabSelectedListener listenerTab = null;


    public TopBar(Context context) {
        this(context, null);
        this.context = context;
        timeLastTabChange = System.currentTimeMillis();
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.top_bar, null);
        this.addView(v);

        tablon = (ImageView) v.findViewById(R.id.imageViewTablon);
        chat = (ImageView) v.findViewById(R.id.imageViewChat);
        archivos = (ImageView) v.findViewById(R.id.imageViewArchivos);

        tablon.setOnClickListener(this);
        chat.setOnClickListener(this);
        archivos.setOnClickListener(this);

    }

    @Override
    /** Onclick del tab , si se cambia rapidamente de TAB , se produce una excepcion.
     * Solución realizada : se incluye un delay de entre cambio de pestaña para evitar dicho error
     */
    public void onClick(View view) {

        boolean operationOK = false;
        int tab_changed = -1;

        if (view == tablon && selectedTab != TAB_TABLON) {
            operationOK = true;
            tab_changed = TAB_TABLON;
        } else if (view == chat && selectedTab != TAB_CHAT) {
            operationOK = true;
            tab_changed = TAB_CHAT;
        } else if (view == archivos && selectedTab != TAB_ARCHIVOS) {
            operationOK = true;
            tab_changed = TAB_ARCHIVOS;
        }

        if (operationOK) {

            long time = System.currentTimeMillis() - timeLastTabChange;
            Log.d(TAG, "Tiempo de cambio entre pestañas : " + time);
            if (time > 300){
                markTab(tab_changed);
                listenerTab.tabSelected(tab_changed);
                timeLastTabChange = System.currentTimeMillis();
            }else{
                Log.d(TAG, "Estas cambiando demasiado rapido de tabs ... ");
            }
        }


    }

    public void markTab(int idtab) {
        selectedTab = idtab;
        updateTabs();
    }

    private void updateTabs() {
        /*
        standings.setTextColor(selectedTab==TAB_STANDINGS ? getResources().getColor(R.color.barradorada) : getResources().getColor(R.color.textcolorsecundario));
        matches.setTextColor(selectedTab==TAB_MATCHES ? getResources().getColor(R.color.barradorada) : getResources().getColor(R.color.textcolorsecundario));
        teams.setTextColor(selectedTab==TAB_TEAMS ? getResources().getColor(R.color.barradorada) : getResources().getColor(R.color.textcolorsecundario));
        headlines.setTextColor(selectedTab==TAB_HEADLINES ? getResources().getColor(R.color.barradorada) : getResources().getColor(R.color.textcolorsecundario));
*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setTabListener(TabSelectedListener l) {
        listenerTab = l;

    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int idtab) {
        switch (idtab) {
            case TAB_TABLON:
                markTab(TAB_TABLON);
                listenerTab.tabSelected(TAB_TABLON);
                break;
            case TAB_CHAT:
                markTab(TAB_CHAT);
                listenerTab.tabSelected(TAB_CHAT);
                break;

            case TAB_ARCHIVOS:
                markTab(TAB_ARCHIVOS);
                listenerTab.tabSelected(TAB_ARCHIVOS);
                break;
        }
    }


    public interface TabSelectedListener {
        public void tabSelected(int tab);
    }
}
