package com.example.a11579.easylifework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class change3 extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{
    private RelativeLayout r1;
    private  GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change3);

        r1=(RelativeLayout)findViewById(R.id.activity_change3);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
    }    public void back(View v){
        finish();
    }
    public  void c1(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int pineapple = readnumber.getInt("pineapple", -1);
        if(pineapple== -1){
            pineapple=0;
            editor2.putInt("pineapple",0);
            editor2.commit();
        }
        int pitaya = readnumber.getInt("pitaya", -1);
        if(pitaya == -1){
            pitaya=0;
            editor2.putInt("pitaya",0);
            editor2.commit();
        }
        int watermelon = readnumber.getInt("watermelon", -1);
        if(watermelon == -1){
            watermelon=0;
            editor2.putInt("pitaya",0);
            editor2.commit();
        }
        int pomegranate   = readnumber.getInt("pomegranate", -1);
        if(pomegranate   == -1){
            pomegranate  =0;
            editor2.putInt("pomegranate",0);
            editor2.commit();
        }
        if(pineapple<2&&pitaya<2&&watermelon<2){
            toast = Toast.makeText(getApplicationContext(),"菠萝、火龙果和西瓜都不足，可要继续加油啊(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=2&&pitaya<2&&watermelon<2) {
            toast = Toast.makeText(getApplicationContext(), "火龙果和西瓜不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple<2&&pitaya>=2&&watermelon<2) {
            toast = Toast.makeText(getApplicationContext(), "菠萝和西瓜不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple<2&&pitaya<2&&watermelon>=2){
            toast = Toast.makeText(getApplicationContext(), "菠萝和火龙果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=2&&pitaya>=2&&watermelon<2){
            toast = Toast.makeText(getApplicationContext(), "西瓜不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=2&&pitaya<2&&watermelon>=2) {
            toast = Toast.makeText(getApplicationContext(), "火龙果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        else if(pineapple<2&&pitaya>=2&&watermelon>=2){
            toast = Toast.makeText(getApplicationContext(), "火龙果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=2&&pitaya>=2&&watermelon>=2){
            pineapple = pineapple - 2;
            pitaya = pitaya - 2;
            watermelon = watermelon - 2;
            pomegranate++;
            editor2.putInt("pineapple",pineapple);
            editor2.putInt("pitaya",pitaya);
            editor2.putInt("watermelon",watermelon);
            editor2.putInt("pomegranate",pomegranate);
            editor2.commit();
            toast = Toast.makeText(getApplicationContext(),"兑换成功" , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
    public  void c2(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int pineapple = readnumber.getInt("pineapple", -1);
        if(pineapple== -1){
            pineapple=0;
            editor2.putInt("pineapple",0);
            editor2.commit();
        }
        int pitaya = readnumber.getInt("pitaya", -1);
        if(pitaya == -1){
            pitaya=0;
            editor2.putInt("pitaya",0);
            editor2.commit();
        }
        int watermelon = readnumber.getInt("watermelon", -1);
        if(watermelon == -1){
            watermelon=0;
            editor2.putInt("pitaya",0);
            editor2.commit();
        }
        int coconut   = readnumber.getInt("coconut", -1);
        if(coconut   == -1){
            coconut  =0;
            editor2.putInt("coconut",0);
            editor2.commit();
        }
        if(pineapple<3&&pitaya<3&&watermelon<3){
            toast = Toast.makeText(getApplicationContext(),"菠萝、火龙果和西瓜都不足，可要继续加油啊(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=3&&pitaya<3&&watermelon<3) {
            toast = Toast.makeText(getApplicationContext(), "火龙果和西瓜不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple<3&&pitaya>=3&&watermelon<3) {
            toast = Toast.makeText(getApplicationContext(), "菠萝和西瓜不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple<3&&pitaya<3&&watermelon>=3){
            toast = Toast.makeText(getApplicationContext(), "菠萝和火龙果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=3&&pitaya>=3&&watermelon<3){
            toast = Toast.makeText(getApplicationContext(), "西瓜不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=3&&pitaya<3&&watermelon>=3) {
            toast = Toast.makeText(getApplicationContext(), "火龙果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        else if(pineapple<3&&pitaya>=3&&watermelon>=3){
            toast = Toast.makeText(getApplicationContext(), "火龙果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(pineapple>=3&&pitaya>=3&&watermelon>=3){
            pineapple = pineapple - 2;
            pitaya = pitaya - 2;
            watermelon = watermelon - 2;
            coconut++;
            editor2.putInt("pineapple",pineapple);
            editor2.putInt("pitaya",pitaya);
            editor2.putInt("watermelon",watermelon);
            editor2.putInt("coconut",coconut);
            editor2.commit();
            toast = Toast.makeText(getApplicationContext(),"兑换成功" , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final int FLING_MIN_DISTANCE=100;
        final int FLING_MIN_VELOCITY=200;

        if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(change3.this, change2.class);
            startActivity(intent);
        }


        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}
