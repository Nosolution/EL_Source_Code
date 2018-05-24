package com.nju.team.timescapes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by ASUS on 2018/5/9.
 */

public class AchievementsPanelActivity extends AppCompatActivity{
    private ImageButton btn_exit_achievements;
    private ImageView ach_1;
    private ImageView ach_2;
    private ImageView ach_3;
    private ImageView ach_4;
    private ImageView ach_5;
    private ImageView ach_6;
    private TextView text_time_focus;
    private TextView text_total_score;
    private TextView text_rate;
    private TextView text_attempts;
    private Button btn_route;

    private String filename = "user_settings.txt";
    private ArrayList<String> settings_list = new ArrayList<String>();
    private Long FOCUS_TIME;
    private boolean IS_REGISTERED;
    private Long TIME_SUM;
    private Long ATTEMPT_SUM;
    private Long SUCCESS_SUM;
    private Long TOTAL_SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_achievements);

        settings_list = readData(filename);
        IS_REGISTERED = Boolean.parseBoolean(settings_list.get(0).substring(0,settings_list.get(0).length()-1));
        FOCUS_TIME = Long.parseLong(settings_list.get(5).substring(0,settings_list.get(5).length()-1));
        TIME_SUM = Long.parseLong(settings_list.get(10).substring(0,settings_list.get(10).length()-1));
        ATTEMPT_SUM = Long.parseLong(settings_list.get(11).substring(0,settings_list.get(11).length()-1));
        SUCCESS_SUM = Long.parseLong(settings_list.get(12).substring(0,settings_list.get(12).length()-1));
        TOTAL_SCORE = Long.parseLong(settings_list.get(13).substring(0,settings_list.get(13).length()-1));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //让状态栏透明
        changeStatusBarColor();

        //指定按钮
        btn_exit_achievements = (ImageButton) findViewById(R.id.imageButton_exitAchievements);
        ach_1 = (ImageView) findViewById(R.id.image_achUser);
        ach_2 = (ImageView) findViewById(R.id.image_ach120);
        ach_3 = (ImageView) findViewById(R.id.image_ach600);
        ach_4 = (ImageView) findViewById(R.id.image_ach1200);
        ach_5 = (ImageView) findViewById(R.id.image_ach3000);
        ach_6 = (ImageView) findViewById(R.id.image_ach6000);
        text_time_focus = (TextView) findViewById(R.id.text_numFocus);
        text_total_score = (TextView) findViewById(R.id.text_numScore);
        text_rate = (TextView) findViewById(R.id.success_rate);
        text_attempts = (TextView) findViewById(R.id.av_length);
        btn_route = (Button) findViewById(R.id.text_title_achs);

        //按钮事件
        btn_exit_achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AchievementsPanelActivity.this, MainScreenActivity.class);
                AchievementsPanelActivity.this.startActivity(mainIntent);
                AchievementsPanelActivity.this.finish();
            }
        });

        //按钮事件
        btn_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AchievementsPanelActivity.this, TimeRecord.class);
                AchievementsPanelActivity.this.startActivity(mainIntent);
                AchievementsPanelActivity.this.finish();
            }
        });

        //设置成就点亮
        if(IS_REGISTERED){
            ach_1.setImageResource(R.drawable.ach_userregistered_light);
        }else{
            ach_1.setImageResource(R.drawable.ach_userregistered_dark);
        }
        if(TIME_SUM>=120){
            ach_2.setImageResource(R.drawable.ach_120_light);
        }else{
            ach_2.setImageResource(R.drawable.ach_120_dark);
        }
        if(TIME_SUM>=600){
            ach_3.setImageResource(R.drawable.ach_600_light);
        }else{
            ach_3.setImageResource(R.drawable.ach_600_dark);
        }
        if(TIME_SUM>=1200){
            ach_4.setImageResource(R.drawable.ach_1200_light);
        }else{
            ach_4.setImageResource(R.drawable.ach_1200_dark);
        }
        if(TIME_SUM>=3000){
            ach_5.setImageResource(R.drawable.ach_3000_light);
        }else{
            ach_5.setImageResource(R.drawable.ach_3000_dark);
        }
        if(TIME_SUM>=6000){
            ach_6.setImageResource(R.drawable.ach_6000_light);
        }else{
            ach_6.setImageResource(R.drawable.ach_6000_dark);
        }

        //设置时长记录
        String str_time_sum = Long.toString(TIME_SUM);
        if(str_time_sum.length()==1){
            str_time_sum = "000" + str_time_sum;
        }else if(str_time_sum.length()==2){
            str_time_sum = "00" + str_time_sum;
        }else if(str_time_sum.length()==3){
            str_time_sum = "0" + str_time_sum;
        }else if(str_time_sum.length()==4 && TIME_SUM<=9999){
            str_time_sum = "" + str_time_sum;
        }else if(TIME_SUM>9999){
            str_time_sum = "9999";
        }
        text_time_focus.setText(str_time_sum);

        //设置积分记录
        String str_total_score = Long.toString(TOTAL_SCORE);
        if(str_total_score.length()==1){
            str_total_score = "0000"+str_total_score;
        }else if(str_total_score.length()==2){
            str_total_score = "000"+str_total_score;
        }else if(str_total_score.length()==3){
            str_total_score = "00"+str_total_score;
        }else if(str_total_score.length()==4){
            str_total_score = "0"+str_total_score;
        }else if(str_total_score.length()==5 && TOTAL_SCORE<=99999){
            str_total_score = ""+str_total_score;
        }else if(TOTAL_SCORE>99999){
            str_total_score = "99999";
        }
        text_total_score.setText(str_total_score);

        //设置成功率
        double success_rate = ((double)SUCCESS_SUM/(double)ATTEMPT_SUM)*100;
        int result_rate = (int)Math.round(success_rate);
        String show_rate = Integer.toString(result_rate)+"%";
        text_rate.setText(show_rate);

        //设置总次数
        String show_attempts = Long.toString(ATTEMPT_SUM);
        text_attempts.setText(show_attempts);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent mainIntent = new Intent(AchievementsPanelActivity.this, MainScreenActivity.class);
                AchievementsPanelActivity.this.startActivity(mainIntent);
                AchievementsPanelActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

    //存数据
    private void saveData(String data,String filename) {

        String content = data + "\n";
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //读数据
    private ArrayList<String> readData(String filename){
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }

        File file=new File(textsDir,filename);
        BufferedReader reader=null;
        String temp=null;
        ArrayList<String> settings = new ArrayList<String>();
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                settings.add(temp+"\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return settings;
    }
}
