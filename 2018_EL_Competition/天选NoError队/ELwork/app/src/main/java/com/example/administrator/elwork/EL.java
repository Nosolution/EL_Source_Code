package com.example.administrator.elwork;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EL extends AppCompatActivity {
    private Button mBtnEdittext;
    private Button mBtnkfz;
    private Button mBtnstudy;
    private Button mBtntask;
    private Button mBtnexercise;
    private Button mBtnachievement;
    private Button mBtnsleep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        mBtnEdittext = (Button) findViewById(R.id.btn_person1);
        mBtnEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到登录界面
                Intent intent = new Intent(EL.this,loginActivity.class);
                startActivity(intent);
            }
        });
        mBtnkfz = (Button) findViewById(R.id.tokfz);
        mBtnkfz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到开发者界面
                Intent intent = new Intent(EL.this,kfzActivity.class);
                startActivity(intent);
            }
        });
        mBtnstudy = (Button) findViewById(R.id.btn_study);
        mBtnstudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到学习界面
                Intent intent = new Intent(EL.this,StudyActivity.class);
                startActivity(intent);
            }
        });
        mBtntask = (Button) findViewById(R.id.task);
        mBtntask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到任务界面
                Intent intent = new Intent(EL.this,TaskActivity.class);
                startActivity(intent);
            }
        });
        mBtnexercise = (Button) findViewById(R.id.btn_exercise);
        mBtnexercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到运动界面
                Intent intent = new Intent(EL.this,SportActivity.class);
                startActivity(intent);
            }
        });
        mBtnsleep=(Button)findViewById(R.id.btn_sleep);
        mBtnsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到睡眠界面
                Intent intent=new Intent(EL.this,SleepActivity.class);
                startActivity(intent);
            }
        });

        mBtnachievement = (Button) findViewById(R.id.success);
        mBtnachievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到成就界面
                Intent intent = new Intent(EL.this,AchievementActivity.class);
                startActivity(intent);
            }
        });

    }

}
