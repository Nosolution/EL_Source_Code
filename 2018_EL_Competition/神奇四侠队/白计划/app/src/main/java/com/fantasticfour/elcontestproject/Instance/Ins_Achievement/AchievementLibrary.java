package com.fantasticfour.elcontestproject.Instance.Ins_Achievement;

import android.content.Context;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AchievementLibrary {
    private AchievementDataDAO m_AchievementDataDAO;

    public AchievementLibrary(Context context){
        m_AchievementDataDAO = new AchievementDataDAO(context);
    }
    public Map<Long, Achievement> GetAchievedAchievementMap(){
        return m_AchievementDataDAO.GetDataMap();
    }
    public List<Achievement> GetAchievedAchievementList(){
        return m_AchievementDataDAO.GetDataList();
    }
    private void Achieve(long id){
        Achievement achievement = m_AchievementDataDAO.GetData(id);
        achievement.m_BeAchieved = 1;
        achievement.m_Time = new Date();
        m_AchievementDataDAO.UpdateData(achievement);
    }
    public void CompleteFocusing(int seconds){
        int minute = seconds/60;
        if(seconds <= 5)
            Achieve(1);
        if(4<=minute && minute<=6)
            Achieve(0);
    }
}
