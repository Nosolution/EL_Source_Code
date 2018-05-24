package com.fantasticfour.elcontestproject.Instance;
/**
 *
 * 在ScheduleActivity.onCreate中，必须调用
 *     Instance.Init(getApplicationContext());
 * 其中s_WNoiseController,s_Schedule为单例，用于后台的存储，计算，播放等
 *
 */


import android.content.Context;

import com.fantasticfour.elcontestproject.Instance.BackendInteraction.BackendDAO;
import com.fantasticfour.elcontestproject.Instance.Database.CommonDAO;
import com.fantasticfour.elcontestproject.Instance.Ins_Achievement.AchievementLibrary;
import com.fantasticfour.elcontestproject.Instance.Ins_Preferences.Preferences;
import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Schedule;
import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoiseController;

import java.util.Date;

public class Instance {
    public static WNoiseController s_WNoiseController;
    public static Schedule s_Schedule;
    public static AchievementLibrary s_AchievementLibrary;
    public static CommonDAO s_CommonDAO;
    public static Preferences s_Preferences;
    public static BackendDAO s_BackendDAO;

    /**
     * 初始化后台
     * 在ScheduleActivity.onCreate中，必须调用该方法
     * @param context
     * 需要ApplicationContext
     */
    public static void Init(Context context){
        s_CommonDAO=new CommonDAO(context);
        s_Schedule=new Schedule(new Date());
        s_WNoiseController=new WNoiseController(context);
        s_AchievementLibrary=new AchievementLibrary(context);
        s_Preferences=new Preferences(context);
        s_BackendDAO=new BackendDAO(context);
    }

    public static void Destroy(){
        s_CommonDAO.Destroy();
    }


}
