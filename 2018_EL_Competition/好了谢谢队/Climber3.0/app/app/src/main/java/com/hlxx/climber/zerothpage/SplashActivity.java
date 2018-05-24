package com.hlxx.climber.zerothpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

import com.hlxx.climber.firstpage.TimeSettingActivity;
import com.hlxx.climber.zerothpage.AppConstants;
import com.hlxx.climber.zerothpage.SpUtils;
import com.hlxx.climber.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }
    private void enterHomeActivity() {
        Intent intent = new Intent(this, TimeSettingActivity.class);
        startActivity(intent);
        finish();
    }

}
