package com.easylife.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easylife.activity.MainControlActivity;
import com.easylife.activity.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class RelaxChartFragment extends Fragment {
    private BarChart barChart;
    private ArrayList<BarEntry> entries;
    private ArrayList<RelaxData> relaxData;
    private BarDataSet dataSet;
    private BarData barData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        initData();
    }

    private void initData() {
        entries = new ArrayList<>(7);
        relaxData = new ArrayList<>(7);

        relaxData = MainControlActivity.relaxData;

        for (RelaxData data : relaxData) {
            entries.add(new BarEntry(data.getDay(), data.getTimeSum()));
        }

        dataSet = new BarDataSet(entries, "[x:day y:min]");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setHighLightAlpha(255);

        barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relax, null);
        barChart = view.findViewById(R.id.relax_barchart);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 6);
        xAxis.setAxisMaximum(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setMinWidth(0);

        barChart.animateY(1000);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getDescription().setEnabled(false);
        barChart.setSelected(false);
        barChart.setData(barData);
        barChart.setFitBars(true);
        return view;
    }

    public static class RelaxData {
        private int timeSum;
        private int day;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public RelaxData(int timeSum) {
            this.timeSum = timeSum;
        }

        public int getTimeSum() {
            return timeSum;
        }

        public void setTimeSum(int timeSum) {
            this.timeSum = timeSum;
        }
    }


}