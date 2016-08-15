package com.svc.note.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svc.note.R;

/**
 * Created by PRIYASH on 22-07-2016.
 */
public class NotificationAdapter extends BaseAdapter {

    Context context;
    int[]notif_id;
    String[] notifs;
    LayoutInflater inflater;

    public NotificationAdapter(Context context, int[] notif_id, String[] notifs)
    {
        inflater = (LayoutInflater.from(context));
        this.context = context;
        this.notif_id = notif_id;
        this.notifs = notifs;
    }

    @Override
    public int getCount() {
        return notif_id.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.notification_items,null);
        ImageView icon = (ImageView)convertView.findViewById(R.id.iv_notif_icon);
        TextView notif = (TextView)convertView.findViewById(R.id.tv_notif);

        icon.setImageResource(notif_id[position]);
        notif.setText(notifs[position]);


        return convertView;
    }
}
