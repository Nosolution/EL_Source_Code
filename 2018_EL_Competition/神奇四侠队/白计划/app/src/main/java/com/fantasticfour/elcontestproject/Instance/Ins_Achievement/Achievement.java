package com.fantasticfour.elcontestproject.Instance.Ins_Achievement;

import java.io.Serializable;
import java.util.Date;

public class Achievement implements Serializable{
    public long m_ID;
    public String m_Name;
    public String m_Description;
    public String m_ImgFileName;
    public int m_BeAchieved;
    public Date m_Time;
    Achievement(){
        m_ID = -1;
    }

    public long getM_ID(){
        return m_ID;
    }
    public String getM_Name(){
        return m_Name;
    }
    public String getM_Description(){
        return m_Description;
    }
    public String getM_ImgFileName(){
        return m_ImgFileName;
    }
    public int getM_BeAchieved(){
        return m_BeAchieved;
    }
    public Date getM_Time(){
        return m_Time;
    }

    public void setM_ID(long id){
        m_ID = id;
    }
    public void setM_Name(String name){
        m_Name = name;
    }
    public void setM_Description(String description){
        m_Description = description;
    }
    public void setM_ImgFileName(String imgFileName){
        m_ImgFileName = imgFileName;
    }
    public void setM_BeAchieved(int beAchieved){
        m_BeAchieved = beAchieved;
    }
    public void setM_Time(Date time){
        m_Time = time;
    }
}
