package com.bsod.tfg.controlador.examenes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsod.tfg.R;
import com.bsod.tfg.modelo.examenes.DocumentBean;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.utils.ViewHolder;
import com.bsod.tfg.vista.examenes.ActivityVerImagenesDocumentos;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Proudly created by Payton on 01/05/2015.
 */
public class AdapterDocuments extends BaseAdapter implements AdapterView.OnItemClickListener {

    private ArrayList<DocumentBean> list;
    private Context context;

    public AdapterDocuments(Context context, ArrayList<DocumentBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DocumentBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_document_list, parent, false);
        }
        DocumentBean db = getItem(position);
        TextView document_item = ViewHolder.get(convertView, R.id.document_item);
        document_item.setText("[ ".concat(String.valueOf(db.getMes()).concat(" - ").concat(String.valueOf(db.getAnno())).concat("]")).concat(" subido por ").concat(db.getUsuario().getName()));
        ImageView profileImage = ViewHolder.get(convertView, R.id.imageProfile);
        ImageLoader im = ImageLoader.getInstance();
        //if (!mb.getUser().getPicImageUrl().equals("")) {
        im.displayImage(Constants.BASE_URL.concat(db.getUsuario().getPicImageUrl()), profileImage);

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position >= 0) {
            DocumentBean db = getItem(position);
            Intent i = new Intent(context, ActivityVerImagenesDocumentos.class);
            i.putExtra(Constants.INTENT_EXTRA_IMAGELIST_DOCUMENTS, db.getImagenes());
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }


    }
}
