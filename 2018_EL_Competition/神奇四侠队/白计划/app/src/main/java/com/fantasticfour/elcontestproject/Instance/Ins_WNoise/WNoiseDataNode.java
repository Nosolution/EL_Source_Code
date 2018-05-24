package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

import android.content.ContentValues;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;

public class WNoiseDataNode extends BasicDataNode<WNoise> {
    public static final String s_TableName = "WNoise";
    public static final String s_col_Name="WNoise_Name";
    public static final String s_col_FileName="WNoise_FileName";
    public static final String s_col_Volume="WNoise_Volume";
    public static final String s_col_BeBuiltIn="WNoise_BeBuiltIn";

    public WNoiseDataNode(){
        m_Data = new WNoise();
    }

    public WNoiseDataNode(WNoise wNoise){
        m_Data = wNoise;
    }

    public WNoiseDataNode(Cursor cursor){
        m_Data = new WNoise();
        FromCursor(cursor);
    }

    public static final String s_CreateWNoiseTable =
            "create table "+s_TableName+"("+
                    s_col_ID+" integer primary key autoincrement,"+
                    s_col_NextID+" integer,"+
                    s_col_PreviousID+" integer,"+
                    s_col_Name+" text,"+
                    s_col_FileName+" text,"+
                    s_col_Volume+" integer,"+
                    s_col_BeBuiltIn+" integer"+
                    ")";

    @Override
    public void FromCursor(Cursor cursor){
        m_ID=m_Data.m_ID=cursor.getLong(cursor.getColumnIndex(s_col_ID));
        m_NextID=cursor.getLong(cursor.getColumnIndex(s_col_NextID));
        m_PreviousID=cursor.getLong(cursor.getColumnIndex(s_col_PreviousID));
        m_Data.m_Name=cursor.getString(cursor.getColumnIndex(s_col_Name));
        m_Data.m_FileName=cursor.getString(cursor.getColumnIndex(s_col_FileName));
        m_Data.m_Volume=cursor.getInt(cursor.getColumnIndex(s_col_Volume));
        m_Data.m_BeBuiltIn=cursor.getInt(cursor.getColumnIndex(s_col_BeBuiltIn));
    }

    @Override
    public ContentValues GetContentValues() {
        ContentValues res = new ContentValues();
        res.put(s_col_NextID,m_NextID);
        res.put(s_col_PreviousID,m_PreviousID);
        res.put(s_col_Name,m_Data.m_Name);
        res.put(s_col_FileName,m_Data.m_FileName);
        res.put(s_col_Volume,m_Data.m_Volume);
        res.put(s_col_BeBuiltIn,m_Data.m_BeBuiltIn);
        return res;
    }

    @Override
    public String GetTableName(){
        return s_TableName;
    }
}
