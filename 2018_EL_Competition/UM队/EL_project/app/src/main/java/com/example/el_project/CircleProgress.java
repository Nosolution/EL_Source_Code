package com.example.el_project;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 计时圆形进度条
 * @author CZ
 */

public class CircleProgress extends View{

    private float mRadius,mStrokeWidth,mRingRadius; //半径，圆环宽度，圆环半径
    private int mCircleColor,mRingColor,mTotalProgress,mProgress,mBigCircleColor,mXCenter,mYCenter; //圆形颜色，圆环颜色，总进度，当前进度，外圆颜色，圆心X坐标，圆心Y坐标
    private Paint mCirclePaint,mRingPaint,mBigCirclePaint; //实心圆画笔，圆环画笔，大圆画笔
    /**
     * 初始化各种属性
     * @param context
     * @param attrs
     */
    public CircleProgress(Context context, AttributeSet attrs){
        super(context,attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.CircleProgress,0,0);

        mRadius = a.getDimension(R.styleable.CircleProgress_radius, 300);
        mStrokeWidth=a.getDimension(R.styleable.CircleProgress_strokeWidth,20);
        mCircleColor=a.getColor(R.styleable.CircleProgress_circleColor, Color.WHITE);
        mRingColor=a.getColor(R.styleable.CircleProgress_ringColor,Color.BLACK);
        mTotalProgress=a.getInt(R.styleable.CircleProgress_totalProgress,100);
        mBigCircleColor=a.getColor(R.styleable.CircleProgress_bigCircleColor,Color.BLUE);
        a.recycle();
        mRingRadius=mRadius+mStrokeWidth/2;

        initVariable();

    }

    private void initVariable(){
        mCirclePaint=new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint=new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mBigCirclePaint=new Paint();
        mBigCirclePaint.setColor(mBigCircleColor);
        mBigCirclePaint.setAntiAlias(true);
        mBigCirclePaint.setStyle(Paint.Style.FILL);

    }

    /**
     * 绘制圆形进度条
     */

    public void onDraw(Canvas canvas){
        int mXCenter=getWidth()/2;
        int mYCenter=getHeight()/2;
        canvas.drawCircle(mXCenter,mYCenter,mRadius+mStrokeWidth,mBigCirclePaint);
        canvas.drawCircle(mXCenter,mYCenter,mRadius,mCirclePaint);

        if(mProgress>0){
            //圆弧所在区域
            RectF oval=new RectF();
            oval.left=mXCenter-mRingRadius;
            oval.top=mYCenter-mRingRadius;
            oval.right=mXCenter+mRingRadius;
            oval.bottom=mYCenter+mRingRadius;

            //绘制圆弧
            canvas.drawArc(oval,-90,((float)mProgress/mTotalProgress)*360,false,mRingPaint);
        }
    }

    public void setProgress(int progress){
        mProgress=progress;
        postInvalidate();
    }

    public void setmTotalProgress(int totalProgress){
        mTotalProgress=totalProgress;
    }
}

