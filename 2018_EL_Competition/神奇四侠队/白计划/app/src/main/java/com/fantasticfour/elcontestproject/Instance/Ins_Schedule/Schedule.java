package com.fantasticfour.elcontestproject.Instance.Ins_Schedule;

/**
 *
 * 注意:开启时默认为当时系统日期
 *     切换日期需调用ChangeDate(date)
 *
 */

import java.util.Date;
import java.util.List;

public class Schedule {
    private PlanDataDAO m_PlanDataDAO;

    public Schedule(Date date){
        m_PlanDataDAO=new PlanDataDAO(date);
    }

    /**
     * 设置日期
     * @param date
     * 为java.util.Date类型
     */
    public void ChangeDate(Date date){
        m_PlanDataDAO.ChangeDate(date);
    }

    /**
     * 获得当天任务列表
    * @return
     * 一个List
     */
    public List<Plan> GetPlanList(){
        return m_PlanDataDAO.GetDataList();
    }

    /**
     * 获得ID为id的Plan
     * @param id
     * 该Plan的id
     * @return
     * ID为id的Plan
     */
    public Plan GetPlan(long id){
        return m_PlanDataDAO.GetData(id);
    }

    /**
     * 交换两个相邻计划src1,src2的位置
     * 注意是相邻的！
     * @param src1ID
     * src1的ID
     * @param src2ID
     * src2的ID
     */
    public void SwapAdjacentPlan(long src1ID, long src2ID){
        m_PlanDataDAO.SwapAdjacentData(src1ID,src2ID);
    }

    /**
     * 移除ID为planID的Plan
     * @param planID
     * 该Plan的ID
     */
    public void RemovePlan(long planID){
        m_PlanDataDAO.DeleteData(planID);
    }

     /**
     *
     * 添加一个新的Plan
     * 并自动为其分配ID
     * @param newPlan
     * 新的Plan
     * @return
     * 返回为该Plan分配的ID
     */
    public long AddPlan(Plan newPlan){
        return m_PlanDataDAO.InsertData(newPlan);
    }

    /**
     * 更新plan
     * @param plan
     * 要更新的plan(需要被添加过)
     */
    public void UpdatePlan(Plan plan){
        m_PlanDataDAO.UpdateData(plan);
    }

    /**
     * 获得当天任务总数
     * @return
     * 当天任务数量
     */
    public int GetPlanNum(){
        return m_PlanDataDAO.GetDataNum();
    }

    /**
     * 获得当天已经完成的任务总数
     * @return
     * 当天已经完成任务的数量
     */
    public int GetCompletedPlanName(){
        return m_PlanDataDAO.GetCompletedPlanNum();
    }

    public void ReversePlanCompletedStatus(long id){
        m_PlanDataDAO.ReversePlanCompletedState(id);
    }
}

