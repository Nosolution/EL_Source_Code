package com.example.vincentmoriarty.sound_of_island;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class The_town extends AppCompatActivity {
    int fish_total;
    int book_total;
    int apple_toatl;
    Boolean readme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_town);
        SharedPreferences reader = getSharedPreferences("data", MODE_PRIVATE);
        readme=reader.getBoolean("readme",true);
        if(readme){
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putBoolean("readme",false);
            editor.apply();
            AlertDialog.Builder teaching = new AlertDialog.Builder(The_town.this);
            teaching.setTitle("帮助");
            teaching.setMessage("欢迎来到鲁德夫拉岛，你现在正位于岛中心的小镇上，地上的箭头能带你到达岛上的各个位置，尝试与岛上的人们对话吧，他们会指引你采集岛之声，赠与他们物品会使你收获更多的声音。");
            teaching.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            teaching.show();
        }
        Button button_beach = (Button) findViewById(R.id.button_town2beach);
        button_beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(The_town.this, Beach.class);
                startActivity(intent);
            }
        });
        Button button_forest = (Button) findViewById(R.id.button_town2forest);
        button_forest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(The_town.this, Forest.class);
                startActivity(intent);
            }
        });
        Button button_caffee = (Button) findViewById(R.id.button_town2caffee);
        button_caffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(The_town.this, Caffee.class);
                startActivity(intent);
            }
        });
        Button button_achievement = (Button) findViewById(R.id.button_town2achievement);
        button_achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(The_town.this, achievement.class);
                startActivity(intent);
            }
        });

    }
    public void begger_button(View v){
        AlertDialog.Builder support = new AlertDialog.Builder(The_town.this);
        support.setTitle("开发者");
        support.setMessage("SNXF\nCY\nKN\nLHT\nYWT");
        support.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        support.show();
    }

    public void person_button(View v){
        SharedPreferences reader = getSharedPreferences("data", MODE_PRIVATE);
        fish_total = reader.getInt("fish_total",0);
        apple_toatl = reader.getInt("apple_total",0);
        book_total = reader.getInt("book_total",0);
        AlertDialog.Builder support = new AlertDialog.Builder(The_town.this);
        support.setTitle("这个人就是你");
        support.setMessage("你是一个寻找到传说中的鲁德夫拉岛的探险家\n在这个拥有神奇魔力的小岛上，埋藏着无数的鱼、苹果和书本等着你去发现\n"+"你已经发现了"+String.valueOf(fish_total)+"条鱼，"+String.valueOf(apple_toatl)+"个苹果，"+String.valueOf(book_total)+"本书。");
        support.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        support.show();
    }
}
