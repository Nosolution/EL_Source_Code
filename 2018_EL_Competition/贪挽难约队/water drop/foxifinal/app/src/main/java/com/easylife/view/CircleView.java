package com.easylife.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.easylife.activity.R;


public class CircleView extends View {

    private Paint mPaint1;
    private static double currentAngle;
    private int flag;

    public CircleView(Context context) {
        super(context);

        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context);

        init();
    }

    private void init() {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(R.color.timer));
        mPaint1.setStrokeWidth(11f);
        mPaint1.setAntiAlias(true); //消除锯齿
        mPaint1.setStyle(Paint.Style.STROKE); //绘制空心圆

        flag = 0; //第一次初始化
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int r = Math.min(width, height) / 2 - 35;

        canvas.drawCircle(width / 2, height / 2, r, mPaint1);

        if(flag == 0){
            flag = 1;
            currentAngle = -90;
            startCirMotion();
            drawBall(canvas, width / 2, height / 2, r);
        }
        else{
            drawBall(canvas, width / 2, height / 2, r);
        }
    }

    protected void drawBall(Canvas canvas, float width, float height, float r) {

        float cx = width + (float) ((r+25) * Math.cos(currentAngle));
        float cy = height + (float) ((r+25) * Math.sin(currentAngle));
        canvas.drawCircle(cx, cy, 10, mPaint1);

    }

    private void startCirMotion() {
        ValueAnimator animator = ValueAnimator.ofFloat(270f, -90f);

        animator.setDuration(3500).setRepeatCount(ValueAnimator.INFINITE);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float angle = (Float) animation.getAnimatedValue();
                currentAngle = angle * Math.PI / 180;
                invalidate();
            }
        });
        animator.start();
    }
}
