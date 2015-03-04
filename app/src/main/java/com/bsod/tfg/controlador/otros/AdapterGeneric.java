package com.bsod.tfg.controlador.otros;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Proudly created by Payton on 06/11/2014.
 */
public abstract class AdapterGeneric<T> extends BaseAdapter {

    protected static final String TAG = "AdapterGeneric";
    protected final Context context;
    protected List<T> list = Collections.emptyList();


    public AdapterGeneric(Context context) {
        this.context = context;
    }

    public void update(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
