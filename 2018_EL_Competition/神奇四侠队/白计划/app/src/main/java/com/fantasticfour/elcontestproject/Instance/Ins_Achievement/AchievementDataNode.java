package com.fantasticfour.elcontestproject.Instance.Ins_Achievement;

import android.content.ContentValues;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;

import java.text.ParseException;

public class AchievementDataNode extends BasicDataNode<Achievement> {
    //信息表，及其字段
    public static final String s_TableName="Achievement";
    public static final String s_col_Name="Name";
    public static final String s_col_Description="Description";
    public static final String s_col_ImgFileName="ImgFileName";
    public static final String s_col_BeAchieved="BeAchieved";
    public static final String s_col_Time="Time";

    /**
     * 创建表的命令
     */
    public static final String s_CreateAchievementTable=
            "create table "+s_TableName+"("+
                    s_col_ID+" integer primary key autoincrement,"+
                    s_col_NextID+" integer,"+
                    s_col_PreviousID+" integer,"+
                    s_col_Name+" text,"+
                    s_col_Description+" text,"+
                    s_col_ImgFileName+" text,"+
                    s_col_BeAchieved+" integer,"+
                    s_col_Time+" text"+
                    ")";

    public AchievementDataNode(){
        m_Data = new Achievement();
    }

    public AchievementDataNode(Achievement achievement){
        m_Data = achievement;
    }

    public AchievementDataNode(Cursor cursor){
        m_Data = new Achievement();
        FromCursor(cursor);
    }

    @Override
    public void FromCursor(Cursor cursor) {
        m_ID=m_Data.m_ID=cursor.getLong(cursor.getColumnIndex(s_col_ID));
        m_NextID=cursor.getLong(cursor.getColumnIndex(s_col_NextID));
        m_PreviousID=cursor.getLong(cursor.getColumnIndex(s_col_PreviousID));
        m_Data.m_Name=cursor.getString(cursor.getColumnIndex(s_col_Name));
        m_Data.m_Description=cursor.getString(cursor.getColumnIndex(s_col_Description));
        m_Data.m_ImgFileName=cursor.getString(cursor.getColumnIndex(s_col_ImgFileName));
        m_Data.m_BeAchieved=cursor.getInt(cursor.getColumnIndex(s_col_BeAchieved));
        try {
            m_Data.m_Time = s_DateFormat_YMDHMS.parse(cursor.getString(cursor.getColumnIndex(s_col_Time)));
        }catch(ParseException e){e.printStackTrace();}
    }

    @Override
    public ContentValues GetContentValues() {
        ContentValues res=new ContentValues();
        res.put(s_col_NextID,m_NextID);
        res.put(s_col_PreviousID,m_PreviousID);
        res.put(s_col_Name,m_Data.m_Name);
        res.put(s_col_Description,m_Data.m_Description);
        res.put(s_col_ImgFileName,m_Data.m_ImgFileName);
        res.put(s_col_BeAchieved,m_Data.m_BeAchieved);
        res.put(s_col_Time, s_DateFormat_YMDHMS.format(m_Data.m_Time));
        return res;
    }

    @Override
    public String GetTableName() {
        return s_TableName;
    }

}
