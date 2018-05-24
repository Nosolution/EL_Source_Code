package com.example.administrator.el_done1;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TimerCancelDialogActivity extends AppCompatActivity {


    //重写活动销毁方式，用ActivityManager来销毁
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //根据主题设置字体
        switch (Theme.getTHEME()){
            case "SIMPLE":
                setTheme(R.style.SIMPLE_THEME);
                break;
            case "OTAKU":
                setTheme(R.style.OTAKU_THEME);
                break;
            case "PET":
                setTheme(R.style.PET_THEME);
                break;
        }

        //设立layout
        setContentView(R.layout.timer_cancel_dialog_activity);

        //存入ActivityManager
        ActivityManager.addActivity(TimerCancelDialogActivity.this);

        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }




        Button timer_cancel_dialog_true = (Button)findViewById(R.id.timer_cancel_dialog_true);
        Button timer_cancel_dialog_false = (Button)findViewById(R.id.timer_cancel_dialog_false);
        ImageView timer_cancel_dialog_deskPetView = (ImageView)findViewById(R.id.timer_cancel_dialog_deskPetView);
        timer_cancel_dialog_deskPetView.setImageResource(DeskPet.getImageIdOf(0));

        timer_cancel_dialog_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimerCancelDialogActivity.this,"真棒！",
                        Toast.LENGTH_SHORT).show();
                ActivityManager.removeActivity(TimerCancelDialogActivity.this);
            }
        });

        timer_cancel_dialog_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimerCancelDialogActivity.this,"要继续努力啊",
                        Toast.LENGTH_SHORT).show();
                ActivityManager.removeActivity(TimerCancelDialogActivity.this);
            }
        });


        int i =0;
        switch (MainActivity.DeskPetName){
            case "DeskPetWanzi":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetWanzi.getImageIdOf(i));
                break;
            case "DeskPetMoli":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetMoli.getImageIdOf(i));
                break;
            case "DeskPetChuyin":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetChuyin.getImageIdOf(i));
                break;
            case "DeskPetGumingdijue":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetGumingdijue.getImageIdOf(i));
                break;
            case "DeskPetMaoerniang":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetMaoerniang.getImageIdOf(i));
                break;
            case "DeskPetMaoerkunan":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetMaoerkunan.getImageIdOf(i));
                break;
            case "DeskPetYinshi":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetYinshi.getImageIdOf(i));
                break;
            case "DeskPetMaolaoshi":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetMaolaoshi.getImageIdOf(i));
                break;
            case "DeskPetPikaqiu":
                timer_cancel_dialog_deskPetView.setImageResource(DeskPetPikaqiu.getImageIdOf(i));
                break;
            default:
                break;
        }

    }


}
