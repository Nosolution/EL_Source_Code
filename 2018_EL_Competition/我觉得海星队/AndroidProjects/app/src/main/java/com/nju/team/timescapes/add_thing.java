package com.nju.team.timescapes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * Created by ASUS on 2018/5/23.
 */

public class add_thing extends AppCompatActivity{
    private Button Add_thing;
    private EditText add_et_1;
    private Button clear_text;
    private ImageButton add_back;
    private TextView current_time;
    private SharedPreferences msharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //任务栏透明
        changeStatusBarColor();

        //读calender的数据
        msharedPreferences = getSharedPreferences("YEAR_MONTH_DATE",MODE_PRIVATE);
        SharedPreferences.Editor editor = msharedPreferences.edit();
        final String date_time = msharedPreferences.getString("INTENT","1970年1月1日");


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        add_et_1=findViewById(R.id.et_1);
        clear_text = (Button) findViewById(R.id.btn_clear_text);
        add_back = (ImageButton) findViewById(R.id.ib_add_back);
        current_time = (TextView) findViewById(R.id.current_time);

        current_time.setText(simpleDateFormat.format(date));

        Add_thing=findViewById(R.id.btn_1);
        Add_thing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String et_1=add_et_1.getText().toString();


                if(et_1.length()==0){
                    Toast.makeText(getApplicationContext(), "不能添加空日程！", Toast.LENGTH_LONG).show();
                    add_et_1.setText("");
                }
                else {
                    //对日程文字进行转换操作
                    if(et_1.substring(et_1.length()-1,et_1.length()).equals("\n")){
                        et_1 = et_1.substring(0,et_1.length()-1);
                    }
                    String result = et_1.replaceAll("\n","@@@@@");
                    saveData(date_time+"  |   "+result, "info.txt");
                    Intent mainIntent = new Intent(add_thing.this, thingsTodo.class);
                    add_thing.this.startActivity(mainIntent);
                    add_thing.this.finish();
                }
            }
        });

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_et_1.setText("");
            }
        });

        add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(add_thing.this, thingsTodo.class);
                add_thing.this.startActivity(mainIntent);
                add_thing.this.finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent mainIntent = new Intent(add_thing.this, thingsTodo.class);
                add_thing.this.startActivity(mainIntent);
                add_thing.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void saveData(String data,String filename) {

        String content = data + "\n";
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {
            FileOutputStream fos = new FileOutputStream(file,true);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
