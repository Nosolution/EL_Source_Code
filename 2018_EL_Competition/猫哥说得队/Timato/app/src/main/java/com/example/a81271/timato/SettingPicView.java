package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by 81271 on 2018/5/23.
 */

public class SettingPicView extends View {

    private Bitmap map;
    int height;
    int width;
    public SettingPicView(Context context)
    {
        this(context,null);
    }
    public SettingPicView(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }
    public SettingPicView(Context context, AttributeSet attrs,int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        map = BitmapFactory.decodeResource(getResources(),R.drawable.acknowledge);
        DisplayMetrics dm = new DisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    protected int getSuggestedMinimumWidth () {
        return 1080;
    }

    protected int getSuggestedMinimumHeight () {
        return 1794;
    }
    /**
     * 获取默认的宽高值
     */
    public static int getDefaultSize (int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec. getMode(measureSpec);
        int specSize = MeasureSpec. getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec. UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec. AT_MOST:
            case MeasureSpec. EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setAntiAlias(true);
        canvas.drawBitmap(map,new Rect(0,0,map.getWidth(),map.getHeight()),new Rect(0,0,1080,1794),p);
    }
}