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

public class seefruit extends AppCompatActivity  implements View.OnTouchListener,GestureDetector.OnGestureListener{
    TextView apple,orange,pear;
    private RelativeLayout r1;
    private  GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seefruit);
        apple=(TextView)findViewById(R.id.apple);
        orange=(TextView)findViewById(R.id.orange);
        pear=(TextView)findViewById(R.id.pear);
        r1=(RelativeLayout)findViewById(R.id.activity_seefruit);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);

        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        int applenumber = readnumber.getInt("apple", -1);
        if(applenumber == -1){
            apple.setText("苹果：0");
        }
        else{
            apple.setText("苹果："+applenumber);
        }
        int orangenumber = readnumber.getInt("orange", -1);
        if(orangenumber == -1){
            orange.setText("橘子：0");
        }
        else{
            orange.setText("橘子："+orangenumber);
        }
        int pearnumber = readnumber.getInt("pear", -1);
        if(pearnumber == -1){
            pear.setText("梨子：0");
        }
        else{
            pear.setText("梨子："+pearnumber);
        }


    }
    public void back(View v){
        finish();
    }
    public void next(View v){
        Intent it =new Intent(this,see2.class);
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
            Intent intent = new Intent(seefruit.this, see2.class);
            startActivity(intent);
        }if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(seefruit.this,FifthActivity.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}
