package com.easylife.activity;

import android.app.Application;

import com.easylife.entity.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class MainApplication extends Application {
    public static int picIndex;
    public static int sentenceIndex;
    public static BmobQuery<User> userQuery;
    public static BmobQuery<BmobObject> dataQuery;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "7be9b42f62bf09ca88164f551f7af615");

        userQuery = new BmobQuery<User>();
        dataQuery = new BmobQuery<BmobObject>();
    }
}
