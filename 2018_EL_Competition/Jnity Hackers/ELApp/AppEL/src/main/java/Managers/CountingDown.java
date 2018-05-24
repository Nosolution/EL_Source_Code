package Managers;


import android.app.Activity;
import android.content.Context;

import cn.iwgang.countdownview.CountdownView;

//compile 'com.github.iwgang:countdownview:2.1.6'
public class CountingDown {
    private CountdownView countdownView;

    private CountingDown(Context context) {
        countdownView = new CountdownView(context);
    }


    public static CountingDown getCountingDown(Context context) {

        return new CountingDown(context);
    }

    public CountdownView getCountingView(Activity activity, int R_id) {
        return activity.findViewById(R_id);
    }

    public CountdownView start(int seconds, CountdownView countdownView) {
        countdownView.start(seconds * 1000);
        return countdownView;
    }

    public CountdownView start(int minute, int seconds, CountdownView countdownView) {
        return start(minute * 60 + seconds, countdownView);
    }

    public CountdownView start(int hour, int minute, int second, CountdownView countdownView) {
        return start(hour * 3600 + minute * 60 + second, countdownView);
    }

    public CountdownView clearToZero(CountdownView countdownView) {
        countdownView.allShowZero();
        return countdownView;
    }

    public CountdownView pause(CountdownView countdownView) {
        countdownView.pause();
        return countdownView;
    }

    public CountdownView stop(CountdownView countdownView) {
        countdownView.stop();
        return countdownView;
    }

    public CountdownView restart(CountdownView countdownView) {
        countdownView.restart();
        return countdownView;
    }


}


//代码模板
/*
* Handler handler = new Handler() {
            //重写handleMessage方法获得子线程传来的数据
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                text.setText(String.valueOf(msg.arg1));
            }
        };
        new Thread(() -> {
            count = 0;
            while (ifStop) {
                count++;
                Message msg = new Message();
                msg.arg1 = Math.toIntExact(count);
                handler.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
* */