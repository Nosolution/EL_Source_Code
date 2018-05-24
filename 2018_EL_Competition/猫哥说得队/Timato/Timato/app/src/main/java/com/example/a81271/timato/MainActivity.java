package com.example.a81271.timato;

//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView item_weixin, item_tongxunlu, item_faxian, item_me;
    private ViewPager vp;
    private WelcomeFragment welcomeFragment;
    private MusicFragment musicFragment;
    private TimeoutFragment timeoutFragment;
    private SettingFragment settingFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    public MusicBar bar;
    public InsideAudioCore Core;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initViews();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        item_weixin.setTextColor(Color.parseColor("#000000"));
        item_tongxunlu.setTextColor(Color.parseColor("#000000"));
        item_faxian.setTextColor(Color.parseColor("#000000"));
        item_me.setTextColor(Color.parseColor("#000000"));
        item_weixin.getBackground().setAlpha(0);
        item_tongxunlu.getBackground().setAlpha(0);
        item_me.getBackground().setAlpha(0);
        item_faxian.getBackground().setAlpha(0);
        bar = (MusicBar)findViewById(R.id.musicbar);
        Core = new InsideAudioCore(this);
        bar.SetMusicCore(Core);
        bar.SetFragment(musicFragment);
        //ViewPager的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        item_weixin = (TextView) findViewById(R.id.item_weixin);
        item_tongxunlu = (TextView) findViewById(R.id.item_tongxunlu);
        item_faxian = (TextView) findViewById(R.id.item_faxian);
        item_me = (TextView) findViewById(R.id.item_me);

        item_weixin.setOnClickListener(this);
        item_tongxunlu.setOnClickListener(this);
        item_faxian.setOnClickListener(this);
        item_me.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        welcomeFragment = new WelcomeFragment();
        musicFragment = new MusicFragment();
        welcomeFragment.SetViewPager(vp);
        timeoutFragment = new TimeoutFragment();
        settingFragment = new SettingFragment();

        //给FragmentList添加数据
        mFragmentList.add(welcomeFragment);
        mFragmentList.add(musicFragment);
        mFragmentList.add(timeoutFragment);
        mFragmentList.add(settingFragment);

    }

    /**
     * 点击底部Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_weixin:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_tongxunlu:
                vp.setCurrentItem(1, true);
                break;
            case R.id.item_faxian:
                vp.setCurrentItem(2, true);
                break;
            case R.id.item_me:
                vp.setCurrentItem(3, true);
                break;
        }
    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */
    private void changeTextColor(int position) {
        if (position == 0) {
/*
            item_me.setBackgroundColor(Color.parseColor("DE0000"));
            item_tongxunlu.setBackgroundColor(Color.parseColor("DE0000"));
            item_faxian.setBackgroundColor(Color.parseColor("DE0000"));
            item_weixin.setBackgroundColor(Color.parseColor("BB0000"));*/
        } else if (position == 1) {
/*
            item_me.setBackgroundColor(Color.parseColor("DE0000"));
            item_tongxunlu.setBackgroundColor(Color.parseColor("BB0000"));
            item_faxian.setBackgroundColor(Color.parseColor("DE0000"));
            item_weixin.setBackgroundColor(Color.parseColor("DE0000"));*/
        } else if (position == 2) {
/*
            item_me.setBackgroundColor(Color.parseColor("DE0000"));
            item_tongxunlu.setBackgroundColor(Color.parseColor("DE0000"));
            item_faxian.setBackgroundColor(Color.parseColor("BB0000"));
            item_weixin.setBackgroundColor(Color.parseColor("DE0000"));*/
        } else if (position == 3) {
/*
           item_me.setBackgroundColor(Color.parseColor("BB0000"));
            item_tongxunlu.setBackgroundColor(Color.parseColor("DE0000"));
            item_faxian.setBackgroundColor(Color.parseColor("DE0000"));
            item_weixin.setBackgroundColor(Color.parseColor("DE0000"));*/
        }
    }
}
