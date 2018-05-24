package com.fantasticfour.elcontestproject.Instance.Ins_Schedule;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

enum PlanType{
    Study,
    Communication,
    Research,
    Train,
    Work,
    Sleep
}

public class Plan implements Serializable{
    //ID
    public long m_ID;
    //日期
    public Date m_Date;
    //计划类型
    public PlanType m_PlanType;
    //计划的描述
    public String m_Description;
    //计划的详情
    public String m_Details;
    //是否完成
    public int m_Completed;
    public Plan(){this.m_ID = -1;}
    public Plan(String Description,String Details,Date Date){
        this.m_Description=Description;
        this.m_Details=Details;
        this.m_Date=Date;
        this.m_Completed=0;
    }
    public void change(Plan A){
        //计划类型
        this.m_PlanType=A.m_PlanType;
        //计划的描述
        this.m_Description=A.m_Description;
        //计划的详情
        this.m_Details=A.m_Details;
        //是否完成
        this.m_Completed=A.m_Completed;
    }
    public String getname(){
        return this.m_Description;
    }
    public String getdescripition(){
        return this.m_Details;
    }
    public long getId(){
        return this.m_ID;
    }
    public Date getddl(){
        return this.m_Date;
    }
}
