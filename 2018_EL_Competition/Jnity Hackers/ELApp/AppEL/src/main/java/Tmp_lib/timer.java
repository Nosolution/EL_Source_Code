package Tmp_lib;

/**
 * Created by hxh on 2018/4/20.
 */

//Description: 倒计时实现方式2：使用java.uitl.Timer实现
//使用两个线程

import java.util.*;
import java.util.concurrent.TimeUnit;

public class timer {
    private int limitSec;
    private int curSec;
    public timer(int limitSec) throws InterruptedException{
        this.limitSec = limitSec;
        this.curSec = limitSec;
        System.out.println("count down from "+limitSec+" s ");
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                System.out.println("Time remians "+ --curSec +" s");
            }
        },0,1000);
        TimeUnit.SECONDS.sleep(limitSec);
        timer.cancel();
        System.out.println("Time is out!");
    }
    //Test
    public static void main(String[] args) throws InterruptedException{
        new timer(10);
    }

}
