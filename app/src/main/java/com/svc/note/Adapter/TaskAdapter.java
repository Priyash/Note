package com.svc.note.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svc.note.Database.TaskData;
import com.svc.note.R;

import java.util.Collection;
import java.util.List;

/**
 * Created by PRIYASH on 04-07-2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {


    private List<TaskData>TaskList;

    public TaskAdapter(List<TaskData>TaskList)
    {
        this.TaskList = TaskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        TaskData tdata = TaskList.get(position);
        holder.Title.setText(tdata.getTASK());
        String sub = tdata.getTASK_DESCRIPTION();
        if(sub.length()>29) {
            holder.Subtitle.setTextSize(15);
            holder.Subtitle.setText(sub);
        }
        else
        {
            holder.Subtitle.setText(sub);
        }

    }

    @Override
    public int getItemCount() {
        return TaskList.size();
    }





    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView Title;
        protected TextView Subtitle;

        public TaskViewHolder(View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.Title);
            Subtitle = (TextView)itemView.findViewById(R.id.Subtitle);

        }
    }
}
