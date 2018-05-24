package com.example.caea8.memoranduml;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private Button buttonManage;
    private Button buttonSetting;
    private FileOutputStream out=null;
    private BufferedWriter writer=null;
    private FileInputStream in=null;
    private BufferedReader reader=null;
    private ActionBar actionBar;
    public static String style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局


        if(style==null){
            style="normal";
        }

        switch (style){
            case"normal":
                setContentView(R.layout.activity_main);break;
            case "nju":
                setContentView(R.layout.activity_main_nju);break;
            default:break;
        }

        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }



        buttonManage=(Button)findViewById(R.id.manage);
        buttonManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

        buttonSetting=(Button)findViewById(R.id.setting);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
