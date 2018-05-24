package com.example.a11579.easylifework;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class luckdraw extends AppCompatActivity {
    ImageView mihoutao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luckdraw);
        mihoutao=(ImageView)findViewById(R.id.imageView21);
    }
    public void luckdraw(View v){
        RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1500);//设置动画持续时间
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        mihoutao.setAnimation(rotate);
        Toast toast = null;
        SharedPreferences readnumber = getSharedPreferences("fruit", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = getSharedPreferences("fruit", MODE_PRIVATE).edit();
        int apple = readnumber.getInt("apple",0);
        int kiwifruit = readnumber.getInt("kiwifruit", 0);
        if(kiwifruit == 0){
            editor2.putInt("kiwifruit",0);
            editor2.commit();
        }
        if (apple<10) {
            toast = Toast.makeText(getApplicationContext(), "苹果不足，要继续加油哦(°∀°)ﾉ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            apple = apple - 10;
            double math=Math.random()*10;
            if(math>=4&&math<=6) {
                kiwifruit++;
                toast = Toast.makeText(getApplicationContext(),"恭喜中奖", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else{
                toast = Toast.makeText(getApplicationContext(), "没有中奖", Toast.LENGTH_LONG);}
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            editor2.putInt("apple",apple);
            editor2.putInt("kiwifruit",kiwifruit);
            editor2.commit();
        }
    }
    public void back(View v){
        finish();
    }
}
