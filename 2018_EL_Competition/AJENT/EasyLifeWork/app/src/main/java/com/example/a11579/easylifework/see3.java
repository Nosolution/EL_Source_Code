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

public class see3 extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{
    TextView pomegranate,coconut,kiwifruit;
    private RelativeLayout r1;
    private  GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see3);
        r1=(RelativeLayout)findViewById(R.id.activity_see3);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
        pomegranate = (TextView) findViewById(R.id.pomegranate);
        coconut = (TextView) findViewById(R.id.coconut);
        kiwifruit = (TextView) findViewById(R.id.kiwifruit);
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        int pomegranatenumber = readnumber.getInt("pomegranate", -1);
        if (pomegranatenumber == -1) {
            pomegranate.setText("石榴：0");
        } else {
            pomegranate.setText("石榴：" + pomegranatenumber);
        }
        int coconutnumber = readnumber.getInt("coconut", -1);
        if (coconutnumber == -1) {
            coconut.setText("椰子：0");
        } else {
            coconut.setText("椰子：" + coconutnumber);
        }
        int kiwifruitnumber = readnumber.getInt("kiwifruit", -1);
        if (kiwifruitnumber == -1) {
            kiwifruit.setText("猕猴桃：0");
        } else {
            kiwifruit.setText("猕猴桃：" + kiwifruitnumber);
        }
    }

    public void back(View v){
        finish();
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
            Intent intent = new Intent(see3.this,see2.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}

