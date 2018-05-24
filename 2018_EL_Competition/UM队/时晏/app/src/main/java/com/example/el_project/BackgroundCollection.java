package com.example.el_project;

import android.graphics.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * The type Background collection.
 * 背景相关信息集合
 * 针对一周的不同天数返回不同的背景图片资源和主题色
 * 实例化相应对象直接调用方法即可
 * @author NAiveD
 * @version 1.1
 */

public class BackgroundCollection {
    private List<Integer> backgroundCollection;
    private List<Integer> colorCollection;
    private List<Integer> themeCollection;

    /**
     * Instantiates a new Background collection.
     */
    BackgroundCollection(){
        backgroundCollection = new ArrayList<Integer>();
        colorCollection = new ArrayList<Integer>();
        themeCollection = new ArrayList<Integer>();
        init();
    }

    private void init(){
        backgroundCollection.add(R.drawable.background_1);
        backgroundCollection.add(R.drawable.background_2);
        backgroundCollection.add(R.drawable.background_3);
        backgroundCollection.add(R.drawable.background_4);
        backgroundCollection.add(R.drawable.background_5);
        backgroundCollection.add(R.drawable.background_6);
        backgroundCollection.add(R.drawable.background_7);

        colorCollection.add(Color.rgb(255, 168, 108));
        colorCollection.add(Color.rgb(128, 57, 181));
        colorCollection.add(Color.rgb(141, 167, 242));
        colorCollection.add(Color.rgb(242, 99, 97));
        colorCollection.add(Color.rgb(73, 102, 88));
        colorCollection.add(Color.rgb(98, 168, 153));
        colorCollection.add(Color.rgb(146, 168, 165));

        themeCollection.add(R.style.AppTheme1);
        themeCollection.add(R.style.AppTheme2);
        themeCollection.add(R.style.AppTheme3);
        themeCollection.add(R.style.AppTheme4);
        themeCollection.add(R.style.AppTheme5);
        themeCollection.add(R.style.AppTheme6);
        themeCollection.add(R.style.AppTheme7);
    }

    private int getWeekNum(){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.getDefault());
        String week = format.format(calendar.getTime());
        int weekNum = 1;
        switch (week){
            case "周一":
            case "Mon": weekNum = 1; break;
            case "周二":
            case "Tue": weekNum = 2; break;
            case "周三":
            case "Wed": weekNum = 3; break;
            case "周四":
            case "The": weekNum = 4; break;
            case "周五":
            case "Fri": weekNum = 5; break;
            case "周六":
            case "Sat": weekNum = 6; break;
            case "周日":
            case "Sun": weekNum = 7; break;
        }
        return weekNum;
    }

    /**
     * Get today background res as int.
     *
     * @return int
     */
    public int getTodayBackground(){
        return backgroundCollection.get(getWeekNum() - 1);
    }

    /**
     * Get today color as int.
     *
     * @return int
     */
    public int getTodayColor(){
        return colorCollection.get(getWeekNum() - 1);
    }

    /**
     * Get today theme res as int.
     *
     * @return int
     */
    public int getTodayTheme(){
        return themeCollection.get(getWeekNum() - 1);
    }
}
