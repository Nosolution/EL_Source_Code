package com.easylife.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylife.activity.MainControlActivity;
import com.easylife.activity.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class FocusChartFragment extends Fragment {
    public int focusTimesTotal = 0;
    public int focusTimesSuccess = 0;
    private List<FocusData> readingDataList = new ArrayList<>(7);
    private List<FocusData> workingDataList = new ArrayList<>(7);
    private LineChart lineChart;
    private List<Entry> readingEntries = new ArrayList<>();
    private List<Entry> workingEntries = new ArrayList<>();
    private LineDataSet readingDataSet;
    private LineDataSet workingDataSet;
    private LineData focusData;
    private List<ILineDataSet> dataSets = new ArrayList<>();
    private TextView focusDegree;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        initData();

    }

    private void initData() {
        if (readingDataList.size() != 0 || workingDataList.size() != 0) {
            return;
        }

        readingDataList = MainControlActivity.readingDataList;
        workingDataList = MainControlActivity.workingDataList;
        focusTimesTotal = MainControlActivity.focusTimesTotal;
        focusTimesSuccess = MainControlActivity.focusTimesSuccess;

        for (FocusData data : readingDataList) {
            readingEntries.add(new Entry(data.getDay(), data.getFocusTime() / 1000 / 60));
        }

        for (FocusData data : workingDataList) {
            workingEntries.add(new Entry(data.getDay(), data.getFocusTime() / 1000 / 60));
        }

        readingDataSet = new LineDataSet(readingEntries, "阅读时长");
        readingDataSet.setLineWidth(3.0f);
        readingDataSet.setCircleRadius(5.0f);
        readingDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        readingDataSet.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        readingDataSet.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);

        workingDataSet = new LineDataSet(workingEntries, "学习时长");
        workingDataSet.setLineWidth(3.0f);
        workingDataSet.setCircleRadius(5.0f);
        workingDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        workingDataSet.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        workingDataSet.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);

        dataSets.add(readingDataSet);
        dataSets.add(workingDataSet);

        focusData = new LineData(dataSets);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus, null);
        focusDegree = view.findViewById(R.id.see_more_focus_data_textview);
        lineChart = view.findViewById(R.id.focus_linechart);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setMinWidth(0);

        lineChartStyling(lineChart);
        lineChart.setData(focusData);
        lineChart.invalidate();
        String degreeToShow = "专注度：" + String.format("%.2f", focusTimesSuccess * 1.0 / focusTimesTotal * 100) + "%  ";
        if ((int) (focusTimesSuccess * 1.0 / focusTimesTotal * 100) >= 90) {
            degreeToShow += "优秀大佬";
        } else if ((int) (focusTimesSuccess * 1.0 / focusTimesTotal * 100) >= 70) {
            degreeToShow += "轻度患者";
        } else if ((int) (focusTimesSuccess * 1.0 / focusTimesTotal * 100) >= 40) {
            degreeToShow += "中度患者";
        } else if ((int) (focusTimesSuccess * 1.0 / focusTimesTotal * 100) >= 10) {
            degreeToShow += "重度患者";
        } else {
            degreeToShow += "无药可救";
        }
        focusDegree.setText(degreeToShow);
        return view;
    }

    //lineChart的风格设置
    private void lineChartStyling(LineChart lineChart) {
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setText("[x:day y:min]");
        lineChart.setDrawBorders(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.animateXY(1000, 1000);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static class FocusData {
        private int day;
        private int focusTime;

        public FocusData(int day, int focusTime) {
            this.day = day;
            this.focusTime = focusTime;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getFocusTime() {
            return focusTime;
        }

        public void setFocusTime(int focusTime) {
            this.focusTime = focusTime;
        }
    }
}