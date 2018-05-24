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
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 81271 on 2018/5/23.
 */

public class TimeSetView extends View {
  //  private Bitmap background;
    public int h1;
    public int h2;
    public int m1;
    public int m2;
    public int s1;
    public int s2;
    private int height = 440;
    private int width = 1080;
    public int GetTotalTicks()
    {
        return s2+s1*10+(m1*10+m2)*60+(h1*10+h2)*3600;
    }

    public void switchh1(int dir)
    {
        if(dir==0)
        {
            if(h1 == 9)
                h1 = 0;
            else
                h1 ++;
        }
        else
        {
            if(h1 == 0)
                h1 = 9;
            else
                h1 --;
        }
        postInvalidate();
    }
    public void switchh2(int dir)
    {
        if(dir==0)
        {
            if(h2 == 9)
                h2 = 0;
            else
                h2 ++;
        }
        else
        {
            if(h2 == 0)
                h2 = 9;
            else
                h2 --;
        }
        postInvalidate();
    }
    public void switchm1(int dir)
    {
        if(dir==0)
        {
            if(m1 == 5)
                m1 = 0;
            else
                m1 ++;
        }
        else
        {
            if(m1 == 0)
                m1 = 5;
            else
                m1 --;
        }
        postInvalidate();
    }
    public void switchm2(int dir)
    {
        if(dir==0)
        {
            if(m2 == 9)
                m2 = 0;
            else
                m2 ++;
        }
        else
        {
            if(m2 == 0)
                m2 = 9;
            else
                m2 --;
        }
        postInvalidate();
    }
    public void switchs1(int dir)
    {
        if(dir==0)
        {
            if(s1 == 5)
                s1 = 0;
            else
                s1 ++;
        }
        else
        {
            if(s1 == 0)
                s1 = 5;
            else
                s1 --;
        }
        postInvalidate();
    }
    public void switchs2(int dir)
    {
        if(dir==0)
        {
            if(s2 == 9)
                s2 = 0;
            else
                s2 ++;
        }
        else
        {
            if(s2 == 0)
                s2 = 9;
            else
                s2 --;
        }
        postInvalidate();
    }
  //  private Rect[] rc;
    private boolean InRect(Rect rc,float x,float y)
    {
        return x>rc.left&&x<rc.right&&y>rc.top&&y<rc.bottom;
    }

    public Rect GetRect(double left,double up,double right,double down)
    {
        Rect rc =  new Rect((int)(left*(float)width),(int)(up*(float)height),(int)((float)right*(float)width),(int)(down*height));
        return rc;
    }
    public TimeSetView(Context context)
    {
        this(context,null);
    }
    public TimeSetView(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }
    public TimeSetView(Context context, AttributeSet attrs,int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        h1=0;
        h2=1;
        m1=0;
        m2=0;
        s1=0;
        s2=0;
      //  background = BitmapFactory.decodeResource(getResources(),R.drawable.timeset);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    protected int getSuggestedMinimumWidth () {
        return 1080;
    }

    protected int getSuggestedMinimumHeight () {
        return 440;
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
    public void Redraw()
    {
        postInvalidate();
    }
    protected void onDraw(Canvas canvas)
    {
       // Rect target = new Rect(0,0,1080,440);
        super.onDraw(canvas);
        Paint p =new Paint();
        p.setAntiAlias(true);
       // canvas.drawBitmap(background,new Rect(0,0,background.getWidth(),background.getHeight()),target,p);
        p.setTextSize(200);

        String H1 = String.valueOf(h1);
        String H2 = String.valueOf(h2);
        String M1 = String.valueOf(m1);
        String M2 = String.valueOf(m2);
        String S1 = String.valueOf(s1);
        String S2 = String.valueOf(s2);


        canvas.drawText(H1,110,270,p);
        canvas.drawText(H2,260,270,p);
        canvas.drawText(M1,425,270,p);
        canvas.drawText(M2,565,270,p);
        canvas.drawText(S1,725,270,p);
        canvas.drawText(S2,860,270,p);
    }

}