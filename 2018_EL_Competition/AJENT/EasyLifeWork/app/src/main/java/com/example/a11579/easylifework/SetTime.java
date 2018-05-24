package com.example.a11579.easylifework;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SetTime extends AppCompatActivity
        implements DialogInterface.OnClickListener{
    Button startButton;
    Button backButton;
    EditText minuteText;
    EditText secondText;
    EditText hourText;
    TextView text_select;
    Toast toast=null;
    private MediaPlayer mp=new MediaPlayer();
    int minute;
    int second;
    int hour;
    int allTimeCount=0;
    ImageView szh;
    Timer timer=new Timer();
    TimerTask timerTask=null;
    private static final int MSG_WHAT_TIME_IS_UP = 1;
    private static final int MSG_WHAT_TIME_TICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        final MediaPlayer mp =MediaPlayer.create(this, R.raw.mu);
        startButton = (Button) findViewById(R.id.button_start);
        backButton=(Button)findViewById(R.id.button_back);
        minuteText = (EditText)findViewById(R.id.minute);
        secondText = (EditText)findViewById(R.id.second);
        hourText = (EditText)findViewById(R.id.hour);
        text_select=(TextView)findViewById(R.id.text_select);
        backButton.setEnabled(false);
        startButton.setEnabled(false);
        szh=(ImageView)findViewById(R.id.imageView44);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("任务完成了吗？")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_menu_edit)
                .setTitle("任务结果")
                .setPositiveButton("完成",this )
                .setNegativeButton("失败",this);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startButton.setEnabled(false);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                if (!hourText.getText().toString().equals("")) {
                    hour = Integer.parseInt(hourText.getText().toString());
                }
                if (!minuteText.getText().toString().equals("")) {
                    minute = Integer.parseInt(minuteText.getText().toString());
                }
                if (!secondText.getText().toString().equals("")) {
                    second = Integer.parseInt(secondText.getText().toString());
                }
                if (hour!=0||minute != 0 || second != 0) {
                    System.out.println(hour+""+minute+":"+second);
                }
                minuteText.setFocusable(false);
                secondText.setFocusable(false);
                hourText.setFocusable(false);
                text_select.setText("时间还剩");
                if (timerTask == null) {
                    allTimeCount=hour*60*60+minute*60+second;
                    timerTask = new TimerTask() {

                        @Override
                        public void run() {
                            allTimeCount--;
                            handle.sendEmptyMessage(MSG_WHAT_TIME_TICK);
                            if (allTimeCount <= 0) {
                                handle.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
                                stopTimer();

                            }
                        }

                        private void stopTimer() {
                            if (timerTask!=null) {
                                timerTask.cancel();
                                timerTask=null;
                            }
                        }

                        private Handler handle = new Handler(){



                            public void handleMessage(android.os.Message msg) {
                                switch (msg.what) {
                                    case MSG_WHAT_TIME_TICK:
                                        int hou=(allTimeCount/3600)%60;
                                        int min = (allTimeCount/60)%60;
                                        int sec = allTimeCount%60;
                                        hourText.setText(hou+"时");
                                        minuteText.setText(min+"分");
                                        secondText.setText(sec+"秒");
                                        break;
                                    case MSG_WHAT_TIME_IS_UP:
                                      mp.start();
                                        builder.show();
                                    default:
                                        break;
                                }
                            };
                        };
                    };
                    timer.schedule(timerTask, 1000, 1000);
                }
            }

        });
        szh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hourText.getText().toString().equals("")||!minuteText.getText().toString().equals("")||!secondText.getText().toString().equals("")){
                    startButton.setEnabled(true);
                    backButton.setEnabled(true);
                }else{
                    toast = Toast.makeText(getApplicationContext(), "还没有设定时间哦！", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                builder.show();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
            SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
            int apple=readnumber.getInt("apple", -1);
            apple=apple+5;
            editor2.putInt("apple",apple);
            editor2.commit();
            mp.release();
            startButton.setEnabled(false);
            backButton.setEnabled(false);
            toast = Toast.makeText(getApplicationContext(), "恭喜完成任务！(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent it=new Intent(this,MainActivity.class);
            startActivity(it);
            this.finish();
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE ){
            mp.release();
            toast = Toast.makeText(getApplicationContext(), "任务失败了，记得把原因记录下来哦\n(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent it=new Intent(this, ChoActivity.class);
            startActivity(it);
            this.finish();
            startButton.setEnabled(false);
            backButton.setEnabled(false);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((keyCode == KeyEvent.KEYCODE_BACK) ||
                (keyCode == KeyEvent.KEYCODE_HOME))
                && event.getRepeatCount() == 0) {
            dialog_Exit(SetTime.this);
        }
        return false;
    }

    public static void dialog_Exit(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                    }
                });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
}
