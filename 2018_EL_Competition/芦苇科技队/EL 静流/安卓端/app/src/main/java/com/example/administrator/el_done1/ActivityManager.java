package com.example.administrator.el_done1;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-5-21.
 */

public class ActivityManager {
    //存储Activity的List
    public static List<Activity> currentActivities = new ArrayList<Activity>();

    //添加Activity
    public static void addActivity(Activity activity) {
        currentActivities.add(activity);
    }

    //移出Activity
    public static void removeActivity(Activity activity) {
        if (!activity.isFinishing()) {
            activity.finish();
        }
        currentActivities.remove(activity);
    }

    //销毁所有Activity
    public static void finishAll() {
        for (Activity activity : currentActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
    public static void finishAllExcept(Activity currentActivity){
        for (Activity activity:currentActivities){
            if(activity!=currentActivity&&(!activity.isFinishing())){
                activity.finish();
            }
        }
    }
}
