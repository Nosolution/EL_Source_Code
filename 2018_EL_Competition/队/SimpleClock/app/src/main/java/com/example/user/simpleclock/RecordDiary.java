package com.example.user.simpleclock;


import org.litepal.crud.DataSupport;

public class RecordDiary extends DataSupport {

    private int id;

    private long diarydate;

    private long finaldate;

    private String title;

    private String context;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDiarydate() {
        return diarydate;
    }

    public void setDiarydate(long diarydate) {
        this.diarydate = diarydate;
    }

    public long getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(long finaldate) {
        this.finaldate = finaldate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
