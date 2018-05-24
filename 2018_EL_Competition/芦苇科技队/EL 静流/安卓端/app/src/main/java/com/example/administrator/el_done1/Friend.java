package com.example.administrator.el_done1;

import android.widget.ImageView;

/**
 * Created by Administrator on 2018-5-12.
 */

public class Friend {
    private String name;
    private int friendFaceId;

    public Friend(String name, int friendFaceId){
        this.name = name;
        this.friendFaceId = friendFaceId;
    }
    public String getName(){
        return name;
    }
    public int getFriendFaceId(){
        return friendFaceId;
    }
}
