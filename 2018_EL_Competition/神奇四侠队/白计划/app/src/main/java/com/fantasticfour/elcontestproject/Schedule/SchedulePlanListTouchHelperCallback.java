package com.fantasticfour.elcontestproject.Schedule;

/**
 * Created by GuoFengyuan on 2018/5/22.
 */
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class SchedulePlanListTouchHelperCallback extends ItemTouchHelper.Callback {
    private SchedulePlanListAdapter m_Adapter;
    private boolean m_BeDragEnabled = true;
    public SchedulePlanListTouchHelperCallback(SchedulePlanListAdapter adapter){
        m_Adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView rv, RecyclerView.ViewHolder holder){
        int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target){
        m_Adapter.ChangePosition(source.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder source, int direction){
        m_Adapter.Delete(source.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled(){
        return m_BeDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled(){
        return false;
    }

    public void SetBeDragEnabled(boolean beDragEnabled){
        m_BeDragEnabled = beDragEnabled;
    }
}
