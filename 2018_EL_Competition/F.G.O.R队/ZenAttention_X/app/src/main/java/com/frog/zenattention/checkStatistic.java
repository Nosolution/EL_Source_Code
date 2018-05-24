package com.frog.zenattention;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.frog.zenattention.utils.AttentionTimeData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class checkStatistic extends AppCompatActivity {
    private String[] week;
    private String[] month;
    private BarChart barChart_week;
    private BarChart barChart_month;

    private static final String TAG = "checkStatistic";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutPageButton:
                startActivity(new Intent(checkStatistic.this, aboutPageActivity.class));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_page, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));          // 设置底部导航栏的颜色
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));             // 设置状态栏的颜色
        setContentView(R.layout.activity_check_statistic);
        week = new String[]{getString(R.string.Sunday)
                , getString(R.string.Monday), getString(R.string.Tuesday)
                , getString(R.string.Wednesday), getString(R.string.Thursday)
                , getString(R.string.Friday), getString(R.string.Saturday)};
        month = new String[]{getString(R.string.Jan)
                , getString(R.string.Feb), getString(R.string.Mar)
                , getString(R.string.Apr), getString(R.string.May)
                , getString(R.string.Jun), getString(R.string.Jul)
                , getString(R.string.Aug), getString(R.string.Sep)
                , getString(R.string.Oct), getString(R.string.Nov)
                , getString(R.string.Dec)};

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.checkStatisticBar);
        toolbar.inflateMenu(R.menu.about_page);
        setSupportActionBar(toolbar);

        ScreenAdapterTools.getInstance().loadView((ViewGroup) getWindow().getDecorView());

        barChart_week = findViewById(R.id.chart_week);
        drawWeekChart();
        barChart_month = findViewById(R.id.chart_month);
        barChart_month.setVisibility(View.INVISIBLE);

        RadioGroup radioGroup = findViewById(R.id.radioButtons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonWeek:
                        drawWeekChart();
                        barChart_week.setVisibility(View.VISIBLE);
                        barChart_month.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.radioButtonMonth:
                        drawMonthChart();
                        barChart_month.setVisibility(View.VISIBLE);
                        barChart_week.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void drawWeekChart() {
        List<BarEntry> entries = new ArrayList<>();
        final String[] quarters = new String[7];
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            String dateInWeek = getDayInWeek(c);
            quarters[6 - i] = dateInWeek;
            String dateInString = getDateInString(c);
            long getTime = AttentionTimeData.getTimeWeek(dateInString, checkStatistic.this);
            float timeInFloat = (float) (getTime / 1000 / 60);
            entries.add(new BarEntry(6 - i, timeInFloat));
            c.add(Calendar.DAY_OF_MONTH, -1);
        }

        quarters[6] = getString(R.string.today);

        BarDataSet set = new BarDataSet(entries, getString(R.string.focus_time));


        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {        // 将x轴设为周一到周日
                return quarters[(int) value];
            }
        };

        set.setValueFormatter(new IValueFormatter() {          // 将柱状图数据设为整数
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;
                return n + "";
            }
        });
        set.setValueTextSize(15f);         // 设置柱状图上方的值的字体
        set.setColors(new int[]{getResources().getColor(R.color.colorPrimary)});


        BarData data = new BarData(set);
        data.setBarWidth(0.9f);
        barChart_week.setData(data);

        barChart_week.setFitBars(true);
        barChart_week.setNoDataText(getString(R.string.have_not_start_attention));
        barChart_week.animateY(2000, Easing.EasingOption.EaseInOutCubic);   // 设置y轴动画
        barChart_week.getDescription().setEnabled(false);       // 将description label去掉

        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/bamboooooo.ttf");

        XAxis xAxis = barChart_week.getXAxis();
        xAxis.setDrawGridLines(false);           // 去掉网格线
        xAxis.setTextSize(15f);                // 设置x轴的字体大小
        xAxis.setTypeface(tf);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(formatter);    // 将x轴设为周一到周日

        YAxis yLAxis = barChart_week.getAxisLeft();
        yLAxis.setAxisMinimum(0f);
        yLAxis.setEnabled(false);

        YAxis yRAxis = barChart_week.getAxisRight();
        yRAxis.setAxisMinimum(0f);
        yRAxis.setEnabled(false);

        Legend legend = barChart_week.getLegend();
        legend.setTextSize(15f);  // 设置标签的字体大小
        legend.setTypeface(tf);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        barChart_week.invalidate();
    }

    private void drawMonthChart() {
        List<BarEntry> entries = new ArrayList<>();
        final String[] quarters = new String[8];
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < 8; i++) {
            int monthI = c.get(Calendar.MONTH);
            String dateInWeek = month[monthI];
            quarters[7 - i] = dateInWeek;
            long getTime = AttentionTimeData.getTimeMonth(Integer.toString(monthI + 1), checkStatistic.this);
            float timeInFloat = (float) (getTime / 1000 / 60);
            entries.add(new BarEntry(7 - i, timeInFloat));
            c.add(Calendar.MONTH, -1);
        }

        quarters[7] = getString(R.string.this_month);

        BarDataSet set = new BarDataSet(entries, getString(R.string.focus_time));

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {        // 将x轴设为一月到十二月
                return quarters[(int) value];
            }
        };

        set.setValueFormatter(new IValueFormatter() {          // 将柱状图数据设为整数
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;
                return n + "";
            }
        });

        set.setValueTextSize(15f);         // 设置柱状图上方的值的字体
        set.setColors(new int[]{getResources().getColor(R.color.colorPrimary)});
        BarData data = new BarData(set);
        data.setBarWidth(0.9f);
        barChart_month.setData(data);

        barChart_month.setFitBars(true);
        barChart_month.setNoDataText(getString(R.string.have_not_start_attention));
        barChart_month.animateY(2000, Easing.EasingOption.EaseInOutCubic);   // 设置y轴动画
        barChart_month.getDescription().setEnabled(false);       // 将description label去掉

        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/bamboooooo.ttf");

        XAxis xAxis = barChart_month.getXAxis();
        xAxis.setDrawGridLines(false);           // 去掉网格线
        xAxis.setTextSize(15f);                // 设置x轴的字体大小
        xAxis.setTypeface(tf);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(formatter);    // 将x轴设为一月到十二月

        YAxis yLAxis = barChart_month.getAxisLeft();
        yLAxis.setAxisMinimum(0f);
        yLAxis.setEnabled(false);

        YAxis yRAxis = barChart_month.getAxisRight();
        yRAxis.setAxisMinimum(0f);
        yRAxis.setEnabled(false);

        Legend legend = barChart_month.getLegend();
        legend.setTextSize(15f);  // 设置标签的字体大小
        legend.setTypeface(tf);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        barChart_month.invalidate();
    }

    private String getDateInString(Calendar c) {
        String year = Integer.toString(c.get(Calendar.YEAR));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String calendarData = year + "//" + month + "//" + day;
        return calendarData;
    }

    private String getDayInWeek(Calendar c) {
        int dayInWeek = c.get(Calendar.DAY_OF_WEEK);
        return week[dayInWeek - 1];
    }
}