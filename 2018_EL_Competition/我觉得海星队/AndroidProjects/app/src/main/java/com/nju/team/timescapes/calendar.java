package com.nju.team.timescapes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by ASUS on 2018/5/23.
 */

public class calendar extends AppCompatActivity{
    private CalendarView cv;
    private Context context;
    private SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //任务栏透明
        changeStatusBarColor();

        cv=findViewById(R.id.cd_1);
        //设置样式

        context = this;

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String content = year+"年"+(month+1)+"月"+dayOfMonth+"日";
                Intent first =new Intent(calendar.this,thingsTodo.class);
                first.putExtra("datetime",content);
                startActivities(new Intent[]{first});
                calendar.this.finish();
                msharedPreferences = getSharedPreferences("YEAR_MONTH_DATE",MODE_PRIVATE);
                SharedPreferences.Editor editor = msharedPreferences.edit();
                editor.putString("INTENT",content);
                editor.commit();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent mainIntent = new Intent(calendar.this, MainScreenActivity.class);
                calendar.this.startActivity(mainIntent);
                calendar.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
