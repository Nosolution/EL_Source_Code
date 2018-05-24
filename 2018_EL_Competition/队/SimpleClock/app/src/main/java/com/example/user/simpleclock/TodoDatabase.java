package com.example.user.simpleclock;

import org.litepal.crud.DataSupport;

public class TodoDatabase extends DataSupport {

    private int id;

    private String todo;

    private int minutes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
