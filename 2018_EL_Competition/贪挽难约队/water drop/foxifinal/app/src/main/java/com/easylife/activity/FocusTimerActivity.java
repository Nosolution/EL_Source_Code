package com.easylife.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylife.entity.Focus;
import com.easylife.entity.User;
import com.easylife.util.LoadingSettingsManager;
import com.easylife.view.CountdownCircle;
import com.easylife.view.RadialGradientView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 主界面
 *
 * @author zengtao 2015年5月20日 下午7:36:03
 */
public class FocusTimerActivity extends Activity {
    private static final String CHANNEL_ID = "focus";
    private String[] sentences;

    private RadialGradientView dot; //倒计时小圆点
    private CountdownCircle countdownCircle;
    private TextView focusResult;//专注成功或者失败的提示语
    private Button focusStop;
    private LinearLayout frameLayout;
    private MediaPlayer mediaPlayer1;
    private TextView sentence;
    private TextView sentenceOrigin;

    private TextView mTv_show;
    private long millisInFuture1 = 0;
    private long millisInFuture2 = 0;//60s
    private long countDownInterval = 1000;//每隔一秒
    private MyCountDownTimer myCountDownTimer;
    private Timer actualFocusTimeRecord;//实际专注时间计数器
    private int focusAndRelaxTimeInterval = 0;//专注多长时间可以休息一次

    private TranslateAnimation mShowAction;

    private String mode;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
    private Timer time;//专注时中断计时器
    private boolean isRelaxing = false;
    private int actualTime = 0;//实际专注时间
    private Boolean isFocusSuccess = false;//记录专注是否成功
    private Button gotoRelax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取专注类型
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        //加载设置
        loadSettings();

        millisInFuture1 = intent.getLongExtra("time1", 0);
        millisInFuture2 = intent.getLongExtra("time2", 0);

        Slide slide1 = new Slide();
        slide1.setSlideEdge(Gravity.TOP);
        slide1.setDuration(1000);

        Slide slide2 = new Slide();
        slide2.setSlideEdge(Gravity.TOP);

        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(slide2);
        getWindow().setExitTransition(slide1);

        setContentView(R.layout.focustimer_layout);

        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setShowAction();
        //初始化控件
        initView();

        Handler handler = new Handler();
        handler.postDelayed(() -> mTv_show.setVisibility(View.VISIBLE), 900);
        startcount();

        //开始记录实际专注时间
        final int[] relaxTimes = {1};
        actualFocusTimeRecord = new Timer();
        actualFocusTimeRecord.schedule(new TimerTask() {
            @Override
            public void run() {
                actualTime += 1;
                //更新文本
                String[] parts = sentences[(int) (Math.random() * sentences.length)].split("——");
                FocusTimerActivity.this.runOnUiThread(() -> {
                    sentence.setText(parts[0]);
                    sentenceOrigin.setText("——" + parts[1]);
                });

                LoadingSettingsManager manager = new LoadingSettingsManager(FocusTimerActivity.this);
                int focusTime = Integer.parseInt(manager.getFocusTimeValue());
                if (actualTime >= focusTime * relaxTimes[0]) {
                    //显示休息按钮
                    FocusTimerActivity.this.runOnUiThread(() -> gotoRelax.setVisibility(View.VISIBLE));
                    relaxTimes[0]++;
                }
            }
        }, 1000 * 60, 1000 * 60);
    }

    //加载设置
    private void loadSettings() {
        LoadingSettingsManager manager = new LoadingSettingsManager(this);
        focusAndRelaxTimeInterval = Integer.parseInt(manager.getFocusTimeValue());

        if (mode.equals("work")) {
            sentences = new String[]{
                    "人的知识愈广，人的本身也愈臻完善。——高尔基",
                    "天赋如同自然花木，要用学习来修剪.——培根",
                    "美国女神嘛，这手拿着火炬，这手拿着书，这是告诉我们停电也要学习。——冯巩",
                    "业精于勤，荒于嬉；行成于思，毁于随。——韩愈",
                    "学如逆水行舟，不进则退。——《增广贤文》",
                    "学习永远不晚。——高尔基",
                    "非淡泊无以明志，非宁静无以致远。——诸葛亮",
                    "知识是珍贵宝石的结晶，文化是宝石放出来的光泽。——泰戈尔",
                    "鸟欲高飞先振翅，人求上进先读书。——李苦禅",
                    "知识是一种快乐，而好奇则是知识的萌芽。——培根"
            };
        } else {
            sentences = new String[]{
                    "书犹药也，善读之可以医愚。　——刘向",
                    "理想的书籍，是智慧的钥匙。　——列·托尔斯泰",
                    "书籍是人类知识的总结。书籍是全世界的营养品。　——莎士比亚",
                    "读一本好书，就是和许多高尚的人谈话。　——歌德",
                    "喜欢读书，就等于把生活中寂寞的辰光换成巨大享受的时刻。　——孟德斯鸠",
                    "了解一页书，胜于匆促地阅读一卷书。　——麦考利",
                    "读书而不回想，犹如食物而不消化。　——伯克",
                    "非学无以广才，非志无以成学。——诸葛亮",
                    "千里之行，始于足下。——老子",
                    "书，人类发出的最美妙的声音。——莱文"
            };
        }
    }

    /**
     * 初始化
     */
    private void initView() {
        mTv_show = findViewById(R.id.time);
        dot = findViewById(R.id.dot);
        countdownCircle = findViewById(R.id.countdown);
        frameLayout = findViewById(R.id.framelayout);
        focusResult = findViewById(R.id.focus_result_notification_textview);
        focusStop = findViewById(R.id.focus_stop_button);
        gotoRelax = findViewById(R.id.start_relax_button);
        sentence = findViewById(R.id.focus_sentence_show_textview);
        sentenceOrigin = findViewById(R.id.focus_sentence_origin_textview);

        String[] parts = sentences[(int) (Math.random() * sentences.length)].split("——");
        sentence.setText(parts[0]);
        sentenceOrigin.setText("—— " + parts[1]);

        loadmusic();
    }

    // 点击监听事件
    public void startcount() {

        if (myCountDownTimer == null) {
            myCountDownTimer = new MyCountDownTimer(millisInFuture1 + millisInFuture2, countDownInterval);
        }
        myCountDownTimer.start();
        CountDownCircle();    //倒计时小圆点移动
        countdownCircle.setTotaltime(millisInFuture1 + millisInFuture2);   //开始画倒计时圈


        mediaPlayer1.start();

    }

    //倒计时小圆点的移动
    public void CountDownCircle() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(dot, "rotation", 360f, 0f);  //旋转view来使圆圈倒转
        animator.setDuration(millisInFuture1 + millisInFuture2);
        animator.start();
    }

    public void focusStop(View view) {
        if (focusStop.getText().toString().equals("返回")) {
            startActivity(new Intent(FocusTimerActivity.this, MainControlActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("停止专注");
            builder.setMessage("你有正在专注的事，再坚持一下！\n确定要停止专注吗？");
            builder.setPositiveButton("忍痛停止", (dialog, which) -> {
                if (myCountDownTimer != null) {
                    myCountDownTimer.cancel();
                }
                //专注失败
                isRelaxing = false;
                //计时器停止
                actualFocusTimeRecord.cancel();
                mTv_show.setVisibility(View.GONE);//隐藏数字倒计时
                focusResult.setText("很遗憾，本次专注失败了！\n希望你下次努力！");
                focusResult.setTextColor(Color.RED);
                focusResult.setVisibility(View.VISIBLE);
                focusStop.setText("返回");
                focusStop.setBackground(getResources().getDrawable(R.drawable.back_to_main_bg));
                //保存专注数据
                saveFocusData();
            });
            builder.setNegativeButton("我还可以坚持", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        }
    }

    //保存专注数据
    private void saveFocusData() {
        User user = getCurrentUser();
        Focus focus = new Focus();
        focus.setUserId(user.getObjectId());
        focus.setPresetTime((int) (millisInFuture1 * 60 + millisInFuture2 / 1000 / 60));
        focus.setActualTime(actualTime);
        focus.setSuccess(isFocusSuccess);
        focus.setFocusType(mode.equals("read") ? "read" : "work");
        focus.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i("focusSave", "success");
                } else {
                    Log.i("focusSave", e.toString());
                }
            }
        });
    }

    //开始休息
    public void startRelax(View view) {
        //进入休息的flag
        isRelaxing = true;
        startActivity(new Intent(FocusTimerActivity.this, RelaxActivity.class));
        //隐藏休息按钮
        view.setVisibility(View.GONE);
    }

    public class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    总共持续的时间
         * @param countDownInterval 倒计时的时间间隔
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * @param millisUntilFinished 还剩下的时间
         */
        @Override
        public void onTick(long millisUntilFinished) {
            mTv_show.setText(toTimeStr(millisUntilFinished));
        }

        /**
         * 倒计时结束时候回调
         */
        @Override
        public void onFinish() {
            //保存专注时间
            actualTime = (int) (millisInFuture1 * 60 + millisInFuture2);
            isFocusSuccess = true;
            //保存专注数据
            saveFocusData();
            //显示专注成功信息
            actualFocusTimeRecord.cancel();
            mTv_show.setVisibility(View.GONE);//隐藏数字倒计时
            String msg = (millisInFuture1 > 0 ? ("0" + millisInFuture1 + "小时") : "") + (millisInFuture2 / 1000 / 60 > 9 ? millisInFuture2 / 1000 / 60 : ("0" + millisInFuture2 / 1000 / 60)) + "分钟！";
            focusResult.setText("专注成功！\n本次专注了" + msg);
            focusResult.setTextColor(Color.BLUE);
            focusResult.setVisibility(View.VISIBLE);
            focusStop.setText("返回");
            focusStop.setBackground(getResources().getDrawable(R.drawable.back_to_main_bg));
            gotoRelax.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FocusTimerActivity.this, MainControlActivity.class));
        finish();
    }

    private String to2Str(long i) {

        if (i > 9) {
            return i + "";
        } else {
            return "0" + i;
        }

    }

    private String toTimeStr(long secTotal) {

        String result = null;
        secTotal = secTotal / 1000;
        long hour = secTotal / 3600;
        long min = (secTotal % 3600) / 60;
        long sec = (secTotal % 3600) % 60;
        result = to2Str(hour) + ":" + to2Str(min) + ":" + to2Str(sec);

        return result;
    }

    public void setShowAction() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.6f);
        mShowAction.setDuration(1000);
    }

    public void loadmusic() {
        mediaPlayer1 = MediaPlayer.create(this, R.raw.lol);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isRelaxing) {
            return;
        }
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        if (!isScreenOn) {
            return;
        }
        //显示通知栏
        showNotification();
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                //专注失败
                focusResult.setText("很遗憾，本次专注失败了！\n希望你下次努力！");
                focusResult.setTextColor(Color.RED);
                focusResult.setVisibility(View.VISIBLE);
                focusStop.setText("返回");
                focusStop.setBackground(getResources().getDrawable(R.drawable.back_to_main_bg));
                time.cancel();
            }
        }, 30000, 30000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRelaxing = false;
        if (time != null) {
            time.cancel();
        }
    }

    private void showNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Objects.requireNonNull(this), CHANNEL_ID)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle("专注中...")
                .setContentText("放下手机，享受专注的美好时光吧！点击立即返回继续专注...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        // 设置启动的程序，如果存在则找出，否则新的启动
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, RelaxActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);//将经过设置了的Intent绑定给PendingIntent
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        notificationManager.notify(0, mBuilder.build());

    }

    //获取本地用户
    public User getCurrentUser() {
        User currentUser;
        SharedPreferences preferences = getSharedPreferences("login_user", MODE_PRIVATE);
        currentUser = new User(
                preferences.getString("username", "null"),
                preferences.getString("nickname", "null"),
                preferences.getString("password", "null"),
                preferences.getString("user_phone", "null"));
        currentUser.setObjectId(preferences.getString("objectID", "null"));
        currentUser.setPassword(preferences.getString("password", "null"));
        currentUser.setAvatarUrl(preferences.getString("avatar", "null"));
        currentUser.setAvatarFileName(preferences.getString("avatar_file_name", "null"));
        return currentUser;
    }
}

