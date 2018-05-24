package com.example.el_project;

import android.graphics.Color;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图助手,用来设置柱状图的各种属性并构造柱状图
 * @author ns
 */

public class MyBarChartManager {
    private BarChart mBarChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private Legend legend;
    private String[] values={"周日","周一","周二","周三","周四","周五","周六"};
    public static final int COLOR = 707;
    public static final int DRAWABLE = 701;

    public MyBarChartManager(BarChart barChart) {
        this.mBarChart = barChart;
        leftAxis = mBarChart.getAxisLeft();
        rightAxis = mBarChart.getAxisRight();
        xAxis = mBarChart.getXAxis();
    }

    /*
     * 初始化LineChart
     */
    private void initLineChart() {
        //网格
        mBarChart.setDrawGridBackground(false);//设置网格不可见
        mBarChart.getAxisLeft().setDrawGridLines(false);//左Y轴网格不可见
        mBarChart.getAxisRight().setEnabled(false);//设置右坐标轴不可见
        mBarChart.getXAxis().setDrawGridLines(false);//X轴网格不可见
        //背景阴影
        mBarChart.setDrawBarShadow(false);
        mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.setPinchZoom(false);//禁用双击放大
        mBarChart.setTouchEnabled(false);//禁止触摸交互

        //显示边界
        mBarChart.setDrawBorders(false);
        //设置动画效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear);
        mBarChart.animateX(1000, Easing.EasingOption.Linear);

        //折线图例 标签 设置
        legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        setDescription("",0x00000000);
        MyXFormatter myXFormatter=new MyXFormatter(values);//为X轴装载字符串
        //XY轴的设置
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(myXFormatter);

        xAxis.setTextSize(12f);
        //保证Y轴从0开始
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        leftAxis.setSpaceTop(13f);
//        leftAxis.setAxisMaximum(240f);//设定最大值
        leftAxis.setTextSize(11f);

//        leftAxis.setTextColor(Color.WHITE);
        //Y轴标签个数（近似值）
        leftAxis.setLabelCount(7,false);
    }

    public void showBarChartWithBackGroundRes(List<Float> xAxisValues, List<Float> yAxisValues, String label,int backGroundInfo,
                                              int yTextColor,int xTextColor,int barColor,int legendColor){


        showBarChart(xAxisValues,yAxisValues, label, backGroundInfo, yTextColor, xTextColor, barColor, legendColor, DRAWABLE);
    }

    public void showBarChart(List<Float> xAxisValues, List<Float> yAxisValues, String label,int backGroundInfo,
                             int yTextColor,int xTextColor,int barColor,int legendColor){
        showBarChart(xAxisValues,yAxisValues, label, backGroundInfo, yTextColor, xTextColor, barColor, legendColor, COLOR);
    }

    /**
     * 展示柱状图(一条)
     *
     * @param xAxisValues
     * @param yAxisValues
     * @param label
     */
    public void showBarChart(List<Float> xAxisValues, List<Float> yAxisValues, String label,int backGroundInfo,
                             int yTextColor,int xTextColor,int barColor,int legendColor, int backgroundInfoType) {
        initLineChart();
        float max=0;
        for(float item:yAxisValues)
            max=item>max? item:max;
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new BarEntry(xAxisValues.get(i), yAxisValues.get(i)));
        }
        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, label);
        //将显示的数据转化为整数
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int)value+"";
            }
        });
        //颜色设置
        if (backgroundInfoType == COLOR) {
            mBarChart.setBackgroundColor(backGroundInfo);
        }else if(backgroundInfoType == DRAWABLE){
            mBarChart.setBackgroundResource(backGroundInfo);
        }
        leftAxis.setTextColor(yTextColor);
        xAxis.setTextColor(xTextColor);
        legend.setTextColor(legendColor);
        barDataSet.setColor(barColor);
        
        barDataSet.setValueTextSize(9f);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setFormLineWidth(0.5f);
        barDataSet.setFormSize(15.f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.3f);//设置bar的宽度
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size() - 1, false);
        mBarChart.setData(data);
    }

    /**
     * 展示柱状图(多条)
     *
     * @param xAxisValues
     * @param yAxisValues
     * @param labels
     * @param colours
     */
    /*public void showBarChart(List<Float> xAxisValues, List<List<Float>> yAxisValues, List<String> labels, List<Integer> colours) {
        initLineChart();
        BarData data = new BarData();
        for (int i = 0; i < yAxisValues.size(); i++) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            for (int j = 0; j < yAxisValues.get(i).size(); j++) {

                entries.add(new BarEntry(xAxisValues.get(j), yAxisValues.get(i).get(j)));
            }
            BarDataSet barDataSet = new BarDataSet(entries, labels.get(i));

            barDataSet.setColor(colours.get(i));
            barDataSet.setValueTextColor(colours.get(i));
            barDataSet.setValueTextSize(10f);
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(barDataSet);
        }
        int amount = yAxisValues.size();

        float groupSpace = 0.12f; //柱状图组之间的间距
        float barSpace = (float) ((1 - 0.12) / amount / 10); // x4 DataSet
        float barWidth = (float) ((1 - 0.12) / amount / 10 * 9); // x4 DataSet

//         (0.2 + 0.02) * 4 + 0.08 = 1.00 -> interval per "group"
        xAxis.setLabelCount(xAxisValues.size() - 1, false);
        data.setBarWidth(barWidth);


        data.groupBars(0, groupSpace, barSpace);
        mBarChart.setData(data);
    }*/


    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, false);

        rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, false);
        mBarChart.invalidate();
    }

    /**
     * 设置X轴的值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setXAxis(float max, float min, int labelCount) {
        xAxis.setAxisMaximum(max);
        xAxis.setAxisMinimum(min);
        xAxis.setLabelCount(labelCount, false);

        mBarChart.invalidate();
    }

    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        mBarChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftAxis.addLimitLine(hightLimit);
        mBarChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str,int color) {
        Description description = new Description();
        description.setText(str);
        description.setTextColor(color);
        description.setTextSize(9f);
        mBarChart.setDescription(description);
        mBarChart.invalidate();
    }
}

