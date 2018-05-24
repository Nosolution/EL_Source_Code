package com.fantasticfour.elcontestproject.Achievement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

public class AchievementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement_activity_main);

        DealWithTitle();
        DealWithViews();
    }
    private void DealWithTitle(){
        //返回键
        Button btn_Back = findViewById(R.id.title_leftbutton);
        btn_Back.setBackground(getDrawable(R.drawable.left_arrow));
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //右侧按钮禁用
        Button btn_Right = findViewById(R.id.title_rightbutton);
        btn_Right.setClickable(false);

        //中间文本
        TextView tv_Middle = findViewById(R.id.title_middletext);
        tv_Middle.setText("成就");
    }
    private void DealWithViews(){
        //Achievement List
        RecyclerView rv = findViewById(R.id.achievement_rv_activity_main_achievement_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rv.setLayoutManager(gridLayoutManager);
        AchievementListAdapter adapter = new AchievementListAdapter(this, Instance.s_AchievementLibrary.GetAchievedAchievementList());
        rv.setAdapter(adapter);
    }
}
