package com.fantasticfour.elcontestproject.Instance.Ins_Schedule;

import android.content.ContentValues;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;

import java.text.ParseException;

public class PlanDataNode extends BasicDataNode<Plan> {
    //信息表，及其字段
    public static final String s_TableName="Plan";
    public static final String s_col_Date="Date";
    public static final String s_col_Description="Description";
    public static final String s_col_Details="Details";
    public static final String s_col_Completed="Completed";


    /**
     * 创建表的命令
     */
    public static final String s_CreatePlanTable=
            "create table "+s_TableName+"("+
                    s_col_ID+" integer primary key autoincrement,"+
                    s_col_NextID+" integer,"+
                    s_col_PreviousID+" integer,"+
                    s_col_Date+" text,"+
                    s_col_Description+" text,"+
             s_col_Details+" text,"+
                    s_col_Completed+" integer"+
                    ")";

    public PlanDataNode(){
        m_Data = new Plan();
    }

    public PlanDataNode(Plan plan){
        m_Data = plan;
    }

    public PlanDataNode(Cursor cursor){
        m_Data = new Plan();
        FromCursor(cursor);
    }

    @Override
    public void FromCursor(Cursor cursor) {
        m_ID=m_Data.m_ID=cursor.getLong(cursor.getColumnIndex(s_col_ID));
        m_NextID=cursor.getLong(cursor.getColumnIndex(s_col_NextID));
        m_PreviousID=cursor.getLong(cursor.getColumnIndex(s_col_PreviousID));
        try {
            m_Data.m_Date = s_DateFormat_YMD.parse(cursor.getString(cursor.getColumnIndex(s_col_Date)));
        }catch(ParseException e){e.printStackTrace();}
        m_Data.m_Description=cursor.getString(cursor.getColumnIndex(s_col_Description));
        m_Data.m_Details=cursor.getString(cursor.getColumnIndex(s_col_Details));
        m_Data.m_Completed=cursor.getInt(cursor.getColumnIndex(s_col_Completed));
    }

    @Override
    public ContentValues GetContentValues() {
        ContentValues res=new ContentValues();
        res.put(s_col_NextID,m_NextID);
        res.put(s_col_PreviousID,m_PreviousID);
        res.put(s_col_Date, s_DateFormat_YMD.format(m_Data.m_Date));
        res.put(s_col_Description,m_Data.m_Description);
        res.put(s_col_Details,m_Data.m_Details);
        res.put(s_col_Completed,m_Data.m_Completed);
        return res;
    }

    @Override
    public String GetTableName() {
        return s_TableName;
    }
}
