package com.hlxx.climber.secondpage.records;

import java.io.Serializable;
import java.util.Calendar;

public class Record implements Serializable {
    static final long serialVersionUID = 1L;

    public void setLevel() {
        this.level = 100 - switchTimes * 25;
    }

    private int timeSetted;//时间
    private boolean finish;
    private int switchTimes;
    private int totalTime;
    private Calendar now;
    private int level;


    public Calendar getNow() {
        return now;
    }


    public int getLevel() {
        return level;
    }

    public Record() {
        this.now = Calendar.getInstance();
    }

    public void setTimeSetted(int timeSetted) {
        this.timeSetted = timeSetted;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public void setSwitchTimes(int switchTimes) {
        this.switchTimes = switchTimes;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "timeSetted=" + timeSetted +
                ", finish=" + finish +
                ", switchTimes=" + switchTimes +
                ", totalTime=" + totalTime +
                ", now=[" + (now.get(Calendar.MONTH) + 1) + ":" + now.get(Calendar.DAY_OF_MONTH) + "   "
                + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND) +
                "], level=" + level +
                '}';
    }

    public int getTimeSetted() {
        return timeSetted;
    }

    public boolean isFinish() {
        return finish;
    }

    public int getSwitchTimes() {
        return switchTimes;
    }

    public int getTotalTime() {
        return totalTime;
    }

}
