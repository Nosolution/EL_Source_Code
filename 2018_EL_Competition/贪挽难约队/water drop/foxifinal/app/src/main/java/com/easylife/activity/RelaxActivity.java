package com.easylife.activity;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.easylife.adapter.FragAdapter;
import com.easylife.fragment.RelaxFragment;
import com.easylife.util.LoadingSettingsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelaxActivity extends FragmentActivity {
    private ViewPager relax;
    private RelaxFragment entertain;

    private int musicNum = 1;
    private int relaxMode;
    private static final String CHANNEL_ID = "relax";
    private LoadingSettingsManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        //加载fragment
        loadView();

        //加载设置
        loadSetting();

        //使设置生效
        makeSettingsWork();
    }

    private void makeSettingsWork() {
        entertain.setMusicNum(musicNum);
    }

    private void loadSetting() {
        manager = new LoadingSettingsManager(this);
        musicNum = Integer.parseInt(manager.getRelaxTimeValue());
        Log.i("musicNum", musicNum + "");
        relaxMode = Integer.parseInt(manager.getRelaxModeValue());
        Log.i("relax_mode", relaxMode + "");
    }

    private void loadView() {
        relax = findViewById(R.id.relax);
        List<Fragment> fragments = new ArrayList<>();
        entertain = new RelaxFragment();
        fragments.add(entertain);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        relax.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        if (!isScreenOn) {
            return;
        }
        //显示消息提醒用户返回休息
        showNotification();
        entertain.getPlayer().pause();
        entertain.getPlay().setBackground(getResources().getDrawable(R.drawable.music_pause_button));
        entertain.getPlay().setText("继续播放");
    }

    private void showNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Objects.requireNonNull(this), CHANNEL_ID)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle("休息中...")
                .setContentText("长时间专注后，欣赏一下音乐休息一下吧！立即点击回到休息界面")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        // 设置启动的程序，如果存在则找出，否则新的启动
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, RelaxActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);//将经过设置了的Intent绑定给PendingIntent
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        notificationManager.notify(0, mBuilder.build());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
