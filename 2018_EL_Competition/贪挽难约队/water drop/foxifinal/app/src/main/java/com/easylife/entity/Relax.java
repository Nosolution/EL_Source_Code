package com.easylife.entity;

import cn.bmob.v3.BmobObject;

public class Relax extends BmobObject{
    private String userId;
    private String time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
