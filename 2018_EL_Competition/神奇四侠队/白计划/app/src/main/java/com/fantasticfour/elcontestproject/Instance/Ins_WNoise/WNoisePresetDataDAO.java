package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

import android.content.Context;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataDAO;
import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;
import com.fantasticfour.elcontestproject.Instance.Instance;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WNoisePresetDataDAO extends BasicDataDAO<WNoisePreset> {
    private Context m_Context;
    private BasicDataNode<WNoisePreset> m_CurrentWNoisePreset;
    private WNoiseDataDAO m_WNoiseDAO;

    private void LoadAllWNoisePreset(){
        Cursor cursor=Instance.s_CommonDAO.m_Database.query(m_VirtualData.GetTableName(),null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                WNoisePresetDataNode wNoisePreset=new WNoisePresetDataNode(cursor);
                m_DataMap.put(wNoisePreset.m_ID,wNoisePreset);
                if(wNoisePreset.m_NextID==m_VirtualData.m_ID)
                    m_VirtualData.m_PreviousID=wNoisePreset.m_ID;
                if(wNoisePreset.m_PreviousID==m_VirtualData.m_ID) {
                    m_VirtualData.m_NextID = wNoisePreset.m_ID;
                    m_FirstData=wNoisePreset;
                }
            } while(cursor.moveToNext());
            for(BasicDataNode<WNoisePreset> wNoisePreset : m_DataMap.values()){
                wNoisePreset.m_Next = m_DataMap.get(wNoisePreset.m_NextID);
                wNoisePreset.m_Previous = m_DataMap.get(wNoisePreset.m_PreviousID);
                if(wNoisePreset.m_Data.m_BeCurrent == 1)
                    m_CurrentWNoisePreset = wNoisePreset;
            }
        }
        else{
            try {
                InputStream is = m_Context.getAssets().open("WNoise/DefaultWNoisePreset");
                int size=is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                String text=new String(buffer,"utf-8");
                String[] table=text.split("\r\n");
                String name = table[0];
                SaveAsNewPreset(m_WNoiseDAO.GetDataList(), name);
            }catch(IOException e){e.printStackTrace();}
        }
        cursor.close();
    }

    public WNoisePresetDataDAO(Context context, WNoiseDataDAO wNoiseDAO){
        super(new WNoisePresetDataNode());
        m_Context = context;
        m_WNoiseDAO = wNoiseDAO;
        LoadAllWNoisePreset();
    }

    public void SetCurrentPreset(long id){
        if(m_CurrentWNoisePreset != null) {
            m_CurrentWNoisePreset.m_Data.m_BeCurrent = 0;
            UpdateData(m_CurrentWNoisePreset);
        }
        m_CurrentWNoisePreset = m_DataMap.get(id);
        m_CurrentWNoisePreset.m_Data.m_BeCurrent = 1;
        UpdateData(m_CurrentWNoisePreset);
    }

    public WNoisePreset GetWNoisePreset(long id){
        return m_DataMap.get(id).m_Data;
    }
    public List<WNoisePreset> GetWNoisePresetList(){
        ArrayList<WNoisePreset> res=new ArrayList<>();
        for(BasicDataNode<WNoisePreset> p=m_FirstData;!p.BeVirtual();p=p.m_Next)
            res.add(p.m_Data);
        return res;
    }

    public long SaveAsNewPreset(List<WNoise> wNoises, String name){
        WNoisePreset preset = new WNoisePreset();
        preset.SetWNoiseByList(wNoises, name);
        WNoisePresetDataNode presetNode = new WNoisePresetDataNode();
        presetNode.m_Data = preset;
        preset.m_ID = InsertData(presetNode);
        SetCurrentPreset(preset.m_ID);
        return preset.m_ID;
    }

    public void SaveCurrentPreset(List<WNoise> wNoises){
        WNoisePreset preset = new WNoisePreset();
        preset.SetWNoiseByList(wNoises, m_CurrentWNoisePreset.m_Data.m_Name);
        m_CurrentWNoisePreset.m_Data = preset;
        UpdateData(m_CurrentWNoisePreset);
    }

    public WNoisePreset GetCurrentWNoisePreset(){
        return m_CurrentWNoisePreset.m_Data;
    }
}
