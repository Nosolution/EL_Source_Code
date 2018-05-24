package com.example.administrator.elwork;

/**
 * Created by dell on 2018/5/20.
 */

public class Task {
    private String task_name;
    //构造方法
    public Task(String task_name){
        this.task_name=task_name;
    }
    public String getTask_name(){
        return task_name;
    }

    @Override
    public String toString() {
        return getTask_name();
    }
}
