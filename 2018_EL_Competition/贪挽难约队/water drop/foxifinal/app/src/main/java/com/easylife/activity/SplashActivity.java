package com.easylife.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylife.util.ImageAssetManager;

import java.util.Calendar;

public class SplashActivity extends Activity {
    private static final long SPLASH_DISPLAY_LENGHT = 2500;
    private static final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    private Handler handler;

    private Transition transition;

    private LinearLayout developerInfo;
    private FrameLayout root;
    private ImageView logo;
    private ImageView background;
    private TextView day;
    private TextView monthAndYear;
    private TextView dailySentence;
    private TextView sentenceOrigin;

    private static Activity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        //初始化控件
        initWidget();

        handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        handler.postDelayed(() -> {
            SharedPreferences preferences = getSharedPreferences("login_user", MODE_PRIVATE);
            if (preferences.getString("user_phone", null) != null) {
                Intent intent = new Intent(SplashActivity.this, MainControlActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                Intent intent = new Intent(SplashActivity.this,
                        LoginOrRegisterActivity.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(SplashActivity.this,
                                Pair.<View, String>create(logo, logo.getTransitionName()),
                                Pair.<View, String>create(root, root.getTransitionName()),
                                Pair.<View, String>create(developerInfo, developerInfo.getTransitionName()),
                                Pair.<View, String>create(day, day.getTransitionName()),
                                Pair.<View, String>create(monthAndYear, monthAndYear.getTransitionName()),
                                Pair.<View, String>create(dailySentence, dailySentence.getTransitionName()),
                                Pair.<View, String>create(sentenceOrigin, sentenceOrigin.getTransitionName()));
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_DISPLAY_LENGHT);

    }

    //初始化控件
    private void initWidget() {
        developerInfo = findViewById(R.id.developer_info_linearlayout);
        logo = findViewById(R.id.logo_imageview);
        root = findViewById(R.id.root_constraintlayout);
        background = findViewById(R.id.background_imageview);
        day = findViewById(R.id.day_textview);
        monthAndYear = findViewById(R.id.month_and_year_textview);
        dailySentence = findViewById(R.id.daily_sentence_textview);
        sentenceOrigin = findViewById(R.id.sentence_origin_textview);

        //设置背景图
        ImageAssetManager manager = new ImageAssetManager();
        manager.initAssets(this);
        MainApplication.picIndex = (int) (Math.random() * manager.getImages().size());
        background.setImageBitmap(BitmapFactory.decodeStream(manager.getInputStream(MainApplication.picIndex)));

        transition = new Explode();
        transition.addTarget(R.id.app_name_textview);
        transition.setDuration(800);
        getWindow().setExitTransition(transition);

        Calendar calendar = Calendar.getInstance();//获取系统的日期
        //年
        int current_year = calendar.get(Calendar.YEAR);
        //月
        int current_month = calendar.get(Calendar.MONTH);
        //日
        int current_day = calendar.get(Calendar.DAY_OF_MONTH);

        day.setText(current_day + "");
        monthAndYear.setText(MONTH[current_month] + ". " + current_year);

        MainApplication.sentenceIndex = (int) (Math.random() * manager.getSentences().size());
        String[] sentence = manager.getSentence(MainApplication.sentenceIndex).split("--");
        dailySentence.setText(sentence[0]);
        if (sentence.length == 2) {
            sentenceOrigin.setText("--" + sentence[1]);
        }else {
            sentenceOrigin.setText("");
        }
    }

    //获取instance
    public static Activity getInstance() {
        return INSTANCE;
    }
}
