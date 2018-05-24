package com.fantasticfour.elcontestproject.WNoise;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by 12509 on 2018/5/11.
 */

public class WNoiseTouchHelperCallback extends ItemTouchHelper.Callback {
    private WNoiseAdapter mAdapter;
    private boolean mBeDragEnabled = false;
    private boolean mBeSwipeEnabled = false;

    public WNoiseTouchHelperCallback(WNoiseAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target){
        mAdapter.ChangePosition(source.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder source, int direction){
    }

    @Override
    public int getMovementFlags(RecyclerView rv, RecyclerView.ViewHolder holder){
        int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled(){
        return mBeDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mBeSwipeEnabled;
    }

    public void SetBeSwipeEnabled(boolean beSwipeEnabled){
        mBeSwipeEnabled = beSwipeEnabled;
    }

    public void SetBeDragEnabled(boolean beDragEnabled){
        mBeDragEnabled = beDragEnabled;
    }
}
