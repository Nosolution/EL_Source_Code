package com.hlxx.climber.secondpage.settings;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import com.hlxx.climber.R;
import com.hlxx.climber.secondpage.ClimbingActivity;

import java.lang.ref.WeakReference;

import static android.content.Context.NOTIFICATION_SERVICE;

public class VibrateSetter {
    private WeakReference<ClimbingActivity> theActivity;
    private static boolean isVibrate = false;

    public static void setVibrate(boolean vibrate) {
        isVibrate = vibrate;
    }

    public VibrateSetter(ClimbingActivity theActivity) {
        this.theActivity = new WeakReference<ClimbingActivity>(theActivity);
    }

    public void makeVibrate(boolean finish) {

        ClimbingActivity activity = theActivity.get();
        //处理震动
        NotificationManager manager =
                (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel mNotificationChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationChannel = new NotificationChannel("default", "verible", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationChannel.setDescription("Exit");
            mNotificationChannel.enableLights(true);
            if (isVibrate) {
                mNotificationChannel.enableVibration(true);
                mNotificationChannel.setVibrationPattern(new long[]{0, 1500});
            }
            manager.createNotificationChannel(mNotificationChannel);
        }
        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(activity, "default");
        } else {
            builder = new Notification.Builder(activity)
                    .setLights(Color.WHITE, 3000, 500);
            if (isVibrate) {
                builder.setVibrate(new long[]{0, 1500});
            }
        }
        //设置 通知 图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher_round));
        builder.setAutoCancel(true);
        if (finish) {
            builder.setTicker("恭喜");
            builder.setContentTitle("成功");
            builder.setContentText("你这次坚持成功了！");
        } else {
            builder.setTicker("可惜");
            builder.setContentTitle("失败");
            builder.setContentText("切换过多，下次加油！");
        }
        Notification notification = null;
        notification = builder.build();
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(5, notification);
    }
}

