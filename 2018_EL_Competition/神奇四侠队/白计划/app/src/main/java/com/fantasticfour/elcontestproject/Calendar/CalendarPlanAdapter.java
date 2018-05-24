package com.fantasticfour.elcontestproject.Calendar;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Plan;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by 58494 on 2018/5/10.
 */

public class CalendarPlanAdapter extends RecyclerView.Adapter<CalendarPlanAdapter.ViewHolder> {
    private List<Plan> mPlanList;
    public boolean mIsEditEnable = false;
    private CalendarPlanListItemTouchHelperCallback mCalendarPlanListItemTouchHelperCallback;
    CalendarPlanAdapterAttachActivity mActivity;
    interface CalendarPlanAdapterAttachActivity{
        void initWeekChart();
        Drawable getDrawableForAdapter(int id);
        void itemOnClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View planView;
        CheckBox isCompleted;
        TextView name;
        Button buttonDrag;
        public ViewHolder(View view){
            super(view);
            planView = view;
            isCompleted = view.findViewById(R.id.calendar_planlist_checkbox);
            name = view.findViewById(R.id.calendar_planlist_planname);
            buttonDrag = view.findViewById(R.id.calendar_button_drag);
        }
        private void SetButtonDragOnClickListener(int position){
            buttonDrag.setOnTouchListener(new ButtonDragOnTouchListener(position));
        }
    }
    public class ButtonDragOnTouchListener implements View.OnTouchListener{
        private int mPosition;
        public ButtonDragOnTouchListener(int position){
            mPosition = position;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(v.getId() == R.id.calendar_button_drag){
                switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mCalendarPlanListItemTouchHelperCallback.setDragEnable(true);
                    break;
                case MotionEvent.ACTION_UP:
                    mCalendarPlanListItemTouchHelperCallback.setDragEnable(false);
                    break;
                default:
                    break;
                }
            }
            return false;
        }
    }
    public CalendarPlanAdapter(List<Plan> planList, CalendarPlanAdapterAttachActivity activity){
        this.mPlanList = planList;
        mActivity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_weeklist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        final Plan plan = mPlanList.get(position);
        holder.name.setText(plan.m_Description);
        if(plan.m_Completed == 1){
            holder.isCompleted.setChecked(true);
        }else {
            holder.isCompleted.setChecked(false);
        }
        if(mIsEditEnable){
            holder.buttonDrag.setBackground(mActivity.getDrawableForAdapter(R.drawable.trash_can_red));
        }else {
            holder.buttonDrag.setBackground(mActivity.getDrawableForAdapter(R.drawable.triple_line_black));
        }
        holder.isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isCompleted.isChecked()){
                    plan.m_Completed = 1;
                }else {
                    plan.m_Completed = 0;
                }
                Instance.s_Schedule.UpdatePlan(plan);
                mActivity.initWeekChart();
            }
        });
        holder.SetButtonDragOnClickListener(position);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.itemOnClick(position);
            }
        });
        holder.buttonDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsEditEnable){
                    mPlanList.remove(plan);
                    Instance.s_Schedule.RemovePlan(plan.m_ID);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mPlanList.size()-position);
                    mActivity.initWeekChart();
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return mPlanList.size();
    }

    public void setmCalendarPlanListItemTouchHelperCallback(CalendarPlanListItemTouchHelperCallback mCalendarPlanListItemTouchHelperCallback) {
        this.mCalendarPlanListItemTouchHelperCallback = mCalendarPlanListItemTouchHelperCallback;
    }

    public void ChangePosition(int sourcePos, int targetPos){
        Collections.swap(mPlanList, sourcePos, targetPos);
        Instance.s_Schedule.SwapAdjacentPlan(mPlanList.get(sourcePos).m_ID, mPlanList.get(targetPos).m_ID);
        notifyItemMoved(sourcePos, targetPos);
    }
    public void resetMPlanList(List<Plan> planList){
        mPlanList = planList;
    }
}
