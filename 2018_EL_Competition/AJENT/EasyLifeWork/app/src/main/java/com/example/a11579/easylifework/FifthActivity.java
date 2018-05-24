package com.example.a11579.easylifework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
    }
    public void go(View v){
        Intent it=new Intent(this,seefruit.class);
        startActivity(it);
    }
    public void Go(View v){
        Intent it=new Intent(this,name.class);
        startActivity(it);
    }
    public void change(View v){
        Intent it=new Intent(this,change.class);
        startActivity(it);
    }
    public void luckdraw(View v){
        Intent it=new Intent(this,luckdraw.class);
        startActivity(it);
    }public void goback (View v){
        Intent it =new Intent(this,MainActivity.class);
        startActivity(it);
        this.finish();
    }
}
