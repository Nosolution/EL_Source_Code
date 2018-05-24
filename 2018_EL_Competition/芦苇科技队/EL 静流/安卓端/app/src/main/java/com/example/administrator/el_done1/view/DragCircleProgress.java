package com.example.administrator.el_done1.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.el_done1.MainActivity;
import com.example.administrator.el_done1.R;
import com.example.administrator.el_done1.utils.DeminUtils;
import com.example.administrator.el_done1.utils.DisplayUtils;
import com.example.administrator.el_done1.utils.ToastUtils;

//import com.example.a11407.myapplication.R;


/**

 * 拖拽圆进度条

 * recreated by cmw 2018-5
 */



public class DragCircleProgress extends View {

    private int mCircleColor;//外圈
    private int mLineColor;//内圈
    private Paint mCirclePaint;//画笔
    protected Paint mTextPaint;
    private Paint mLinePaint;
    protected Drawable mThumb;//拖拽图片
    private int mDraggerRadius = 0;//半径
    protected int mCenterPointX;
    protected int mCenterPointY;//圆心坐标
    private int mThumbXPos;
    private int mThumbYPos;//拖拽部分圆心坐标
    private float mRadius;//内圆半径
    private double mTouchAngle;//角度
    protected RectF mArcRect = null;
    protected static final int THUMB_ANGLE = 18;
    private static final int LINES_NUMBER = 200;
    protected double mCurrentValueD = 0;
    protected long mCurrentValue = 0;
    protected int mRoundValue = 60;
    private static final int LINE_LENTH = 18;
    protected long mTotalValue = 1000 * 60 * 60 * 1;
    // 为了减少误操作，只有按下区域在thumb范围内才能拖动
    private boolean mDragThumb = false;
    private static final int SENSITIVE_RADIUS = 30;
    private int state = OFF;//倒计时的状态
    private static final int OFF = 0;
    private static final int ON_DRAGING = 1;//是否在拖动
    private static final int ON_COUNT = 2;//是否再计时
    private Handler mHandler = new Handler();
    private long systemTime = 0l;
    private boolean isCount;
    private int width = 0;//文字宽度
    private long circleCount = 0;
    private double lastAngle = 0;//上次滑动角度

    public DragCircleProgress(Context context) {

        super(context);

        init();

    }


    public DragCircleProgress(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();

    }

    public DragCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init();

    }



    protected Resources mRes;


    //初始化
    private void init() {

        mRes = getContext().getResources();

        float density = mRes.getDisplayMetrics().density;

        setWillNotDraw(false);

        mCircleColor = Color.WHITE;

        mLineColor = Color.WHITE;

        //画圆

        mCirclePaint = new Paint();

        mCirclePaint.setColor(mCircleColor);

        mCirclePaint.setAntiAlias(true);

        mCirclePaint.setStyle(Paint.Style.STROKE);

        mCirclePaint.setStrokeWidth(density);

        //画文字

        mTextPaint = new Paint();





        mTextPaint.setColor(Color.WHITE);

        mTextPaint.setAntiAlias(true);

        mTextPaint.setStyle(Paint.Style.FILL);

        mTextPaint.setTextSize(density * 18);


        //画内圆

        mLinePaint = new Paint();

        mLinePaint.setColor(mLineColor);

        mLinePaint.setAntiAlias(true);

        mLinePaint.setStyle(Paint.Style.STROKE);

        mLinePaint.setStrokeWidth(1 * density);

        //初始化拖拽圆的图片

        initThumb(false);

    }





    private void initThumb(Boolean drag) {

        if (drag) {

            mThumb = mRes.getDrawable(R.mipmap.count_down_timer_thumb_on);

        } else {

            mThumb = mRes.getDrawable(R.mipmap.count_down_timer_thumb_off);

        }



        int thumbHalfheight = (int) mThumb.getIntrinsicHeight() / 2;

        int thumbHalfWidth = (int) mThumb.getIntrinsicWidth() / 2;

        if (mDraggerRadius == 0) {

            mDraggerRadius = thumbHalfWidth;

        }

        mThumb.setBounds(-thumbHalfWidth, -thumbHalfheight, thumbHalfWidth,

                thumbHalfheight);



    }





    protected void onLayout(boolean changed, int left, int top, int right,

                            int bottom) {

        super.onLayout(changed, left, top, right, bottom);

        int availableWidth = getRight() - getLeft();

        int availableHeight = getBottom() - getTop();



        int x = availableWidth / 2;

        int y = availableHeight / 2;



        mCenterPointX = x;

        mCenterPointY = y;

        //更新拖动圆的坐标

        updateThumbPosition();

    }





    protected void updateThumbPosition() {

        mThumbXPos = (int) (mRadius * Math.sin(Math.toRadians(mTouchAngle)));

        mThumbYPos = (int) (mRadius * Math.cos(Math.toRadians(mTouchAngle)));

    }




    public void setSystemTime(long systemTime) {

        this.systemTime = systemTime;





        mCurrentValue = systemTime - System.currentTimeMillis();

        if (systemTime <= System.currentTimeMillis() || systemTime == 0) {

            mCurrentValue = 0;

            state = OFF;

            mTouchAngle = 0;

            circleCount = 0;

            initThumb(false);

            isCount = false;

        } else {

            circleCount = mCurrentValue / mTotalValue;

            mTouchAngle = (mCurrentValue * 360 / mTotalValue) % 360;

            state = ON_COUNT;



            initThumb(true);

            isCount = true;

            startCountDown();

        }



        lastAngle = mTouchAngle;



        invalidate();



    }




    protected void onDraw(Canvas canvas) {

        if (mArcRect == null) {

            initArcRect();

        }



        mCirclePaint.setColor(mCircleColor);

        // canvas.drawCircle(mCenterPointX, mCenterPointY, mRadius,

        // mCirclePaint);

        canvas.drawArc(mArcRect, (float) mTouchAngle - 90 + THUMB_ANGLE

                / 2, 360 - THUMB_ANGLE, false, mCirclePaint);

        if (mThumb != null) {

            updateThumbPosition();

            canvas.save();

            canvas.translate(mCenterPointX + mThumbXPos, mCenterPointY

                    - mThumbYPos);

            canvas.rotate((float) mTouchAngle, mThumb.getBounds()

                    .exactCenterX(), mThumb.getBounds().exactCenterY());

            mThumb.draw(canvas);

            canvas.restore();

        }

        canvas.save();

        float rotate = 360.0f / LINES_NUMBER;

        mLinePaint.setColor(mLineColor);

        mLinePaint.setAlpha(255);

        float density = mRes.getDisplayMetrics().density;



        for (int i = 0; i < LINES_NUMBER; i++) {



            if (rotate * i > mTouchAngle && circleCount == 0) {



                mLinePaint.setColor(mLineColor);



            } else {



                mLinePaint.setColor(0xFF3DFDAF);



            }

            if (mTouchAngle > 0 && i == 1) {

                mLinePaint.setColor(0xFF3DFDAF);

            }

            if (mCurrentValue <= 0) {

                mLinePaint.setColor(mLineColor);

            }

            canvas.drawLine(mCenterPointX, mCenterPointY - mRadius

                            + mDraggerRadius + 5 * density, mCenterPointX,

                    mCenterPointY - mRadius + mDraggerRadius + 5 * density

                            + LINE_LENTH * density, mLinePaint);

            canvas.rotate(rotate, mCenterPointX, mCenterPointY);

        }

        canvas.restore();

        onDrawText(canvas, mCurrentValue, mTotalValue);

    }





    public boolean dispatchTouchEvent(MotionEvent event) {



        int x = (int) event.getX();

        int y = (int) event.getY();



        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (isLocateInThumbArea(x, y)) {

                mDragThumb = true;

            } else {

                mDragThumb = false;

            }

        }

        return super.dispatchTouchEvent(event);

    }





    public boolean onTouchEvent(MotionEvent event) {



        if (!isTrackingStart()) {

            return super.onTouchEvent(event);

        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:



                if (!updateOnDownTouch(event)) {

                    return false;

                }



                break;

            case MotionEvent.ACTION_MOVE:

                setPressed(true);

                double angle = getTouchDegrees(event.getX(), event.getY());

                if (!updateOnTouch(angle)) {

                    return false;

                }

                break;

            case MotionEvent.ACTION_UP:





                if (mCurrentValue > 6 * mTotalValue) {

                    mCurrentValue = 6 * mTotalValue;

                    mTouchAngle = 0;

                    circleCount = 6;

                    ToastUtils.showMsg(getContext(), getResources().getString(R.string.delayTipStr4));

                }else if (mTouchAngle<180){
                    mCurrentValue = 0;

                    mTouchAngle = 0;

                    circleCount = 0;


                }

                if (circleCount < 0) {

                    circleCount = 0;

                    mTouchAngle = 0;

                    lastAngle = 0;

                }

                if (state != OFF) {

                    if (mDragThumb) {

                        if (mCurrentValue < 1) {

                            state = OFF;

                            isCount = false;

                            mTouchAngle = 0;

                            initThumb(false);

                            stopCountDown();

                            systemTime = mCurrentValue + System.currentTimeMillis();

                            if (onCountDownListener != null) {

                                onCountDownListener.onStop(this, systemTime, true);

                            }



                            invalidate();

                            break;

                        }

                        state = ON_COUNT;

                        systemTime = mCurrentValue + System.currentTimeMillis();

                        isCount = true;

                        initThumb(true);

                        if (onCountDownListener != null) {

                            onCountDownListener.onStart(this, systemTime);

                        }

                        startCountDown();



                    }

                }









                invalidate();

                setPressed(false);

                break;

            case MotionEvent.ACTION_CANCEL:


                setPressed(false);

                break;

        }



        return true;

    }







    private void startCountDown() {





        if (isCount && state != OFF) {

            mHandler.postDelayed(new Runnable() {

                @Override

                public void run() {



                    mCurrentValue = systemTime - System.currentTimeMillis();

                    mTouchAngle = (mCurrentValue % mTotalValue) * 360 / mTotalValue;

                    circleCount = mCurrentValue / mTotalValue;



                    if (mCurrentValue <= 0) {

                        mTouchAngle = 0;

                        isCount = false;

                        initThumb(false);

                        state = OFF;

                        mHandler.removeCallbacksAndMessages(null);

                        if (onCountDownListener != null) {

                            onCountDownListener.onStop(DragCircleProgress.this, systemTime, false);

                        }

                    }



                    invalidate();

                    startCountDown();

                }

            }, 1000);

        }





    }




    protected boolean updateOnTouch(double angle) {

        if (!mDragThumb) {

            return true;

        }



        if (mCurrentValue <= 0) {

            lastAngle = 0;

        }



        state = ON_DRAGING;





        if (lastAngle - angle < -270) {

            circleCount--;



        }

        if (lastAngle - angle > 270) {

            if (circleCount < 0) {

                circleCount = 0;

            }

            circleCount++;





        }



        mCurrentValue = (long) ((angle + circleCount * 360) * mTotalValue / 360);

        if (mCurrentValue <= 0) {

            mCurrentValue = 0;

            mTouchAngle = 0;

            lastAngle = 0;





        } else {

            mTouchAngle = angle;

            lastAngle = angle;

        }


        updateThumbPosition();

        if (onCountDownListener != null) {

            onCountDownListener.onSlide(this, systemTime);

        }





        invalidate();

        return true;

    }



    protected double getTouchDegrees(float xPos, float yPos) {

        float x = xPos - mCenterPointX;

        float y = mCenterPointY - yPos;

        double angle = Math.toDegrees(Math.atan2(x, y));

        if (xPos < mCenterPointX && yPos > mCenterPointY) {

            angle += 360;

        } else if (xPos < mCenterPointX && yPos < mCenterPointY) {

            angle += 360;

        }

        return angle;

    }



    protected boolean updateOnDownTouch(MotionEvent event) {

        if (mDragThumb) {

            isCount = false;

            mHandler.removeCallbacksAndMessages(null);

        }

        if (circleCount < 0) {

            circleCount = 0;

        }

        if (state == OFF) {

            if (mDragThumb) {

                state = ON_DRAGING;

            }

        }



        if (state == ON_COUNT) {

            if (!mDragThumb && Math.abs(event.getX() - mCenterPointX) < 1 * mRadius / 3 && Math.abs(event.getY() - mCenterPointY) < 1 * mRadius / 3) {

                state = OFF;

                mCurrentValue = 0;

                mTouchAngle = 0;

                lastAngle = 0;

                systemTime = 0;

                circleCount = 0;

                if (onCountDownListener != null) {

                    onCountDownListener.onStop(this, systemTime, true);

                }

                stopCountDown();

                initThumb(false);

            } else {



            }

        }





        invalidate();

        return true;

    }




    private void stopCountDown() {

        isCount = false;

        mCurrentValue = 0;

        mHandler.removeCallbacksAndMessages(null);



    }



    protected boolean isTrackingStart() {

        return true;

    }





    public boolean isLocateInThumbArea(int x, int y) {

        int thumbX = mCenterPointX + mThumbXPos;

        int thumbY = mCenterPointY - mThumbYPos;

        return (thumbX - SENSITIVE_RADIUS < x && thumbY - SENSITIVE_RADIUS < y

                && (thumbX + mThumb.getIntrinsicWidth() + SENSITIVE_RADIUS) > x && (thumbY

                + mThumb.getIntrinsicHeight() + SENSITIVE_RADIUS) > y);

    }





    protected void onDrawText(Canvas canvas, long currentValue, long totalValue) {

        mTextPaint.setTextSize(mRadius / 4);

        if (currentValue <= 0) {

            currentValue = 0;

        }

        String time = format(currentValue);

        String des = null;

        switch (state) {

            case OFF:

                mTextPaint.setColor(Color.WHITE);

                des = getContext().getResources().getString(R.string.delayTipStr1);

                break;

            case ON_COUNT:

                mTextPaint.setColor(0xFF3DFDAF);

                des = getContext().getResources().getString(R.string.delayTipStr2);

                break;

            default:

                mTextPaint.setColor(0xFF3DFDAF);

                des = getContext().getResources().getString(R.string.delayTipStr3);

                break;

        }

        if (width == 0) {

            width = DeminUtils.getTextWidth(mTextPaint, time);

        }
        if("00:00:01".equals(time)) MainActivity.times();

        if ("00:00:00".equals(time)) {

            mTextPaint.setColor(Color.WHITE);



        }

        canvas.drawText(time, 0, time.length(), mCenterPointX - width / 2, mCenterPointY - DisplayUtils.dip2px(getContext(), 4), mTextPaint);

        mTextPaint.setTextSize(mRadius / 10);

        mTextPaint.setColor(0x80FFFFFF);

        int width = DeminUtils.getTextWidth(mTextPaint, des);

        int height = DeminUtils.getTextHeight(mTextPaint, des);



        canvas.drawText(des, 0, des.length(), mCenterPointX - width / 2, mCenterPointY + DisplayUtils.dip2px(getContext(), 4) + height, mTextPaint);



    }





    private String format(long currentValue) {

        currentValue = currentValue / 1000;

        int second = (int) (currentValue % 60);

        int min = (int) (currentValue / 60 % 60);

        int hour = (int) (currentValue / 3600);

        return lenghtFormat(hour) + ":" + lenghtFormat(min) + ":" + lenghtFormat(second);

    }



    private String lenghtFormat(int time) {

        if ((time + "").length() < 2) {

            return "0" + time;

        }

        return String.valueOf(time);

    }




    private void initArcRect() {

        final int height = getHeight();

        final int width = getWidth();

        final int min = Math.min(width, height);

        float top = 0;

        float left = 0;

        int arcDiameter = 0;



        arcDiameter = (int) (min - getPaddingLeft() - getPaddingRight() - mDraggerRadius * 2);



        mRadius = arcDiameter / 2;

        top = height / 2 - mRadius;

        left = width / 2 - mRadius;

        mArcRect = new RectF();

        mArcRect.set(left, top, left + arcDiameter, top + arcDiameter);

    }





    public void colse() {

        state = OFF;

        mTouchAngle = 0;

        lastAngle = 0;

        circleCount = 0;

        mCurrentValue = 0;

        initThumb(false);

        isCount = false;

        mHandler.removeCallbacksAndMessages(null);



        invalidate();

    }





    public interface OnCountDownListener {


        void onStart(DragCircleProgress dragCircleProgress, long descTime);





        void onSlide(DragCircleProgress dragCircleProgress, long descTime);



        void countSlide(DragCircleProgress dragCircleProgress, long descTime);




        void onStop(DragCircleProgress dragCircleProgress, long descTime, boolean byUser);

    }



    private OnCountDownListener onCountDownListener;



    public void setOnCountDownListener(OnCountDownListener onCountDownListener) {

        this.onCountDownListener = onCountDownListener;

    }

}