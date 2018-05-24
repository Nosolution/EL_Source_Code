package com.example.a81271.timato;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

/**
 * Created by 81271 on 2018/5/18.
 */
public class MusicBar extends View
{
    private InsideAudioCore Core;
    private MusicFragment frag;
    private Bitmap Current_Background;
    private Bitmap Play;
    private Bitmap Pause;
    private Bitmap Status;
    private Bitmap WhiteDesc;
    private Bitmap Dragger;
    private String Song_name;
    private Rect PlayRect;
    private Rect StatusBarRect;
    private Rect DragRect;
    private Rect NextRect;
    private Rect PrevRect;
    private int DragOffset;
    private boolean is_whitenoise;
    public boolean is_playing;
    private int width;
    private int height;
    private Rect Background;
    public double ratio;
    private Paint mPaint;
    private Rect WhiteRect;
    private boolean is_pressed;

    public void SetFragment(MusicFragment fr)
    {
        frag = fr;
    }
    private Rect GetSrcRect(Bitmap map)
    {
        return new Rect(0,0,map.getWidth(),map.getHeight());
    }

    private boolean InRect(Rect rc,float x,float y)
    {
        return x>rc.left&&x<rc.right&&y>rc.top&&y<rc.bottom;
    }

    public Rect GetRect(double left,double up,double right,double down)
    {
        new Rect(1,1,1,1);
        return new Rect((int)(left*(float)width),(int)(up*(float)height),(int)((float)right*(float)width),(int)(down*height));
    }

    public void SetDraggerRect()
    {
        double up = 0.2285;
        double down=0.37857;
        double left= (double)DragOffset/width;
        double right = left+0.037;
        DragRect = GetRect(left,up,down,right);
    }

    public MusicBar(Context context)
    {
        this(context,null);
    }
    public MusicBar(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }
    public MusicBar(Context context, AttributeSet attrs,int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = 280;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        PlayRect = GetRect(0.735185, 0.55714, 0.8, 0.835);
        Background = GetRect(0, 0, 1, 1);
        DragRect = GetRect(0.0759,0.2285,0.11388,0.37857);
        StatusBarRect = GetRect(0.08981,0.2892,0.9212,0.3178);
        WhiteRect = GetRect(0.0759,0.1607,0.5074,0.4214);
        NextRect = GetRect(0.8462,0.5392,0.9092,0.8321);
        PrevRect = GetRect(0.620,0.5392,0.6851,0.8321);
        is_pressed = false;
        is_whitenoise = true;
        is_playing = false;
        Current_Background= BitmapFactory.decodeResource(getResources(),R.drawable.musicbarbackground);
        Play= BitmapFactory.decodeResource(getResources(),R.drawable.musicbarplay);
        Pause= BitmapFactory.decodeResource(getResources(),R.drawable.musicbarpause);
        Dragger = BitmapFactory.decodeResource(getResources(),R.drawable.musicbardragger);
        WhiteDesc = BitmapFactory.decodeResource(getResources(),R.drawable.musicbarwhite);
        Status = BitmapFactory.decodeResource(getResources(),R.drawable.musicbartime);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN&&InRect(PlayRect, motionEvent.getX(), motionEvent.getY())) {
                    if (is_playing) {
                        is_playing = false;
                        Core.Pause();
                        //注意改变
                    } else {
                        is_playing = true;
                        if(!Core.GetLoadState()) {
                            Core.LoadInternalMusic("ClockTick");
                            Core.Play();
                        }
                        else
                        {
                            Core.Play();
                        }
                    }
                    postInvalidate();
                }

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&InRect(NextRect, motionEvent.getX(), motionEvent.getY()))
                {
                    frag.PLAY(frag.current_position+1);
                    is_playing = true;
                    postInvalidate();
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&InRect(PrevRect, motionEvent.getX(), motionEvent.getY()))
                {
                    frag.PLAY(frag.current_position-1);
                    is_playing=true;
                    postInvalidate();
                }

                if (!is_whitenoise) {
                    if (!is_pressed && InRect(DragRect, motionEvent.getX(), motionEvent.getY())) {
                        is_pressed = true;
                    }

                    if (is_pressed && motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                        if(DragOffset<0.08981*width)
                            DragOffset=(int)0.08981*width;

                        else if(DragOffset>0.9212*width)
                            DragOffset=(int)0.9212*width;

                        else DragOffset = (int) motionEvent.getY();
                        SetDraggerRect();
                    }
                    if (is_pressed && motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        is_pressed = false;
                        SetMusicPosition((float)(DragOffset-0.08981*width)/(0.8314*width));
                    }
                }
                return true;
            }

        });

    }

    public void SetMusicCore(InsideAudioCore core)
    {
        Core = core;
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
        return 280;
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

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(Current_Background,GetSrcRect(Current_Background),Background,mPaint);
        if(is_playing)
        {
            canvas.drawBitmap(Pause,GetSrcRect(Pause),PlayRect,mPaint);
        }
        else
        {
            canvas.drawBitmap(Play,GetSrcRect(Play),PlayRect,mPaint);
        }
        mPaint.setTextSize(70);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(frag.GetCurrentName(),100,215,mPaint);
        if(is_whitenoise)
        {
            canvas.drawBitmap(WhiteDesc,GetSrcRect(WhiteDesc),WhiteRect,mPaint);
        }
        else
        {
            //底纹
            canvas.drawBitmap(Status,GetSrcRect(Status),StatusBarRect,mPaint);
            //进度
            canvas.drawBitmap(Dragger,GetSrcRect(Dragger),DragRect,mPaint);
            //【补充】绘制时间消息

        }

    }

    public void SetMusicInformation(String SongName,int internal_id)
    {

    }

    public void SetMusicPosition(double ratio)
    {
        //【填充】改变播放核心MusicCore中音乐的位置。

    }

    public void UpdateDragOffset()
    {
        ratio = Core.GetPlayingPosition();
        DragOffset = (int)(ratio*width*0.83148)+(int)(0.08981*width);
        SetDraggerRect();
    }

    public void Update()
    {
        is_playing = Core.GetPlayingStatus();
        if(!is_playing)
        {
            postInvalidate();
            return;
        }
        else
        {
            if(is_whitenoise)
                return;
            if(is_pressed)
                return; //正在被操作时，不更新。
            postInvalidate();
            UpdateDragOffset();

        }
        postInvalidate();
    }
}