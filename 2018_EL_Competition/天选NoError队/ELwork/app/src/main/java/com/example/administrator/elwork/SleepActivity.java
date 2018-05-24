package com.example.administrator.elwork;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SleepActivity extends AppCompatActivity {

    private Button bt;
    private Button pause;
    private Button stop;
    private Button continue1;
    private CountDownView countDownView;
    private MediaPlayer mediaPlayer;

    private String[] mItemTexts = new String[] { "1min", "5min", "10min",
            "30min","60min","120min"};

    static int m;//获取点击按钮的分钟数，以便传入数据使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);

        initMediaPlayer();
        ////
        bt=(Button)findViewById(R.id.sleep_main);
        bt.setEnabled(false);
        final SemicircleMenu mSemicircleMenu = (SemicircleMenu) findViewById(R.id.circlemenu);

        stop=(Button)findViewById(R.id.stop_sleep);
        pause=(Button)findViewById(R.id.pause_sleep);
        continue1=(Button)findViewById(R.id.continue_sleep);


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pause.isClickable()){
                    pause.setVisibility(View.GONE);
                    continue1.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    mediaPlayer.pause();
                }
                countDownView.pauseCountDownTime();

                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                editor.putInt("end",(int)(countDownView.getmCurrentProgress()/120*m));//存入暂停时间
                int sum=pref.getInt("sum",0)+(int)(countDownView.getmCurrentProgress()/120*m)-pref.getInt("start",0);

                editor.putInt("sum",sum);//存入暂停时间和开始时间差
                editor.apply();
                //System.out.println("start:"+pref.getInt("start",0)+" end:"+pref.getInt("end",0));//控制台测试
                //Toast.makeText(SleepActivity.this,"end:"+pref.getInt("end",0),Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stop.isClickable()){
                    stop.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                    continue1.setVisibility(View.GONE);

                    bt.setVisibility(View.VISIBLE);
                    bt.setEnabled(false);
                    countDownView.setVisibility(View.GONE);
                    mSemicircleMenu.setVisibility(View.VISIBLE);
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                countDownView.stopCountDownTime();

            }
        });

        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(continue1.isClickable()){
                    pause.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.GONE);
                    continue1.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
                countDownView.resumeCounDownTime();

                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putInt("start",(int)(countDownView.getmCurrentProgress()/120*m));//存入开始时间
                editor.apply();
                //Toast.makeText(SleepActivity.this,"start:"+(int)(countDownView.getmCurrentProgress()/120*m),Toast.LENGTH_SHORT).show();
            }
        });

        mSemicircleMenu.setMenuItemIconsAndTexts(mItemTexts);
        mSemicircleMenu.setOnMenuItemClickListener(new SemicircleMenu.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                countDownView=(CountDownView)findViewById(R.id.cdv);
                Toast.makeText(SleepActivity.this, mItemTexts[pos], Toast.LENGTH_SHORT).show();
                if (mSemicircleMenu.isClickable()) {
                    mSemicircleMenu.setVisibility(View.GONE);
                    bt.setEnabled(true);
                }

                //
                String s=mItemTexts[pos].substring(0,mItemTexts[pos].length()-3);
                int i=Integer.parseInt(s);
                m=i;//获得分钟数
                //
                countDownView.setCountdownTime(i*60);
                countDownView.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
                    @Override
                    public void countDownFinished() {
                        pause.setVisibility(View.GONE);
                        bt.setVisibility(View.VISIBLE);
                        mSemicircleMenu.setVisibility(View.VISIBLE);
                        bt.setEnabled(false);
                        countDownView.setVisibility(View.GONE);
                        mediaPlayer.reset();
                        initMediaPlayer();
                        Toast.makeText(SleepActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();//自动结束时的方法,并考虑点击继续后自动结束的情况
                        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                        if(!stop.isClickable()) {
                            int sum = pref.getInt("sum", 0) + m * 60 - pref.getInt("start", 0);
                            editor.putInt("sum", sum);
                            editor.apply();
                        }
                        else{
                            int sum = pref.getInt("sum", 0) - pref.getInt("start", 0);
                            editor.putInt("sum", sum);
                            editor.apply();
                        }
                    }
                });
            }
        });

        mSemicircleMenu.setOnCentralItemCallback(new SemicircleMenu.OnCentralItemCallback() {
            @Override
            public void centralItemOperate(int pos) {
                Log.d("cylog", "The position is " + pos);
            }
        });
        ////


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///CountDownView countDownView = (CountDownView) findViewById(R.id.cdv);
                ///countDownView.setCountdownTime(30);
                ///countDownView.startCountDown();

                if(bt.isClickable()){
                    bt.setVisibility(View.GONE);
                    countDownView.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    countDownView.startCountDown();
                    mediaPlayer.start();
                }
                else{
                    bt.setVisibility(View.VISIBLE);
                }
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putInt("start",0);//每次开始专注就初始化start
                editor.apply();
                /*countDownView.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
                    @Override
                    public void countDownFinished() {
                        Toast.makeText(StudyActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

    }
    private void initMediaPlayer(){
        mediaPlayer=MediaPlayer.create(this,R.raw.zzz);
    }


    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}



