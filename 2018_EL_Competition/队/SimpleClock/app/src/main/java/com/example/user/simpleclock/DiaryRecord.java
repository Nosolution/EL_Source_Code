package com.example.user.simpleclock;

import android.annotation.SuppressLint;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DiaryRecord {

    private String diaryDate;

    private String diaryTitle;

    private String diaryContext;

    private int diaryId;


    public DiaryRecord(String diaryDate, String diaryTitle, String diaryContext, int diaryId) {

        this.diaryDate = diaryDate;
        this.diaryTitle = diaryTitle;
        this.diaryContext = diaryContext;
        this.diaryId = diaryId;
    }

    public static List<DiaryRecord> getDataFromDatabase() {
        List<DiaryRecord> targetList = new ArrayList<>();
        List<RecordDiary> recordDiary = DataSupport.findAll(RecordDiary.class);

        for (RecordDiary rd: recordDiary) {
            String temp1 = getDateName(rd.getDiarydate());
            String temp2 = rd.getTitle();
            String temp3 = rd.getContext();
            int temp4 = rd.getId();
            DiaryRecord diaryRecord = new DiaryRecord(temp1, temp2, temp3, temp4);
            targetList.add(diaryRecord);
        }

        Collections.reverse(targetList);
        return targetList;
    }

    public String getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryContext() {
        return diaryContext;
    }

    public int getDiaryId() {
        return diaryId;
    }

    private static String getDateName(long time) {
        Date mDate = new Date(time);
        String temp1 = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("MMdd");
        String temp2 = dateformat.format(mDate);
        Calendar latestTime = Calendar.getInstance();
        latestTime.setTime(mDate);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(System.currentTimeMillis()));
        if (latestTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            temp1 = "今天";
            return temp1;
        } else if (latestTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) - 1) {
            temp1 = "昨天";
            return temp1;
        } else {
            return temp2;
        }
    }

}
