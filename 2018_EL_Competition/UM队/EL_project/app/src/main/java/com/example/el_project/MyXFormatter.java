package com.example.el_project;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * 为X轴装载字符
 * @author ns
 */

public class MyXFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public MyXFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value % mValues.length];
    }
}
