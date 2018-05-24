package com.example.lenovo.elapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import Activitys.MainActivity;

/**
 * Created by hxh on 2018/5/22.
 */

public class RemindActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private Switch aSwitch;
    private SwitchCompat aSwitchCompat;
    private TextView text1,text2,switchText,switchCompatText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        aSwitch = (Switch) findViewById(R.id.morning_switch);
        aSwitchCompat = (SwitchCompat) findViewById(R.id.goodnight_switch);
        text1 = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text1);
        //设置Switch事件监听
        aSwitch.setOnCheckedChangeListener(this);
        aSwitchCompat.setOnCheckedChangeListener(this);
        //返回button的实现
        Button backBtn = (Button) findViewById(R.id.remind_btdButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    继承监听器的接口并实现onCheckedChanged方法
    * */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.morning_switch:
                if(isChecked){
                    text1.setText("开");
                }else {
                    text1.setText("关");
                }
                break;
            case R.id.goodnight_switch:
                if(isChecked){
                    text2.setText("开");
                }else {
                    text2.setText("关");
                }
                break;
            default:
                break;
        }
    }

}
