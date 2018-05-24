package com.example.shutime;

import cn.bmob.v3.BmobObject;

/**
 * Created by 薛宇豪 on 2018/4/16.
 */

class Classroom extends BmobObject {
    private String name;
    private Integer room_number;
    private Integer order;
    private Integer allTime;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getRoom_number() {
        return room_number;
    }
    public void setRoom_number(int number) {
        this.room_number = number;
    }
    public Integer getOrder(){
        return order;
    }
    public void setOrder(int order){
        this.order=order;
    }

    public Integer getAllTime(){
        return allTime;
    }
    public void setAllTime(int allTime){
        this.allTime=allTime;
    }
}
