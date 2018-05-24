package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

import com.fantasticfour.elcontestproject.Instance.Tool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WNoisePreset {
    public long m_ID;
    public String m_Name;
    public int m_BeCurrent;

    private Map<Long, Integer> m_WNoiseVolumeMap = new TreeMap<>();
    private Map<Long, Integer> m_WNoiseOrderMap = new TreeMap<>();
    private List<Long> m_WNoiseIDList=new LinkedList<>();

    WNoisePreset(){m_ID = -1;}
    public void SetWNoiseByList(List<WNoise> wNoiseList, String name){
        m_Name = name;
        int cnt = 0;
        for(WNoise wNoise : wNoiseList) {
            m_WNoiseIDList.add(wNoise.m_ID);
            m_WNoiseVolumeMap.put(wNoise.m_ID, wNoise.m_Volume);
            m_WNoiseOrderMap.put(wNoise.m_ID, cnt++);
        }
    }

    private static final String s_SplitMark1 = Tool.AsciiToStr(1);
    private static final String s_SplitMark2 = Tool.AsciiToStr(2);
    public void SetStatus(String status){
        String[] strArray=status.split(s_SplitMark1);
        for(String a : strArray){
            String[] temp=a.split(s_SplitMark2);
            m_WNoiseVolumeMap.put(Long.parseLong(temp[0]),Integer.parseInt(temp[1]));
            m_WNoiseIDList.add(Long.parseLong(temp[0]));
        }
    }
    public String GetStatus(){
        StringBuilder res=new StringBuilder();
        for(Long id : m_WNoiseIDList)
            res.append(id).append(s_SplitMark2).append(m_WNoiseVolumeMap.get(id)).append(s_SplitMark1);
        return res.toString();
    }

    public Map<Long,Integer> GetWNoiseVolumeMap() {
        return m_WNoiseVolumeMap;
    }

    public List<Long> GetWNoiseIDList(){
        return m_WNoiseIDList;
    }
}
