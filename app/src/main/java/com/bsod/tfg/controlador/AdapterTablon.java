package com.bsod.tfg.controlador;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.bsod.tfg.utils.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Proudly created by Payton on 25/09/2014.
 */
public class AdapterTablon extends BaseAdapter implements AdapterView.OnItemClickListener {

    private static final String TAG = "AdapterTablon";
    private final Context context;
    private List<MessageBoard> messageList = Collections.emptyList();

    public AdapterTablon(Context context) {
        this.context = context;
    }

    public void updateMessages(List<MessageBoard> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public MessageBoard getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            //holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.tablonlayout, parent, false);
        }

            /*holder.messageText = (TextView) convertView.findViewById(R.id.first_line);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);*/

        TextView message = ViewHolder.get(convertView, R.id.message_board_text);
        TextView title = ViewHolder.get(convertView, R.id.message_board_title);
        ImageView image = ViewHolder.get(convertView, R.id.message_board_image);
        TextView date = ViewHolder.get(convertView, R.id.message_board_date);
        ImageView like = ViewHolder.get(convertView, R.id.message_board_like);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Te gusta este mensaje", Toast.LENGTH_SHORT).show();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "No toques la imagen!", Toast.LENGTH_SHORT).show();
            }
        });

        MessageBoard mb = getItem(position);

        // Formating Date
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
        date.setText(dt.format(mb.getCreationDate()));

        title.setText(mb.getUser().getName());
        title.setTypeface(null, Typeface.BOLD);
        message.setText(mb.getMessage());

        // change the icon for Windows and iPhone
        String s = String.valueOf(getItem(position));
        int wtf = s.hashCode() % 3;
        if (wtf == 0)
            image.setImageResource(R.drawable.ic_owned_fire);
        else if (wtf == 1)
            image.setImageResource(R.drawable.ic_cthulhu_president);
        else {
            image.setImageResource(R.drawable.ic_trololol);
        }

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(context, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();
    }


}



