package com.example.administrator.el_done1;
import java.util.ArrayList;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 倒计时View
 * @author cuiran
 * @version 1.0.0
 */


/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/











public class CountDownView extends SurfaceView implements Runnable, Callback,Constants {
    private static final String TAG="CountDownView";
    private SurfaceHolder mHolder;    //用于控制SurfaceView
    private Canvas mCanvas;    //声明画布
    private Paint mPaint;    //声明画笔
    private Thread mThread;    //声明一个线程
    private static final int RADIUS=8; //声明小球半径
    private static final int MARGIN_TOP = 40;
    private static final int MARGIN_LEFT = 20;
    private ArrayList<int[][]> list=new ArrayList<int[][]>();


    private static final String END="2018-05-17 11:25:00";
    private Date endDate=null;
    private long curShowTimeSeconds=0;
    public CountDownView(Context context) {
        super(context);
        mHolder = this.getHolder();       //获得SurfaceHolder对象
        mHolder.addCallback(this);        //添加状态监听
        mPaint = new Paint();             //创建一个画笔对象
        mPaint.setColor(Color.WHITE);      //设置画笔的颜色


    }

    private long getCurrentShowTimeSeconds() {
        Date curTime = new Date();
        long ret = endDate.getTime() - curTime.getTime();
        ret =Math.round( ret/1000 );


        return ret >= 0 ? ret : 0;
    }
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        mThread = new Thread(this);    //创建线程对象

        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

    }
    @SuppressLint("SimpleDateFormat")
    public  Date strToDateLong(String strDate)
    {
        if("".equals(strDate)||null==strDate){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    @Override
    public void run() {
        try{

            list.add(data0);
            list.add(data1);
            list.add(data2);
            list.add(data3);
            list.add(data4);
            list.add(data5);
            list.add(data6);
            list.add(data7);
            list.add(data8);
            list.add(data9);
            list.add(data10);
            endDate=strToDateLong(END);
            while(true){
                curShowTimeSeconds=getCurrentShowTimeSeconds();

                mDraw();
                Thread.sleep(50);
            }

        }catch(Exception e){
            Log.e(TAG,"run error",e);
        }


    }

    /**
     * 自定义绘图方法
     * 2014-12-19 下午2:22:45
     *
     */
    public void mDraw() {
        mCanvas = mHolder.lockCanvas();             //获得画布对象,开始对画布画画
        mCanvas.drawColor(Color.BLACK);             //设置画布颜色为黑色
        canvas(mCanvas);
        mHolder.unlockCanvasAndPost(mCanvas);       //把画布显示在屏幕上
    }


    public void canvas(Canvas mCanvas) {
        //画圆,(x轴,y轴,半径,画笔)

        int hours=(int)curShowTimeSeconds / 3600;
        int minutes=(int)(curShowTimeSeconds - hours * 3600)/60 ;
        int seconds=(int)curShowTimeSeconds % 60;

        canvasDigit( MARGIN_LEFT , MARGIN_TOP ,     hours/10 , mCanvas );
        canvasDigit( MARGIN_LEFT + 15*(RADIUS+1) , MARGIN_TOP , hours%10 , mCanvas );
        canvasDigit( MARGIN_LEFT + 30*(RADIUS + 1) , MARGIN_TOP , 10 , mCanvas );
        canvasDigit( MARGIN_LEFT + 39*(RADIUS+1) , MARGIN_TOP , minutes/10 , mCanvas);
        canvasDigit( MARGIN_LEFT + 54*(RADIUS+1) , MARGIN_TOP , minutes%10 , mCanvas);
        canvasDigit( MARGIN_LEFT + 69*(RADIUS+1) , MARGIN_TOP , 10 , mCanvas);
        canvasDigit( MARGIN_LEFT + 78*(RADIUS+1) , MARGIN_TOP , seconds/10 , mCanvas);
        canvasDigit( MARGIN_LEFT + 93*(RADIUS+1) , MARGIN_TOP , seconds%10 , mCanvas);
    }


    public void canvasDigit(int x,int y,int num,Canvas mCanvas) {
        int [][] data=list.get(num);
        for(int i=0;i<data.length;i++){

            for(int j=0;j<data[i].length;j++){

                if(data[i][j]==1){

                    mCanvas.drawCircle(x + j*2*(RADIUS+1)+(RADIUS+1), y + i*2*(RADIUS+1)+(RADIUS+1), RADIUS, mPaint);

                }

            }

        }
    }


}