package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.


import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 81271 on 2018/5/21.
 */



public class Tree {
    public ArrayList<Edge> list;
    public ArrayList<Edge> buffer;
    private int ticks;
    private boolean add = false;

    public Tree() {
        list = new ArrayList<Edge>();
        buffer = new ArrayList<Edge>();
        Edge Base = new Edge(new Point(505, 1200), new Point(540, 1200), new Point(575, 1200));
        Base.SetDestination(new Point(540, 850), Math.toRadians(30), 20);
        Base.SetLayer(1);
        Base.SetGrowingTime(10);
        Base.SetStartingPoint(0.1);
        list.add(Base);
        ticks = 0;
    }

    public void Reset() {
        list.clear();
        buffer.clear();
        Edge Base = new Edge(new Point(505, 1100), new Point(540, 1100), new Point(575, 1100));
        Base.SetDestination(new Point(540, 850), 0, 20);
        Base.SetLayer(1);
        Base.SetGrowingTime(10);
        Base.SetStartingPoint(0.1);
        list.add(Base);
        ticks = 0;
    }

    public void Draw(Canvas canvas) {
        for (Edge e : list) {
            e.Draw(canvas);
        }
    }

    public void SetTicks(int a)
    {
        ticks =a;
    }

    public void Update() {
        for (Edge e : list) {
            e.Grow();
            if(e.can_growleaf)
                switch(e.layers)
                {

                    case 1:
                    {
                        Edge m,p,q,r;
                        m = e.GenerateSon(140,Utils.GetRadians(40,70),20,0,10);
                        p = e.GenerateSon(140,-Utils.GetRadians(40,70),20,0,10);
                        q = e.GenerateSon(140,Utils.GetRadians(0,30),20,0,10);
                        r = e.GenerateSon(140,-Utils.GetRadians(0,30),20,0,10);
                        list.add(m);
                        list.add(p);
                        list.add(q);
                        list.add(r);
                    }
                    break;
                    case 2:
                        if((Utils.GetRandom(0,10)<3))
                        {
                            add = true;
                            Edge m ;

                            if(Utils.Half())
                                m = e.GenerateSon(110,Utils.GetRadians(10,40),20-e.layers*2,0,10);
                            else
                                m = e.GenerateSon(110,-Utils.GetRadians(10,40),20-e.layers*2,0,10);
                            buffer.add(m);
                        }
                        break;
                    case 3:case 4:
                    if(Utils.GetRandom(0,20)<5)
                    {
                        add = true;
                        Edge m ;

                        if(Utils.Half())
                            m = e.GenerateSon(95,Utils.GetRadians(10,45),20-e.layers*2,0,10);
                        else
                            m = e.GenerateSon(95,-Utils.GetRadians(10,45),20-e.layers*2,0,10);
                        buffer.add(m);
                    }
                    break;
                    case 5:
                        if(Utils.GetRandom(0,20)<7)
                        {
                            add = true;
                            Edge m ;

                            if(Utils.Half())
                                m = e.GenerateSon(85,Utils.GetRadians(0,40),20-e.layers*2,0,10);
                            else
                                m = e.GenerateSon(85,-Utils.GetRadians(0,40),20-e.layers*2,0,10);
                            buffer.add(m);
                        }
                        break;
                    default:

                }

        }
        if(add)
        {
            for(Edge e:buffer)
            {
                list.add(e);

            }
            buffer.clear();
            add = false;
        }

    }

}