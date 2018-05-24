package Managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/*
 * 检查手机状态，只需要关注的是内部接口ScreenStateListener
 * */


public class ScreenManager {
    private Context context;
    private ScreenBroadcastReceiver screenBroadcastReceiver;
    private ScreenStateListener listener;

    public static ScreenManager getScreenManager(Context context) {
        return new ScreenManager(context);
    }

    public interface ScreenStateListener {// 返回给调用者屏幕状态信息

        //屏幕点亮
        void onScreenOn();

        //屏幕关闭
        void onScreenOff();

        //用户解锁
        void onUserPresent();
    }

    public ScreenManager(Context context) {
        this.context = context;
        screenBroadcastReceiver = new ScreenBroadcastReceiver();
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {

        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                listener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                listener.onScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                listener.onUserPresent();
            }
        }

    }

    /**
     * 开始监听screen状态
     *
     * @param listener
     */
    public void begin(ScreenStateListener listener) {
        this.listener = listener;
        registerListener();
        getScreenState();
    }

    /**
     * 获取screen状态
     */
    private void getScreenState() {
        PowerManager manager = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        if (manager.isScreenOn()) {
            if (listener != null) {
                listener.onScreenOn();
            }
        } else {
            if (listener != null) {
                listener.onScreenOff();
            }
        }
    }

    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        context.unregisterReceiver(screenBroadcastReceiver);
    }

    /**
     * 启动screen状态广播接收器
     */
    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(screenBroadcastReceiver, filter);
    }

}
