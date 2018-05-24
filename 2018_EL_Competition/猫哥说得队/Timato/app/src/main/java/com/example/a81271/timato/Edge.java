package com.example.a81271.timato;

/**
 * Created by 81271 on 2018/5/23.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by 81271 on 2018/5/21.
 */
//注意，角度采取弧度制，并且规定顺时针为正方向。
public class Edge
{
    public Point Left;
    public Point Right;
    public Point BaseLeft;
    public Point BaseRight;
    public Point CenterPoint;
    public Point CenterDestination;
    public Point LeftDestination;
    public Point RightDestination;
    public int grow_second;
    public boolean is_growing;
    public boolean can_growleaf;
    public boolean can_grow;
    public boolean can_fruit;
    public double grow_rate;
    public int layers;
    private int have_grown;
    private int ticks;
    private int XL,XR,YL,YR;
    public Edge(Point baseleft,Point Centerpoint,Point baseright)
    {
        BaseLeft = baseleft;
        BaseRight = baseright;
        CenterPoint = Centerpoint;
        Left = new Point();
        Right = new Point();
        CenterDestination = new Point();
        LeftDestination = new Point();
        RightDestination = new Point();
        ticks =0;
        is_growing = true;
        have_grown = 0;
        can_growleaf = false;
        can_grow = false;
        can_fruit = false;
        layers = 1;
        Log.i("BASE LEFT X",String.valueOf(baseleft.x));
        Log.i("BASE LEFT Y",String.valueOf(baseleft.y));
        Log.i("BASE RIGHT X",String.valueOf(baseright.x));
        Log.i("BASE RIGHT Y",String.valueOf(baseright.y));
    }
    public void SetDestination(Point CenterPT,double angle,int length)
    {
        CenterDestination = CenterPT;double rotate;
        int len = (int)Math.sqrt(Math.pow(BaseLeft.x-CenterPoint.x,2)+Math.pow(BaseLeft.y-CenterPoint.y,2));
        if(CenterDestination.y==CenterPoint.y) rotate = Math.PI/2;
        else rotate = Math.atan((CenterDestination.x-CenterPoint.x)/(CenterDestination.y-CenterPoint.y));
        LeftDestination.x = (int)(CenterDestination.x-length*Math.sin(rotate+Math.PI/2));
        LeftDestination.y = (int)(CenterDestination.y-length*Math.cos(rotate+Math.PI/2));
        RightDestination.x = (int)(CenterDestination.x-length*Math.sin(rotate-Math.PI/2));
        RightDestination.y = (int)(CenterDestination.y-length*Math.cos(rotate-Math.PI/2));
        Log.i("LEFT DEST X",String.valueOf(LeftDestination.x));
        Log.i("LEFT DEST Y",String.valueOf(LeftDestination.y));
        Log.i("RIGHT DEST X",String.valueOf(RightDestination.x));
        Log.i("RIGHT DEST Y",String.valueOf(RightDestination.y));

    }
    public void SetLayer(int l)
    {
        layers = l;
    }

    public void SetStartingPoint(double ratio)
    {
        int X = CenterDestination.x-CenterPoint.x;
        int Y = CenterDestination.y-CenterPoint.y;
        Left.x=(int)(CenterPoint.x+X*ratio);
        Left.y=(int)(CenterPoint.y+Y*ratio);
        Right.x=(int)(CenterPoint.x+X*ratio);
        Right.y=(int)(CenterPoint.y+Y*ratio);
        XL = LeftDestination.x-Left.x;
        YL = LeftDestination.y-Left.y;
        XR = RightDestination.x-Right.x;
        YR = RightDestination.y-Right.y;
    }

    public void SetGrowingTime(int ticks)
    {
        grow_second = ticks;
        grow_rate = (double)1/ticks;
    }
    public void Draw(Canvas canvas)
    {
        Path path = new Path();
        path.moveTo(Left.x,Left.y);
        path.lineTo(BaseLeft.x,BaseLeft.y);
        path.lineTo(BaseRight.x,BaseRight.y);
        path.lineTo(Right.x,Right.y);
        path.close();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        switch (layers)
        {
            case 1:paint.setARGB(255,0x00,0x72,0);
                break;
            case 2:paint.setARGB(255,0x00,0x80,0);
                break;
            case 3:paint.setARGB(255,0x02,0x8A,0);
                break;
            case 4:paint.setARGB(255,0x03,0xA3,0);
                break;
            case 5:paint.setARGB(255,0x0D,0xB1,0);
                break;
            case 6:paint.setARGB(255,0,0xCC,0);
                break;
        }

        canvas.drawPath(path,paint);
    }

    public Point GetNextCenter(int length,double angle)
    {
        Point CenterPt = new Point((Left.x+Right.x)/2,(Left.y+Right.y)/2);
        double rotate;
        if(CenterDestination.y==CenterPoint.y) rotate = Math.PI/2;
        else rotate = Math.atan((CenterDestination.x-CenterPoint.x)/(CenterDestination.y-CenterPoint.y));
        Point res = new Point();
        res.x = CenterPt.x-(int)(length*Math.sin(rotate-angle));
        res.y = CenterPt.y-(int)(length*Math.cos(rotate-angle));
        return res;
    }


    public Edge GenerateSon(int length,double angle,int endlength,double endangle,int period)
    {
        Log.i("GENE","SON!");
        Edge son = new Edge(new Point(Left.x,Left.y),new Point((Left.x+Right.x)/2,(Left.y+Right.y)/2),new Point(Right.x,Right.y));
        son.SetDestination(this.GetNextCenter(length,angle),endangle,endlength);
        son.SetGrowingTime(period);
        son.SetLayer(this.layers+1);
        son.SetStartingPoint(0.1);
        have_grown++;
        if(layers<=3&&have_grown==3)
            can_growleaf=false;
        else if(layers>3&&have_grown==2)
            can_growleaf=false;
        return son;
    }

    public void Grow()
    {
        if(is_growing) {
            if (ticks != grow_second - 1) {
                Left.x = (int) ((double) Left.x + ((double) XL * grow_rate));
                Left.y = (int) ((double) Left.y + ((double) YL * grow_rate));
                Right.x = (int) ((double) Right.x + ((double) XR * grow_rate));
                Right.y = (int) ((double) Right.y + ((double) YR * grow_rate));
                Log.i("LEFT X", String.valueOf(Left.x));
                Log.i("LEFT Y", String.valueOf(Left.y));
                Log.i("RIGHT X", String.valueOf(Right.x));
                Log.i("RIGHT Y", String.valueOf(Right.y));
                ticks++;
            } else {
                Left = LeftDestination;
                Right = RightDestination;
                is_growing = false;
                if(layers<=5)
                    can_growleaf = true;
            }
        }
    }
}


