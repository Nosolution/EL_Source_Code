package com.fantasticfour.elcontestproject.Instance.Ins_Schedule;

import android.database.Cursor;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataDAO;
import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;
import com.fantasticfour.elcontestproject.Instance.Instance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanDataDAO extends BasicDataDAO<Plan> {

    private static final String m_WhereDate=PlanDataNode.s_col_Date+"=?";
    private String[] WhereArgsDate(Date date){
        String[] res=new String[1];
        res[0]=PlanDataNode.s_DateFormat_YMD.format(date);
        return res;
    }


    public PlanDataDAO(Date date){
        super(new PlanDataNode());
        ChangeDate(date);
    }
    public void ChangeDate(Date date){
        m_VirtualData.SetVirtual();
        m_DataMap.clear();
        m_DataMap.put(m_VirtualData.m_ID,m_VirtualData);
        m_FirstData=m_VirtualData;
        Cursor cursor = Instance.s_CommonDAO.m_Database.query(m_VirtualData.GetTableName(), null, m_WhereDate, WhereArgsDate(date), null, null, null);

        if(cursor.moveToFirst()){
            do{
                PlanDataNode plan=new PlanDataNode(cursor);
                m_DataMap.put(plan.m_ID,plan);
                if(plan.m_NextID==m_VirtualData.m_ID)
                    m_VirtualData.m_PreviousID=plan.m_ID;
                if(plan.m_PreviousID==m_VirtualData.m_ID) {
                    m_VirtualData.m_NextID = plan.m_ID;
                    m_FirstData=plan;
                }
            }while(cursor.moveToNext());
            for(BasicDataNode<Plan> plan : m_DataMap.values()){
                plan.m_Next = m_DataMap.get(plan.m_NextID);
                plan.m_Previous = m_DataMap.get(plan.m_PreviousID);
            }
        }
        cursor.close();
    }

    public long InsertData(Plan plan){
        plan.m_ID = super.InsertData(new PlanDataNode(plan));
        return plan.m_ID;
    }

    public void UpdateData(Plan plan){
        BasicDataNode<Plan> planData = m_DataMap.get(plan.m_ID);
        planData.m_Data = plan;
        super.UpdateData(planData);
    }

    public void ReversePlanCompletedState(long id){
        BasicDataNode<Plan> planData = m_DataMap.get(id);
        planData.m_Data.m_Completed = 1-planData.m_Data.m_Completed;
        UpdateData(planData);
    }

    public int GetCompletedPlanNum(){
        int res = 0;
        for(BasicDataNode<Plan> plan : m_DataMap.values())
            if(plan.m_Data.m_Completed == 1)
                res++;
        return res;
    }
}
