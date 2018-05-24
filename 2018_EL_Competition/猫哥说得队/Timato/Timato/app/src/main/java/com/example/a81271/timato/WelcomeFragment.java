package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

/**
 * Created by 81271 on 2018/5/23.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class WelcomeFragment extends Fragment implements Anime{

    SwipeRefreshLayout mSwipeRefreshLayout;
    ViewPager pg;
    private FrameAnimation mFrameAnimation1;
    private FrameAnimation mFrameAnimation2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.welcome_fragment, container, false);
        return view;
    }

    public void SetViewPager(ViewPager og)
    {
        pg = og;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();

        mFrameAnimation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setCurrentItem(2,true);
            }
        });
        mFrameAnimation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setCurrentItem(1,true);
            }
            }
        );
    }

        private void initView() {
            mFrameAnimation1 = (FrameAnimation) getActivity().findViewById(R.id.frameAnimation);
            mFrameAnimation2 = (FrameAnimation) getActivity().findViewById(R.id.frameAnimation2);
        }

        private void initData() {
            initAnimation();
        }

        private void initAnimation() {
            //设置资源文件
            mFrameAnimation1.setBitmapResoursID(srcId1);
            mFrameAnimation2.setBitmapResoursID(srcId2);
            mFrameAnimation1.setOnFrameFinisedListener(new FrameAnimation.OnFrameFinishedListener() {
                @Override
                public void onStart() {

                }
            });
            mFrameAnimation2.setOnFrameFinisedListener(new FrameAnimation.OnFrameFinishedListener() {
                @Override
                public void onStart() {

                }
            });

            //设置单张图片展示时长
            mFrameAnimation1.setGapTime(30);
            mFrameAnimation2.setGapTime(30);
            mFrameAnimation1.start();
            mFrameAnimation2.start();
        }
}


