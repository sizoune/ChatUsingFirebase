package com.example.asus.knowingfirebase;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Asus on 11/25/2016.
 */

public class CustomAdapter extends BaseAdapter {
    String user;
    ArrayList<ChatMessage> cmm;
    Context context;

    public CustomAdapter(ArrayList<ChatMessage> cmm, Context context, String user) {
        this.cmm = cmm;
        this.context = context;
        this.user = user;
        //System.out.println(cm.get(0).getMessageUser());
    }

    @Override
    public int getCount() {
        return cmm.size();
    }

    @Override
    public Object getItem(int position) {
        return cmm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ChatMessage cm1 = cmm.get(position);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (cm1.getMessageUser().equals(user)) {
                v = inflater.inflate(R.layout.message1, parent, false);
                TextView email1 = (TextView) v.findViewById(R.id.txtNama1);
                TextView pesan1 = (TextView) v.findViewById(R.id.txtPesan1);
                email1.setTextColor(Color.parseColor("#76FF03"));
                pesan1.setTextColor(Color.parseColor("#76FF03"));
                email1.setText(cm1.getMessageUser());
                pesan1.setText(cm1.getMessageText());
            } else {
                v = inflater.inflate(R.layout.message, parent, false);
                TextView email = (TextView) v.findViewById(R.id.txtNama);
                TextView pesan = (TextView) v.findViewById(R.id.txtPesan);
                email.setTextColor(Color.parseColor("#76FF03"));
                pesan.setTextColor(Color.parseColor("#76FF03"));
                email.setText(cm1.getMessageUser());
                pesan.setText(cm1.getMessageText());
            }
        }
        return v;
    }
}
