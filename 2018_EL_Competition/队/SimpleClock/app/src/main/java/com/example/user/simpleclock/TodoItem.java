package com.example.user.simpleclock;

import android.provider.ContactsContract;
import android.widget.LinearLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoItem {

    private String todo;

    private int minutes;

    private int id;

    public TodoItem(String todo, int minutes, int id) {
        this.todo = todo;
        this.minutes = minutes;
        this.id = id;
    }

    public static List<TodoItem> getDataFromDatabase() {
        List<TodoItem> targetList = new ArrayList<>();
        List<TodoDatabase> todoList = DataSupport.findAll(TodoDatabase.class);

        for (TodoDatabase td : todoList) {
            targetList.add(new TodoItem(td.getTodo(), td.getMinutes(), td.getId()));
        }

        Collections.reverse(targetList);

        return targetList;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
