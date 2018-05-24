package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 81271 on 2018/5/23.
 */

public class PictureView extends View {

    private Bitmap map;
    private Rect Click;
    private Boolean down;
    int height;
    int width;
    public PictureView(Context context)
    {
        this(context,null);
    }
    public PictureView(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }
    public PictureView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        map = BitmapFactory.decodeResource(getResources(),R.drawable.timesettingbackground);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        h1=0;
        down = false;
        h2=1;
        m1=0;
        m2=0;
        s1=0;
        s2=0;
        Click = new Rect((int)(0.022*width),(int)(0.5886*height),(int)(0.975*width),(int)(0.686*height));
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN&&InRect(Click,event.getX(),event.getY())){
                    down = true;
                    postInvalidate();
                }
                if(event.getAction()==MotionEvent.ACTION_UP&&down)
                {
                    down = false;
                    postInvalidate();
                    if(InRect(Click,event.getX(),event.getY()))
                    {
                        Intent intent = new Intent();
                        intent.setClass(context,TreeGrowingActivity.class);
                        intent.putExtra("time",GetTotalTicks());
                        context.startActivity(intent);
                    }

                }
                return true;
            }
        });
    }
    private boolean InRect(Rect rc,float x,float y)
    {
        return x>rc.left&&x<rc.right&&y>rc.top&&y<rc.bottom;
    }

    public Rect GetRect(double left,double up,double right,double down)
    {
        Rect rc =  new Rect((int)(left*(float)width),(int)(up*(float)height),(int)((float)right*(float)width),(int)(down*height));
        return rc;
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
        canvas.drawBitmap(map,new Rect(0,0,map.getWidth(),map.getHeight()),new Rect(0,0,width,height),p);
        p.setTextSize(200);
        String H1 = String.valueOf(h1);
        String H2 = String.valueOf(h2);
        String M1 = String.valueOf(m1);
        String M2 = String.valueOf(m2);
        String S1 = String.valueOf(s1);
        String S2 = String.valueOf(s2);
        canvas.drawText(H1,125,750,p);
        canvas.drawText(H2,245,750,p);
        canvas.drawText(M1,425,750,p);
        canvas.drawText(M2,545,750,p);
        canvas.drawText(S1,725,750,p);
        canvas.drawText(S2,850,750,p);
        String R = ":";
        canvas.drawText(R,360,740,p);
        canvas.drawText(R,660,740,p);
    }


    public int h1;
    public int h2;
    public int m1;
    public int m2;
    public int s1;
    public int s2;
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
}
