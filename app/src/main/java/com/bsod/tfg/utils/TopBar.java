package com.bsod.tfg.utils;

import android.content.Context;
import android.util.AttributeSet;
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

    private int selectedTab;

    private ImageView tablon;
    private ImageView chat;
    private ImageView archivos;


    private Context context;
    private TabSelectedListener listenerTab = null;


    public TopBar(Context context) {
        this(context, null);
        this.context = context;
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
    public void onClick(View view) {

        if (view == tablon) {
            markTab(TAB_TABLON);
            listenerTab.tabSelected(TAB_TABLON);
        } else if (view == chat) {
            markTab(TAB_CHAT);
            listenerTab.tabSelected(TAB_CHAT);
        } else if (view == archivos) {
            markTab(TAB_ARCHIVOS);
            listenerTab.tabSelected(TAB_ARCHIVOS);
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

    public void setTabListener(TabSelectedListener l) {
        listenerTab = l;

    }

    public int getSelectedTab() {
        return selectedTab;
    }


    public interface TabSelectedListener {
        public void tabSelected(int tab);
    }
}
