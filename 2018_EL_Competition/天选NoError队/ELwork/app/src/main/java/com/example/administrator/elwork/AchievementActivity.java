package com.example.administrator.elwork;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementActivity extends AppCompatActivity {
    private Button bt1;
    private Button bt1_1;
    private Button bt2;
    private Button bt2_2;
    private Button bt3;
    private Button bt3_3;
    private Button bt4;
    private Button bt4_4;
    private Button bt5;
    private Button bt5_5;

    private Button j0;
    private Button j1;
    private Button j2;
    private Button j3;
    private Button j4;
    private Button j5;

    private TextView textView;

    static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        bt1=(Button)findViewById(R.id.jewel1);
        bt1_1=(Button)findViewById(R.id.jewel1_1);
        bt2=(Button)findViewById(R.id.jewel2);
        bt2_2=(Button)findViewById(R.id.jewel2_2);
        bt3=(Button)findViewById(R.id.jewel3);
        bt3_3=(Button)findViewById(R.id.jewel3_3);
        bt4=(Button)findViewById(R.id.jewel4);
        bt4_4=(Button)findViewById(R.id.jewel4_4);
        bt5=(Button)findViewById(R.id.jewel5);
        bt5_5=(Button)findViewById(R.id.jewel5_5);

        j0=(Button)findViewById(R.id.ac0);
        j1=(Button)findViewById(R.id.ac1);
        j2=(Button)findViewById(R.id.ac2);
        j3=(Button)findViewById(R.id.ac3);
        j4=(Button)findViewById(R.id.ac4);
        j5=(Button)findViewById(R.id.ac5);

        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        textView=(TextView)findViewById(R.id.achievement);
        int number=pref.getInt("sum",0);
        int hour=number/3600;
        int minutes=number%3600/60;
        int sec=number-hour*3600-minutes*60;
        textView.setText("当前已专注时间："+hour+"小时"+minutes+"分钟"+sec+"秒");

        i=pref.getInt("sum",0);
        if(i>60){
            bt1.setVisibility(View.VISIBLE);
            bt1_1.setVisibility(View.GONE);

            j1.setVisibility(View.VISIBLE);
            j0.setVisibility(View.GONE);

            if(i>600){
                bt2.setVisibility(View.VISIBLE);
                bt2_2.setVisibility(View.GONE);

                j2.setVisibility(View.VISIBLE);
                j1.setVisibility(View.GONE);

                if(i>6000){
                    bt3.setVisibility(View.VISIBLE);
                    bt3_3.setVisibility(View.GONE);

                    j3.setVisibility(View.VISIBLE);
                    j2.setVisibility(View.GONE);

                    if(i>60000){
                        bt4.setVisibility(View.VISIBLE);
                        bt4_4.setVisibility(View.GONE);

                        j4.setVisibility(View.VISIBLE);
                        j3.setVisibility(View.GONE);

                        if(i>600000){
                            bt5.setVisibility(View.VISIBLE);
                            bt5_5.setVisibility(View.GONE);

                            j5.setVisibility(View.VISIBLE);
                            j4.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"获得成就：初入江湖（专注时间达到1分钟）",Toast.LENGTH_SHORT).show();
            }
        });

        bt1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"专注时间未达到1分钟",Toast.LENGTH_SHORT).show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"获得成就：小有所成（专注时间达到10分钟）",Toast.LENGTH_SHORT).show();
            }
        });

        bt2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"专注时间未达到10分钟",Toast.LENGTH_SHORT).show();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"获得成就：渐入佳境（专注时间达到100分钟）",Toast.LENGTH_SHORT).show();
            }
        });

        bt3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"专注时间未达到100分钟",Toast.LENGTH_SHORT).show();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"获得成就：炉火纯青（专注时间达到1000分钟）",Toast.LENGTH_SHORT).show();
            }
        });

        bt4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"专注时间未达到1000分钟",Toast.LENGTH_SHORT).show();
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"获得成就：专注宗师（专注时间达到10000分钟）",Toast.LENGTH_SHORT).show();
            }
        });

        bt5_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"专注时间未达到10000分钟",Toast.LENGTH_SHORT).show();
            }
        });

        j0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"成就完成度:0%",Toast.LENGTH_SHORT).show();
            }
        });

        j1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"成就完成度:20%",Toast.LENGTH_SHORT).show();
            }
        });

        j2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"成就完成度:40%",Toast.LENGTH_SHORT).show();
            }
        });

        j3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"成就完成度:60%",Toast.LENGTH_SHORT).show();
            }
        });

        j4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"成就完成度:80%",Toast.LENGTH_SHORT).show();
            }
        });

        j5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AchievementActivity.this,"成就完成度:100%,恭喜您已经完成了所有成就！",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
