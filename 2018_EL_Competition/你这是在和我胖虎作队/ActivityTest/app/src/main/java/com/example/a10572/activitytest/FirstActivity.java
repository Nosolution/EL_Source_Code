package com.example.a10572.activitytest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;


public class FirstActivity extends BaseActivity {
    private boolean istiming = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1  = (Button) findViewById(R.id.button_1);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        button1.setTypeface(typeface1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                istiming = false;
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        final Intent it = new Intent(FirstActivity.this, SecondActivity.class); //你要转向的Activity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (istiming)                 //判断是否点击
                {startActivity(it);} //执行
            }
        };
        timer.schedule(task, 1000 * 3); //3秒后 自动跳转second

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.PlayGame:
                Toast.makeText(this, "检测一下你的专注程度", Toast.LENGTH_SHORT).show();
                Intent intentToGame = new Intent(FirstActivity.this,GameActivity.class);
                startActivity(intentToGame);
                break;
            case R.id.Settings:
                Intent intentToSettings = new Intent(FirstActivity.this,SettingsActivity.class);
                startActivity(intentToSettings);
                break;
            case R.id.Quit:
                ActivityCollector.finishAll();
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
}
