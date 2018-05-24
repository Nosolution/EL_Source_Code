package com.nju.team.timescapes;

/**
 * Created by ASUS on 2018/5/21.
 */
import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());
    }
}
