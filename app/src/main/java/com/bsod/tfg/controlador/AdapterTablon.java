package com.bsod.tfg.controlador;

import android.content.Context;
import android.widget.ArrayAdapter;


import java.util.HashMap;
import java.util.List;

/**
 * Created by Payton on 25/09/2014.
 */

public class AdapterTablon extends ArrayAdapter<String> {

    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public AdapterTablon(Context context, int textViewResourceId,
                         List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}

