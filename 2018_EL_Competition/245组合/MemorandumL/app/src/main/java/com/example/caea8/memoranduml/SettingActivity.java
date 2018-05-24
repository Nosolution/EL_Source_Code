package com.example.caea8.memoranduml;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SettingActivity extends AppCompatActivity {

    private Button setNormal;
    private Button setNju;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        switch (MainActivity.style){
            case"normal":
                setContentView(R.layout.setting_layout);break;
            case "nju":
                setContentView(R.layout.setting_layout_nju);break;
            default:break;
        }

        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        setNormal=(Button)findViewById(R.id.set_normal);
        setNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.style="normal";

                Intent intent=new Intent(SettingActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        setNju=(Button)findViewById(R.id.set_nju);
        setNju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.style="nju";

                Intent intent=new Intent(SettingActivity.this,SettingActivity.class);
                startActivity(intent);

                Log.d("SettingActivity","kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            }
        });

    }


}
