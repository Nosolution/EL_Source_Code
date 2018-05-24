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
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Forest extends AppCompatActivity {
    private TextView mTvShow;
    private TextView item_show;
    int apple;//持有苹果的数量
    int apple_total;
    MediaPlayer sound;
    MediaPlayer extra_sound;
    int collecting_time;
    boolean apple_bool = false;
    boolean windbell_sound = false;//风铃的声音是否可用
    long settime=25*60*1000;//settime用于保存计时器的总时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest);
        mTvShow = (TextView) findViewById(R.id.timer);
        item_show = (TextView) findViewById(R.id.apple_amount);
        sound = MediaPlayer.create(Forest.this,R.raw.forest_sound);
        extra_sound = MediaPlayer.create(Forest.this,R.raw.bells);
        SharedPreferences reader = getSharedPreferences("data", MODE_PRIVATE);
        apple = reader.getInt("apple_temp",10);
        apple_total = reader.getInt("apple_total",0);
        collecting_time = reader.getInt("collecting_time",0);
        item_show.setText(String.valueOf(apple));
        Button button_forest = (Button) findViewById(R.id.button_forest2town);
        button_forest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
                sound.stop();
                finish();
            }
        });
    }
    public void update_data(){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("apple_temp",apple);
        editor.putInt("apple_total",apple_total);
        editor.putInt("collecting_time",collecting_time);
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
            if((apple_bool)&&(millisUntilFinished%10000==0)){

            }
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
            sound.pause();
            sound.seekTo(0);
            extra_sound.pause();
            extra_sound.seekTo(0);
            apple++;
            apple_total++;
            collecting_time++;
            item_show.setText(String.valueOf(apple));
            Toast.makeText(Forest.this,"专注完成",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor_achievement = getSharedPreferences("data_achievement", MODE_PRIVATE).edit();
            editor_achievement.putBoolean("apple_1",(apple_total>=10));
            editor_achievement.putBoolean("apple_2",(apple_total>=100));
            editor_achievement.putBoolean("apple_3",(apple_total>=500));
            editor_achievement.putBoolean("apple_4",(collecting_time>=5));
            editor_achievement.putBoolean("apple_5",(collecting_time>=10));
            editor_achievement.putBoolean("apple_6",(collecting_time>=50));
            editor_achievement.apply();
            if((apple_total==10)||(apple_total==100)||(apple_total==500)||(collecting_time==5)||(collecting_time==10)||(collecting_time==50)){
                Toast.makeText(Forest.this,"获得新成就",Toast.LENGTH_SHORT).show();
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
    public void forester(View v){
        AlertDialog.Builder give_apple = new AlertDialog.Builder(Forest.this);
        give_apple.setTitle("护林者");
        give_apple.setMessage("要送一些苹果给她吗？");
        give_apple.setPositiveButton("是的"+"(" +apple+")", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (apple>0) {
                    apple_bool=true;
                    windbell_sound = true;
                    forester2();
                }else{
                    apple_bool = false;
                    windbell_sound = false;
                    forester2();
                    Toast.makeText(Forest.this,"你没有足够的苹果",Toast.LENGTH_SHORT).show();
                }
            }
        });
        give_apple.setNegativeButton("不了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apple_bool = false;
                windbell_sound = false;
                forester2();
            }
        });
        give_apple.show();

    }//forester按钮将会打开一级对话框
    public void time_choose_forest(View v){
        AlertDialog.Builder time_dia = new AlertDialog.Builder(Forest.this);
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
    public void forester2(){
        AlertDialog.Builder forester2_dia = new AlertDialog.Builder(Forest.this);
        forester2_dia.setTitle("护林者");
        if(apple_bool){
            forester2_dia.setMessage("到林中小屋来休息一会吧。");
        }else{
            forester2_dia.setMessage("你要在树林散步吗？");
        }
        forester2_dia.setPositiveButton("嗯", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(apple_bool){apple--;item_show.setText(String.valueOf(apple));extra_sound.setVolume(0.1f,0.1f);
                    extra_sound.start();}
                extra_sound.setLooping(true);
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putInt("fishing_time",0);
                editor.putInt("reading_time",0);
                update_data();
                changeMillisInFuture(settime);
                timer.start();
                sound.start();
                sound.setLooping(true);
            }
        });
        forester2_dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
            }
        });
        forester2_dia.create().show();
    }//二级对话框
    @Override
    public void finish() {
        sound.stop();
        extra_sound.stop();
        super.finish();
    }
}
