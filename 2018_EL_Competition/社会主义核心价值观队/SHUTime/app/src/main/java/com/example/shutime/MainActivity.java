package com.example.shutime;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String name = "h";
    int room_number;
    int hourz=0;
    int munitez=0;
    int secondz=0;
    int restTime=0;
    int allTime=0;
    Button btn_start;
    ImageButton imagebutton;
    ImageButton imagebutton2;
    Timer timer = new Timer();
    String tomorrow;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagebutton=(ImageButton)findViewById(R.id.imageButton);
        imagebutton.setOnClickListener(this);
        imagebutton2=(ImageButton)findViewById(R.id.imageButton2);
        imagebutton2.setOnClickListener(this);
        btn_start=(Button)findViewById(R.id.button);
        Button t0=(Button)findViewById(R.id.t0);
        t0.setOnClickListener(this);
        Button t15=(Button)findViewById(R.id.t15);
        t15.setOnClickListener(this);
        Button t30=(Button)findViewById(R.id.t30);
        t30.setOnClickListener(this);
        Button t45=(Button)findViewById(R.id.t45);
        t45.setOnClickListener(this);

        SharedPreferences sp4 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String xx=sp4.getString("name","h");
        if(xx.equals("h")){}
        else {
            name=xx;
        }

        if(name.equals("h")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            final AlertDialog dialog = builder.create();
            dialog.setView(LayoutInflater.from(this).inflate(R.layout.name, null));
            dialog.show();
            dialog.getWindow().setContentView(R.layout.name);
            final EditText etContent = (EditText) dialog.findViewById(R.id.edit_text);
            View btnPositive = (View) dialog.findViewById(R.id.yes);

            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = etContent.getText().toString();
                    if (isNullEmptyBlank(str)) {
                        etContent.setError("输入内容不能为空");
                    } else {
                        name = str;

                        SharedPreferences sp3 = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp3.edit();
                        editor.putString("name", name);
                        editor.commit();


                        dialog.dismiss();
                    }
                }
            });
        }

        Bmob.initialize(this, "80a3f3a0644557187f4fda469d72ef05");
        TextView yiji=(TextView)findViewById(R.id.textView);





        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String today=year+""+"-"+month+""+"-"+day+"";

        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-M-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(month<10) {
            tomorrow = dft2.format(endDate);
        }
        else if(month>=10){
            tomorrow = dft.format(endDate);
        }

        int z=0;
        int total=0;
        while(true){
            z=z+1;
            String x=null;
            x=sp4.getString("date"+z+"","h");
            if(x.equals("h")){
                break;
            }
            else{
                if(x.equals(today) || x.equals(tomorrow)){
                    total=total+1;
                }
            }
        }
        if(total>=1){
            Toast.makeText(MainActivity.this, "你今明两天还有"+total+"个DDL等待完成！", Toast.LENGTH_SHORT).show();
            final AlertDialog dialog2 = new AlertDialog.Builder(MainActivity.this).create();
            dialog2.setView(LayoutInflater.from(MainActivity.this).inflate(R.layout.ddl, null));
            dialog2.show();
            dialog2.getWindow().setContentView(R.layout.ddl);
            TextView warn=(TextView)dialog2.findViewById(R.id.ddl);
            warn.setText("你今明两天还有"+total+"个DDL等待完成！");
            View btnPositive = (View) dialog2.findViewById(R.id.yes);
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                }
            });

        }



        String mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            yiji.setText("宜温故知新");
        }else if("2".equals(mWay)){
            yiji.setText("忌三心二意");
        }else if("3".equals(mWay)){
            yiji.setText("忌好高骛远");
        }else if("4".equals(mWay)){
            yiji.setText("忌敝帚自珍");
        }else if("5".equals(mWay)){
            yiji.setText("忌固步自封");
        }else if("6".equals(mWay)){
            yiji.setText("忌碌碌无为");
        }else if("7".equals(mWay)){
            yiji.setText("宜平心定气");
        }


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageButton){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = View.inflate(MainActivity.this, R.layout.choose1, null);


            builder.setView(view);
            Dialog dialog = builder.create();

            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();


            dialog.getWindow().setAttributes(lp);
            dialog.show();
            dialog.getWindow().setLayout(750,425);

            Button create=(Button)dialog.findViewById(R.id.button1);
            create.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                    dialog.setView(LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null));
                    dialog.show();
                    dialog.getWindow().setContentView(R.layout.dialog);
                    View btnPositive = (View) dialog.findViewById(R.id.yes);
                    View btnNegative = (View) dialog.findViewById(R.id.no);
                    btnNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });



                    btnPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Classroom classroom = new Classroom();
                            classroom.setName(name);
                            classroom.setOrder(1);
                            //暂未考虑房间号重复的问题
                            final int room_number = 1000 + (int) (Math.random() * 9000);

                            classroom.setRoom_number(room_number);
                            classroom.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        dialog.dismiss();
                                        Toast.makeText(MainActivity.this, "创建教室成功", Toast.LENGTH_SHORT).show();
                                        String id=objectId;
                                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putString("objectId",id);
                                        bundle.putInt("number",room_number);
                                        bundle.putString("name",name);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    } else {
                                    }
                                }
                            });
                        }
                    });
                }
            });


            Button join=(Button)dialog.findViewById(R.id.button2);
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                    dialog.setView(LayoutInflater.from(MainActivity.this).inflate(R.layout.join, null));
                    dialog.show();
                    dialog.getWindow().setContentView(R.layout.join);
                    final EditText etContent = (EditText) dialog.findViewById(R.id.edit_text);
                    View btnPositive = (View) dialog.findViewById(R.id.yes);
                    View btnNegative = (View) dialog.findViewById(R.id.no);

                    btnPositive.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String str = etContent.getText().toString();
                            if (isNullEmptyBlank(str)) {
                                etContent.setError("输入内容不能为空");
                            }
                            else if(isNumericZidai(str)){
                                etContent.setError("请输入数字");
                            }
                            else {
                                int number = Integer.parseInt(str);
                                BmobQuery<Classroom> query = new BmobQuery<Classroom>();
                                query.addWhereEqualTo("room_number", number);
                                query.setLimit(5);
                                query.findObjects(new FindListener<Classroom>() {
                                    @Override
                                    public void done(List<Classroom> object, BmobException e) {

                                        if (e == null) {
                                            if(object.size()>0 && object.size()<4) {

                                                int max_order=1;
                                                final ArrayList<String> name_list=new ArrayList<String>();
                                                for (Classroom room : object){
                                                    room_number=room.getRoom_number();
                                                    String other_name=room.getName();
                                                    int order=room.getOrder();
                                                    if(order>max_order){
                                                        max_order=order;
                                                    }
                                                    if(order==1){
                                                        name_list.add(0,other_name);
                                                    }
                                                    else if(order==2){
                                                        name_list.add(1,other_name);
                                                    }
                                                    else if(order==3){
                                                        name_list.add(2,other_name);
                                                    }
                                                    //暂未考虑4人满教室时，出现除4号退出外的情况
                                                    else if(order==4){
                                                        name_list.add(3,other_name);
                                                    }
                                                }
                                                Classroom classroom = new Classroom();
                                                classroom.setName(name);
                                                classroom.setOrder(max_order+1);
                                                classroom.setRoom_number(room_number);
                                                classroom.save(new SaveListener<String>() {
                                                    @Override
                                                    public void done(String objectId, BmobException e) {
                                                        Toast.makeText(MainActivity.this, "加入教室成功", Toast.LENGTH_SHORT).show();
                                                        String id=objectId;
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                                                        Bundle bundle=new Bundle();
                                                        bundle.putString("objectId",id);
                                                        bundle.putInt("number",room_number);
                                                        bundle.putString("name",name);
                                                        bundle.putSerializable("ARRAYLIST",(Serializable)name_list);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                    }
                                                });

                                            }
                                            else if(object.size()==0){
                                                Toast.makeText(MainActivity.this, "请输入正确的房间号", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(object.size()>=4){
                                                Toast.makeText(MainActivity.this, "该教室人已满", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                        }
                                    }
                                });
                            }
                        }
                    });

                    btnNegative.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog.dismiss();
                        }
                    });
                }
            });

        }

        else if(v.getId() == R.id.imageButton2){
            Intent intent = new Intent(MainActivity.this, Main4Activity.class);
            startActivity(intent);
        }


        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            View view = View.inflate(MainActivity.this, R.layout.timepicker, null);

            final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);

            builder.setView(view);
            int hour=0;
            int minute=0;
            int second=0;
            int setminute=00;
            int sethour=00;
            Calendar calendar=Calendar.getInstance();
            hour=calendar.get(Calendar.HOUR);
            minute=calendar.get(Calendar.MINUTE);
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(sethour);
            if(v.getId()==R.id.t0){
                timePicker.setCurrentMinute(00);
            }
            else if(v.getId()==R.id.t15){
                timePicker.setCurrentMinute(15);
            }else if(v.getId()==R.id.t30){
                timePicker.setCurrentMinute(30);
            }else if(v.getId()==R.id.t45){
                timePicker.setCurrentMinute(45);
            }



            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {




                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if((timePicker.getCurrentHour() == 0) && timePicker.getCurrentMinute() == 0){
                        Toast.makeText(MainActivity.this, "请输入正确的时间", Toast.LENGTH_SHORT).show();
                    }else{
                        hourz=timePicker.getCurrentHour();
                        munitez=timePicker.getCurrentMinute();
                        restTime=hourz*3600+munitez*60+secondz;
                        allTime=restTime;
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



    private static boolean isNullEmptyBlank(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()))
            return true;
        return false;
    }
    public static boolean isNumericZidai(String str) {
        boolean result=str.matches("[0-9]+");
        if (result == true) {
            return false;
        }
        else {
            return true;
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






