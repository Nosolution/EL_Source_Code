package com.example.administrator.elwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2018/5/20.
 */

public class TasksAdapter extends ArrayAdapter<Task> {
    private int resourceId;

    public TasksAdapter(Context context, int textViewResourceId,
                       List<Task> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,
                false);
        TextView taskName = (TextView) view.findViewById(R.id.task_name);
        taskName.setText(task.getTask_name());
        return view;
    }
}