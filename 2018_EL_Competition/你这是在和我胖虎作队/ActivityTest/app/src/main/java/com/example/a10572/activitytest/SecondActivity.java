package com.example.a10572.activitytest;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.PlayGame:
                Toast.makeText(this, "检测一下你的专注程度", Toast.LENGTH_SHORT).show();
                Intent intentToGame = new Intent(SecondActivity.this,GameActivity.class);
                startActivity(intentToGame);
                break;
            case R.id.Settings:
                Intent intentToSettings = new Intent(SecondActivity.this,SettingsActivity.class);
                startActivity(intentToSettings);
                break;
            case R.id.Quit:
                MusicPlayer.stop();
                ActivityCollector.finishAll();
            default:
        }
        return true;
    }
    private MediaPlayer MusicPlayer = new MediaPlayer();

    private boolean isPlayingLight = false;
    private boolean isPlayingClassic = false;
    private boolean isPlayingNature = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    private Chronometer timer;
    private int mode = 0;
    private long mRecordTime;
    private boolean startFromZero = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        timer = (Chronometer)this.findViewById(R.id.chronometer);
        //长按计时器时，出现上下文菜单
        this.registerForContextMenu(timer);


        final Button button2 = (Button) findViewById(R.id.button_2);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        button2.setTypeface(typeface2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        实现Light Music （button2）
                if(!isPlayingLight ) {
                    MusicPlayer.stop();
                    isPlayingNature = false;
                    isPlayingClassic = false;
                    MusicPlayer = MediaPlayer.create(SecondActivity.this, R.raw.light);
                    MusicPlayer.start();
                    MusicPlayer.setLooping(true);
                    isPlayingLight = true;
                    Toast.makeText(SecondActivity.this, "正在播放", Toast.LENGTH_SHORT).show();
                }
                else {
                    MusicPlayer.stop();
                    isPlayingLight = false;
                    Toast.makeText(SecondActivity.this, "安静如我胖虎", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button button3 = (Button) findViewById(R.id.button_3);
        Typeface typeface3 = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        button3.setTypeface(typeface3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        实现 classical music（button3）
                if(!isPlayingClassic) {
                    MusicPlayer.stop();
                    isPlayingNature = false;
                    isPlayingLight = false;
                    MusicPlayer = MediaPlayer.create(SecondActivity.this, R.raw.classic);
                    MusicPlayer.start();
                    MusicPlayer.setLooping(true);
                    isPlayingClassic = true;
                    Toast.makeText(SecondActivity.this, "正在播放", Toast.LENGTH_SHORT).show();
                }
                else{
                    MusicPlayer.stop();
                    isPlayingClassic = false;
                    Toast.makeText(SecondActivity.this, "安静如我胖虎", Toast.LENGTH_SHORT).show();

                }

            }
        });

        final Button button4 = (Button) findViewById(R.id.button_4);
        Typeface typeface4 = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        button4.setTypeface(typeface4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        实现 nature music（button4）
                if(!isPlayingNature) {
                    MusicPlayer.stop();
                    isPlayingClassic = false;
                    isPlayingLight = false;
                    MusicPlayer = MediaPlayer.create(SecondActivity.this, R.raw.nature);
                    MusicPlayer.start();
                    MusicPlayer.setLooping(true);
                    isPlayingNature = true;
                    Toast.makeText(SecondActivity.this, "正在播放", Toast.LENGTH_SHORT).show();

                }
                else{
                    MusicPlayer.stop();
                    isPlayingNature = false;
                    Toast.makeText(SecondActivity.this, "安静如我胖虎", Toast.LENGTH_SHORT).show();
                }

            }
        });


        final Button button5 = (Button) findViewById(R.id.button_5);
        Typeface typeface5 = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        button5.setTypeface(typeface5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mode==0) {
                    if(startFromZero){
                        timer.setBase(SystemClock.elapsedRealtime());
                    }
                    else {
                        timer.setBase(SystemClock.elapsedRealtime() - mRecordTime);
                    }
                    timer.start();
                    button5.setText(R.string.Rest);
                    mode = 1-mode;
                }
                else{
                    timer.stop();
                    mRecordTime = SystemClock.elapsedRealtime()-timer.getBase();
                    button5.setText(R.string.Concentration);
                    mode = 1-mode;
                }
                startFromZero = false;
            }
        });

        final Button button6 = (Button) findViewById(R.id.button_6);
        Typeface typeface6 = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        button6.setTypeface(typeface6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button5.setText(R.string.Concentration);
                timer.stop();
                timer.setBase(SystemClock.elapsedRealtime());
                // double lastTime = new Long (SystemClock.elapsedRealtime()-timer.getBase());

                // double h = (lastTime/3600);
                // double min = ((lastTime%3600)/60);
                // double sec = ((lastTime%3600)%60);
                Toast.makeText(SecondActivity.this,"与胖虎度过安静时光(●'◡'●)",Toast.LENGTH_LONG).show();
                startFromZero = true;
                mode = 0;


            }
        });
    }
    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        // ContextMenu的Item不支持Icon，所以不用再资源文件中，为它们设定图标
        if (v.getId() == R.id.chronometer)
        {
            //加载xml菜单布局文件
            this.getMenuInflater().inflate(R.menu.timekeepermenu, menu);
//            设定头部图标
//            menu.setHeaderIcon(R.drawable.icon);
            // 设定头部标题
            menu.setHeaderTitle("Options");
        }
    }
    //选择菜单项后的响应
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.timer_start:
                // 将计时器清零
                timer.setBase(SystemClock.elapsedRealtime());
                //开始计时
                timer.start();
                break;
            case R.id.timer_stop:
                //停止计时
                timer.stop();
                break;
            case R.id.timer_reset:
                //将计时器清零
                timer.setBase(SystemClock.elapsedRealtime());
                break;
        }
        return super.onContextItemSelected(item);
    }
}
