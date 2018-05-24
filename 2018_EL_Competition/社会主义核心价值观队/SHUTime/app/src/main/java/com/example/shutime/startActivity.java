package com.example.shutime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;



public class startActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.start1, null);
        setContentView(view);
        TextView textView = (TextView) findViewById(R.id.start);
        textView.setTextColor(0xCCffffff);

        TextView textView2 = (TextView) findViewById(R.id.name);
        textView2.setTextColor(0xCCffffff);
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(4000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });


    }








    /**
     * 跳转到...
     */
    private void redirectTo(){
        Intent intent = new Intent(startActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

