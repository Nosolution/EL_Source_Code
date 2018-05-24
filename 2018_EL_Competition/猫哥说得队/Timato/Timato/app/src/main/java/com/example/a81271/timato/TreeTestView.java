package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * Created by 81271 on 2018/5/21.
 */

public class TreeTestView extends View
{
    Tree t;
    private int current_countdown_second;
    private int current_countdown_minute;
    private int current_countdown_hour;
    //private int current_time_minute;
    // private int current_time_hour;
    private int current_ticks;
    private boolean counting;
    private boolean terminated;
    // private int second;
    private TextView Countdown;
    public TreeTestView(Context context)
    {
        this(context,null);
    }
    public TreeTestView(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }
    public TreeTestView(Context context, AttributeSet attrs,int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        t = new Tree();
        current_countdown_hour = 0;
        current_countdown_minute = 0;
        current_countdown_second = 0;
        current_ticks = 3600;
        new Thread(logicthread).start();
    }
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));


    }
    public void SetTicks(int time)
    {
        current_ticks=time;
        t.SetTicks(time);
    }
    public void UpdateTime()
    {
        if(current_ticks>0)
            current_ticks--;
        else
            terminated = true;

        current_countdown_hour = current_ticks/3600;
        int remains = current_ticks - current_countdown_hour*3600;
        current_countdown_minute = remains /60;
        remains -= current_countdown_minute*60;
        current_countdown_second = remains;
    }
    public void Reset()
    {
        t.Reset();
    }
    protected int getSuggestedMinimumWidth () {

        return 1080;
    }
    protected int getSuggestedMinimumHeight () {
        return 1794;
    }

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

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint b = new Paint();
        b.setARGB(255,0xE1,0XFF,0x79);
        canvas.drawRect(0,0,1080,1794,b);
        t.Draw(canvas);
        String time = new String();
        Paint s = new Paint();
        s.setAntiAlias(true);
        s.setColor(Color.WHITE);
        s.setTypeface(Typeface.DEFAULT);
        s.setARGB(255,0,0x66,0x66);
        if(current_countdown_hour<10)
            time+="0";
        time+=String.valueOf(current_countdown_hour);
        time+=":";
        if(current_countdown_minute<10)
            time+="0";
        time+=String.valueOf(current_countdown_minute);
        time+=":";
        if(current_countdown_second<10)
            time+="0";
        time+=String.valueOf(current_countdown_second);
        s.setTextSize(200);
        canvas.drawText(time,170,1450,s);
    }

    protected void Update() {
        t.Update();
        UpdateTime();
    }

    private Runnable logicthread = new Runnable() {
        @Override
        public void run() {

            try {
                while(true) {
                    Thread.sleep(1000);
                    Update();
                    postInvalidate();
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    };
}
