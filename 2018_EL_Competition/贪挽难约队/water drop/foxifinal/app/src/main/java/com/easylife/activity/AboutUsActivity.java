package com.easylife.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylife.util.ApplicationVertionUtil;


public class AboutUsActivity extends Activity {
    private ImageView back;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //初始化标题栏
        initActionBar();

        //初始化控件
        initWidget();

        //设置点击事件
        setOnclickEvent();
    }

    private void setOnclickEvent() {
        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void initWidget() {
        back = findViewById(R.id.back_imageview);
        version = findViewById(R.id.version_textview);

        String appVer = "Version: "+ApplicationVertionUtil.getVersionName(this) + " "+ ApplicationVertionUtil.getVersionCode(this);
        version.setText(appVer);
    }

    //初始化标题栏
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.user_actionbar, null);
        if (actionBar != null) {
            actionBar.setCustomView(actionBarView);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }

    public void feedback(View view) {
        startActivity(new Intent(AboutUsActivity.this,FeedbackActivity.class));
    }
}
