package com.easylife.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.easylife.adapter.NotePagerAdapter;
import com.easylife.entity.User;
import com.easylife.fragment.DelayChartFragment;
import com.easylife.fragment.FocusChartFragment;
import com.easylife.fragment.RelaxChartFragment;
import com.easylife.util.LoadingSettingsManager;
import com.github.mikephil.charting.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class UserActivity extends FragmentActivity {
    private TextView nickname;
    private RoundedImageView avatar;
    private ViewPager charts;
    private RadioButton focusChart;
    private RadioButton delayChart;
    private RadioButton relaxChart;
    private RadioGroup chartGroup;
    private ImageView back;
    private TextView focusModeExplain;
    private TextView focusTimeExplain;
    private TextView taskRemindExplain;
    private TextView relaxModeExplain;
    private TextView relaxTimeExplain;
    private TextView modifyInfo;
    //    private TextView focusReport;
    private TextView aboutUs;
    private Switch focusMode;
    private Switch taskRemindMode;
    private Switch relaxMode;
    private AppCompatSeekBar focusTime;
    private AppCompatSeekBar relaxTime;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LoadingSettingsManager settingsManager;

    private String avatarSrc = Environment.getExternalStorageDirectory().getPath() + "/EasyLife/avatar/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化统计表工具包
        Utils.init(this);

        setContentView(R.layout.activity_user);

        //初始化标题栏
        initActionBar();

        //初始化控件
        initWidget();

        //初始化用户数据
        initUserInfo();

        //设置点击事件
        setOnClick();

    }

    //初始化标题栏
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.user_actionbar, null);
        if (actionBar != null) {
            actionBar.setCustomView(actionBarView);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }


    //初始化用户数据
    private void initUserInfo() {
        User user = getCurrentUser();
        nickname.setText(user.getNickname());

        Runnable runnable = () -> {
            //初始化头像
            if (new File(avatarSrc + user.getAvatarFileName()).exists()) {
                avatar.setImageBitmap(BitmapFactory.decodeFile(avatarSrc + user.getAvatarFileName()));
            } else if (!user.getAvatarUrl().equals("null")) {
                BmobFile avatar = new BmobFile(user.getAvatarFileName(), "", user.getAvatarUrl());
                avatar.download(new File(avatarSrc + user.getAvatarFileName()), new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e != null) {
                            UserActivity.this.avatar.setImageResource(R.mipmap.avatar);
                        } else {
                            UserActivity.this.avatar.setImageBitmap(BitmapFactory.decodeFile(s + "/" + user.getAvatarFileName()));
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            } else {
                UserActivity.this.avatar.setImageResource(R.mipmap.avatar);
            }
        };
        runnable.run();
    }

    private void setOnClick() {
        //初始化editor
        editor = preferences.edit();

        avatar.setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, UserInfoActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        modifyInfo.setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, UserInfoActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

//        focusReport.setOnClickListener(v -> {
//            startActivity(new Intent(UserActivity.this, FocusReportActivity.class));
//        });

        aboutUs.setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, AboutUsActivity.class));
        });

        focusMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                focusMode.setText("强制模式");
                focusModeExplain.setText("专注时不能进行其他操作");
                editor.putString("focus_mode", String.valueOf(settingsManager.FOCUS_MODE_SPECIAL));
            } else {
                focusMode.setText("一般模式");
                focusModeExplain.setText("专注时可以进行其他操作");
                editor.putString("focus_mode", String.valueOf(settingsManager.FOCUS_MODE_GENERAL));
            }
            editor.apply();
        });

        focusTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                focusTimeExplain.setText(25 + progress * 5 + "分钟");
                editor.putString("focus_time", String.valueOf(25 + progress * 5));
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        taskRemindMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                taskRemindMode.setText("提醒");
                taskRemindExplain.setText("待办事务在ddl之前会显示通知栏通知");
                editor.putString("task_remind_mode", String.valueOf(settingsManager.TASK_MODE_REMIND));
            } else {
                taskRemindMode.setText("不提醒");
                taskRemindExplain.setText("待办事务在ddl之前不会显示通知栏通知");
                editor.putString("task_remind_mode", String.valueOf(settingsManager.TASK_MODE_NOT_REMIND));
            }
            editor.apply();
        });

        relaxMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                relaxMode.setText("强制模式");
                relaxModeExplain.setText("音乐结束之前不能进行其他操作");
                editor.putString("relax_mode", String.valueOf(settingsManager.RELAX_MODE_SPECIAL));
            } else {
                relaxMode.setText("一般模式");
                relaxModeExplain.setText("音乐结束之前可以进行其他操作");
                editor.putString("relax_mode", String.valueOf(settingsManager.RELAX_MODE_GENERAL));
            }
            editor.apply();
        });

        relaxTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                relaxTimeExplain.setText(progress + 1 + "首音乐");
                editor.putString("relax_time", String.valueOf(progress + 1));
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP && event.getRepeatCount() == 0) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void initWidget() {
        avatar = findViewById(R.id.avatar_imageview);
        charts = findViewById(R.id.charts_viewpager);
        focusChart = findViewById(R.id.focus_radiobutton);
        delayChart = findViewById(R.id.delay_radiobutton);
        relaxChart = findViewById(R.id.relax_radiobutton);
        chartGroup = findViewById(R.id.chart_radiogroup);
        nickname = findViewById(R.id.nickname_textview);
        back = findViewById(R.id.back_imageview);
        focusModeExplain = findViewById(R.id.focus_mode_explain_textview);
        focusTimeExplain = findViewById(R.id.focus_time_show_textview);
        taskRemindExplain = findViewById(R.id.task_remind_textview);
        relaxModeExplain = findViewById(R.id.relax_mode_explain_textview);
        relaxTimeExplain = findViewById(R.id.relax_time_show_textview);
        modifyInfo = findViewById(R.id.modify_info_textview);
//        focusReport = findViewById(R.id.focus_report_textview);
        aboutUs = findViewById(R.id.about_us_textview);
        focusMode = findViewById(R.id.focus_mode_switch);
        taskRemindMode = findViewById(R.id.task_remind_switch);
        relaxMode = findViewById(R.id.relax_mode_switch);
        focusTime = findViewById(R.id.focus_time_seekbar);
        relaxTime = findViewById(R.id.relax_time_seekbar);

        //初始化统计表
        initChart();

        //加载设置
        loadSettings();
    }

    private void loadSettings() {
        settingsManager = new LoadingSettingsManager(this);
        //初始化sharepreference
        preferences = settingsManager.getPreferences();

        String focusModeValue = settingsManager.getFocusModeVale();
        if (focusModeValue.equals(String.valueOf(settingsManager.FOCUS_MODE_GENERAL))) {
            focusMode.setText("一般模式");
            focusMode.setChecked(false);
            focusModeExplain.setText("专注时可以进行其他操作");
        } else {
            focusMode.setText("强制模式");
            focusMode.setChecked(true);
            focusModeExplain.setText("专注时不能进行其他操作");
        }

        String relaxModeValue = settingsManager.getRelaxModeValue();
        if (relaxModeValue.equals(String.valueOf(settingsManager.RELAX_MODE_GENERAL))) {
            relaxMode.setText("一般模式");
            relaxMode.setChecked(false);
            relaxModeExplain.setText("音乐结束之前可以进行其他操作");
        } else {
            relaxMode.setText("强制模式");
            relaxMode.setChecked(true);
            relaxModeExplain.setText("音乐结束之前不能进行其他操作");
        }

        String taskRemindValue = settingsManager.getTaskRemindValue();
        if (taskRemindValue.equals(String.valueOf(settingsManager.TASK_MODE_REMIND))) {
            taskRemindMode.setText("提醒");
            taskRemindMode.setChecked(true);
            taskRemindExplain.setText("待办事务在ddl之前会显示通知栏通知");
        } else {
            taskRemindMode.setText("不提醒");
            taskRemindMode.setChecked(false);
            taskRemindExplain.setText("待办事务在ddl之前不会显示通知栏通知");
        }

        String focusTimeValue = settingsManager.getFocusTimeValue();
        focusTimeExplain.setText(focusTimeValue + "分钟");
        focusTime.setProgress((Integer.parseInt(focusTimeValue) - 25) / 5);

        String relaxTimeValue = settingsManager.getRelaxTimeValue();
        relaxTimeExplain.setText(relaxTimeValue + "首音乐");
        relaxTime.setProgress(Integer.parseInt(relaxTimeValue) - 1);
    }

    private void initChart() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FocusChartFragment());
        fragments.add(new DelayChartFragment());
        fragments.add(new RelaxChartFragment());
        NotePagerAdapter adapter = new NotePagerAdapter(getSupportFragmentManager(), fragments);
        charts.setAdapter(adapter);
        /**
         * 为 Viewpager 设置页面切换监听，当页面切换完成被选中时，我们同步 RadioButton 的状态
         **/
        charts.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        focusChart.setChecked(true);
                        break;
                    case 1:
                        delayChart.setChecked(true);
                        break;
                    case 2:
                        relaxChart.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 为 RadioGroup 设置选中变化事件监听，当 RadioButton 状态变化，我们同步 Viewpager 的选中页面
         **/
        chartGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.focus_radiobutton:
                    charts.setCurrentItem(0);
                    break;
                case R.id.delay_radiobutton:
                    charts.setCurrentItem(1);
                    break;
                case R.id.relax_radiobutton:
                    charts.setCurrentItem(2);
                    break;
            }
        });
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
