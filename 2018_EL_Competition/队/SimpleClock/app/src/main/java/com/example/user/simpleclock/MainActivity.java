package com.example.user.simpleclock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triggertrap.seekarc.SeekArc;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

import java.io.IOException;
import java.util.ResourceBundle;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private SeekBar timeSetStr;
    private int inputMin = 0;
    private ImageView bingPicImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 初始界面布局：全屏，隐藏系统自带标题栏
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        /*
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingpic();
        }
        */

        // LitePal



        // 在界面上安放一个SeekBar和Button
        final TextView textView = (TextView) findViewById(R.id.text_view);
        ImageButton button = (ImageButton) findViewById(R.id.button_set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMin > 0) {
                    Clock.actionStart(MainActivity.this, inputMin, -1);
                    overridePendingTransition(0,0);
                } else {
                    Toast.makeText(MainActivity.this, "时间太短啦！多专注一些时间哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageButton buttonBack = (ImageButton) findViewById(R.id.main_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SeekArc seekArc = (SeekArc) findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                textView.setText(Integer.toString(i) + "分钟");
                inputMin = i;
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

    }




    /*
    private void loadBingpic() {
        String requestBingpic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingpic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }
    */

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
