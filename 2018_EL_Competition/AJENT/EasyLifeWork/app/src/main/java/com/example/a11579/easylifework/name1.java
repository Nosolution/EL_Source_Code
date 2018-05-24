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
import android.widget.TextView;
import android.widget.Toast;

public class name1 extends AppCompatActivity  implements View.OnTouchListener,GestureDetector.OnGestureListener{
    TextView peard,pineappled;
    private RelativeLayout r1;
    private  GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name1);
        r1=(RelativeLayout)findViewById(R.id.activity_name1);
        r1.setOnTouchListener(this);
        r1.setLongClickable(true);
        gd=new GestureDetector(this);
        peard =(TextView)findViewById(R.id.peard);
        pineappled =(TextView)findViewById(R.id.pineappled);
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        int onenumber = readnumber.getInt("pearname", -1);
        int twonumber = readnumber.getInt("pineapplename", -1);
        if(onenumber==-1){
            peard.setText("暂未获得");
        }
        if(onenumber==233){
            peard.setText("已获得");
        }
        if(twonumber==-1){
            pineappled.setText("暂未获得");
        }
        if(twonumber==233){
            pineappled.setText("已获得");
        }

    }
    public void pearchange(View v) {
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int applenumber = readnumber.getInt("pear", -1);
        if (applenumber >= 8) {
            editor.putInt("pearname", 233);
            applenumber = applenumber - 8;
            editor.putInt("pear", applenumber);
            editor.commit();
            toast = Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            peard.setText("已获得");
        } else {
            toast = Toast.makeText(getApplicationContext(), "数量不足", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    }
    public void pineapplechange(View v){
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int number = readnumber.getInt("pineapple", -1);
        if(number>=6){
            editor.putInt("pineapplename", 233);
            number=number-6;
            editor.putInt("pineapple",number);
            editor.commit();
            toast = Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            pineappled.setText("已获得");
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
        Intent it =new Intent(this,name2.class);
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
            Intent intent = new Intent(name1.this, name2.class);
            startActivity(intent);
        }if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(name1.this,name.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}