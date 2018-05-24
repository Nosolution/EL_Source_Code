package com.example.administrator.el_done1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class user extends AppCompatActivity {

    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_el_done1);

        //存入ActivityManager
        ActivityManager.addActivity(this);



        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }


        Button button_dairy = (Button)findViewById(R.id.dairy);
        Button button_weekReport = (Button)findViewById(R.id.weekReport);
        Button button_friends = (Button)findViewById(R.id.friends);
        Button button_setting = (Button)findViewById(R.id.setting);
        button_dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user.this, DairyActivity.class);
                startActivity(intent);
            }
        });
        button_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user.this, FriendsActivity.class);
                startActivity(intent);
            }
        });
        button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        button_weekReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user.this, WeekReportActivity.class);
                startActivity(intent);
            }
        });
    }
}
