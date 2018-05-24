package com.example.a11579.easylifework;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class see2 extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener {
    TextView watermelon,pitaya,pineapple;
    private RelativeLayout r1;
    private  GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see2);
        r1=(RelativeLayout)findViewById(R.id.activity_see2);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
        watermelon=(TextView)findViewById(R.id.watermelon);
        pitaya=(TextView)findViewById(R.id.pitaya);
        pineapple=(TextView)findViewById(R.id.pineapple);
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        int watermelonnumber = readnumber.getInt("watermelon", -1);
        if(watermelonnumber == -1){
            watermelon.setText("西瓜：0");
        }
        else{
            watermelon.setText("西瓜："+watermelonnumber);
        }
        int pitayanumber = readnumber.getInt("pitaya", -1);
        if(pitayanumber == -1){
            pitaya.setText("火龙果：0");
        }
        else{
            pitaya.setText("火龙果："+pitayanumber);
        }
        int pineapplenumber = readnumber.getInt("pineapple", -1);
        if(pineapplenumber == -1){
            pineapple.setText("菠萝：0");
        }
        else{
            pineapple.setText("菠萝："+pineapplenumber);
        }
    }
    public void back(View v){
        finish();
    }
    public void next(View v){
        Intent it =new Intent(this,see3.class);
        startActivity(it);
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

        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(see2.this, see3.class);
            startActivity(intent);
        }if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(see2.this,seefruit.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}