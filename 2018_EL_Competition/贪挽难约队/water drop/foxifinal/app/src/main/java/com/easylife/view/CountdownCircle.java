package com.easylife.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.easylife.activity.R;


public class CountdownCircle extends View {

    private Paint mPaint1;
    private static float currentAngle = 0f;
    private static int flag = 1;
    private static long totaltime = 0;

    public CountdownCircle(Context context) {
        super(context);

        init();
    }

    public CountdownCircle(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(R.color.countdownCircle));
        mPaint1.setStrokeWidth(11f);
        mPaint1.setAntiAlias(true); //消除锯齿
        mPaint1.setStyle(Paint.Style.STROKE); //绘制空心圆
    }

    public void setTotaltime(long time){
        totaltime = time;

        flag = 0;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectf_head=new RectF(35f,35f,this.getWidth()-35f,this.getHeight()-35f);//确定外切矩形范围
        canvas.drawArc(rectf_head, -90f, currentAngle, false, mPaint1);//绘制圆弧，不含圆心

        if(flag == 0){
            flag = 1;
            startCirMotion();
        }
    }

    private void startCirMotion() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, -360f);

        animator.setDuration(totaltime);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}