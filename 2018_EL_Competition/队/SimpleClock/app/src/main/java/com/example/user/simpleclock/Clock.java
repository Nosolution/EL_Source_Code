package com.example.user.simpleclock;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.progresviews.ProgressWheel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Clock extends AppCompatActivity {
    long milliseconds;
    CountDownTimer countDownTimer;
    private int isNoticeOpen = 0;
    RecordTime recordTime;
    long startTime;
    String focusType = "";
    Intent intent;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 基本界面设置
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_clock);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 获得时间信息
        intent = getIntent();
        final int minutes = intent.getIntExtra("toMinutes", 0);
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.my_clock);
            rl.setBackground(getResources().getDrawable(R.mipmap.diary_page));
        }
        milliseconds = toMilliseconds(minutes);
        startTime = System.currentTimeMillis();

        ImageButton timerCancel = (ImageButton) findViewById(R.id.timer_cancel);
        timerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Clock.this);
                alert.setTitle("坚持下去！");
                alert.setMessage("你的专注还没有结束哦！你要放弃此次专注吗？");
                alert.setCancelable(false);
                alert.setPositiveButton("继续专注", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.setNegativeButton("放弃专注", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.cancel();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("result", false);
                        bundle.putInt("reid", id);
                        intent.putExtras(bundle);
                        Clock.this.setResult(RESULT_OK, intent);
                        Clock.this.finish();
                    }
                });
                alert.show();
            }
        });



        final ProgressWheel progressWheel = (ProgressWheel) findViewById(R.id.progress_bar);
        final TextView countTimeText = (TextView) findViewById(R.id.seconds_remaining);
        countDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutesRemaining = millisUntilFinished / 1000 / 60;
                long secondsRemaining = (millisUntilFinished / 1000) % 60;
                String timeRemaining = null;
                if (secondsRemaining < 10 && secondsRemaining >= 0) {
                    timeRemaining = minutesRemaining + ":0" + secondsRemaining;
                } else {
                    timeRemaining = minutesRemaining + ":" + secondsRemaining;
                }
                String definitionWords = null;
                if ((millisUntilFinished * 100.0 / milliseconds) >= 50) {
                    definitionWords = "\n放下手机";
                } else if ((millisUntilFinished * 100.0 / milliseconds) < 50
                        && (millisUntilFinished * 100.0 / milliseconds) > 10) {
                    definitionWords = "\n坚持下去";
                } else if ((millisUntilFinished * 100.0 / milliseconds) <= 10
                        && (millisUntilFinished * 100.0 / milliseconds) > 0) {
                    definitionWords = "\n快成功啦";
                }

                int percentage = (int)Math.round(360.0 * (milliseconds - millisUntilFinished) / milliseconds);
                if (percentage < 1) {
                    percentage = 1;
                }
                progressWheel.setPercentage(percentage);
                //progressWheel.setStepCountText(timeRemaining);
                //progressWheel.setDefText(definitionWords);
                countTimeText.setText(timeRemaining);

            }

            @Override
            public void onFinish() {
                countTimeText.setText("");
                recordTime = new RecordTime();
                recordTime.setFocusType("");
                recordTime.setStartTime(startTime);
                recordTime.setTargetMinute(minutes);
                recordTime.setEndTime(System.currentTimeMillis());
                recordTime.setIsFinished(1);
                recordTime.setRemark("");
                recordTime.save();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Clock.this);
                alertDialog.setTitle("时间到！");
                alertDialog.setMessage("恭喜你又完成了一段专注！");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("结束专注", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent.putExtra("result", true);
                            intent.putExtra("reid", id);
                            setResult(0, intent);
                            Clock.this.finish();
                        }
                    });
                alertDialog.show();
                sendNotice();
            }

        };
        countDownTimer.start();

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Clock.this);
        alert.setTitle("坚持下去！");
        alert.setMessage("你的专注还没有结束哦！你要放弃此次专注吗？");
        alert.setCancelable(false);
        alert.setPositiveButton("继续专注", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setNegativeButton("放弃专注", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countDownTimer.cancel();
                intent.putExtra("result", false);
                intent.putExtra("reid", id);
                setResult(0, intent);
                Clock.this.finish();
            }
        });
        alert.show();
    }


    public long toMilliseconds(int minutes) {
        return minutes * 60 * 1000;
    }

    // 启动Clock活动的方法
    public static void actionStart(Context context, int minutes) {
        Intent intent = new Intent(context, Clock.class);
        intent.putExtra("toMinutes", minutes);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, int minutes, int id) {
        Intent intent = new Intent(context, Clock.class);
        intent.putExtra("toMinutes", minutes);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    // 发送结束通知
    public void sendNotice() {
        NotificationManager manager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, HomePageActivity.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "channel_timesup";
            String id = "channel_timesup";
            String description = "channel for Android O";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            manager.createNotificationChannel(mChannel);
            Notification notification = new NotificationCompat.Builder(this, "channel_timesup")
                    .setContentTitle("FOXUS")
                    .setContentText("恭喜你又完成了一次专注！")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.circle_logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                    .setVibrate(new long[] {0, 1000, 1000, 1000})
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                    .build();
            manager.notify(1, notification);
        } else {
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("FOXUS")
                    .setContentText("恭喜你又完成了一次专注！")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.circle_logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                    .setVibrate(new long[] {0, 1000, 1000, 1000})
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(4)
                    .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                    .build();
            manager.notify(1, notification);
        }
    }

}
