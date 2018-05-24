package com.easylife.entity;

import cn.bmob.v3.BmobObject;

public class Task extends BmobObject{
    public static final int STATE_TODO = 0;
    public static final int STATE_FINISH = 1;
    public static final int STATE_FAILED = 2;
    private String userId;
    private String taskName;
    private int state;
    private String ddl;

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
