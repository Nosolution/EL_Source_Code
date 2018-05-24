package com.example.vincentmoriarty.sound_of_island;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Beach extends AppCompatActivity {
    private TextView mTvShow;
    private TextView item_show;
    int fish;//持有鱼的数量
    int fish_total;//鱼的累积总数
    MediaPlayer sound;
    MediaPlayer extra_sound;
    int fishing_time = 0;
    boolean fish_bool = false;
    boolean boat_sound = false;//船的声音是否可用
    long settime=25*60*1000;//settime用于保存计时器的总时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach);
        sound = MediaPlayer.create(Beach.this,R.raw.beach_sound);
        extra_sound = MediaPlayer.create(Beach.this,R.raw.boat);
        mTvShow = (TextView) findViewById(R.id.timer);
        item_show = (TextView) findViewById(R.id.fish_amount);
        SharedPreferences reader = getSharedPreferences("data", MODE_PRIVATE);
        fish = reader.getInt("fish_temp",10);
        fish_total = reader.getInt("fish_total",0);
        fishing_time = reader.getInt("fishing_time",0);
        item_show.setText(String.valueOf(fish));
        Button button_beach = (Button) findViewById(R.id.button_beach2town);
        button_beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
                finish();
            }
        });
    }
    public void update_data(){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("fish_temp",fish);
        editor.putInt("fish_total",fish_total);
        editor.putInt("fishing_time",fishing_time);
        editor.apply();
    }
    private CountDownTimer timer = new CountDownTimer(settime,1000) {
        long minute;
        long second;
        String min_str;
        String sec_str;
        @Override
        public void onTick(long millisUntilFinished) {
            minute = millisUntilFinished/60000;
            second = (millisUntilFinished - minute*60000)/1000;
            if (minute<10){
                min_str = "0"+String.valueOf(minute);
            }else{
                min_str = String.valueOf(minute);
            }
            if(second<10){
                sec_str ="0"+String.valueOf(second);
            }else{
                sec_str = String.valueOf(second);
            }
            mTvShow.setText(min_str+":"+sec_str);
        }
        @Override
        public void onFinish() {
            extra_sound.pause();
            extra_sound.seekTo(0);
            sound.pause();
            sound.seekTo(0);
            fish_total++;
            fish++;
            fishing_time++;
            item_show.setText(String.valueOf(fish));
            Toast.makeText(Beach.this,"专注完成",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor_achievement = getSharedPreferences("data_achievement", MODE_PRIVATE).edit();
            editor_achievement.putBoolean("fish_1",(fish_total>=10));
            editor_achievement.putBoolean("fish_2",(fish_total>=100));
            editor_achievement.putBoolean("fish_3",(fish_total>=500));
            editor_achievement.putBoolean("fish_4",(fishing_time>=5));
            editor_achievement.putBoolean("fish_5",(fishing_time>=10));
            editor_achievement.putBoolean("fish_6",(fishing_time>=50));
            editor_achievement.apply();
            if((fish_total==10)||(fish_total==100)||(fish_total==500)||(fishing_time==5)||(fishing_time==10)||(fishing_time==50)){
                Toast.makeText(Beach.this,"获得新成就",Toast.LENGTH_SHORT).show();
            }
            update_data();
            mTvShow.setEnabled(true);
            mTvShow.setText("00:00");
        }
    };
    private void changeMillisInFuture(long time) {
        try {
            // 反射父类CountDownTimer的mMillisInFuture字段，动态改变定时总时间
            Class clazz = Class.forName("android.os.CountDownTimer");
            Field field = clazz.getDeclaredField("mMillisInFuture");
            field.setAccessible(true);
            field.set(timer, time);
        } catch (Exception e) {
            Log.e("Ye", "反射类android.os.CountDownTimer.mMillisInFuture失败： "+ e);
        }
    }//用于改变CountDownTimer的总时间
    public void fisherman(View v){
        AlertDialog.Builder give_fish = new AlertDialog.Builder(Beach.this);
        give_fish.setTitle("渔夫");
        give_fish.setMessage("要送一些鱼给他吗？");
        give_fish.setPositiveButton("是的"+"(" +fish+")", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (fish>0) {
                    fish_bool = true;
                    boat_sound = true;
                    fisherman2();
                }else{
                    fish_bool = false;
                    boat_sound = false;
                    fisherman2();
                    Toast.makeText(Beach.this,"你没有足够的鱼",Toast.LENGTH_SHORT).show();
                }
            }
        });
        give_fish.setNegativeButton("不了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fish_bool = false;
                boat_sound = false;
                fisherman2();
            }
        });
        give_fish.show();

    }//fisherman按钮将会打开一个对话框用于选择时间
    public void time_choose_beach(View v){
        AlertDialog.Builder time_dia = new AlertDialog.Builder(Beach.this);
        time_dia.setTitle("时间选择");
        settime = 25*60000;
        String[] time_menu = new String[]{"5min", "10min", "25min", "30min", "60min"};
        time_dia.setSingleChoiceItems(time_menu, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0: settime = 5*60*1000;break;
                    case 1: settime = 10*60*1000;break;
                    case 2: settime = 25*60*1000;break;
                    case 3: settime = 30*60*1000;break;
                    case 4: settime = 60*60*1000;break;
                }
            }
        });
        time_dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeMillisInFuture(settime);
            }
        });
        time_dia.create().show();
    }//打开时settime更新为25min
    public void fisherman2(){
        AlertDialog.Builder time_dia = new AlertDialog.Builder(Beach.this);
        time_dia.setTitle("渔夫");
        if(fish_bool){
            time_dia.setMessage("我把船借给你吧。");
        }else{
            time_dia.setMessage("你要在这里钓鱼吗？");
        }
        time_dia.setPositiveButton("嗯", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(fish_bool){ fish--;item_show.setText(String.valueOf(fish));extra_sound.start();}
                extra_sound.setLooping(true);
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putInt("collecting_time",0);
                editor.putInt("reading_time",0);
                update_data();
                changeMillisInFuture(settime);
                timer.start();
                sound.start();
                sound.setLooping(true);
            }
        });
        time_dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
            }
        });
        time_dia.create().show();
    }//二级对话框，用于选择时间
    @Override
    public void finish() {
        sound.stop();
        extra_sound.stop();
        super.finish();
    }
}
