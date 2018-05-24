package com.example.a11579.easylifework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClicktwo(View v) {
        Intent it =new Intent(this,ChoActivity.class);
        startActivity(it);
    }
    public void onClickthree(View v) {
        Intent it=new Intent(this,FifthActivity.class);
        startActivity(it);
    }
    public void onClickfour(View v) {
        Intent it =new Intent(this,SetWork.class);
        startActivity(it);
    }
    public void onClickfive(View v){
        Intent it =new Intent(this,shuoming.class);
        startActivity(it);
    }
}

