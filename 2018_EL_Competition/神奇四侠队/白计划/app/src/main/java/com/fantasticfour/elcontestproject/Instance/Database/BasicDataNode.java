package com.fantasticfour.elcontestproject.Instance.Database;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class BasicDataNode<T> {
    //ID
    public long m_ID;
    //数据
    public T m_Data;

    //下一个Data的ID
    public long m_NextID;
    //上一个Data的ID
    public long m_PreviousID;
    //下一个Data
    public BasicDataNode<T> m_Next;
    //下一个Data
    public BasicDataNode<T> m_Previous;

    //设置为虚拟节点
    public void SetVirtual(){
        m_ID=m_NextID=m_PreviousID=-1;
        m_Previous=m_Next=this;
    }
    //判断是否为虚拟节点
    public boolean BeVirtual(){
        return m_ID==-1;
    }

    public abstract void FromCursor(Cursor cursor);

    public abstract ContentValues GetContentValues();


    public static final String s_col_ID="_id";
    public static final String s_col_NextID="NextID";
    public static final String s_col_PreviousID="PreviousID";

    //年月日-时间字段的格式
    public static final String s_DateFormatString_YMD="yyyy-MM-dd";
    public static final DateFormat s_DateFormat_YMD=new SimpleDateFormat(s_DateFormatString_YMD,new Locale("zh","CN"));

    //年月日时分秒-时间字段的格式
    public static final String s_DateFormatString_YMDHMS="yyyy-MM-dd HH:mm:ss";
    public static final DateFormat s_DateFormat_YMDHMS=new SimpleDateFormat(s_DateFormatString_YMDHMS,new Locale("zh","CN"));

    public abstract String GetTableName();
}
