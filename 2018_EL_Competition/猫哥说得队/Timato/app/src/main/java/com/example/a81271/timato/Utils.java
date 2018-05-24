package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

/**
 * Created by 81271 on 2018/5/23.
 */

public class Utils
{
    public static int GetRandom(int l,int h)
    {
        return ((int)(Math.random()*(h+1-l))+l);
    }

    public static boolean Half()
    {
        if(1==GetRandom(0,1))
            return true;
        else
            return false;
    }

    public static double GetRadians(int l,int h)
    {
        return Math.toRadians(GetRandom(l,h));
    }
}


