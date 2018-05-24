package com.example.user.simpleclock;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextClock;

import org.litepal.LitePal;

public class HomePageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        LitePal.getDatabase();

        TextClock textClock = (TextClock) findViewById(R.id.hp_clock);
        textClock.getFormat24Hour();
        textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordDiaryActivity.actionStart(HomePageActivity.this);
            }
        });
        TextClock dateClock = (TextClock) findViewById(R.id.hp_date);
        dateClock.getFormat24Hour();
        dateClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordDiaryActivity.actionStart(HomePageActivity.this);
            }
        });

        ImageButton focusButton = (ImageButton) findViewById(R.id.focus_button);
        focusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actionStart(HomePageActivity.this);
            }
        });
        ImageButton recordButton = (ImageButton) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordFocusActivity.actionStart(HomePageActivity.this);
            }
        });
        ImageButton todoButton = (ImageButton) findViewById(R.id.mine_button);
        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoActivity.actionStart(HomePageActivity.this);
            }
        });
    }


}
