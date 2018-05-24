package com.fantasticfour.elcontestproject.Calendar;

import android.media.browse.MediaBrowser;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class CalendarPlanListItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private CalendarPlanAdapter mAdapter;
    private boolean mIsOnDrag = false;
    public CalendarPlanListItemTouchHelperCallback(CalendarPlanAdapter adapter){
        mAdapter = adapter;
    }
    public void setmAdapter(CalendarPlanAdapter adapter){
        mAdapter = adapter;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder sourse, RecyclerView.ViewHolder target) {
        mAdapter.ChangePosition(sourse.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mIsOnDrag;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    public void setDragEnable(boolean isOnDrag){
        mIsOnDrag = isOnDrag;
    }
}
