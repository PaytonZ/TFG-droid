package com.bsod.tfg.controlador;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsod.tfg.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Payton on 25/09/2014.
 */

public class AdapterTablon extends ArrayAdapter<String> {

    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    private final Context context;

    public AdapterTablon(Context context, int textViewResourceId,
                         List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tablonlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.first_line);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(String.valueOf(getItem(position)));
        textView.setTypeface(null, Typeface.BOLD);

        // change the icon for Windows and iPhone
        String s = String.valueOf(getItem(position));

        if (s.hashCode() % 2 == 0) {
            imageView.setImageResource(R.drawable.ic_owned_fire);
        } else {
            imageView.setImageResource(R.drawable.ic_cthulhu_president);
        }


        return rowView;
    }

}

