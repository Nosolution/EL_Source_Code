package com.example.administrator.elwork;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.administrator.elwork.DisplayUtil;

public class CountDownView extends View{
    //圆轮颜色
    private int mRingColor;
    //圆轮宽度
    private float mRingWidth;
    //圆轮进度值文本大小
    private int mRingProgessTextSize;
    //宽度
    private int mWidth;
    //高度
    private int mHeight;
    private Paint mPaint;
    //圆环的矩形区域
    private RectF mRectF;
    //
    private int mProgessTextColor;
    private int mCountdownTime;//倒计时时间
    private float mCurrentProgress;
    private OnCountDownFinishListener mListener;

    private ValueAnimator valueAnimator;
    //新的代码


    //新的代码


    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mRingColor = a.getColor(R.styleable.CountDownView_ringColor, context.getResources().getColor(R.color.blue));
        mRingWidth = a.getFloat(R.styleable.CountDownView_ringWidth, 40);
        mRingProgessTextSize = a.getDimensionPixelSize(R.styleable.CountDownView_progressTextSize, DisplayUtil.sp2px(context, 40));
        mProgessTextColor = a.getColor(R.styleable.CountDownView_progressTextColor, context.getResources().getColor(R.color.white));
        mCountdownTime = a.getInteger(R.styleable.CountDownView_countdownTime, 20);
        a.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        this.setWillNotDraw(false);

    }

        /*新的代码
        Button bt=(Button)findViewById(R.id.button1);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountdownTime(30);
                startCountDown();
            }
        });
    }

    新的代码*/

    public void setCountdownTime(int mCountdownTime) {
        this.mCountdownTime = mCountdownTime;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRectF = new RectF(0 + mRingWidth / 2, 0 + mRingWidth / 2,
                mWidth - mRingWidth / 2, mHeight - mRingWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *圆环
         */
        //颜色
        mPaint.setColor(mRingColor);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        //宽度
        mPaint.setStrokeWidth(mRingWidth);
        canvas.drawArc(mRectF, -90, mCurrentProgress/20 - 360, false, mPaint);
        //绘制文本
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        String text="";

        //text = mCountdownTime - (int) (mCurrentProgress / 360f * mCountdownTime) + "秒";//转换成分秒形式
        int min=mCountdownTime/60;
        int n=(int) (mCurrentProgress / 7200f * mCountdownTime);
        if(n==0){
            text=min+"分"+0+"秒";
        }
        else {
            text = (min - 1-(n-1) / 60) + "分" + (60 * ((n - 1) / 60 + 1) - n) + "秒";
        }

        textPaint.setTextSize(mRingProgessTextSize);
        textPaint.setColor(mProgessTextColor);

        //文字居中显示
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(text, mRectF.centerX(), baseline, textPaint);
    }

    private ValueAnimator getValA(long countdownTime) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(countdownTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        return valueAnimator;
    }
    /**
     * 开始倒计时
     */
    public void startCountDown() {
        setClickable(false);
        valueAnimator = getValA(mCountdownTime * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = Float.valueOf(String.valueOf(animation.getAnimatedValue()));
                mCurrentProgress = (7200 * (i / 100f));
                invalidate();
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //倒计时结束回调
                if (mListener != null) {
                    mListener.countDownFinished();
                }
                setClickable(true);
            }

        });
    }
    //动画暂停
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void pauseCountDownTime(){
        valueAnimator.pause();
    }
    //动画停止
    public void stopCountDownTime(){
        valueAnimator.end();
    }
    //动画继续
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void resumeCounDownTime(){
        valueAnimator.resume();
    }



    public void setAddCountDownListener(OnCountDownFinishListener mListener) {
        this.mListener = mListener;
    }
    public interface OnCountDownFinishListener {
        void countDownFinished();
    }

    public float getmCurrentProgress(){
        return mCurrentProgress;
    }
}
