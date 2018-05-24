package com.hlxx.climber.services;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.hlxx.climber.R;
import com.hlxx.climber.dataTables.Comments;
import com.hlxx.climber.thirdpage.StoneActivity;

public class CommentItemAdapter  extends ArrayAdapter<Comments>{
    Context mContext;
    int mLayoutResourceId;

    public CommentItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final Comments currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.row_list_comment, parent, false);
        }
        row.setTag(currentItem);
        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
        checkBox.setText(currentItem.getText());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (checkBox.isChecked()) {
                    checkBox.setEnabled(false);
                    if (mContext instanceof StoneActivity) {
                        StoneActivity activity = (StoneActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });
        return row;
    }
}
