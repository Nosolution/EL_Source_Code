package com.example.shutime;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    String name;
    String id;
    Button one;
    Button two;
    Button three;
    Button four;
    int room_number;
    int total_number;


    int hourz=0;
    int munitez=0;
    int secondz=0;
    int restTime=0;
    int allTime=0;
    Button btn_start;
    Timer timer = new Timer();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        name=bundle.getString("name","");
        room_number=bundle.getInt("number",0);
        id=bundle.getString("objectId","");
        TextView textView = (TextView) findViewById(R.id.room_number);
        textView.setText("房间号："+room_number);
        one=(Button)findViewById(R.id.one);
        two=(Button)findViewById(R.id.two);
        three=(Button)findViewById(R.id.three);
        four=(Button)findViewById(R.id.four);
        one.setText(name);
        two.setText(" ");
        three.setText(" ");
        four.setText(" ");




        btn_start=(Button)findViewById(R.id.button);
        Button t0=(Button)findViewById(R.id.t0);
        t0.setOnClickListener(this);
        Button t15=(Button)findViewById(R.id.t15);
        t15.setOnClickListener(this);
        Button t30=(Button)findViewById(R.id.t30);
        t30.setOnClickListener(this);
        Button t45=(Button)findViewById(R.id.t45);
        t45.setOnClickListener(this);





        thread.start();
        findViewById(R.id.exit).setOnClickListener(this);

    }


    Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
            total_number = 1;
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





    private void Thread_stop() {
        try {
            Thread.sleep(800);
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
                    Classroom classroom = new Classroom();
                    classroom.setObjectId(id);
                    classroom.delete(new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(Main2Activity.this, "成功退出教室", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                    one.setText("");
                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(intent);

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }



        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            View view1 = View.inflate(this, R.layout.timepicker, null);

            final TimePicker timePicker = (TimePicker) view1.findViewById(R.id.timePicker);

            builder.setView(view1);
            int hour = 0;
            int minute = 0;
            int second = 0;
            int setminute = 00;
            int sethour = 00;
            final Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR);
            minute = calendar.get(Calendar.MINUTE);
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(sethour);
            if (view.getId() == R.id.t0) {
                timePicker.setCurrentMinute(00);
            } else if (view.getId() == R.id.t15) {
                timePicker.setCurrentMinute(15);
            } else if (view.getId() == R.id.t30) {
                timePicker.setCurrentMinute(30);
            } else if (view.getId() == R.id.t45) {
                timePicker.setCurrentMinute(45);
            }


            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if ((timePicker.getCurrentHour() == 0) && timePicker.getCurrentMinute() == 0) {
                        Toast.makeText(Main2Activity.this, "请输入正确的时间", Toast.LENGTH_SHORT).show();
                    } else {
                        hourz = timePicker.getCurrentHour();
                        munitez = timePicker.getCurrentMinute();
                        restTime = hourz * 3600 + munitez * 60 + secondz;
                        allTime = restTime;

                        Classroom classroom = new Classroom();
                        classroom.setAllTime(allTime);
                        classroom.update(id, new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","更新成功");
                                }else{

                                }
                            }
                        });
                        Thread_exit();
                        timer.schedule(task, 1000, 1000);       // timeTask
                    }
                }

            });


            Dialog dialog = builder.create();

            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

            lp.alpha = 1f;

            dialog.getWindow().setAttributes(lp);

            dialog.show();

        }





    }

    private void Thread_exit() {
        Thread.interrupted();
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

