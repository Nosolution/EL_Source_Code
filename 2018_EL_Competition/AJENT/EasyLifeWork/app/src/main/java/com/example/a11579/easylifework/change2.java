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

public class change2 extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{
    private RelativeLayout r1;
    private  GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change2);

        r1=(RelativeLayout)findViewById(R.id.activity_change2);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
    }

    public  void c1(View v){
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
        int pitaya = readnumber.getInt("pitaya", -1);
        if(pitaya == -1){
            pitaya=0;
            editor2.putInt("pitaya",0);
            editor2.commit();
        }
        if(juice<4&&pear>=4){
            toast = Toast.makeText(getApplicationContext(),"橘子不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(juice>=4&&pear<4){
            toast = Toast.makeText(getApplicationContext(),"香梨不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(juice<4&&pear<4){
            toast = Toast.makeText(getApplicationContext(),"香梨和橘子不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(juice>=5&&pear>=5){
            juice = juice - 5;
            pear=pear-5;
            pitaya++;
            editor2.putInt("orange",juice);
            editor2.putInt("pear",pear);
            editor2.putInt("pitaya",pitaya);
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
        int watermelon = readnumber.getInt("watermelon", -1);
        if(watermelon== -1){
            watermelon=0;
            editor2.putInt("watermelon",0);
            editor2.commit();
        }
        if(juice<5&&pear>=5){
            toast = Toast.makeText(getApplicationContext(),"橘子不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(juice>=5&&pear<5){
            toast = Toast.makeText(getApplicationContext(),"香梨不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(juice<5&&pear<5){
            toast = Toast.makeText(getApplicationContext(),"香梨和橘子不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
        else if(juice>=5&&pear>=5){
            juice = juice - 5;
            pear=pear-5;
            watermelon++;
            editor2.putInt("orange",juice);
            editor2.putInt("pear",pear);
            editor2.putInt("watermelon",watermelon);
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
        Intent it =new Intent(this,change3.class);
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
            Intent intent = new Intent(change2.this, change3.class);
            startActivity(intent);
        }if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(change2.this, change.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}
