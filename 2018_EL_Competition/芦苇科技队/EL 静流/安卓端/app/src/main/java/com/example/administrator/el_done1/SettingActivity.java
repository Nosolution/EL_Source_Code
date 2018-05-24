package com.example.administrator.el_done1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {


    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //根据主题设置字体
        switch (Theme.getTHEME()){
            case "SIMPLE":
                setTheme(R.style.SIMPLE_THEME);
                break;
            case "OTAKU":
                setTheme(R.style.OTAKU_THEME);
                break;
            case "PET":
                setTheme(R.style.PET_THEME);
                break;
        }

        //设立layout
        setContentView(R.layout.setting_activity);

        //存入ActivityManager
        ActivityManager.addActivity(this);



        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }

        Button button_back = (Button)findViewById(R.id.set_back);
        Button button_self_information = (Button)findViewById(R.id.set_self_information);
        Button button_theme = (Button)findViewById(R.id.set_theme);
        Button button_delete_data = (Button)findViewById(R.id.set_delete_data);
        Button button_change_user = (Button)findViewById(R.id.set_change_user);
        Button button_help = (Button)findViewById(R.id.set_help);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.removeActivity(SettingActivity.this);
            }
        });
        button_self_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, SettingSelfInformationActivity.class);
                startActivity(intent);
            }
        });
        button_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,SettingThemeActivity.class);
                startActivity(intent);
            }
        });
        button_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,SettingDeleteActivity.class);
                startActivity(intent);
            }
        });
        button_change_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,first.class);
                ActivityManager.finishAll();
                startActivity(intent);
            }
        });
        button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, SettingHelpActivity.class);
                startActivity(intent);
            }
        });
    }
}
