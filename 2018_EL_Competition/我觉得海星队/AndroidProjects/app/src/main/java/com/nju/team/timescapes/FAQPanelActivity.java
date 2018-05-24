package com.nju.team.timescapes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by ASUS on 2018/5/9.
 */

public class FAQPanelActivity extends AppCompatActivity{

    private ImageButton btn_exit_faqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.faq_page);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //让状态栏透明
        changeStatusBarColor();

        //指定按钮
        btn_exit_faqs = (ImageButton) findViewById(R.id.imageButton_exitFAQ);

        //按钮事件
        btn_exit_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(FAQPanelActivity.this, SettingsPanelActivity.class);
                FAQPanelActivity.this.startActivity(mainIntent);
                FAQPanelActivity.this.finish();
            }
        });


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent mainIntent = new Intent(FAQPanelActivity.this, SettingsPanelActivity.class);
                FAQPanelActivity.this.startActivity(mainIntent);
                FAQPanelActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }
}
