package com.example.el_project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 算法集合，封装了一些复杂操作
 * @author ns
 */

public class MyAlgorithm {
    /**
     * calculate scores
     * 计算本次完成任务的分数
     *
     * @param totalTime 总用时
     * @param usedTime  专注时间
     * @param breakCount 切出应用的次数
     * @return 分数
     */
    public static int calcScores(int totalTime,int usedTime,int breakCount){
        int[] highestScores={96,97,98,99,100};
        int breakWeight=0;
        breakCount-=(Math.ceil((double)(usedTime+30)/60/15));
        if(breakCount<0) breakCount=0;
        double timeRate=(double)usedTime/totalTime;
        double breakRate=(double)breakCount/((usedTime+30)/60);
        if(breakRate>=1) breakRate=1.0;
        int scores=timeRate>=0.75? highestScores[(int)(Math.random()*5)]:(int)(timeRate*100+20);
//        while(breakCount>0){
//            scores-=(int)(Math.random()*(2+0.3*breakWeight));
//            breakWeight++;
//            breakCount--;
//        }
        scores-=(int)(18+4*Math.random())*breakRate;
        return scores;
    }

    public static int calcDateDiffWeight(String currentDate,String deadline)throws ParseException{
        int result=calcDateDifference(currentDate,deadline);
        return result>0? 5-result:0;
    }

    /**
     * calculate the difference of two date
     * 计算两日期的天数差
     * @param date1 较前的日期
     * @param date2 较后的日期
     * @return 如果data1在date2之前，返回天数差，否则返回-1
     * @throws ParseException
     */
    public static int calcDateDifference(String date1,String date2)throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date1));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(date2));
        long time2 = cal.getTimeInMillis();
        if(time2<=time1)
            return -1;
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * calculate the weight of date difference
     * 计算ddl与当前日期天数差的权重
     * @param deadline 截止时间
     * @return
     */
    public static int calcDateDiffWeight(String deadline){
        try {
            int result = calcDateDifference(deadline);
            return result > 0 ? 5 - result : 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * calculate the difference of two date
     * 计算目标日期与当前日期的天数差
     * @param date 目标日期
     * @return 天数差或者-1
     * @throws ParseException
     */
    public static int calcDateDifference(String date)throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = formatDate.format(calendar.getTime());
        cal.setTime(sdf.parse(currentDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(date));
        long time2 = cal.getTimeInMillis();
        if(time2<=time1)
            return -1;
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * calculate the rest days of the task
     * 计算任务距ddl的剩余天数，结果经过构造
     * @param ddl
     * @return 剩余天数
     */
    public static String getTaskRestDays(String ddl){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String deadLine=ddl.split(" ")[0];
        String currentDate=formatDate.format(calendar.getTime());
        try {
            int diff = MyAlgorithm.calcDateDifference(currentDate, deadLine);
            if(diff>0&&diff<=3){
                switch (diff){
                    case 1:
                        return "1天";
                    case 2:
                        return "2天";
                    case 3:
                        return "3天";
                }
            }
            else if(diff>3){
                String[] temp=deadLine.substring(5).split("-");
                if(Integer.parseInt(temp[0])<10)
                    return temp[0].substring(1)+"."+temp[1];
                else
                    return temp[0]+"."+temp[1];
            }
            else
                return "";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
