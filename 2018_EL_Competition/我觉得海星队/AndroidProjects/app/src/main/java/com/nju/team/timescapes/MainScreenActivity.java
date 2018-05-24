package com.nju.team.timescapes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.KeyEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import android.text.TextUtils;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.graphics.drawable.GradientDrawable;
import java.util.Date;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainScreenActivity extends AppCompatActivity {

    private ImageButton btn_msc;
    private ImageButton btn_settings;
    private ImageButton btn_achievements;
    private ImageButton btn_calendar;
    private boolean isMscIconChanged = true;
    private TextView text_hour;
    private TextView text_minute;
    private TextView text_second;
    CountDownTimer counter;
    private Button start_timing;
    private Button cancel_timing;
    PrefManager pref;
    private RelativeLayout space;
    private CircleAnimation mCircle;
    private Typeface typeFace;
    private int rd_min=1;
    private int rd_max=99;
    private ImageView main_img;
    private String default_time_hour;
    private String default_time_minute;
    private int size;
    private boolean COUNTER_ON_RUN = false;
    private ImageButton btn_tag;


    private long time_length = 1800000;
    private String filename = "user_settings.txt";
    private ArrayList<String> settings_list = new ArrayList<String>();
    private Long FOCUS_TIME;
    private boolean IS_REGISTERED;
    private Long TIME_SUM;
    private Long ATTEMPT_SUM;
    private Long SUCCESS_SUM;
    private Long TOTAL_SCORE;
    private boolean FAIL_MINIMIZE;
    private boolean REMIND_FAILURE;
    private String REMIND_METHOD;
    private boolean APP_DETECT;
    private boolean ACHIEVE_REMIND;

    private boolean ACH_120_REMIND;
    private boolean ACH_600_REMIND;
    private boolean ACH_1200_REMIND;
    private boolean ACH_3000_REMIND;
    private boolean ACH_6000_REMIND;

    private String tag_transfer;


    //生成一个1-99随机数来决定使用哪张插画图片
    Random rd = new Random();
    int decide = rd.nextInt(rd_max)%(rd_max-rd_min+1) + rd_min;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(0x80000000, 0x80000000);

        setContentView(R.layout.activity_main_screen);

        //注册广播
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        //按钮描边
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // 画框
        drawable.setStroke(2,Color.argb(255,188,165,75)); // 边框粗细及颜色
        drawable.setColor(0x00000000); // 边框内部颜色


        //读取设置
        settings_list = readData(filename);
        FOCUS_TIME = Long.parseLong(settings_list.get(5).substring(0,settings_list.get(5).length()-1));
        IS_REGISTERED = Boolean.parseBoolean(settings_list.get(0).substring(0,settings_list.get(0).length()-1));
        TIME_SUM = Long.parseLong(settings_list.get(10).substring(0,settings_list.get(10).length()-1));
        ATTEMPT_SUM = Long.parseLong(settings_list.get(11).substring(0,settings_list.get(11).length()-1));
        SUCCESS_SUM = Long.parseLong(settings_list.get(12).substring(0,settings_list.get(12).length()-1));
        TOTAL_SCORE = Long.parseLong(settings_list.get(13).substring(0,settings_list.get(13).length()-1));
        FAIL_MINIMIZE = Boolean.parseBoolean(settings_list.get(7).substring(0,settings_list.get(7).length()-1));
        REMIND_FAILURE = Boolean.parseBoolean(settings_list.get(8).substring(0,settings_list.get(8).length()-1));
        REMIND_METHOD = settings_list.get(3).substring(0,settings_list.get(3).length()-1);
        APP_DETECT = Boolean.parseBoolean(settings_list.get(6).substring(0,settings_list.get(6).length()-1));
        ACHIEVE_REMIND = Boolean.parseBoolean(settings_list.get(4).substring(0,settings_list.get(4).length()-1));
        ACH_120_REMIND = Boolean.parseBoolean(settings_list.get(16).substring(0,settings_list.get(16).length()-1));
        ACH_600_REMIND = Boolean.parseBoolean(settings_list.get(17).substring(0,settings_list.get(17).length()-1));
        ACH_1200_REMIND = Boolean.parseBoolean(settings_list.get(18).substring(0,settings_list.get(18).length()-1));
        ACH_3000_REMIND = Boolean.parseBoolean(settings_list.get(19).substring(0,settings_list.get(19).length()-1));
        ACH_6000_REMIND = Boolean.parseBoolean(settings_list.get(20).substring(0,settings_list.get(20).length()-1));

        time_length = FOCUS_TIME*(60*1000);


        //设置插画
        main_img = (ImageView) findViewById(R.id.pic_short);
        if(decide<=27){
            main_img.setImageDrawable(getResources().getDrawable(R.drawable.pic_short));
        }else if(27<decide && decide<=54){
            main_img.setImageDrawable(getResources().getDrawable(R.drawable.pic_middle));
        }else if(54<decide && decide<=81){
            main_img.setImageDrawable(getResources().getDrawable(R.drawable.pic_long));
        }else if(81<decide && decide<=99){
            main_img.setImageDrawable(getResources().getDrawable(R.drawable.pic_egg_1));
        }

        pref = new PrefManager(this);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        //按钮指定
        btn_msc = (ImageButton) findViewById(R.id.musicoff);
        btn_settings = (ImageButton) findViewById(R.id.imageButton_settings);
        btn_achievements = (ImageButton) findViewById(R.id.imageButton_rankings);
        btn_calendar = (ImageButton) findViewById(R.id.imageButton_calendar);
        start_timing = (Button) findViewById(R.id.button_timer);
        cancel_timing = (Button) findViewById(R.id.button_timer_cancel);
        cancel_timing.setBackground(drawable);
        btn_tag = (ImageButton) findViewById(R.id.imageButton_tag);

        text_hour = (TextView) findViewById(R.id.time_hour);
        text_minute = (TextView) findViewById(R.id.time_minute);
        text_second = (TextView) findViewById(R.id.time_second);

        final long default_hour_temp = time_length/(1000*60*60);
        long default_minute_temp = (time_length - default_hour_temp*(1000*60*60))/(60*1000);
        default_time_hour = Long.toString(default_hour_temp);
        default_time_minute = Long.toString(default_minute_temp);
        if(default_time_hour.length() == 1){
            default_time_hour = "0" + default_time_hour;
        }
        if(default_time_minute.length() == 1){
            default_time_minute = "0" + default_time_minute;
        }
        text_hour.setText(default_time_hour);
        text_minute.setText(default_time_minute);

        //设置细体字
        typeFace =Typeface.createFromAsset(getAssets(),"font/fzltcxhjt.TTF");
        text_hour.setTypeface(typeFace);
        text_minute.setTypeface(typeFace);
        text_second.setTypeface(typeFace);

        //设置圆环
        space = (RelativeLayout) findViewById(R.id.circle_animation);
        mCircle = new CircleAnimation(this);
        space.addView(mCircle);
        mCircle.render();

        final Vibrator vibrate_after_comp = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //让状态栏透明
        changeStatusBarColor();



        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nothing
                AlertDialog.Builder builder = new AlertDialog.Builder(MainScreenActivity.this);
                builder.setTitle("选择标签");
                final String[] tag = {"未设定", "学习", "工作","阅读","运动","社交","娱乐","其他"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setSingleChoiceItems(tag, 0, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Toast.makeText(MainScreenActivity.this, "您选择了" + tag[which], Toast.LENGTH_SHORT).show();
                        String tag_selected = tag[which]+"\r\n";
                        tag_transfer = tag[which];
                        settings_list.set(21,tag_selected);
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        String result = "";
                        for(int i=0;i<settings_list.size();i++){
                            result = result + settings_list.get(i);
                        }
                        saveData(result,filename);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.show();
            }
        });

        //计时器
        counter = new CountDownTimer(time_length,1000) {
            @Override
            public void onTick(long l) {
                long time_hours = l/(1000*60*60);
                long time_minutes = (l - time_hours*(1000*60*60))/(60*1000);
                long time_seconds = (l - time_hours*(1000*60*60) - time_minutes*(1000*60))/1000;

                String str_hour = Long.toString(time_hours);
                String str_minutes = Long.toString(time_minutes);
                String str_seconds = Long.toString(time_seconds);
                if(str_hour.length() == 1){
                    str_hour = "0" + str_hour;
                }
                if(str_minutes.length() == 1){
                    str_minutes = "0" + str_minutes;
                }
                if(str_seconds.length() == 1){
                    str_seconds = "0" + str_seconds;
                }

                text_hour.setText(str_hour);
                text_minute.setText(str_minutes);
                text_second.setText(str_seconds);

            }

            @Override
            public void onFinish() {
                COUNTER_ON_RUN = false;


                //获取当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time1=simpleDateFormat.format(date);

                //获取tag类型
                String tag=tag_transfer;

                //存储数据到txt中供TimeRecord取用
                saveDataAppend(String.valueOf(FOCUS_TIME),"FocusTime.txt");
                saveDataAppend(time1,"Time.txt");
                saveDataAppend(tag,"Tag.txt");


                start_timing.setVisibility(View.VISIBLE);
                cancel_timing.setVisibility(View.INVISIBLE);
                btn_achievements.setVisibility(View.VISIBLE);
                btn_settings.setVisibility(View.VISIBLE);
                btn_calendar.setVisibility(View.VISIBLE);
                btn_tag.setVisibility(View.GONE);
                //若未开启成就提示，则显示默认的完成通知，否则根据成就来判断
                TIME_SUM = TIME_SUM + FOCUS_TIME;
                setAlertWindow_finishTime();
                if(IS_REGISTERED && ACHIEVE_REMIND && !ACH_120_REMIND && TIME_SUM >=120 && TIME_SUM < 600){
                    settings_list.set(16,"true\r\n");
                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                    // TODO Auto-generated method stub
                    AlertDialog.Builder build = new AlertDialog.Builder(MainScreenActivity.this);
                    build.setTitle("祝贺！")
                            .setMessage("您已获得成就： 树林")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("去看看", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    Intent mainIntent = new Intent(MainScreenActivity.this, AchievementsPanelActivity.class);
                                    MainScreenActivity.this.startActivity(mainIntent);
                                    MainScreenActivity.this.finish();

                                }
                            })
                            .show();
                }else if(IS_REGISTERED && ACHIEVE_REMIND && !ACH_600_REMIND && TIME_SUM >=600 && TIME_SUM < 1200){
                    settings_list.set(17,"true\r\n");
                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                    // TODO Auto-generated method stub
                    AlertDialog.Builder build = new AlertDialog.Builder(MainScreenActivity.this);
                    build.setTitle("祝贺！")
                            .setMessage("您已获得成就： 河流")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("去看看", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    Intent mainIntent = new Intent(MainScreenActivity.this, AchievementsPanelActivity.class);
                                    MainScreenActivity.this.startActivity(mainIntent);
                                    MainScreenActivity.this.finish();

                                }
                            })
                            .show();
                }else if(IS_REGISTERED && ACHIEVE_REMIND && !ACH_1200_REMIND && TIME_SUM >=1200 && TIME_SUM < 3000){
                    settings_list.set(18,"true\r\n");
                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                    // TODO Auto-generated method stub
                    AlertDialog.Builder build = new AlertDialog.Builder(MainScreenActivity.this);
                    build.setTitle("祝贺！")
                            .setMessage("您已获得成就： 海洋")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("去看看", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    Intent mainIntent = new Intent(MainScreenActivity.this, AchievementsPanelActivity.class);
                                    MainScreenActivity.this.startActivity(mainIntent);
                                    MainScreenActivity.this.finish();

                                }
                            })
                            .show();
                }else if(IS_REGISTERED && ACHIEVE_REMIND && !ACH_3000_REMIND && TIME_SUM >=3000 && TIME_SUM < 6000){
                    settings_list.set(19,"true\r\n");
                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                    // TODO Auto-generated method stub
                    AlertDialog.Builder build = new AlertDialog.Builder(MainScreenActivity.this);
                    build.setTitle("祝贺！")
                            .setMessage("您已获得成就： 天空")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("去看看", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    Intent mainIntent = new Intent(MainScreenActivity.this, AchievementsPanelActivity.class);
                                    MainScreenActivity.this.startActivity(mainIntent);
                                    MainScreenActivity.this.finish();

                                }
                            })
                            .show();
                }else if(IS_REGISTERED && ACHIEVE_REMIND && !ACH_6000_REMIND && TIME_SUM >=6000){
                    settings_list.set(20,"true\r\n");
                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                    // TODO Auto-generated method stub
                    AlertDialog.Builder build = new AlertDialog.Builder(MainScreenActivity.this);
                    build.setTitle("祝贺！")
                            .setMessage("您已获得成就： 无尽？")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("去看看", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    Intent mainIntent = new Intent(MainScreenActivity.this, AchievementsPanelActivity.class);
                                    MainScreenActivity.this.startActivity(mainIntent);
                                    MainScreenActivity.this.finish();

                                }
                            })
                            .show();
                }

                if(REMIND_METHOD.equals("vibrate")){
                    vibrate_after_comp.vibrate(1000);
                }else if(REMIND_METHOD.equals("closed")){
                    //Do Nothing
                }else if(REMIND_METHOD.equals("vibrate_and_ring")){
                    vibrate_after_comp.vibrate(1000);
                    Intent intent = new Intent(MainScreenActivity.this,ringService.class);
                    startService(intent);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainScreenActivity.this,ringService.class);
                            stopService(intent);
                        }
                    }, 5000);
                }else if(REMIND_METHOD.equals("ring")){
                    Intent intent = new Intent(MainScreenActivity.this,ringService.class);
                    startService(intent);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainScreenActivity.this,ringService.class);
                            stopService(intent);
                        }
                    }, 5000);
                }
                text_second.setText("00");
                text_minute.setText(default_time_minute);
                text_hour.setText(default_time_hour);

                //判断是否满足加积分的条件
                if(FAIL_MINIMIZE && IS_REGISTERED){
                    ATTEMPT_SUM++;
                    SUCCESS_SUM++;
                    if(FOCUS_TIME<=30){
                        TOTAL_SCORE = TOTAL_SCORE + FOCUS_TIME;
                    }else if(FOCUS_TIME<=60 && FOCUS_TIME>30){
                        TOTAL_SCORE = TOTAL_SCORE + FOCUS_TIME*2;
                    }else if(FOCUS_TIME>=90){
                        TOTAL_SCORE = TOTAL_SCORE + FOCUS_TIME*3;
                    }
                    String timesum = Long.toString(TIME_SUM)+"\r\n";
                    String attemptsum = Long.toString(ATTEMPT_SUM)+"\r\n";
                    String successsum = Long.toString(SUCCESS_SUM)+"\r\n";
                    String totalscore = Long.toString(TOTAL_SCORE)+"\r\n";

                    settings_list.set(10,timesum);
                    settings_list.set(11,attemptsum);
                    settings_list.set(12,successsum);
                    settings_list.set(13,totalscore);

                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                }
            }
        };


        //按钮事件
        btn_msc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMscIconChanged){
                    Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                    startService(intent);
                    btn_msc.setImageResource(R.drawable.icon_musicon);
                    isMscIconChanged = false;
                }
                else{
                    btn_msc.setImageResource(R.drawable.icon_musicoff);
                    isMscIconChanged = true;
                    Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                    stopService(intent);
                }
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainScreenActivity.this, SettingsPanelActivity.class);
                MainScreenActivity.this.startActivity(mainIntent);
                MainScreenActivity.this.finish();
                Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                stopService(intent);
                Intent intent2 = new Intent(MainScreenActivity.this,ringService.class);
                stopService(intent2);
            }
        });

        btn_achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IS_REGISTERED){
                    Intent mainIntent = new Intent(MainScreenActivity.this, AchievementsPanelActivity.class);
                    MainScreenActivity.this.startActivity(mainIntent);
                    MainScreenActivity.this.finish();
                    Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                    stopService(intent);
                    Intent intent2 = new Intent(MainScreenActivity.this,ringService.class);
                    stopService(intent2);
                }else{
                    setAlertWindow_notRegistered();
                }
            }
        });


        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainScreenActivity.this, calendar.class);
                MainScreenActivity.this.startActivity(mainIntent);
                MainScreenActivity.this.finish();
                Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                stopService(intent);
                Intent intent2 = new Intent(MainScreenActivity.this,ringService.class);
                stopService(intent2);
            }
        });


        start_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_timing.setVisibility(View.VISIBLE);
                start_timing.setVisibility(View.INVISIBLE);
                btn_achievements.setVisibility(View.INVISIBLE);
                btn_settings.setVisibility(View.INVISIBLE);
                btn_calendar.setVisibility(View.INVISIBLE);
                btn_msc.setVisibility(View.VISIBLE);
                btn_tag.setVisibility(View.VISIBLE);
                counter.start();
                COUNTER_ON_RUN = true;
            }
        });

        cancel_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertWindow_cancelTiming();
                ATTEMPT_SUM++;
                String attemptsum = Long.toString(ATTEMPT_SUM)+"\r\n";
                settings_list.set(11,attemptsum);
                String result = "";
                for(int i=0;i<settings_list.size();i++){
                    result = result + settings_list.get(i);
                }
                saveData(result,filename);
            }
        });

    }

    /**
     * 监听是否点击了home键将客户端推到后台
     */
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                //Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_LONG).show();
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //若点到home键，或是从多任务返回
                    if(COUNTER_ON_RUN && FAIL_MINIMIZE){
                        start_timing.setVisibility(View.VISIBLE);
                        cancel_timing.setVisibility(View.INVISIBLE);
                        btn_achievements.setVisibility(View.VISIBLE);
                        btn_settings.setVisibility(View.VISIBLE);
                        btn_calendar.setVisibility(View.VISIBLE);
                        btn_tag.setVisibility(View.GONE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                counter.cancel();
                                text_second.setText("00");
                                text_minute.setText(default_time_minute);
                                text_hour.setText(default_time_hour);
                                if(REMIND_FAILURE){
                                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    vb.vibrate(1000);
                                }
                            }
                        }, 1000);

                        COUNTER_ON_RUN = false;
                        ATTEMPT_SUM++;
                        String attemptsum = Long.toString(ATTEMPT_SUM)+"\r\n";
                        settings_list.set(11,attemptsum);
                        String result = "";
                        for(int i=0;i<settings_list.size();i++){
                            result = result + settings_list.get(i);
                        }
                        saveData(result,filename);
                    }
                }
            }
        }
    };


    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                String title_text = "<font color='#FF1C2E51'>"+"退出"+"</font>";
                build.setTitle(Html.fromHtml(title_text))
                        .setMessage("您确定要退出 Timescapes 吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                if(COUNTER_ON_RUN && APP_DETECT){
                                    COUNTER_ON_RUN =false;
                                    counter.cancel();
                                    ATTEMPT_SUM++;
                                    String attemptsum = Long.toString(ATTEMPT_SUM)+"\r\n";
                                    settings_list.set(11,attemptsum);
                                    String result = "";
                                    for(int i=0;i<settings_list.size();i++){
                                        result = result + settings_list.get(i);
                                    }
                                    saveData(result,filename);
                                }else{
                                    COUNTER_ON_RUN=false;
                                }
                                Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                                stopService(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .show();
                break;
            case KeyEvent.KEYCODE_HOME:
                if(FAIL_MINIMIZE){
                    start_timing.setVisibility(View.VISIBLE);
                    cancel_timing.setVisibility(View.INVISIBLE);
                    btn_achievements.setVisibility(View.VISIBLE);
                    btn_settings.setVisibility(View.VISIBLE);
                    btn_calendar.setVisibility(View.VISIBLE);
                    btn_tag.setVisibility(View.GONE);
                    counter.cancel();
                    text_second.setText("00");
                    text_minute.setText(default_time_minute);
                    text_hour.setText(default_time_hour);
                    ATTEMPT_SUM++;
                    String attemptsum = Long.toString(ATTEMPT_SUM)+"\r\n";
                    settings_list.set(11,attemptsum);
                    String result = "";
                    for(int i=0;i<settings_list.size();i++){
                        result = result + settings_list.get(i);
                    }
                    saveData(result,filename);
                }
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void setAlertWindow_finishTime() {
        // TODO Auto-generated method stub
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("祝贺！")
                .setMessage("您完成了一次专注。")
                .setNegativeButton("好", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .show();
    }

    public void setAlertWindow_notRegistered() {
        // TODO Auto-generated method stub
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("未注册")
                .setMessage("您还没有创建账户，无法开启积分与成就功能。快去设置中注册一个属于您的账户吧！")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent mainIntent = new Intent(MainScreenActivity.this, SettingsPanelActivity.class);
                        MainScreenActivity.this.startActivity(mainIntent);
                        MainScreenActivity.this.finish();
                        Intent intent = new Intent(MainScreenActivity.this,mscService.class);
                        stopService(intent);
                        Intent intent2 = new Intent(MainScreenActivity.this,ringService.class);
                        stopService(intent2);

                    }
                })
                .show();
    }

    public void setAlertWindow_cancelTiming() {
        // TODO Auto-generated method stub
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("放弃？")
                .setMessage("您选择了停止计时，这将使您无法获得专注积分。要放弃本次专注吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                })
                .setPositiveButton("放弃", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        COUNTER_ON_RUN = false;
                        start_timing.setVisibility(View.VISIBLE);
                        cancel_timing.setVisibility(View.INVISIBLE);
                        btn_achievements.setVisibility(View.VISIBLE);
                        btn_settings.setVisibility(View.VISIBLE);
                        btn_calendar.setVisibility(View.VISIBLE);
                        btn_tag.setVisibility(View.GONE);
                        counter.cancel();
                        text_second.setText("00");
                        text_minute.setText(default_time_minute);
                        text_hour.setText(default_time_hour);

                    }
                })
                .show();
    }

    //存数据
    private void saveData(String data,String filename) {

        String content = data + "\n";
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //不覆盖
    private void saveDataAppend(String data,String filename) {

        String content = data + "\n";
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {

            FileOutputStream fos = new FileOutputStream(file,true);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //读数据
    private ArrayList<String> readData(String filename){
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }

        File file=new File(textsDir,filename);
        BufferedReader reader=null;
        String temp=null;
        ArrayList<String> settings = new ArrayList<String>();
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                settings.add(temp+"\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return settings;
    }

}
