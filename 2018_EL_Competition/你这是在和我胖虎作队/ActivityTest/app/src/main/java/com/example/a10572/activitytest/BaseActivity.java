package com.example.a10572.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class BaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
