package com.example.administrator.el_done1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    private List<Friend> friendList = new ArrayList<>();

    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //根据主题设置字体
        switch (Theme.getTHEME()){
            case "SIMPLE":
                setTheme(R.style.SIMPLE_THEME);
                break;
            case "OTAKU":
                setTheme(R.style.OTAKU_THEME);
                break;
            case "PET":
                setTheme(R.style.PET_THEME);
                break;
        }

        //设立layout
        setContentView(R.layout.friends_activity);

        //存入ActivityManager
        ActivityManager.addActivity(this);


        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }

        initFriends();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.friend_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FriendsAdapter adapter = new FriendsAdapter(friendList);
        recyclerView.setAdapter(adapter);

        Button back_button = (Button)findViewById(R.id.friends_title_back);
        Button add_button = (Button)findViewById(R.id.friends_title_add);
        Button more_button = (Button)findViewById(R.id.friends_title_more);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.removeActivity(FriendsActivity.this);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this,FrendAddActivity.class);
                startActivity(intent);
            }
        });
        more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this,FriendAddApplyActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initFriends(){
        Friend f1 = new Friend("f1",R.drawable.d1_button);
        friendList.add(f1);
        Friend f2 = new Friend("f2",R.drawable.d2_button);
        friendList.add(f2);
        Friend f3 = new Friend("f3",R.drawable.d3_button);
        friendList.add(f3);
        Friend f4 = new Friend("f4",R.drawable.d4_button);
        friendList.add(f4);
        Friend f5 = new Friend("f5",R.drawable.d5_button);
        friendList.add(f5);
        Friend f6 = new Friend("f6",R.drawable.d6_button);
        friendList.add(f6);
        Friend f7 = new Friend("f7",R.drawable.d7_button);
        friendList.add(f7);
        Friend f8 = new Friend("f8",R.drawable.d8_button);
        friendList.add(f8);
        Friend f9 = new Friend("f9",R.drawable.d9_button);
        friendList.add(f9);
        Friend f10 = new Friend("f10",R.drawable.d10_button);
        friendList.add(f10);

    }
}
