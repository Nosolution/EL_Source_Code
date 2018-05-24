package com.example.administrator.elwork;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //圆形按钮
    private Button mbutton;
    private CountDownView countDownView;
    private Button mpause;
    private Button mstop;
    private Button mcontinue;
    private MediaPlayer mediaPlayer;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    final Date date = new Date();
    //
    //private Button mtest;
    //
    //时间选项菜单
    private  String[] mtime = new String[]{"1","5","20","40","50","120"};
    static int m;//获取点击按钮的分钟数，以便传入数据使用
    final static String NAME="time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化播放器
        initMediaPlayer();
        //指定sharedpreferences的文件名为time
        editor = getSharedPreferences(NAME, MODE_PRIVATE).edit();
        pref = getSharedPreferences(NAME,MODE_PRIVATE);
        mbutton=(Button)findViewById(R.id.btn_1);
        mpause= (Button)findViewById(R.id.pause);
        mstop=(Button)findViewById(R.id.stop);
        mcontinue=(Button)findViewById(R.id.ccontinue);

        //
        //mtest = (Button)findViewById(R.id.test);
        //

        //设置圆形按钮不可点击
        mbutton.setEnabled(false);
        //半圆菜单点击
        final SemicircleMenu mSemicircleMenu = (SemicircleMenu) findViewById(R.id.circlemenu);
        mSemicircleMenu.setMenuItemIconsAndTexts(mtime);
        mSemicircleMenu.setOnMenuItemClickListener(new SemicircleMenu.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(MainActivity.this, mtime[pos], Toast.LENGTH_SHORT).show();
                countDownView = (CountDownView) findViewById(R.id.cdv);
                if (mSemicircleMenu.isClickable()) {
                    mSemicircleMenu.setVisibility(View.GONE);
                    //mbutton.setVisibility(View.VISIBLE);

                    //圆形按钮设置为可点击
                    mbutton.setEnabled(true);
                }
                //设置倒计时
                countDownView.setCountdownTime(Integer.parseInt(mtime[pos])*60);
                int i=Integer.parseInt(mtime[pos]);
                m=i;//获得分钟数
                //调用开始倒计时
                //countDownView.startCountDown();
                countDownView.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
                    @Override
                    public void countDownFinished() {
                        //倒计时结束 暂停按钮不可见
                        mpause.setVisibility(View.GONE);
                        mbutton.setVisibility(View.VISIBLE);
                        mSemicircleMenu.setVisibility(View.VISIBLE);
                        countDownView.setVisibility(View.GONE);
                        mbutton.setEnabled(false);

                        mediaPlayer.reset();
                        initMediaPlayer();

                        //editor.putLong("end_time",date.getTime());
                        //editor.apply();
                        //Toast.makeText(MainActivity.this,"end time is "+(pref.getLong("end_time",0)+""),
                        //      Toast.LENGTH_SHORT).show();

                        //PLAN B 获得currentProgress
//                        float time = countDownView.getmCurrentProgress();
//                        Toast.makeText(MainActivity.this,"add is "+time,
//                                Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor=getSharedPreferences(NAME,MODE_PRIVATE).edit();//自动结束时的方法,并考虑点击继续后自动结束的情况
                        SharedPreferences pref=getSharedPreferences(NAME,MODE_PRIVATE);
                        int sum=pref.getInt("sum",0)+m*60-pref.getInt("start",0);
                        editor.putInt("sum",sum);
                        editor.apply();

                        //Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
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

        //圆形按钮点击
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///CountDownView countDownView = (CountDownView) findViewById(R.id.cdv);
                ///countDownView.setCountdownTime(30);
                ///countDownView.startCountDown();

                if(mbutton.isClickable()){
                    //点击圆形按钮开始倒计时
                    countDownView.startCountDown();

                    countDownView.setVisibility(View.VISIBLE);
                    mbutton.setVisibility(View.GONE);
                    mpause.setVisibility(View.VISIBLE);

                    //播放音乐
                    mediaPlayer.start();

                    //存储开始时间
                    //editor.putLong("start_time",date.getTime());
                    //editor.apply();

                    //Toast.makeText(MainActivity.this,"start time is "+(pref.getLong("start_time",0)+""),
                    //        Toast.LENGTH_SHORT).show();

                }
                else{
                    mbutton.setVisibility(View.VISIBLE);
                }

                SharedPreferences.Editor editor=getSharedPreferences(NAME ,MODE_PRIVATE).edit();
                editor.putInt("start",0);//每次开始专注就初始化start
                editor.apply();
                /*countDownView.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
                    @Override
                    public void countDownFinished() {
                        Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

        //暂停倒计时
        mpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpause.isClickable()){
                    mstop.setVisibility(View.VISIBLE);
                    mcontinue.setVisibility(View.VISIBLE);
                    mpause.setVisibility(View.GONE);

                    mediaPlayer.pause();
                }
                countDownView.pauseCountDownTime();
                //获得系统时间
                //editor.putLong("end_time",date.getTime());
                //editor.apply();
                //Toast.makeText(MainActivity.this,"end time is "+(pref.getLong("end_time",0)+""),
                //       Toast.LENGTH_SHORT).show();
                //Long add = pref.getLong("end_time",0)-pref.getLong("start_time",0);
                //Toast.makeText(MainActivity.this,"add is "+(add+""),
                //      Toast.LENGTH_SHORT).show();

//                float time = countDownView.getCurrentProgress();
//                Toast.makeText(MainActivity.this,"add is "+time,
//                        Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=getSharedPreferences(NAME,MODE_PRIVATE).edit();
                SharedPreferences pref=getSharedPreferences(NAME,MODE_PRIVATE);
                editor.putInt("end",(int)(countDownView.getmCurrentProgress()/120*m));//存入暂停时间
                int sum=pref.getInt("sum",0)+(int)(countDownView.getmCurrentProgress()/120*m)-pref.getInt("start",0);

                editor.putInt("sum",sum);//存入暂停时间和开始时间差
                editor.apply();
                System.out.println("start:"+pref.getInt("start",0)+" end:"+pref.getInt("end",0));//控制台测试
                Toast.makeText(MainActivity.this,"end:"+pref.getInt("end",0),Toast.LENGTH_SHORT).show();

            }
        });
        //倒计时停止
        mstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mstop.isClickable()){
                    mstop.setVisibility(View.GONE);
                    mpause.setVisibility(View.GONE);
                    mcontinue.setVisibility(View.GONE);

                    mbutton.setVisibility(View.VISIBLE);
                    mbutton.setEnabled(false);
                    mSemicircleMenu.setVisibility(View.VISIBLE);
                    countDownView.setVisibility(View.GONE);

                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                countDownView.stopCountDownTime();

                //获得系统时间
                //editor.putLong("end_time",date.getTime());
                //editor.apply();
                //Toast.makeText(MainActivity.this,"end time is "+(pref.getLong("end_time",0)+""),
                //      Toast.LENGTH_SHORT).show();

                float time = countDownView.getmCurrentProgress();
                Toast.makeText(MainActivity.this,"add is "+time,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //倒计时继续
        mcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mcontinue.isClickable()){
                    mcontinue.setVisibility(View.GONE);
                    mstop.setVisibility(View.GONE);
                    mpause.setVisibility(View.VISIBLE);

                    mediaPlayer.start();
                }
                countDownView.resumeCounDownTime();
                //editor.putLong("start_time",date.getTime());
                //editor.apply();

                //Toast.makeText(MainActivity.this,"start time is "+(pref.getLong("start_time",0)+""),
                //      Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putInt("start",(int)(countDownView.getmCurrentProgress()/120*m));//存入开始时间
                editor.apply();
                Toast.makeText(MainActivity.this,"start:"+(int)(countDownView.getmCurrentProgress()/120*m),Toast.LENGTH_SHORT).show();
            }
        });

    }



/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    //播放器初始化
    private void initMediaPlayer(){
        mediaPlayer= MediaPlayer.create(this,R.raw.hhh);
    }
/*
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
        }
    }
    */

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
