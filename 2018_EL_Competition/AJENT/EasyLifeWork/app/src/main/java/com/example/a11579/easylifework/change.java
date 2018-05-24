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

public class change extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{
    private  RelativeLayout r1;
    private  GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        r1=(RelativeLayout)findViewById(R.id.activity_change);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
    }
    public  void c1(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int apple = readnumber.getInt("apple", -1);
        if(apple == -1){
            apple=0;
            editor2.putInt("apple",0);
            editor2.commit();
        }
        int juice = readnumber.getInt("orange", -1);
        if(juice == -1){
            juice=0;
            editor2.putInt("orange",0);
            editor2.commit();
        }
        if(apple<3) {
            toast = Toast.makeText(getApplicationContext(), "苹果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            apple = apple - 3;
            juice++;
            editor2.putInt("apple",apple);
            editor2.putInt("orange",juice);
            editor2.commit();
            toast = Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    public  void c2(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int apple = readnumber.getInt("apple", -1);
        if(apple == -1){
            apple=0;
            editor2.putInt("apple",0);
            editor2.commit();
        }
        int pear = readnumber.getInt("pear", -1);
        if(pear == -1){
            pear=0;
            editor2.putInt("pear",0);
            editor2.commit();
        }
        if(apple<2) {
            toast = Toast.makeText(getApplicationContext(), "苹果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            apple = apple - 2;
            pear++;
            editor2.putInt("apple",apple);
            editor2.putInt("pear",pear);
            editor2.commit();
            toast = Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    public  void c3(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int pear = readnumber.getInt("pear", -1);
        if(pear == -1){
            pear=0;
            editor2.putInt("pear",0);
            editor2.commit();
        }
        int juice = readnumber.getInt("orange", -1);
        if(juice == -1){
            juice=0;
            editor2.putInt("orange",0);
            editor2.commit();
        }
        int pineapple = readnumber.getInt("pineapple", -1);
        if(pineapple == -1){
            pineapple=0;
            editor2.putInt("pineapple",0);
            editor2.commit();
        }
        if(juice<3&&pear>=3){
            toast = Toast.makeText(getApplicationContext(),"橘子不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(juice>=3&&pear<3){
            toast = Toast.makeText(getApplicationContext(),"香梨不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(juice<3&&pear<3){
            toast = Toast.makeText(getApplicationContext(),"香梨和橘子不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(juice>=3&&pear>=3){
            juice = juice - 3;
            pear=pear-3;
            pineapple++;
            editor2.putInt("orange",juice);
            editor2.putInt("pear",pear);
            editor2.putInt("pineapple",pineapple);
            editor2.commit();
            toast = Toast.makeText(getApplicationContext(),"兑换成功" , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    public void back(View v){
        finish();
    }
    public void next(View v){
        Intent it =new Intent(this,change2.class);
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
            Intent intent = new Intent(change.this, change2.class);
            startActivity(intent);
        }if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(change.this,FifthActivity.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}
