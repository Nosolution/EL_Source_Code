package com.example.a81271.timato;
//This project source is created by Zhaoheng Yin.
//Copyright 2018 Zhaoheng Yin.
//Catbro group Confidential.

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by 81271 on 2018/5/23.
 */

public class TreeGrowingActivity extends AppCompatActivity {
    private ImageView exit;
    private ImageView Play;
    private ImageView Pause;
    private ImageView Next;
    private InsideAudioCore Core;
    public TreeTestView t;
    boolean pause;
    private int current;
    String songs[] = {"ClockTick","Sea","Street","River","Fan","Night"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tree);
        t= (TreeTestView)findViewById(R.id.tree);
        t.SetTicks(getIntent().getIntExtra("time",0));
        Core = new InsideAudioCore(this);
        Play = (ImageView)findViewById(R.id.play);
        Play.setAlpha((float)0.0);
        pause = false;
        current = 0;
        Pause = (ImageView)findViewById(R.id.pause);
        exit = (ImageView)findViewById(R.id.trexit);
        Next = (ImageView)findViewById(R.id.next);

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pause)
                {
                    Pause.setAlpha(1.0f);
                    Play.setAlpha(0.0f);
                    Core.Play();
                    pause = false;
                }else
                {
                    Play.setAlpha(1.0f);
                    Pause.setAlpha(0.0f);
                    Core.Pause();
                    pause = true;
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Core.Pause();
                Pause.setAlpha(1.0f);
                Play.setAlpha(0.0f);
                current =(current+1)%6;
                Core.LoadInternalMusic(songs[current]);
                Core.Play();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TreeGrowingActivity.this,MainActivity.class);
                startActivity(intent);
                Core.Pause();
                finish();
            }
        });
        Core.LoadInternalMusic(songs[0]);
        Core.Play();
    }
    public void Reset()
    {
        t.Reset();
    }

}
