package com.example.user.simpleclock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoItem> mTodo;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mTodoView;

        TextView mTodoTime;
        TextView mTodoName;

        public ViewHolder(View view) {
            super(view);
            mTodoView = view;
            mTodoTime = (TextView) view.findViewById(R.id.todo_time);
            mTodoName = (TextView) view.findViewById(R.id.todo_name);
        }
    }

    public TodoAdapter(List<TodoItem> todoItems) {
        mTodo = todoItems;
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_todo, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TodoAdapter.ViewHolder holder, int position) {
        TodoItem todoItem = mTodo.get(position);
        holder.mTodoName.setText(todoItem.getTodo());
        holder.mTodoTime.setText(todoItem.getMinutes() + "分钟");

    }

    @Override
    public int getItemCount() {
        return mTodo.size();
    }
}
