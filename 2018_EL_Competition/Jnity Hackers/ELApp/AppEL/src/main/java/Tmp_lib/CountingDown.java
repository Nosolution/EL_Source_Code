package Tmp_lib;

import android.app.Activity;

import cn.iwgang.countdownview.CountdownView;

//compile 'com.github.iwgang:countdownview:2.1.6'
public class CountingDown {
    public static CountdownView getCountingDown(Activity activity, int R_id) {
        return activity.findViewById(R_id);
    }

    public static CountdownView start(int seconds, CountdownView countdownView) {
        countdownView.start(seconds * 1000);
        return countdownView;
    }

    public static CountdownView start(int minute, int seconds, CountdownView countdownView) {
        return start(minute * 60 + seconds, countdownView);
    }

    public static CountdownView start(int hour, int minute, int second, CountdownView countdownView) {
        return start(hour * 3600 + minute * 60 + second, countdownView);
    }

    public static CountdownView clearToZero(CountdownView countdownView) {
        countdownView.allShowZero();
        return countdownView;
    }

    public static CountdownView pause(CountdownView countdownView) {
        countdownView.pause();
        return countdownView;
    }

    public static CountdownView stop(CountdownView countdownView) {
        countdownView.stop();
        return countdownView;
    }

    public static CountdownView restart(CountdownView countdownView) {
        countdownView.restart();
        return countdownView;
    }
}


/*
<cn.iwgang.countdownview.CountdownView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:isHideTimeBackground="true"
    app:isShowDay="true"
    app:isShowHour="true"
    app:isShowMinute="true"
    app:isShowSecond="true"
    app:isShowMillisecond="true"
    app:timeTextColor="#000000"
    app:timeTextSize="22sp"
    app:isTimeTextBold="true"
    app:suffixGravity="bottom"
    app:suffixTextColor="#000000"
    app:suffixTextSize="12sp"
    app:suffixHour="时"
    app:suffixMinute="分"
    app:suffixSecond="秒"
    app:suffixMillisecond="毫秒" />
*
*
*
* */
