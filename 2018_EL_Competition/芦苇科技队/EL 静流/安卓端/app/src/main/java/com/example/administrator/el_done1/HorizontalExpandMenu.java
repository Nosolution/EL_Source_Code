package com.example.administrator.el_done1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HorizontalExpandMenu extends RelativeLayout {
    private Context mContext;
    private AttributeSet mAttrs;
    private int defaultWidth;//默认宽度
    private int defaultHeight;//默认长度
    private int viewWidth;
    private int viewHeight;
    private int menuBackColor;//菜单栏背景色
    private float menuStrokeSize;//菜单栏边框线的size
    private int menuStrokeColor;//菜单栏边框线的颜色
    private float menuCornerRadius;//菜单栏圆角半径

    private float buttonIconDegrees;//按钮icon符号竖线的旋转角度
    private float buttonIconSize;//按钮icon符号的大小
    private float buttonIconStrokeWidth;//按钮icon符号的粗细
    private int buttonIconColor;//按钮icon颜色

    private int buttonStyle;//按钮类型
    private int buttonRadius;//按钮矩形区域内圆半径
    private float buttonTop;//按钮矩形区域top值
    private float buttonBottom;//按钮矩形区域bottom值

    private Point rightButtonCenter;//右按钮中点
    private float rightButtonLeft;//右按钮矩形区域left值
    private float rightButtonRight;//右按钮矩形区域right值

    private Point leftButtonCenter;//左按钮中点
    private float leftButtonLeft;//左按钮矩形区域left值
    private float leftButtonRight;//左按钮矩形区域right值








    /**
     * 根按钮所在位置，默认为右边
     */
    public class ButtonStyle {
        public static final int Right = 0;
        public static final int Left = 1;
    }



    public HorizontalExpandMenu(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public HorizontalExpandMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttrs = attrs;
        init();
    }

    private void init(){
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.HorizontalExpandmenu);



        menuBackColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_back_color, Color.WHITE);
        menuStrokeSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_stroke_size,1);
        menuStrokeColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_stroke_color,Color.GRAY);






        buttonStyle = typedArray.getInteger(R.styleable.HorizontalExpandMenu_button_style,ButtonStyle.Right);
        buttonIconDegrees = 0;
       
        buttonIconStrokeWidth = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_stroke_width,8);
        buttonIconColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_button_icon_color,Color.GRAY);

        Paint buttonIconPaint = new Paint();
        buttonIconPaint.setColor(buttonIconColor);
        buttonIconPaint.setStyle(Paint.Style.STROKE);
        buttonIconPaint.setStrokeWidth(buttonIconStrokeWidth);
        buttonIconPaint.setAntiAlias(true);

        Path path = new Path();
        leftButtonCenter = new Point();
        rightButtonCenter = new Point();



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        viewHeight = height;
        viewWidth = width;
        setMeasuredDimension(viewWidth,viewHeight);


        buttonRadius = viewHeight/2;
        layoutRootButton();


        //布局代码中如果没有设置background属性则在此处添加一个背景
        if(getBackground()==null){
            setMenuBackground();
        }
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    /**
     * 设置菜单背景，如果要显示阴影，需在onLayout之前调用
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setMenuBackground(){
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(menuBackColor);
        gd.setStroke((int)menuStrokeSize, menuStrokeColor);
        gd.setCornerRadius(menuCornerRadius);
        setBackground(gd);
    }








    protected void onDraw(Canvas canvas) {
        layoutRootButton();
        if(buttonStyle == ButtonStyle.Right){
            drawRightIcon(canvas);
        }else {
            drawLeftIcon(canvas);
        }

        super.onDraw(canvas);//注意父方法在最后调用，以免icon被遮盖
    }

    /**
     * 测量按钮中点和矩形位置
     */
    private void layoutRootButton(){
        buttonTop = 0;
        buttonBottom = viewHeight;

        rightButtonCenter.x = viewWidth- buttonRadius;
        rightButtonCenter.y = viewHeight/2;
        rightButtonLeft = rightButtonCenter.x- buttonRadius;
        rightButtonRight = rightButtonCenter.x+ buttonRadius;

        leftButtonCenter.x = buttonRadius;
        leftButtonCenter.y = viewHeight/2;
        leftButtonLeft = leftButtonCenter.x- buttonRadius;
        leftButtonRight = leftButtonCenter.x+ buttonRadius;
    }

    /**
     * 绘制左边的按钮
     * @param canvas
     */
    private void drawLeftIcon(Canvas canvas){
        Path path = null;
        path.reset();
        path.moveTo(leftButtonCenter.x- buttonIconSize, leftButtonCenter.y);
        path.lineTo(leftButtonCenter.x+ buttonIconSize, leftButtonCenter.y);


        canvas.save();
        canvas.rotate(-buttonIconDegrees, leftButtonCenter.x, leftButtonCenter.y);//旋转画布，让竖线可以随角度旋转
        path.reset();
        path.moveTo(leftButtonCenter.x, leftButtonCenter.y- buttonIconSize);
        path.lineTo(leftButtonCenter.x, leftButtonCenter.y+ buttonIconSize);

        canvas.restore();
    }

    /**
     * 绘制右边的按钮
     * @param canvas
     */
    private void drawRightIcon(Canvas canvas){
        Path path = null;
        path.reset();
        path.moveTo(rightButtonCenter.x- buttonIconSize, rightButtonCenter.y);
        path.lineTo(rightButtonCenter.x+ buttonIconSize, rightButtonCenter.y);


        canvas.save();
        canvas.rotate(buttonIconDegrees, rightButtonCenter.x, rightButtonCenter.y);//旋转画布，让竖线可以随角度旋转
        path.reset();
        path.moveTo(rightButtonCenter.x, rightButtonCenter.y- buttonIconSize);
        path.lineTo(rightButtonCenter.x, rightButtonCenter.y+ buttonIconSize);

        canvas.restore();
    }
}

