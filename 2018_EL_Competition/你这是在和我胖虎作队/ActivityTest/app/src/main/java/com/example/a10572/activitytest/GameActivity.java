package com.example.a10572.activitytest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public class GameActivity extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.Settings:
                Intent intentToSettings = new Intent(GameActivity.this,SettingsActivity.class);
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
        getMenuInflater().inflate(R.menu.gamemenu,menu);
        return true;
    }

    private Map<Integer, String> map = new HashMap<Integer, String>();
    private Button kaishi;
    private TextView txtview;
    private threadone t1;
    private Button[] btn=new Button[25];
    private int num = 1; // 点击按钮验证数字
    private int width = 0; //
    private int length = 0; //
    private String s=null;
    private boolean playable=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm1 = this.getWindowManager();
        width = wm1.getDefaultDisplay().getWidth();
        length = (width - 40) / 5;
        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackground(getDrawable(R.drawable.backround));
        txtview=new TextView(this);


        kaishi=new Button(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
        kaishi.setTypeface(typeface);              //开始按钮
        kaishi.setText("Start");
        kaishi.setBackground(getDrawable(R.drawable.start));
        kaishi.setTextSize(50);
        txtview.setTextSize(30);


        t1=new threadone(250);
        findViews(layout);
        txtview.setText("25.0s");
        txtview.setGravity(Gravity.CENTER);
        setContentView(layout);
        setListener(); // 批量设置按钮点击监听事件
    }




    private void findViews(RelativeLayout layout) {
        for (int i = 0, j = 0; i < 25; i++) {
            btn[i] = new Button(this);

            btn[i].setBackgroundResource(R.drawable.kuang);    //按钮背景
            Typeface typeface = Typeface.createFromAsset(getAssets(),"font/our_font.ttf");
            btn[i].setTypeface(typeface);       //按钮字体
            btn[i].setTextSize(25);        //按钮字体大小
            int temp = getRandom();
            btn[i].setText(temp + "");
            btn[i].setId(temp);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    length, length);
            if (i != 0 & i % 5 == 0) {
                j++;
            }
            params.leftMargin = 40 + length * (i % 5) - (i % 5) * 10;
            params.topMargin = 40 + length * j - j * 10;
            layout.addView(btn[i], params);

        }

        RelativeLayout.LayoutParams paramthree = new RelativeLayout.LayoutParams(
                5*length, 2*length);
        paramthree.leftMargin=20;
        paramthree.topMargin=6*length;
        layout.addView(kaishi, paramthree);

        RelativeLayout.LayoutParams paramtwo = new RelativeLayout.LayoutParams(
                width-40, length);
        paramtwo.leftMargin=20;
        paramtwo.topMargin=5*length+10;
        layout.addView(txtview, paramtwo);
    }


    private Integer getRandom() {
        Random random = new Random(System.currentTimeMillis());
        int i = 0;
        do {
            i = random.nextInt(25) + 1;
        } while (map.get(i) != null);
        map.put(i, i + "");

        return i;
    }


    private void setListener() {
        btn[0].setTag(btn[0].getId());
        btn[0].setOnClickListener(btn1);
        for (int i = 1; i < 25; i++) {
            btn[i].setTag(btn[i].getId());
            btn[i].setOnClickListener(ifEqu);
        }
        kaishi.setOnClickListener(kt);
    }

    private Button.OnClickListener ifEqu = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (Integer) v.getTag();
            if (i == num) {
                num++;
                ((Button) v).setVisibility(View.INVISIBLE);
            } else {
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("Warning")
                        .setMessage("只能按顺序点击噢！")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
            if (num == 26) {
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("Finish")
                        .setMessage("Congratulation!!")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
                jishienable = false;
            }
        }
    };



    private Button.OnClickListener btn1 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (Integer) v.getTag();
            if (i == num) {
                num++;
                ((Button) v).setVisibility(View.INVISIBLE);
            } else {
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("Warning")
                        .setMessage("只能按顺序点击噢！")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        }
    };

    private void tanchuang(){
        new AlertDialog.Builder(GameActivity.this)
                .setTitle("Failed")
                .setMessage("你太菜了！种地去吧！")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    private Button.OnClickListener kt = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            jishienable=true;
            t1.start();
        }
    };


    Handler hmessage=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.what==33435)
            {
                //dialog();
                tanchuang();
            }
            super.handleMessage(msg);
        }
    };
    private boolean jishienable = true;
    public class threadone extends Thread {
        private int rec;
        public threadone(int rec){
            this.rec=rec;
        }
        public int getrec(){
            return rec;
        }


        public void run() {
            try{
                while(jishienable && playable ){
                    Thread.sleep(100);
                    rec--;
                    if(rec==0){
                        playable=false;
                    }
                    s=String.format("%d.%ds",rec/10,rec%10);
                    txtview.setText(s);
                }
                if(playable==false){
                    for(int i=0;i<25;i++){
                        btn[i].setVisibility(View.INVISIBLE);
                        hmessage.sendEmptyMessage(33435);
                    }
                }



            }
            catch(Exception e){
                System.out.println(e);
            }

        }

    }
}
