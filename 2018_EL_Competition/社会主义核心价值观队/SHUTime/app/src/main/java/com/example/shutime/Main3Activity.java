package com.example.shutime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    String id;
    Button one;
    Button two;
    Button three;
    Button four;
    String name;
    int room_number;
    int total_number=2;
    int length_name_list;

    int hourz=0;
    int munitez=0;
    int secondz=0;
    int restTime=0;
    int allTime=0;
    Button btn_start;
    Timer timer = new Timer();

    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom2);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ArrayList<String> name_list= (ArrayList<String>) bundle.getSerializable("ARRAYLIST");
        name=bundle.getString("name","");
        room_number=bundle.getInt("number",0);
        id=bundle.getString("objectId","");
        TextView textView = (TextView) findViewById(R.id.room_number);
        textView.setText("房间号："+room_number);

        btn_start=(Button)findViewById(R.id.button);

        one=(Button)findViewById(R.id.one);
        two=(Button)findViewById(R.id.two);
        three=(Button)findViewById(R.id.three);
        four=(Button)findViewById(R.id.four);
        two.setText(" ");
        three.setText(" ");
        four.setText(" ");
        length_name_list=name_list.size();
        if(length_name_list==1){
            one.setText(name_list.get(0));
            two.setText(name);
        }
        else if(length_name_list==2){
            one.setText(name_list.get(0));
            two.setText(name_list.get(1));
            three.setText(name);
        }
        else if(length_name_list==3){
            one.setText(name_list.get(0));
            two.setText(name_list.get(1));
            three.setText(name_list.get(2));
            four.setText(name);
        }
        findViewById(R.id.exit).setOnClickListener(this);
        thread.start();
        thread2.start();




    }

    Thread thread2=new Thread(new Runnable() {
        @Override
        public synchronized void run() {
            while (allTime==0) {
                Look_for();
            }
            timer.schedule(task, 1000, 1000);       // timeTask
        }
    });



    Thread thread=new Thread(new Runnable() {
        @Override
        public synchronized void run() {
            while (total_number < 5) {
                BmobQuery<Classroom> query = new BmobQuery<Classroom>();
                query.addWhereEqualTo("room_number", room_number);
                query.setLimit(5);
                query.findObjects(new FindListener<Classroom>() {
                    @Override
                    public void done(List<Classroom> object, BmobException e) {
                        if (e == null) {
                            if (object.size() > total_number) {
                                for (Classroom classroom : object) {
                                    int order = classroom.getOrder();
                                    if (order == 2) {
                                        if (two.getText() == " ") {
                                            two.setText(classroom.getName());
                                            total_number = total_number + 1;
                                        }
                                    } else if (order == 3) {
                                        if (three.getText() == " ") {
                                            three.setText(classroom.getName());
                                            total_number = total_number + 1;
                                        }
                                    } else if (order == 4) {
                                        if (four.getText() == " ") {
                                            four.setText(classroom.getName());
                                            total_number = total_number + 1;
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                Thread_stop();

            }
        }
    });




    public synchronized void Look_for(){
        BmobQuery<Classroom> query = new BmobQuery<Classroom>();
        query.addWhereEqualTo("room_number", room_number);
        query.addWhereEqualTo("order", 1);
        query.setLimit(5);
        query.findObjects(new FindListener<Classroom>() {
            @Override
            public void done(List<Classroom> object, BmobException e) {
                if (e == null) {

                    for (Classroom classroom : object) {
                        int time = classroom.getAllTime();
                        if (time > 0) {
                            allTime = time;
                            restTime = time;
                        }
                        else {
                            try {
                                thread2.sleep(800);
                            }catch (InterruptedException ex){
                                ex.printStackTrace();
                            }
                        }

                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


    private void Thread_exit() {
        Thread.interrupted();
    }

    private void Thread_stop() {
        try {
            thread.sleep(800);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.exit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("你确定要退出教室吗？");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(length_name_list==1){
                        two.setText("");
                    }
                    else if(length_name_list==2){
                        three.setText("");
                    }
                    else if(length_name_list==3){
                        four.setText("");
                    }
                    Classroom classroom = new Classroom();
                    classroom.setObjectId(id);
                    classroom.delete(new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(Main3Activity.this, "成功退出教室", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                    Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {

                    if(restTime/3600>0){
                        hourz=restTime/3600;
                        restTime=restTime-hourz*3600;
                    }else{
                        hourz=0;
                    }
                    if(restTime/60>0){
                        munitez=restTime/60;
                        restTime=restTime-munitez*60;
                    }else{
                        munitez=0;
                    }
                    if(restTime>0){
                        secondz=restTime;
                    }else{
                        secondz=0;
                    }
                    String strhour=""+hourz;
                    String strmunite=""+munitez;
                    String strsecond=""+secondz;
                    if(hourz>=0&&hourz<=9){
                        strhour="0"+hourz;
                    }
                    if(munitez>=0&&munitez<=9){
                        strmunite="0"+munitez;
                    }
                    if(secondz>=0&&secondz<=9){
                        strsecond="0"+secondz;
                    }
                    String timestr=strhour+":"+strmunite+":"+strsecond;
                    btn_start.setText(""+timestr);
                    if(allTime< 0){
                        timer.cancel();
                    }
                    allTime--;
                    restTime=allTime;
                }
            });
        }
    };



}

