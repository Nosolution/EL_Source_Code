package com.example.user.simpleclock;

import org.litepal.crud.DataSupport;

import java.sql.Date;

public class RecordTime extends DataSupport {

    private int id;

    private String focusType;

    private long startTime;

    private long endTime;

    private int targetMinute;

    private int isFinished;

    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFocusType() {
        return focusType;
    }

    public void setFocusType(String focusType) {
        this.focusType = focusType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public int getTargetMinute() {
        return targetMinute;
    }

    public void setTargetMinute(int targetMinute) {
        this.targetMinute = targetMinute;
    }


    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        if (isFinished == 1 || isFinished == 0) {
            this.isFinished = isFinished;
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
