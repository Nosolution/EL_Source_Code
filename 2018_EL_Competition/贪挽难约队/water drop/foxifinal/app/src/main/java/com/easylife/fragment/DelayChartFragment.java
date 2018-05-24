package com.easylife.fragment;

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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DelayChartFragment extends Fragment {
    private PieChart pieChart;
    private ArrayList<PieEntry> entries;
    private ArrayList<DelayData> delayData;
    private PieDataSet dataSet;
    private PieData pieData;
    private Legend legend;
    private static final String[] tasks = new String[]{"待办事务", "已完成事务", "已逾期事务"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        initData();
    }

    private void initData() {
        entries = new ArrayList<>(3);
        delayData = MainControlActivity.delayData;

        for (int i = 0, delayDataSize = delayData.size(); i < delayDataSize; i++) {
            DelayData data = delayData.get(i);
            entries.add(new PieEntry(data.getTimes(), tasks[i] + "(" + data.getTimes() + ")"));
        }

        dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(2f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        pieData = new PieData(dataSet);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delay, null);
        TextView textView = view.findViewById(R.id.see_more_delay_data_textview);
        int taskSum = delayData.get(0).getTimes() + delayData.get(1).getTimes() + delayData.get(2).getTimes();
        if (taskSum == 0) {
            taskSum = Integer.MAX_VALUE;
        }
        textView.setText("事务完成率:" + String.format("%.2f", 100.0 * delayData.get(1).getTimes() / taskSum) + "% 事务拖延率:" + String.format("%.2f", 100.0 * delayData.get(2).getTimes() / taskSum) + "%");
        pieChart = view.findViewById(R.id.delay_piechart);
        pieChartStyling(pieChart);
        pieChart.setData(pieData);
        return view;
    }

    private void pieChartStyling(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.getDescription().setEnabled(false);
        legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

    public static class DelayData {
        private int times;

        public DelayData(int times) {
            this.times = times;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }
}