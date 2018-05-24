package com.example.lenovo.elapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Activitys.MainActivity;


public class AchievementActivity extends AppCompatActivity {

    private List<Achievement_item> itemList = new ArrayList<Achievement_item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        //设置瀑布流
        initItems();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Achievement_Adapter adapter=new Achievement_Adapter(itemList);
        recyclerView.setAdapter(adapter);
        //隐藏actionbar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        //实现点击按钮返回
        Button backBtn = (Button) findViewById(R.id.achievement_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchievementActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initItems(){
        for(int i=0;i<2;i++){
            Achievement_item item_1 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_1);
            Achievement_item item_2 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_2);
            Achievement_item item_3 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_3);
            Achievement_item item_4 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_4);
            Achievement_item item_5 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_5);
            Achievement_item item_6 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_6);
            Achievement_item item_7 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_7);
            Achievement_item item_8 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_8);
            Achievement_item item_9 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_9);
            Achievement_item item_10 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_10);
            Achievement_item item_11 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_11);
            Achievement_item item_12 = new Achievement_item("master",R.mipmap.achievement);
            itemList.add(item_12);
        }
    }

}
