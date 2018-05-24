package com.example.administrator.el_done1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingThemeActivity extends AppCompatActivity {

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
        setContentView(R.layout.setting_theme_activity);

        //存入ActivityManager
        ActivityManager.addActivity(this);



        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }

        Button set_theme_back = (Button)findViewById(R.id.set_theme_back);

        ImageButton set_otaku_button = (ImageButton)findViewById(R.id.cartoon_theme);
        ImageButton set_simple_button = (ImageButton)findViewById(R.id.simple_theme);
        ImageButton set_cute_pet_button = (ImageButton)findViewById(R.id.pets_theme);



        set_theme_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.removeActivity(SettingThemeActivity.this);
            }
        });
        set_otaku_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme.setTHEME("OTAKU");
                Intent intent = new Intent(SettingThemeActivity.this,MainActivity.class);
                ActivityManager.finishAllExcept(SettingThemeActivity.this);
                startActivity(intent);
            }
        });
        set_cute_pet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme.setTHEME("PET");
                Intent intent = new Intent(SettingThemeActivity.this,MainActivity.class);
                ActivityManager.finishAll();
                startActivity(intent);
            }
        });
        set_simple_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme.setTHEME("SIMPLE");
                Intent intent = new Intent(SettingThemeActivity.this,MainActivity.class);
                ActivityManager.finishAll();
                startActivity(intent);
            }
        });

    }
}
