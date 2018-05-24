package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

import android.content.Context;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataDAO;
import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;
import com.fantasticfour.elcontestproject.Instance.Instance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class WNoiseDataDAO extends BasicDataDAO<WNoise> {
    private Context m_Context;

    private void LoadWNoise() {
        Cursor cursor=Instance.s_CommonDAO.m_Database.query(m_VirtualData.GetTableName(),null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                WNoiseDataNode wNoise=new WNoiseDataNode(cursor);
                m_DataMap.put(wNoise.m_ID,wNoise);
                if(wNoise.m_NextID==m_VirtualData.m_ID)
                    m_VirtualData.m_PreviousID=wNoise.m_ID;
                if(wNoise.m_PreviousID==m_VirtualData.m_ID) {
                    m_VirtualData.m_NextID = wNoise.m_ID;
                    m_FirstData=wNoise;
                }
            }while(cursor.moveToNext());
            for(BasicDataNode<WNoise> wNoise : m_DataMap.values()){
                wNoise.m_Next = m_DataMap.get(wNoise.m_NextID);
                wNoise.m_Previous = m_DataMap.get(wNoise.m_PreviousID);
            }
        }
        else{
            try {
                InputStream is = m_Context.getAssets().open("WNoise/WNoiseList.csv");
                int size=is.available();
                byte[] buffer1 = new byte[size];
                is.read(buffer1);
                is.close();

                is = m_Context.getAssets().open("WNoise/DefaultWNoisePreset");
                size = is.available();
                byte[] buffer2 = new byte[size];
                is.read(buffer2);
                is.close();

                String text1=new String(buffer1, "utf-8");
                String text2=new String(buffer2, "utf-8");
                String[] table1=text1.split("\r\n");
                String[] table2=text2.split("\r\n");
                for(int i=0; i<table1.length; i++) {
                    String item = table1[i];
                    int volume = Integer.valueOf(table2[i+1]);
                    String[] info = item.split(",");
                    WNoise wNoise = new WNoise();
                    wNoise.m_Name=info[0];
                    wNoise.m_FileName=info[1];
                    wNoise.m_Volume=volume;
                    wNoise.m_BeBuiltIn=1;
                    InsertData(wNoise);
                }
            }catch(IOException e){e.printStackTrace();}
        }
        cursor.close();
    }

    public WNoiseDataDAO(Context context){
        super(new WNoiseDataNode());
        m_Context = context;
        LoadWNoise();
    }

    public long InsertData(WNoise wNoise){
        wNoise.m_ID = super.InsertData(new WNoiseDataNode(wNoise));
        return wNoise.m_ID;
    }

    public void UpdateData(WNoise wNoise){
        BasicDataNode<WNoise> wNoiseData = m_DataMap.get(wNoise.m_ID);
        wNoiseData.m_Data = wNoise;
        super.UpdateData(wNoiseData);
    }

    public void ResetWNoiseVolume(){
        for (BasicDataNode<WNoise> wNoise : m_DataMap.values()) {
            wNoise.m_Data.m_Volume = 0;
            UpdateData(wNoise);
        }
    }

    public void SetOrder(List<Long> newOrder){
        Set<Long> orderedID = new TreeSet<>();
        orderedID.add(m_VirtualData.m_ID);
        BasicDataNode<WNoise> pre = m_VirtualData;
        for(Long id : newOrder){
            BasicDataNode<WNoise> p = m_DataMap.get(id);
            if(m_DataMap.get(id)==null)continue;
            LinkedData_Link(pre, p);
            orderedID.add(p.m_ID);
            pre = p;
        }
        for(Map.Entry<Long, BasicDataNode<WNoise>> entry : m_DataMap.entrySet()){
            if(orderedID.contains(entry.getKey()))continue;
            BasicDataNode<WNoise> p = entry.getValue();
            LinkedData_Link(pre, p);
            pre = p;
        }
        LinkedData_Link(pre, m_VirtualData);
    }

    public void SetWNoiseVolume(long id, int volume){
        BasicDataNode<WNoise> wNoiseData = m_DataMap.get(id);
        wNoiseData.m_Data.m_Volume=volume;
        UpdateData(wNoiseData);
    }

    public void SetWNoiseName(long id, String name){
        BasicDataNode<WNoise> wNoiseData = m_DataMap.get(id);
        wNoiseData.m_Data.m_Name=name;
        UpdateData(wNoiseData);
    }

    public void SetWNoiseVolume(Map<Long, Integer> volumeMap){
        ResetWNoiseVolume();
        for(Map.Entry<Long, Integer> entry : volumeMap.entrySet()) {
            long id = entry.getKey();
            int volume = entry.getValue();
            if(m_DataMap.get(id)==null) continue;
            SetWNoiseVolume(id, volume);
        }
    }
}
