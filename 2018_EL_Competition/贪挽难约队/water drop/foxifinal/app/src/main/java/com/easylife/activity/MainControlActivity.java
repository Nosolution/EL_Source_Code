package com.easylife.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.easylife.adapter.FragAdapter;
import com.easylife.entity.Focus;
import com.easylife.entity.Relax;
import com.easylife.entity.Task;
import com.easylife.entity.User;
import com.easylife.fragment.DelayChartFragment;
import com.easylife.fragment.DelayFragment;
import com.easylife.fragment.FocusChartFragment;
import com.easylife.fragment.FocusFragment;
import com.easylife.fragment.RelaxChartFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainControlActivity extends FragmentActivity {
    public static List<FocusChartFragment.FocusData> readingDataList = new ArrayList<>(7);
    public static List<FocusChartFragment.FocusData> workingDataList = new ArrayList<>(7);
    public static List<String> dayToShow = new ArrayList<>(7);
    public static ArrayList<RelaxChartFragment.RelaxData> relaxData = new ArrayList<>(7);
    public static ArrayList<DelayChartFragment.DelayData> delayData = new ArrayList<>(3);
    public static int focusTimesTotal = 0;
    public static int focusTimesSuccess = 0;
    public CountDownTimer timer;
    public static ViewPager vp;

    private View yege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从服务器端加载数据
        loadingDataFromServer();

        Slide slide1 = new Slide();
        slide1.setSlideEdge(Gravity.BOTTOM);
        slide1.setDuration(1000);

        Slide slide2 = new Slide();
        slide2.setSlideEdge(Gravity.BOTTOM);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(slide2);
        getWindow().setExitTransition(slide1);

        setContentView(R.layout.maincontrol_layout);

        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //构造适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FocusFragment());
        fragments.add(new DelayFragment());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        //设定适配器
        vp = findViewById(R.id.viewpager);
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        //跳转到叶哥的账户界面
        yege = findViewById(R.id.yege);
        yege.setOnClickListener(v -> {
            Intent intent = new Intent(MainControlActivity.this, UserActivity.class);
            startActivity(intent);
        });

    }

    private void loadingDataFromServer() {
        //初始化数据源List
        for (int i = 1; i <= 7; i++) {
            readingDataList.add(new FocusChartFragment.FocusData(i, 0));
            workingDataList.add(new FocusChartFragment.FocusData(i, 0));
        }
        for (int i = 0; i < 7; i++) {
            relaxData.add(new RelaxChartFragment.RelaxData(0));
        }

        //加载最近7天的日期
        Calendar calendar = Calendar.getInstance();//获取系统的日期
        //年
        int current_year = calendar.get(Calendar.YEAR);
        //月
        int current_month = calendar.get(Calendar.MONTH);
        //日
        int current_day = calendar.get(Calendar.DAY_OF_MONTH);
        //得到要显示的月份
        if (current_day >= 7 && current_day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            for (int i = 0; i < 7; i++) {
                dayToShow.add(current_year + "-" + (current_month > 8 ? (current_month + 1) : "0" + (current_month + 1)) + "-" + ((current_day - (6 - i)) > 9 ? (current_day - (6 - i)) : ("0" + (current_day - (6 - i)))));
                Log.i("day", String.valueOf(dayToShow));
            }
        } else {
            // TODO: 2018/5/21 不想修复1月份时的bug
            calendar.set(Calendar.MONTH, current_month - 1);
            int maxDayOfLastMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 0; i < 7; i++) {
                String day = (current_day - (6 - i)) > 0 ? String.valueOf((current_month > 8 ? current_month : ("0" + current_month)) + "-" + (current_day - (6 - i))) : ((current_month > 8 ? current_month : ("0" + current_month)) + "-" + ((current_day - (6 - i)) + maxDayOfLastMonth + ""));
                dayToShow.add(current_year + "-" + day);
            }
        }

        User user = getCurrentUser();

        for (int i = 0; i < 3; i++) {
            delayData.add(new DelayChartFragment.DelayData(0));
        }
        BmobQuery<Task> taskBmobQuery = new BmobQuery<>();
        taskBmobQuery.addWhereEqualTo("userId", user.getObjectId());
        taskBmobQuery.findObjects(new FindListener<Task>() {
            @Override
            public void done(List<Task> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    for (Task task : list) {
                        if (task.getState() == Task.STATE_TODO) {
                            delayData.get(0).setTimes(delayData.get(0).getTimes() + 1);
                        }else if (task.getState() == Task.STATE_FINISH){
                            delayData.get(1).setTimes(delayData.get(1).getTimes() + 1);
                        }else {
                            delayData.get(2).setTimes(delayData.get(2).getTimes() + 1);
                        }
                    }
                }
            }
        });

        //查询服务器数据
        BmobQuery<Focus> focusBmobQuery = new BmobQuery<>();
        focusBmobQuery.addWhereEqualTo("userId", user.getObjectId());
        focusBmobQuery.findObjects(new FindListener<Focus>() {
            @Override
            public void done(List<Focus> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    for (Focus value : list) {
                        focusTimesTotal++;
                        if (value.getSuccess()) {
                            focusTimesSuccess++;
                        }
                        if (value.getFocusType().equals("read")) {
                            FocusChartFragment.FocusData focusData = readingDataList.get(dayToShow.indexOf(value.getCreatedAt().split(" ")[0]));
                            focusData.setFocusTime(focusData.getFocusTime() + value.getActualTime());
                        } else {
                            FocusChartFragment.FocusData focusData = workingDataList.get(dayToShow.indexOf(value.getCreatedAt().split(" ")[0]));
                            focusData.setFocusTime(focusData.getFocusTime() + value.getActualTime());
                        }
                    }

                    for (int i = 0; i < 7; i++) {
                        readingDataList.get(i).setDay(Integer.parseInt(dayToShow.get(i).split("-")[2]));
                        workingDataList.get(i).setDay(Integer.parseInt(dayToShow.get(i).split("-")[2]));
                    }

                } else {
                    if (e != null)
                        Log.i("findFocus", e.toString());
                }
            }
        });

        BmobQuery<Relax> relaxBmobQuery = new BmobQuery<>();
        relaxBmobQuery.addWhereEqualTo("userId", user.getObjectId());
        relaxBmobQuery.findObjects(new FindListener<Relax>() {
            @Override
            public void done(List<Relax> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    for (int i = 0, listSize = list.size(); i < listSize; i++) {
                        Relax value = list.get(i);
                        RelaxChartFragment.RelaxData relax = relaxData.get(dayToShow.indexOf(value.getCreatedAt().split(" ")[0]));
                        relax.setTimeSum((int) (Integer.parseInt(value.getTime()) / 1000.0));
                        relax.setDay(Integer.parseInt(dayToShow.get(i).split("-")[2]));
                    }
                } else {
                    if (e != null)
                        Log.i("findRelax", e.toString());
                }
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
