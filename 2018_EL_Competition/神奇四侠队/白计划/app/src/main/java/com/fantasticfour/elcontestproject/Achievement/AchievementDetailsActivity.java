package com.fantasticfour.elcontestproject.Achievement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Ins_Achievement.Achievement;
import com.fantasticfour.elcontestproject.Instance.Tool;
import com.fantasticfour.elcontestproject.R;

public class AchievementDetailsActivity extends AppCompatActivity {
    private Achievement m_Achievement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement_activity_details);

        GetIntent();
        ModifyDisplay();
        DealWithViews();

    }
    private void GetIntent(){
        Intent intent = getIntent();
        m_Achievement = (Achievement) intent.getSerializableExtra("achievement");
    }
    private void ModifyDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();display.getSize(size);
        params.height = (int) (size.y*0.7);
        params.width = (int) (size.x*0.8);
        getWindow().setAttributes(params);
    }
    private void DealWithViews(){
        ImageView iv_Img = findViewById(R.id.achievement_iv_activity_details_img);
        TextView tv_Name = findViewById(R.id.achievement_tv_activity_details_name);
        TextView tv_Description = findViewById(R.id.achievement_tv_activity_details_description);
        TextView tv_Achieved = findViewById(R.id.achievement_tv_activity_details_achieved);

        Bitmap oriImg = Tool.GetBitmapFromAssets(this, "Achievement/"+m_Achievement.m_ImgFileName);
        if(m_Achievement.m_BeAchieved==0)
            oriImg = Tool.ToGrayScale(oriImg);
        iv_Img.setImageBitmap(oriImg);
        tv_Name.setText(m_Achievement.m_Name);
        tv_Description.setText(m_Achievement.m_Description);
        tv_Achieved.setText(m_Achievement.m_BeAchieved==1?"已获得":"未获得");
    }
}

