package com.fantasticfour.elcontestproject.Calendar;

/**
 * Created by nongzengyun on 2018/4/10.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
    private Date today;
    private Date dayOnDisplay;
    private ArrayList<Date> toWeek;
    private ArrayList<Date> weekOnDisplay;
    public DateUtils(){
        Calendar calendar = Calendar.getInstance();
        today = calendar.getTime();
        toWeek = weekCount(today);
        setDayOnDisplay(today);
        setWeekOnDisplay(toWeek);
    }
    public void setDayOnDisplay(Date date){
        this.dayOnDisplay = date;
    }//重设dayOnDisplay
    public Date getDayOnDisplay(){
        return dayOnDisplay;
    }//获取dayOnDisplay
    public void setWeekOnDisplay(ArrayList<Date> week){
        this.weekOnDisplay = week;
    }//重设weekOnDisplay
    public ArrayList<Date> getWeekOnDisplay(){
        return weekOnDisplay;
    }//获取weekOnDisplay
    public Date getToday(){
        return today;
    }
    public ArrayList<Date> getToWeek(){
        return toWeek;
    }
    public void setPreWeek(){
        Date date1 = weekOnDisplay.get(0);
        int year = date1.getYear() + 1900;
        int month = date1.getMonth() + 1;
        int day = date1.getDate();
        if(day - 7 < 1){
            if(month == 1){
                year -= 1;
                month = 12;
                day += 24;
            }else {
                month -= 1;
                int daysOfMonth = getDaysOfMonth(year, month);
                day = day + daysOfMonth - 7;
            }
        }else {
            day -= 7;
        }
        if((year == toWeek.get(0).getYear() + 1900) && (month == toWeek.get(0).getMonth() + 1) && (day == toWeek.get(0).getDate())){
            setWeekOnDisplay(toWeek);
            setDayOnDisplay(today);
        }else {
            Date date11 = new Date(year - 1900, month - 1, day);
            setDayOnDisplay(date11);
            setWeekOnDisplay(weekCount(date11));
        }
    }//将weekOnDisplay更改至上一星期
    public void setNextWeek(){
        Date date1 = weekOnDisplay.get(0);
        int year = date1.getYear() + 1900;
        int month = date1.getMonth() + 1;
        int day = date1.getDate();
        int dayOfMonth = getDaysOfMonth(year, month);
        if(day + 7 > dayOfMonth){
            if(month == 12){
                year += 1;
                month = 1;
                day = day - 24;
            }else {
                month += 1;
                day = day + 7 - dayOfMonth;
            }
        }else {
            day += 7;
        }
        if((year == toWeek.get(0).getYear() + 1900) && (month == toWeek.get(0).getMonth() + 1) && (day == toWeek.get(0).getDate())){
            setWeekOnDisplay(toWeek);
            setDayOnDisplay(today);
        }else {
            Date date11 = new Date(year - 1900, month - 1, day);
            setDayOnDisplay(date11);
            setWeekOnDisplay(weekCount(date11));
        }
    }//将weekOnDisplay更改至下一星期
    public ArrayList<Date> weekCount(Date date){
        ArrayList<Date> week = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfweek = calendar.get(Calendar.DAY_OF_WEEK);//1-周日，2-周一......
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;//month范围1—12
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int daysOfMonth = getDaysOfMonth(year, month);
        for(int i = 1; i <= 7; i++){
            Date date1;
            if(day - dayOfweek + i <= 0){
                if(month == 1) {
                    date1 = new Date(year - 1901, 11, 30 + i - dayOfweek + day);
                }else {
                    date1 = new Date(year - 1900, month - 2, getDaysOfMonth(year, month) + i - dayOfweek + day);
                }
            }else if(day - dayOfweek + i > daysOfMonth){
                if(month == 12){
                    date1 = new Date(year - 1899, 0, day - dayOfweek + i - 30);
                }else {
                    date1 = new Date(year - 1900, month, day - dayOfweek + i - daysOfMonth);
                }
            }else {
                date1 = new Date(year - 1900, month - 1, day - dayOfweek + i);
            }
            week.add(date1);
        }
        return week;
    }//返回某天所在的整个星期
    private int getDaysOfMonth(int year,int month){
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if((year%4==0&&year%100!=0)||year%400==0){
                    return 29;
                }else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }//计算某年某月的天数
}
