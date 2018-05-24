package com.fantasticfour.elcontestproject.Schedule;

/**
 * Created by GuoFengyuan on 2018/5/15.
 */

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Plan;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.Collections;
import java.util.List;

import android.widget.Button;

public class SchedulePlanListAdapter extends RecyclerView.Adapter<SchedulePlanListAdapter.ViewHolder>{
    private ScheduleActivity m_Context;
    private boolean m_deleteMode=false;
    private List<Plan> m_PlanList;
    private SchedulePlanListTouchHelperCallback m_Schedule_PlanListTouchHelperCallback;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameview;
        private Button m_btn_Right;
        public ViewHolder(View view){
            super(view);
            nameview=view.findViewById(R.id.plan_name_textView);
            m_btn_Right=view.findViewById(R.id.planitem_btn_drag);
        }
    }
    @Override
    public int getItemCount(){
        return m_PlanList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item_activity_main_plan_list,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    abstract class PositionOnClickListener implements View.OnClickListener{
        int m_Position;
        public PositionOnClickListener(int position){
            m_Position = position;
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position){
        Plan plan=m_PlanList.get(position);
        holder.itemView.setOnClickListener(new PositionOnClickListener(position){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(m_Context,ScheduleAddPlanActivity.class);
                intent.putExtra("requestCode", ScheduleActivity.s_EditPlanRequestCode);
                intent.putExtra("plan_position", m_Position);
                intent.putExtra("source_plan", m_PlanList.get(m_Position));
                m_Context.startActivityForResult(intent,ScheduleActivity.s_EditPlanRequestCode);
            }
        });
        if (!m_deleteMode)
            holder.m_btn_Right.setBackground(m_Context.getDrawable(R.drawable.triple_line_black));
        else
            holder.m_btn_Right.setBackground(m_Context.getDrawable(R.drawable.trash_can_red));
        holder.m_btn_Right.setOnClickListener(new PositionOnClickListener(position){
            @Override
            public void onClick(View view){
                if(m_deleteMode)
                    Delete(position);
            }
        });
        holder.nameview.setText(plan.m_Description);
        holder.itemView.setTag(plan.m_ID);
    }
    public void SetItemTouchHelperCallback(SchedulePlanListTouchHelperCallback mSchedulePlanListTouchHelperCallback){
        m_Schedule_PlanListTouchHelperCallback = mSchedulePlanListTouchHelperCallback;
    }

    public void ChangePosition(int sourcePos, int targetPos){
        Collections.swap(m_PlanList,sourcePos,targetPos);
        Instance.s_Schedule.SwapAdjacentPlan(m_PlanList.get(sourcePos).m_ID, m_PlanList.get(targetPos).m_ID);
        notifyItemMoved(sourcePos, targetPos);
    }

    public void Delete(int sourcePos){
        Instance.s_Schedule.RemovePlan(m_PlanList.get(sourcePos).m_ID);
        m_PlanList.remove(sourcePos);
        notifyItemRemoved(sourcePos);
        notifyItemRangeChanged(sourcePos, m_PlanList.size()-sourcePos);
        m_Context.CheckPlanNumAddFirstPlan();
    }
    public SchedulePlanListAdapter(ScheduleActivity context, List<Plan> pList){
        m_Context = context;
        m_PlanList=pList;
    }

    public void changeMode(){
        m_deleteMode = ! m_deleteMode;
        notifyDataSetChanged();
        m_Schedule_PlanListTouchHelperCallback.SetBeDragEnabled(!m_deleteMode);
    }

    public void RefreshPlanList(List<Plan> planList){
        m_PlanList = planList;
        notifyDataSetChanged();
    }
}