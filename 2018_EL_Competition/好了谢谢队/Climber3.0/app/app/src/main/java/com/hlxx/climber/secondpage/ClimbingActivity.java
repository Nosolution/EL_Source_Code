package com.hlxx.climber.secondpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hlxx.climber.R;
import com.hlxx.climber.firstpage.TimeSettingActivity;
import com.hlxx.climber.secondpage.records.Record;
import com.hlxx.climber.secondpage.records.RecorderEditor;
import com.hlxx.climber.secondpage.settings.*;
import com.hlxx.climber.thirdpage.EndingActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;

import static com.hlxx.climber.secondpage.settings.TimeGet.getTimeSecondSetted;
import static com.hlxx.climber.secondpage.settings.TimeGet.setTimeSecondSetted;
import static com.hlxx.climber.secondpage.settings.TimePut.intsToSecond;
import static com.hlxx.climber.secondpage.settings.TimePut.stringToInts;

public class ClimbingActivity extends AppCompatActivity {
    private static boolean willScreenOn = false;
    private long firstPressedTime;
    private boolean isSwitch = false;
    private boolean cancle = false;
    private CountDownTimer timer;
    private VibrateSetter vibrateSetter = new VibrateSetter(this);
    private boolean isScreenOn = true;
    private static Thread gcRequest1;
    private static Thread gcRequest2;
    private Record aRecord = new Record();
    private RecorderEditor aRecorderEditor;
    private static int hightPixels;
    private static int widthPixels;
    private static double backgroundSpeed;
    private static double mountainSpeed = 10;
    private static Bitmap sourceBGBitmap;
    private static Bitmap toShowBGBitmap;
    private static Bitmap sourceMBitmap;
    private static Bitmap toShowMBitmap;
    private Handler mHandler = new MyHandler(this);
    private static boolean amiationStart = false;
    private static boolean anotherThreadStart = false;

    //设置是否常亮
    public static void setWillScreenOn(boolean willScreenOn) {
        ClimbingActivity.willScreenOn = willScreenOn;
    }

    //是否熄屏
    private void screenState() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
    }

    //用于处理背景图片
    private static class MyHandler extends Handler {
        private final WeakReference<ClimbingActivity> weakReference;

        private MyHandler(ClimbingActivity activty) {
            this.weakReference = new WeakReference<>(activty);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ClimbingActivity activity = weakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        if (sourceBGBitmap != null && sourceMBitmap != null) {
                            TextView tv = activity.findViewById(R.id.lastTime);
                            TextView hint = activity.findViewById(R.id.hint);
                            int timeUsed = intsToSecond(stringToInts(tv.getText().toString()));
                            int yLocation = (int) backgroundSpeed * timeUsed + hightPixels;
                            if (yLocation / (double) sourceBGBitmap.getHeight() < 0.595) {
                                tv.setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_start));
                            } else if (yLocation / (double) sourceBGBitmap.getHeight() > 0.79) {
                                tv.setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                                ((TextView) activity.findViewById(R.id.theRestTimePrompt)).setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                                ((TextView) activity.findViewById(R.id.theLastTimePrompt)).setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                                ((TextView) activity.findViewById(R.id.restTime)).setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                            }
                            if (yLocation / (double) sourceBGBitmap.getHeight() > 0.5) {
                                hint.setText(R.string.noon_hint);
                            }
                            if (yLocation / (double) sourceBGBitmap.getHeight() > 0.71) {
                                hint.setText(R.string.dust_hint);
                            }
                            if (yLocation / (double) sourceBGBitmap.getHeight() > 0.91) {
                                hint.setText(R.string.night_hint);
                            }
                            tv = null;

                            ImageView bgdImageView = activity.findViewById(R.id.climb_background);
                            yLocation = yLocation > sourceBGBitmap.getHeight() ? sourceBGBitmap.getHeight() : yLocation;
                            toShowBGBitmap = null;
                            toShowBGBitmap = Bitmap.createBitmap(sourceBGBitmap, 0, sourceBGBitmap.getHeight() - yLocation, sourceBGBitmap.getWidth(), hightPixels);
                            bgdImageView.setImageBitmap(toShowBGBitmap);

                            int[] restTime = stringToInts(String.valueOf(((TextView) activity.findViewById(R.id.restTime)).getText()));
                            if (restTime[0] != 0 || restTime[1] != 0 || restTime[2] > 31) {
                                ImageView mtImageView = activity.findViewById(R.id.mountain);
                                yLocation = (int) mountainSpeed * timeUsed + hightPixels;
                                yLocation = yLocation > sourceMBitmap.getHeight() ? sourceMBitmap.getHeight() : yLocation;
                                toShowMBitmap = null;
                                toShowMBitmap = Bitmap.createBitmap(sourceMBitmap, 0, sourceMBitmap.getHeight() - yLocation, sourceMBitmap.getWidth(), hightPixels);
                                mtImageView.setImageBitmap(toShowMBitmap);
                            } else {
                                gcRequest1.interrupt();
                                gcRequest1 = null;
                                gcRequest2.start();
                                anotherThreadStart = true;
                            }
                        }
                        break;
                    case 2:
                        if (sourceBGBitmap != null) {
                            TextView tv = activity.findViewById(R.id.lastTime);
                            TextView hint = activity.findViewById(R.id.hint);
                            int timeUsed = intsToSecond(stringToInts(tv.getText().toString()));
                            int yLocation = (int) backgroundSpeed * timeUsed + hightPixels;
                            if (yLocation / (double) sourceBGBitmap.getHeight() > 0.79) {
                                tv.setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                                ((TextView) activity.findViewById(R.id.theRestTimePrompt)).setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                                ((TextView) activity.findViewById(R.id.theLastTimePrompt)).setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                                ((TextView) activity.findViewById(R.id.restTime)).setTextColor(ContextCompat.getColor(activity, R.color.color_time_rest_end));
                            }
                            if (yLocation / (double) sourceBGBitmap.getHeight() > 0.71) {
                                hint.setText(R.string.dust_hint);
                            }
                            if (yLocation / (double) sourceBGBitmap.getHeight() > 0.91) {
                                hint.setText(R.string.night_hint);
                            }
                            tv = null;

                            if (!amiationStart) {
                                amiationStart = true;
                                ImageView climber = activity.findViewById(R.id.climber);

                                TranslateAnimation animation = new TranslateAnimation(0, 0, 0, (float) climber.getTop() * -1);
                                animation.setDuration(33000);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        ImageView climber = activity.findViewById(R.id.climber);
                                        climber.layout(climber.getLeft(), climber.getTop() - climber.getTop(), climber.getRight(), climber.getBottom() - climber.getTop());
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                                climber.startAnimation(animation);
                            }
                            ImageView bgdImageView = activity.findViewById(R.id.climb_background);
                            yLocation = yLocation > sourceBGBitmap.getHeight() ? sourceBGBitmap.getHeight() : yLocation;
                            toShowBGBitmap = null;
                            toShowBGBitmap = Bitmap.createBitmap(sourceBGBitmap, 0, sourceBGBitmap.getHeight() - yLocation, sourceBGBitmap.getWidth(), hightPixels);
                            bgdImageView.setImageBitmap(toShowBGBitmap);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //返回由TimeGet的时间决定的新的计时器
    private CountDownTimer creatNewOne() {
        final TextView theRestTime = findViewById(R.id.restTime);
        return new CountDownTimer(TimeGet.getTime(true), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                theRestTime.setText(TimePut.intsToString(millisUntilFinished));
            }

            //倒计时结束后
            @Override
            public void onFinish() {
                theRestTime.setText("到达");
                recordWrite(true);
                amiationStart = false;
                anotherThreadStart = false;
                sourceMBitmap = null;
                sourceBGBitmap = null;
                ((TextView) findViewById(R.id.theRestTimePrompt)).setText("");
                ((Chronometer) findViewById(R.id.lastTime)).stop();//Chronometer暂停
                Toast.makeText(ClimbingActivity.this, "成功！", Toast.LENGTH_LONG).show();//进行弹窗提示
                vibrateSetter.makeVibrate(true);//处理震动
                IsForeground.setTimes(0);
                if (gcRequest2 != null) {
                    gcRequest2.interrupt();
                    gcRequest2 = null;
                }
                screenState();
                //切换
                Intent nextIntent = new Intent(ClimbingActivity.this, EndingActivity.class);
                startActivity(nextIntent);
                mHandler = null;
                finish();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climbing);
        String data = getIntent().getStringExtra("time");
        setTimeSecondSetted(Integer.parseInt(data));
        aRecorderEditor = new RecorderEditor(getFilesDir());
        int settingTime = getTimeSecondSetted();
        aRecord.setTimeSetted(settingTime);//默认+和上一页面交接+初始化记录仪

        if (willScreenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }//设置是否常亮

        //输出持续时间
        Chronometer lastTimeChronometer = findViewById(R.id.lastTime);
        lastTimeChronometer.setBase(SystemClock.elapsedRealtime() - 1000);
        lastTimeChronometer.start();


        //剩余时间输出
        timer = creatNewOne();
        timer.start();


        //设置背景
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        hightPixels = dm.heightPixels;
        widthPixels = dm.widthPixels;
        dm = null;
        sourceBGBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.climb_sky);
        backgroundSpeed = (sourceBGBitmap.getHeight() - hightPixels) / (double) getTimeSecondSetted();
        ImageView bgImageView = findViewById(R.id.climb_background);
        toShowBGBitmap = Bitmap.createBitmap(sourceBGBitmap, 0, sourceBGBitmap.getHeight() - hightPixels, sourceBGBitmap.getWidth(), hightPixels);
        bgImageView.setImageBitmap(toShowBGBitmap);

        //设置山
        sourceMBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.climb_mountain);
        mountainSpeed = mountainSpeed < (sourceMBitmap.getHeight() - hightPixels) / (double) getTimeSecondSetted() ? mountainSpeed : (sourceMBitmap.getHeight() - hightPixels) / (double) getTimeSecondSetted();
        ImageView mtImageView = findViewById(R.id.mountain);
        toShowMBitmap = Bitmap.createBitmap(sourceMBitmap, 0, sourceMBitmap.getHeight() - hightPixels, sourceMBitmap.getWidth(), hightPixels);
        mtImageView.setImageBitmap(toShowMBitmap);

        Toast.makeText(ClimbingActivity.this, "加油！", Toast.LENGTH_LONG).show();//进行弹窗提示
    }


    @Override
    protected void onResume() {
        super.onResume();

        //放弃按钮
        (findViewById(R.id.button_giveUp)).setOnClickListener((view) -> {
            if (System.currentTimeMillis() - firstPressedTime < 5000) {
                cancle = true;
                recordWrite(false);
                amiationStart = false;
                anotherThreadStart = false;
                sourceMBitmap = null;
                sourceBGBitmap = null;
                if (gcRequest2 != null) {
                    gcRequest2.interrupt();
                    gcRequest2 = null;
                }
                if (gcRequest1 != null) {
                    gcRequest1.interrupt();
                    gcRequest1 = null;
                }
                mHandler = null;
                startActivity(new Intent(ClimbingActivity.this, TimeSettingActivity.class));
                finish();
            } else {
                Toast.makeText(getBaseContext(), "骚年，何弃疗！", Toast.LENGTH_SHORT).show();
                firstPressedTime = System.currentTimeMillis();
            }
        });

        //处理缓存+更新背景
        gcRequest2 = new Thread(() -> {
            boolean threadState = true;
            while (threadState) {
                try {
                    Message msg = new Message();
                    msg.what = 2;  //消息(一个整型值)
                    if (mHandler != null) {
                        mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    threadState = false;
                }
            }
            System.gc();
        });

        if (!anotherThreadStart) {
            gcRequest1 = new Thread(() -> {
                boolean threadState = true;
                while (threadState) {
                    try {
                        Message msg = new Message();
                        msg.what = 1;  //消息(一个整型值)
                        if (mHandler != null) {
                            mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        threadState = false;
                    }
                }
                System.gc();
            });
            if (!gcRequest1.isAlive()) {
                gcRequest1.start();
            }
        } else {
            if (!gcRequest2.isAlive()) {
                gcRequest2.start();
            }
        }


    }

    @Override
    protected void onPause() {
        final TextView theRestTime = findViewById(R.id.restTime);//剩余时间栏，右下角
        super.onPause();

        screenState();
        //是否后台
        if (!cancle && !theRestTime.getText().toString().equals("到达") && isScreenOn) {
            isSwitch = true;
            IsForeground.setTimes(IsForeground.getTimes() + 1);
            try {
                TimeChange.changeTime(theRestTime.getText().toString());
            } catch (TooManyTimesException e) {//强制退出
                cancle = true;
                timer.cancel();
                timer = null;
                recordWrite(false);
                amiationStart = false;
                anotherThreadStart = false;
                sourceMBitmap = null;
                sourceBGBitmap = null;
                if (gcRequest2 != null) {
                    gcRequest2.interrupt();
                    gcRequest2 = null;
                }
                if (gcRequest1 != null) {
                    gcRequest1.interrupt();
                    gcRequest1 = null;
                }
                mHandler = null;
                startActivity(new Intent(ClimbingActivity.this, EndingActivity.class));
                finish();
                vibrateSetter.makeVibrate(false);//处理震动
            }
            if (timer != null) {
                timer.cancel();
            }
            timer = null;
            timer = creatNewOne();
            timer.start();
        }
        if (gcRequest1 != null) {
            gcRequest1.interrupt();
        }
    }

    @Override
    protected void onDestroy() {
        ((Chronometer) findViewById(R.id.lastTime)).stop();
        timer.cancel();
        sourceMBitmap = null;
        sourceBGBitmap = null;
        timer = null;
        super.onDestroy();
        IsForeground.setTimes(0);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 5000) {
            cancle = true;
            mHandler = null;
            sourceMBitmap = null;
            sourceBGBitmap = null;
            recordWrite(false);
            ActivityCompat.finishAffinity(this);//退出整个程序
        } else {
            Toast.makeText(getBaseContext(), "再点一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }

    private void recordWrite(boolean finish) {
        Calendar tempVer = Calendar.getInstance();
        int seconds = (int) ((tempVer.getTimeInMillis() - aRecord.getNow().getTimeInMillis()) / 1000);
        aRecord.setTotalTime(seconds);
        aRecord.setSwitchTimes(IsForeground.getTimes());
        aRecord.setFinish(finish);
        aRecorderEditor.setFinish(finish);
        aRecord.setLevel();
        try {
            aRecorderEditor.oneRecordAdd(aRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
