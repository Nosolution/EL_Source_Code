package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

import android.content.ContentValues;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;

public class WNoisePresetDataNode extends BasicDataNode<WNoisePreset> {
    private static final String s_TableName = "WNoisePreset";
    private static final String s_col_Name="WNoisePreset_Name";
    private static final String s_col_Status="WNoisePreset_Status";
    private static final String s_col_BeCurrent="WNoisePreset_BeCurrent";

    public static final String s_CreateWNoisePresetTable =
            "create table "+s_TableName+"("+
                    s_col_ID+" integer primary key autoincrement,"+
                    s_col_NextID+" integer,"+
                    s_col_PreviousID+" integer,"+
                    s_col_Name+" text,"+
                    s_col_Status+" text,"+
                    s_col_BeCurrent+" integer"+
                    ")";


    public WNoisePresetDataNode(){
        m_Data = new WNoisePreset();
    }

    public WNoisePresetDataNode(WNoisePreset wNoisePreset){
        m_Data = wNoisePreset;
    }

    public WNoisePresetDataNode(Cursor cursor){
        m_Data = new WNoisePreset();
        FromCursor(cursor);
    }


    @Override
    public void FromCursor(Cursor cursor) {
        m_ID=m_Data.m_ID=cursor.getLong(cursor.getColumnIndex(s_col_ID));
        m_NextID=cursor.getLong(cursor.getColumnIndex(s_col_NextID));
        m_PreviousID=cursor.getLong(cursor.getColumnIndex(s_col_PreviousID));
        m_Data.m_Name=cursor.getString(cursor.getColumnIndex(s_col_Name));
        m_Data.SetStatus(cursor.getString(cursor.getColumnIndex(s_col_Status)));
        m_Data.m_BeCurrent=cursor.getInt(cursor.getColumnIndex(s_col_BeCurrent));
    }

    @Override
    public ContentValues GetContentValues() {
        ContentValues res = new ContentValues();
        res.put(s_col_NextID,m_NextID);
        res.put(s_col_PreviousID,m_PreviousID);
        res.put(s_col_Name,m_Data.m_Name);
        res.put(s_col_Status,m_Data.GetStatus());
        res.put(s_col_BeCurrent,m_Data.m_BeCurrent);
        return res;
    }

    @Override
    public String GetTableName() {
        return s_TableName;
    }
}
