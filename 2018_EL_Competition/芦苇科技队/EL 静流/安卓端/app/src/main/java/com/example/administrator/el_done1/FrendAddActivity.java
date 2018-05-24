package com.example.administrator.el_done1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FrendAddActivity extends AppCompatActivity {

    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();

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
        ActivityManager.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frend_add);

        //存入ActivityManager
        ActivityManager.addActivity(this);

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

        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();

        }
    }
}
