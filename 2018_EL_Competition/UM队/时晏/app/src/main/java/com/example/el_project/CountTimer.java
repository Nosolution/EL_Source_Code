package com.example.el_project;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * 一个正向计时器，使用Handler定时发送消息来计时，虽然是我写的但没怎么理解
 * <p>
 * 可用方法如下：
 * start()：开始计时
 * pause()：暂停计时
 * resume()：恢复计时
 * cancel()：取消计时，注意在onDestroy时要调用
 * 通过重载onTick来实现每时间间隔的刷新等
 * <p>
 * 似乎是有着比较高的内存泄露风险，以及可能出现空指针问题，因此使用注意点如下：
 * 在CountDownTimer的onTick方法中记得判空，即if(!activity.isFinishing())这类的内部再写代码
 * 在宿主Activity或fragment生命周期结束的时候，记得调用timer.cancel()方法，先判断timer非空，在调用，然后将其置空
 *
 * @author NAiveD
 * @version 1.0
 */
public abstract class CountTimer {
    /*计时间隔即执行onTick间隔*/
    private final long mCountInterval;

    /*开始时系统时间*/
    private long mStartTime;

    /*相对开始时系统时间，并非真实开始时间*/
    private long mRelativeStartTime;

    private long millisPassed = 0;

    /*代表计时器是否被取消*/
    private boolean mCancelled = false;

    /*代表计时器是否暂停*/
    private boolean mPaused = false;

    /**
     * Instantiates a new Count timer.
     * 构造函数，接收一个计时间隔
     * @param countInterval 一个计时间隔，每个此间隔时间执行onTick(long)函数
     * */
    public CountTimer(long countInterval){
        mCountInterval = countInterval;
    }

    /**
     * Cancel.
     * 取消定时器，从消息列表移除所有消息
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Pause.
     * 暂停定时器，从消息列表移除所有消息
     */
    public synchronized final void pause(){
        mPaused = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Resume.
     * 恢复定时器，重置相对启动时间，发送引发计时的消息
     */
    public synchronized final void resume(){
        mPaused = false;
        mRelativeStartTime = SystemClock.elapsedRealtime() - millisPassed;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
    }

    /**
     * Start count timer.
     * 启动计时器
     *
     * @return the count timer
     */
    public synchronized final CountTimer start(){
        return startWithPassedTime(0);
    }

    /**
     * Start with passed time count timer.
     * 从一个时间点启动计时器
     *
     * @param millisGoneThrough the millis gone through，计时器从多少毫秒开始计时
     * @return the count timer
     */
    /*
     * 以一定的已计算时间启动计时器
     * */
    public synchronized final CountTimer startWithPassedTime(long millisGoneThrough){
        mCancelled = false;
        mPaused = false;

        mStartTime = SystemClock.elapsedRealtime() - millisGoneThrough;
        mRelativeStartTime = mStartTime;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /**
     * Get millis passed long.
     * 返回计时经过的毫秒数
     * @return long
     */
    public final long getMillisPassed(){
        return millisPassed;
    }

    /**
     * 以一定时间间隔调用
     *
     * @param millisGoneThrough 自开启定时器以来除暂停时经过的时间
     */
    public abstract void onTick(long millisGoneThrough);



    private static final int MSG = 1;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            synchronized (CountTimer.this) {

                if (mCancelled || mPaused)
                    return;

                millisPassed = SystemClock.elapsedRealtime() - mRelativeStartTime;

                long lastTickStart = SystemClock.elapsedRealtime();
                onTick(millisPassed);

                //处理用户onTick执行的时间
                long delay = lastTickStart + mCountInterval - SystemClock.elapsedRealtime();

                // 特殊情况：用户的onTick方法花费的时间比interval长，那么直接跳转到下一次interval
                while (delay <= 0) {
                    delay += mCountInterval;
                }

                sendMessage(obtainMessage(MSG));
            }
        }
    };
}
