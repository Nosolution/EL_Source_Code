package com.example.user.simpleclock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecordDiaryAdapter extends RecyclerView.Adapter<RecordDiaryAdapter.ViewHolder> {

    private List<DiaryRecord> mDiaryRecord;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mDiaryView;

        TextView diaryDate;

        TextView diaryTitle;

        public ViewHolder(View view) {
            super(view);
            mDiaryView = view;
            diaryDate = (TextView) view.findViewById(R.id.diary_date);
            diaryTitle = (TextView) view.findViewById(R.id.diary_title);
        }
    }

    public RecordDiaryAdapter(List<DiaryRecord> recordList) {
        mDiaryRecord = recordList;
    }

    @Override
    public RecordDiaryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_diary, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.mDiaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DiaryRecord diaryRecord = mDiaryRecord.get(position);
                DiaryViewActivity.actionStart(parent.getContext(), diaryRecord.getDiaryId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordDiaryAdapter.ViewHolder holder, int position) {
        DiaryRecord diaryRecord = mDiaryRecord.get(position);
        holder.diaryDate.setText(diaryRecord.getDiaryDate());
        holder.diaryTitle.setText(diaryRecord.getDiaryTitle());


    }

    @Override
    public int getItemCount() {
        return mDiaryRecord.size();
    }
}
