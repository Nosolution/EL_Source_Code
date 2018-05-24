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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Caffee extends AppCompatActivity {
    private TextView mTvShow;
    private TextView item_show;
    int book;//书的数量
    int book_total;
    int reading_time;
    MediaPlayer sound;
    MediaPlayer extra_sound;
    boolean book_bool= false;//书的声音是否可用
    boolean library_sound= false;//书的声音是否可用
    long settime=25*60*1000;//settime用于保存计时器的总时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffee);
        sound = MediaPlayer.create(Caffee.this,R.raw.chaos2);
        extra_sound = MediaPlayer.create(Caffee.this,R.raw.book_sound);
        mTvShow = (TextView) findViewById(R.id.timer);
        item_show = (TextView) findViewById(R.id.book_amount);
        SharedPreferences reader = getSharedPreferences("data", MODE_PRIVATE);
        book = reader.getInt("book_temp",10);
        book_total = reader.getInt("book_total",0);
        item_show.setText(String.valueOf(book));
        Button button_caffee = (Button) findViewById(R.id.button_caffee2town);
        button_caffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
                finish();
            }
        });
    }
    public void update_data(){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("book_temp",book);
        editor.putInt("book_total",book_total);
        editor.putInt("reading_time",reading_time);
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
            book++;
            book_total++;
            reading_time++;
            item_show.setText(String.valueOf(book));
            Toast.makeText(Caffee.this,"专注完成",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor_achievement = getSharedPreferences("data_achievement", MODE_PRIVATE).edit();
            editor_achievement.putBoolean("book_1",(book_total>=10));
            editor_achievement.putBoolean("book_2",(book_total>=100));
            editor_achievement.putBoolean("book_3",(book_total>=500));
            editor_achievement.putBoolean("book_4",(reading_time>=5));
            editor_achievement.putBoolean("book_5",(reading_time>=10));
            editor_achievement.putBoolean("book_6",(reading_time>=50));
            editor_achievement.apply();
            if((book_total==10)||(book_total==100)||(book_total==500)||(reading_time==5)||(reading_time==10)||(reading_time==50)){
                Toast.makeText(Caffee.this,"获得新成就",Toast.LENGTH_SHORT).show();
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
    public void boss(View v){
        AlertDialog.Builder give_coffee = new AlertDialog.Builder(Caffee.this);
        give_coffee.setTitle("咖啡馆老板");
        give_coffee.setMessage("你要看书还是喝咖啡吗？");
        give_coffee.setPositiveButton("看书"+"(" +book+")", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (book>0) {
                    book_bool = true;
                    library_sound = true;
                    boss2();
                }else{
                    book_bool = false;
                    library_sound = false;
                    boss2();
                    Toast.makeText(Caffee.this,"已经没有书可以看了...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        give_coffee.setNegativeButton("喝咖啡", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                book_bool = false;
                library_sound = false;
                boss2();
            }
        });
        give_coffee.show();

    }//boss按钮将会打开一个对话框用于选择时间
    public void time_choose_caffee(View v){
        AlertDialog.Builder time_dia = new AlertDialog.Builder(Caffee.this);
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
    public void boss2(){
        AlertDialog.Builder time_dia = new AlertDialog.Builder(Caffee.this);
        time_dia.setTitle("咖啡店老板");
        if(book_bool){
            time_dia.setMessage("你要在这里看书吗？");
        }else{
            time_dia.setMessage("你要喝咖啡吗？我来帮你找找有没有书。");
        }
        time_dia.setPositiveButton("嗯", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(book_bool){book--; item_show.setText(String.valueOf(book));extra_sound.start();}
                extra_sound.setLooping(true);
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putInt("collecting_time",0);
                editor.putInt("fishing_time",0);
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
        extra_sound.pause();
        sound.stop();
        super.finish();
    }
}
