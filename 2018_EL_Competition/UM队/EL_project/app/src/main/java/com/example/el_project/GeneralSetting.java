package com.example.el_project;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 这是控制设置的静态类，较为复杂的采用了各种get和set来控制元素。
 * 当为某次打开应用程序第一次获取设置内容时，自动为这次打开应用程序初始化设置。
 * 每次调用方法修改设置，自动同步设置内容到SharePreferences。
 * 做点修改然后push上去
 *
 * @author NAiveD
 * @version 1.3
 */

public class GeneralSetting {
    private static boolean settingInitialized = false;    //设置是否初始化过了，即是否从SharedPreferences中取出过所有设置了
    private static boolean firstOpen = true;              //设置是否是第一次打开
    private static boolean musicOn = true;               //音乐是否开启
    private static boolean tomatoClockEnable = true;     //番茄钟是否开启
    private static boolean callWhenTimeUpEnable = true;  //是否倒计时到时时提醒
    private static int tomatoClockTime = 25;              //番茄钟设置时长(分钟计)
    private static int tomatoBreakTime = 5;               //番茄钟间隔休息时间（分钟计）

    private static int totalTaskTime = 0;                 //总共使用此软件工作学习的时长，这个不是设置的

    private static void initializeSetting(Context context){
        SharedPreferences pref = context.getSharedPreferences("general_setting", Context.MODE_PRIVATE);
        firstOpen = pref.getBoolean("first_open", true);
        musicOn = pref.getBoolean("music_on", true);
        tomatoClockEnable = pref.getBoolean("tomato_clock_enable", true);
        callWhenTimeUpEnable = pref.getBoolean("call_when_time_up_enable", false);
        tomatoClockTime = pref.getInt("tomato_clock_time", 25);
        tomatoBreakTime = pref.getInt("tomato_break_time", 5);

        totalTaskTime = pref.getInt("total_task_time", 0);

        settingInitialized = true;
    }

    private static void syncSetting(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("general_setting", Context.MODE_PRIVATE).edit();
        editor.putBoolean("first_open", firstOpen);
        editor.putBoolean("music_on", musicOn);
        editor.putBoolean("tomato_clock_enable", tomatoClockEnable);
        editor.putBoolean("call_when_time_up_enable", callWhenTimeUpEnable);
        editor.putInt("tomato_clock_time", tomatoClockTime);
        editor.putInt("tomato_break_time", tomatoBreakTime);
        editor.putInt("total_task_time", totalTaskTime);
        editor.apply();
    }

    /**
     * Get first open boolean.
     * 判断是否为第一次打开应用，若是，则将数据库内的置否
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean getFirstOpen(Context context){
        if(!settingInitialized){
            initializeSetting(context);
        }
        if (firstOpen){
            firstOpen = false;
            syncSetting(context);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Get music on boolean.
     * 判断是否开启背景音乐，默认开启
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean getMusicOn(Context context){
        if(!settingInitialized){
            initializeSetting(context);
        }
        return musicOn;
    }

    /**
     * Set music on.
     * 设置背景音乐是否开启
     *
     * @param context the context
     * @param enable  the enable 背景音乐是否开启
     */
    public static void setMusicOn(Context context, boolean enable){
        if(!settingInitialized){
            initializeSetting(context);
        }
        musicOn = enable;
        syncSetting(context);
    }

    /**
     * Get tomato clock enable boolean.
     * 判断是否启用番茄钟，默认开启
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean getTomatoClockEnable(Context context){
        if(!settingInitialized){
            initializeSetting(context);
        }
        return tomatoClockEnable;
    }

    /**
     * Set tomato clock enable.
     * 设置番茄钟是否启用
     *
     * @param context the context
     * @param enable  the enable 是否启用番茄钟
     */
    public static void setTomatoClockEnable(Context context, boolean enable){
        if(!settingInitialized){
            initializeSetting(context);
        }
        tomatoClockEnable = enable;
        syncSetting(context);
    }

    /**
     * Get call when time up enable boolean.
     * 判断是否启用番茄钟到时提醒，暂未可设置，默认开启
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean getCallWhenTimeUpEnable(Context context){
        if(!settingInitialized){
            initializeSetting(context);
        }
        return callWhenTimeUpEnable;
    }

    /**
     * Set call when time up enable.
     * 设置是否启用番茄钟到时提醒，暂未可设置
     *
     * @param context the context
     * @param enable  the enable
     */
    public static void setCallWhenTimeUpEnable(Context context, boolean enable){
        if(!settingInitialized){
            initializeSetting(context);
        }
        callWhenTimeUpEnable = enable;
        syncSetting(context);
    }

    /**
     * Get tomato clock time int.
     * 返回番茄钟工作计时时长
     *
     * @param context the context
     * @return the int
     */
    public static int getTomatoClockTime(Context context){
        if(!settingInitialized)
            initializeSetting(context);
        return tomatoClockTime;
    }

    /**
     * Set tomato clock time.
     * 设置番茄钟工作计时时长
     *
     * @param context       the context
     * @param timeInMinutes the time in minutes 番茄钟工作计时的时长
     */
    public static void setTomatoClockTime(Context context, int timeInMinutes){
        if(!settingInitialized){
            initializeSetting(context);
        }
        tomatoClockTime = timeInMinutes;
        syncSetting(context);
    }

    /**
     * Get tomato break time int.
     * 返回番茄钟休息时长
     *
     * @param context the context
     * @return the int
     */
    public static int getTomatoBreakTime(Context context){
        if(!settingInitialized){
            initializeSetting(context);
        }
        return tomatoBreakTime;
    }

    /**
     * Set tomato break time.
     * 设置番茄钟休息时长
     *
     * @param context       the context
     * @param timeInMinutes the time in minutes 番茄钟休息的时长
     */
    public static void setTomatoBreakTime(Context context, int timeInMinutes){
        if(!settingInitialized){
            initializeSetting(context);
        }
        tomatoBreakTime = timeInMinutes;
        syncSetting(context);
    }

    /**
     * Get total task time int.
     * 得到总共使用该APP专注（即番茄钟开启）的攻击时长，未使用
     *
     * @param context the context
     * @return the int
     */
    public static int getTotalTaskTime(Context context){
        if (!settingInitialized){
            initializeSetting(context);
        }
        return totalTaskTime;
    }

    /**
     * Increase total task time.
     * 每次完成任务后增加使用该APP专注的时长，未启用
     *
     * @param context      the context
     * @param increasement the increasement 本次任务的专注时长
     */
    public static void increaseTotalTaskTime(Context context, int increasement){
        if(!settingInitialized){
            initializeSetting(context);
        }
        totalTaskTime += increasement;
        syncSetting(context);
    }

}
