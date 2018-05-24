package com.hlxx.climber.firstpage.setting;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hlxx.climber.R;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


}