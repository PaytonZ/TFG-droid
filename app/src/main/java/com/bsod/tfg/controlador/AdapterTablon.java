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

    private final Context context;
    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

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

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.tablonlayout, parent, false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        // object item based on the position
        Long objectItem = getItemId(position);

        // assign values if the object is not null
        if (objectItem != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            holder.textView = (TextView) convertView.findViewById(R.id.first_line);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.textView.setText(String.valueOf(getItem(position)));
            holder.textView.setTypeface(null, Typeface.BOLD);

            // change the icon for Windows and iPhone
            String s = String.valueOf(getItem(position));

            if (s.hashCode() % 2 == 0) {
                holder.imageView.setImageResource(R.drawable.ic_owned_fire);
            } else {
                holder.imageView.setImageResource(R.drawable.ic_cthulhu_president);
            }

        }


        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;

    }

}

