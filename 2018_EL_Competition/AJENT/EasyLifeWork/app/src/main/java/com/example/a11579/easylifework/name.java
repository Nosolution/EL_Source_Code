package com.example.a11579.easylifework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class name extends AppCompatActivity  implements View.OnTouchListener,GestureDetector.OnGestureListener{
    TextView appled,oranged;
    private  RelativeLayout r1;
    private  GestureDetector gd;
    ImageView imageViewapple;
    ImageView imageVieworange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        r1=(RelativeLayout)findViewById(R.id.activity_name);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
        appled =(TextView)findViewById(R.id.appled);
        oranged =(TextView)findViewById(R.id.oranged);
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        int applenumber = readnumber.getInt("applename", -1);
        int prangenumber = readnumber.getInt("orangename", -1);
        if(applenumber==-1){
            appled.setText("暂未获得");
        }
        if(applenumber==233){
            appled.setText("已获得");
        }
        if(prangenumber==-1){
            oranged.setText("暂未获得");
        }
        if(prangenumber==233){
            oranged.setText("已获得");
        }

    }
    public void applechange(View v) {
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int applenumber = readnumber.getInt("apple", -1);
        if (applenumber >= 10) {
            editor.putInt("applename", 233);
            applenumber = applenumber - 10;
            editor.putInt("apple", applenumber);
            editor.commit();
            toast = Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            appled.setText("已获得");
        } else {
            toast = Toast.makeText(getApplicationContext(), "数量不足", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    }
    public void orangechange(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int number = readnumber.getInt("orange", -1);
        if(number>=7){
            editor.putInt("orangename", 233);
            number=number-7;
            editor.putInt("orange",number);
            editor.commit();
            toast = Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            appled.setText("已获得");
        }
        else{
            toast = Toast.makeText(getApplicationContext(), "数量不足", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

    }
    public void back(View v){
        finish();
    }
    public void next(View v){
        Intent it =new Intent(this,name1.class);
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
            Intent intent = new Intent(name.this, name1.class);
            startActivity(intent);
        }if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(name.this,FifthActivity.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}