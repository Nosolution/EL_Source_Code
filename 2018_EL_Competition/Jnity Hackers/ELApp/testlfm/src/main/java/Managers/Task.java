package Managers;

import java.util.Timer;

/*
*
* //任务名，重要性（颜色：四种颜色）
//开始时间，完成时间
//所需交互APP
//单次事项or每周每天
//提醒时间
//备注
* */
public class Task {
    private Timer timer;
    private String taskName;
    private String remarks;
    private String importance;
    private String beginTime;
    private String duration;
    private String endTime;
    private String frequency;
    private String condition;
    private String Accession;

    private Task() {
        timer = new Timer();
        taskName = "None";
        remarks = "None";
        importance = "None";
        beginTime = "None";
        endTime = "None";
        frequency = "None";
        condition = "None";
        Accession = "None";
    }

    public static Task getTask() {
        return new Task();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean IsReady() {
        return condition.equals("ready");
    }

    public boolean IsBegin() {
        return condition.equals("begin");
    }

    public boolean IsFinish() {
        return condition.equals("finish");
    }

    public String getAccession() {
        return Accession;
    }

    public void setAccession(String accession) {
        Accession = accession;
    }
}
