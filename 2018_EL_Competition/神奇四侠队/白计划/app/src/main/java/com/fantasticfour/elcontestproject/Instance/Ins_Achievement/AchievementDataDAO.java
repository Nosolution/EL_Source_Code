package com.fantasticfour.elcontestproject.Instance.Ins_Achievement;

import android.content.Context;
import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataDAO;
import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;
import com.fantasticfour.elcontestproject.Instance.Instance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class AchievementDataDAO extends BasicDataDAO<Achievement> {
    private Context m_Context;

    public AchievementDataDAO(Context context){
        super(new AchievementDataNode());
        m_Context = context;
        LoadAchievement();
    }

    private void LoadAchievement(){
        Cursor cursor = Instance.s_CommonDAO.m_Database.query(m_VirtualData.GetTableName(),null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                AchievementDataNode achievement=new AchievementDataNode(cursor);
                m_DataMap.put(achievement.m_ID,achievement);
                if(achievement.m_NextID==m_VirtualData.m_ID)
                    m_VirtualData.m_PreviousID=achievement.m_ID;
                if(achievement.m_PreviousID==m_VirtualData.m_ID) {
                    m_VirtualData.m_NextID = achievement.m_ID;
                    m_FirstData=achievement;
                }
            }while(cursor.moveToNext());
            for(BasicDataNode<Achievement> achievement : m_DataMap.values()){
                achievement.m_Next = m_DataMap.get(achievement.m_NextID);
                achievement.m_Previous = m_DataMap.get(achievement.m_PreviousID);
            }
        }
        else{
            try {
                InputStream is = m_Context.getAssets().open("Achievement/AchievementList.csv");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);

                String text = new String(buffer, "utf-8");
                String[] table = text.split("\r\n");
                for(String line : table) {
                    String[] info = line.split(",");
                    Achievement achievement = new Achievement();
                    achievement.m_Name = info[0];
                    achievement.m_Description = info[1];
                    achievement.m_ImgFileName = info[2];
                    achievement.m_BeAchieved = 0;
                    achievement.m_Time = new Date();
                    InsertData(achievement);
                }
            }catch (IOException e){e.printStackTrace();}
        }
        cursor.close();
    }

    public long InsertData(Achievement achievement){
        achievement.m_ID = super.InsertData(new AchievementDataNode(achievement));
        return achievement.m_ID;
    }

    public void UpdateData(Achievement achievement){
        BasicDataNode<Achievement> achievementData = m_DataMap.get(achievement.m_ID);
        achievementData.m_Data = achievement;
        super.UpdateData(achievementData);
    }
}
