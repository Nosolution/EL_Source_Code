package com.example.user.simpleclock;

import android.annotation.SuppressLint;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FocusRecord {

    /*
     * LitePal for record time.
     */

    private static final int GET_RECORD_DATE = 1;

    private static final int GET_RECORD_TIME = 2;

    private static final int GET_RECORD_TYPE = 3;

    private String recordDate;

    private String recordTime;

    private String recordType;

    public FocusRecord(String recordDate, String recordTime) {
        this.recordDate = recordDate;
        this.recordTime = recordTime;
    }


    public static List<FocusRecord> getDataFromDatabase() {
        List<FocusRecord> targetList = new ArrayList<>();
        List<RecordTime> recordTimes = DataSupport.findAll(RecordTime.class);

        for (RecordTime rt : recordTimes) {
            Date tempDate = new Date(rt.getStartTime());
            String tempString1 = getDateName(rt.getEndTime());
            String tempString2 = rt.getTargetMinute() + "分钟";
            FocusRecord focusRecord = new FocusRecord(tempString1, tempString2);
            targetList.add(focusRecord);
        }

        Collections.reverse(targetList);

        return targetList;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    private static String getDateName(long time) {
        Date mDate = new Date(time);
        String temp1 = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("MM月dd日  ");
        String temp2 = dateformat.format(mDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newdateformat = new SimpleDateFormat("HH:mm");
        String temp3 = newdateformat.format(mDate);
        Calendar latestTime = Calendar.getInstance();
        latestTime.setTime(mDate);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(System.currentTimeMillis()));
        if (latestTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            temp1 = "今天 " + temp3;
            return temp1;
        } else if (latestTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) - 1) {
            temp1 = "昨天 " + temp3;
            return temp1;
        } else {
            return temp2;
        }
    }
}
