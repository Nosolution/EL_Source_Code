package com.example.user.simpleclock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecordFocusAdapter extends RecyclerView.Adapter<RecordFocusAdapter.ViewHolder> {

    /*
     *
     * RecyclerView的设置;
     *
     */

    private List<FocusRecord> mFocusRecord;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView focusRecordDate;

        TextView focusRecordTime;

        public ViewHolder(View view) {
            super(view);
            focusRecordDate = (TextView) view.findViewById(R.id.focus_record_date);
            focusRecordTime = (TextView) view.findViewById(R.id.focus_record_time);
        }
    }

    public RecordFocusAdapter(List<FocusRecord> recordList) {
        mFocusRecord = recordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_focus, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FocusRecord focusRecord = mFocusRecord.get(position);
        holder.focusRecordDate.setText(focusRecord.getRecordDate());
        holder.focusRecordTime.setText(focusRecord.getRecordTime());


    }

    @Override
    public int getItemCount() {
        return mFocusRecord.size();
    }

}
