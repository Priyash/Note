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
public class PriorityAdapter extends BaseAdapter {

    Context context;
    int[] priority_id;
    String[] priorities;
    LayoutInflater inflater;
    public PriorityAdapter(Context context, int[] priority_id, String[] priorities)
    {
        this.context = context;
        this.priority_id = priority_id;
        this.priorities = priorities;
        inflater = (LayoutInflater.from(context));
    }



    @Override
    public int getCount() {
        return priority_id.length;
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
        convertView = inflater.inflate(R.layout.priority_items,null);
        ImageView icon = (ImageView)convertView.findViewById(R.id.iv_icon);
        TextView priority = (TextView)convertView.findViewById(R.id.tv_priority);

        icon.setImageResource(priority_id[position]);
        priority.setText(priorities[position]);

        return convertView;
    }
}
