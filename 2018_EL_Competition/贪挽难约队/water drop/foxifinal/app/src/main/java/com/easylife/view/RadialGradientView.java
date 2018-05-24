package com.easylife.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class RadialGradientView extends View {

    public Paint mPaint = null;
    // 环形渐变渲染
    public Shader mRadialGradient = null;
    public static float r;
    public static int flag;


    public RadialGradientView(Context context) {
        super(context);

        init();
    }

    public RadialGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        r = 20;
        flag = 0;

        mPaint = new Paint();
        int width = getWidth();
        int height = getHeight();

        float cx = width/2;
        float cy = height/2;

        mRadialGradient = new RadialGradient(cx,cy,r,new int[] {Color.WHITE, Color.WHITE},null,Shader.TileMode.CLAMP);
        // 绘制环形渐变
        mPaint.setShader(mRadialGradient);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float cx = width/2;
        float cy = 35;

        if(flag == 0){
            flag = 1;
            canvas.drawCircle(cx, cy, r, mPaint);
            startCirMotion();
        }
        else {
            canvas.drawCircle(cx, cy, r, mPaint);
        }
    }

    private void startCirMotion() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 15f, 0f);

        animator.setDuration(1000).setRepeatCount(ValueAnimator.INFINITE);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                r = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
