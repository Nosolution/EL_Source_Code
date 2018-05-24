package com.example.user.simpleclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordFocusActivity extends AppCompatActivity {

    /*
     *
     * 显示专注时间的记录，使用RecyclerView;
     *
     */

    private final String totalRecord = "共foXus             ";
    private final String latestRecord = "最近一次foXus    ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record_focus);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent = getIntent();

        ImageButton backButton = (ImageButton) findViewById(R.id.record_focus_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView totalFocus = (TextView) findViewById(R.id.focus_total_min);
        totalFocus.setText(totalRecord + getTotalMinute() + "分钟");
        TextView latestFocus = (TextView) findViewById(R.id.latest_focus);
        if (getLatestDate().equals("$")) {
            latestFocus.setText("你还没有专注过哦！");
        } else {
            latestFocus.setText(latestRecord + getLatestDate());
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.record_focus_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecordFocusAdapter recordFocusAdapter = new RecordFocusAdapter(FocusRecord.getDataFromDatabase());
        recyclerView.setAdapter(recordFocusAdapter);
    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RecordFocusActivity.class);
        context.startActivity(intent);

    }

    private int getTotalMinute() {
        int total = 0;
        List<RecordTime> recordTimes = DataSupport.findAll(RecordTime.class);

        for (RecordTime rt : recordTimes) {
            total += rt.getTargetMinute();
        }

        return total;
    }

    private String getLatestDate() {
        List<RecordTime> recordTimes = DataSupport.findAll(RecordTime.class);
        if (recordTimes.size() != 0) {
            long time = DataSupport.findLast(RecordTime.class).getEndTime();
            Date mDate = new Date(time);
            String temp1 = "";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat1 = new SimpleDateFormat("HH:mm");
            String temp2 = dateformat1.format(mDate);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat2 = new SimpleDateFormat("MM月dd日 HH:mm");
            String temp3 = dateformat2.format(mDate);
            Calendar latestTime = Calendar.getInstance();
            latestTime.setTime(mDate);
            Calendar now = Calendar.getInstance();
            now.setTime(new Date(System.currentTimeMillis()));
            if (latestTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                temp1 = "今天 ";
                return (temp1 + temp2);
            } else if (latestTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) - 1) {
                temp1 = "昨天 ";
                return (temp1 + temp2);
            } else {
                return temp3;
            }
        } else {
            return "$";
        }
    }
}
