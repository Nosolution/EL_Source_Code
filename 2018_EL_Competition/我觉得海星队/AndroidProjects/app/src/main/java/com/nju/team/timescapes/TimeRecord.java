package com.nju.team.timescapes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.TextView;

public class TimeRecord extends AppCompatActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> list;
    private ImageButton btn_exitTimeRecord;
    private ImageView pic_no_route;
    private TextView text_no_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //让状态栏透明
        changeStatusBarColor();

        pic_no_route = (ImageView) findViewById(R.id.image_no_route);
        text_no_route = (TextView) findViewById(R.id.text_no_route);


        lv=findViewById(R.id.lv_1);
        getData();
        simpleAdapter=new SimpleAdapter(this,list,R.layout.item_timerecord,new String[]{"time","timeLength","tag"},new int[]{R.id.tv_1,R.id.tv_2,R.id.tv_3});
        lv.setAdapter(simpleAdapter);
        lv.setDividerHeight(0);

        btn_exitTimeRecord = (ImageButton) findViewById(R.id.imageButton_exitTimeRecord);
        //按钮事件
        btn_exitTimeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(TimeRecord.this, AchievementsPanelActivity.class);
                TimeRecord.this.startActivity(mainIntent);
                TimeRecord.this.finish();
            }
        });
    }



    private void getData() {
        list=new ArrayList<Map<String, Object>>();
        ArrayList TimeList=readData("Time.txt");
        ArrayList FocusTimeList=readData("FocusTime.txt");
        ArrayList TagList=readData("Tag.txt");
        if(TimeList.size() != 0){
            pic_no_route.setVisibility(View.GONE);
            text_no_route.setVisibility(View.GONE);
        }else{
            pic_no_route.setVisibility(View.VISIBLE);
            text_no_route.setVisibility(View.VISIBLE);
        }
        for(int i=TimeList.size()-1;i>=0;i--){
            Map map=new HashMap<String,Object>();
            String focus_time = FocusTimeList.get(i).toString();
            focus_time = focus_time.substring(0,focus_time.length()-1);
            map.put("time",TimeList.get(i));
            map.put("timeLength","完成了一次"+focus_time+"分钟的专注");
            map.put("tag",TagList.get(i));
            list.add(map);
        }
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
                Intent mainIntent = new Intent(TimeRecord.this, AchievementsPanelActivity.class);
                TimeRecord.this.startActivity(mainIntent);
                TimeRecord.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

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
