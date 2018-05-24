package com.nju.team.timescapes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import android.widget.Toast;
import android.text.InputFilter;

/**
 * Created by ASUS on 2018/5/8.
 */

public class SettingsPanelActivity extends AppCompatActivity {

    private ImageButton btn_exit_settings;
    private Button btn_suggestions;
    private Button btn_developers;
    private Button btn_faqs;
    private Switch switch_achieve_remind;
    private Switch switch_app_detect;
    private Switch switch_fail_minimize;
    private Switch switch_remind_failure;
    private Switch switch_jump_cal;
    private Spinner spinner_remind;
    private Spinner spinner_time;
    private EditText input_user_name;
    private Button btn_user_login;
    private Button btn_user_delete;
    private Button btn_data_delete;

    private String filename = "user_settings.txt";
    private ArrayList<String> settings_list = new ArrayList<String>();

    private boolean ACHIEVE_REMIND;
    private boolean APP_DETECT;
    private boolean FAIL_MINIMIZE;
    private boolean REMIND_FAILURE;
    private boolean JUMP_TO_CAL;
    private long FOCUS_TIME;
    private String REMIND_METHOD;
    private boolean IS_REGISTERED;
    private String USER_NAME;
    private boolean IS_WARNED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //让状态栏透明
        changeStatusBarColor();

        //读出字符组
        settings_list = readData(filename);
        IS_REGISTERED = Boolean.parseBoolean(settings_list.get(0).substring(0,settings_list.get(0).length()-1));
        IS_WARNED = Boolean.parseBoolean(settings_list.get(15).substring(0,settings_list.get(15).length()-1));
        USER_NAME = settings_list.get(1).substring(0,settings_list.get(1).length()-1);


        //指定按钮和开关，列表
        btn_exit_settings = (ImageButton) findViewById(R.id.imageButton_exitSettings);
        btn_suggestions = (Button) findViewById(R.id.button_suggestions);
        btn_developers = (Button) findViewById(R.id.button_developers);
        btn_faqs = (Button) findViewById(R.id.button_faqs);
        switch_achieve_remind = (Switch) findViewById(R.id.switch_keepScreenOn);
        switch_app_detect = (Switch) findViewById(R.id.switch_openAppObserve);
        switch_fail_minimize = (Switch) findViewById(R.id.switch_failWhenMini);
        switch_remind_failure = (Switch) findViewById(R.id.switch_remindWhenFail);
        switch_jump_cal = (Switch) findViewById(R.id.switch_jumpToCal);
        spinner_remind = (Spinner) findViewById(R.id.spinner_alertWhenFinish);
        spinner_time = (Spinner) findViewById(R.id.spinner_setFocusLeng);
        btn_user_login = (Button) findViewById(R.id.button_userlogin);
        btn_user_delete = (Button) findViewById(R.id.button_deleteUser);
        btn_data_delete = (Button) findViewById(R.id.button_clearhistory);


        //按钮事件
        btn_exit_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsPanelActivity.this, MainScreenActivity.class);
                SettingsPanelActivity.this.startActivity(mainIntent);
                SettingsPanelActivity.this.finish();
            }
        });

        btn_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertWindow();
                btn_suggestions.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_suggestions.setClickable(true);
                    }
                }, 500);
            }
        });

        btn_developers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SettingsPanelActivity.this, DevelopersPanelActivity.class);
                SettingsPanelActivity.this.startActivity(mainIntent);
                SettingsPanelActivity.this.finish();
            }
        });

        btn_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SettingsPanelActivity.this, FAQPanelActivity.class);
                SettingsPanelActivity.this.startActivity(mainIntent);
                SettingsPanelActivity.this.finish();
            }
        });

        spinner_remind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] method = getResources().getStringArray(R.array.alert_types);
                String str_arr = method[i];
                String result="";
                if(str_arr.equals("震动")){
                    result = "vibrate"+"\r\n";
                }else if(str_arr.equals("响铃")){
                    result = "ring"+"\r\n";
                }else if(str_arr.equals("关闭")){
                    result = "closed"+"\r\n";
                }else if(str_arr.equals("震动并响铃")){
                    result = "vibrate_and_ring"+"\r\n";
                }
                settings_list.set(3,result);
                String add_up="";
                for(int j=0;j<settings_list.size();j++){
                    add_up = add_up + settings_list.get(j);
                }
                saveData(add_up,filename);
                TextView v1 = (TextView) view;
                v1.setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing to be done
            }
        });

        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] timelist = getResources().getStringArray(R.array.focus_time);
                String str_long = timelist[i].substring(0,timelist[i].length()-2)+"\r\n";
                settings_list.set(5,str_long);
                String result = "";
                for(int j=0;j<settings_list.size();j++){
                    result = result + settings_list.get(j);
                }
                saveData(result,filename);
                TextView v1 = (TextView) view;
                v1.setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing to be done
            }
        });

        switch_achieve_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    String screenOn = "false"+"\r\n";
                    settings_list.set(4,screenOn);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
                else{
                    String screenOn = "true"+"\r\n";
                    settings_list.set(4,screenOn);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
            }
        });

        switch_app_detect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    String appDetect = "false"+"\r\n";
                    settings_list.set(6,appDetect);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
                else{
                    String appDetect = "true"+"\r\n";
                    settings_list.set(6,appDetect);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
            }
        });

        switch_fail_minimize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    if(!IS_WARNED){
                        setAlertWindow_FailMini();
                    }else{
                        IS_WARNED = true;
                        String failMini = "false"+"\r\n";
                        settings_list.set(7,failMini);
                        settings_list.set(15,"true\r\n");
                        String result = "";
                        for(int j=0;j<settings_list.size();j++){
                            result = result + settings_list.get(j);
                        }
                        saveData(result,filename);
                    }
                }
                else {
                    String failMini = "true"+"\r\n";
                    settings_list.set(7,failMini);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
            }
        });

        switch_remind_failure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    String remindFail = "false"+"\r\n";
                    settings_list.set(8,remindFail);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
                else{
                    String remindFail = "true"+"\r\n";
                    settings_list.set(8,remindFail);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
            }
        });

        switch_jump_cal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    String jumpCal = "false"+"\r\n";
                    settings_list.set(9,jumpCal);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
                else{
                    String jumpCal = "true"+"\r\n";
                    settings_list.set(9,jumpCal);
                    String result = "";
                    for(int j=0;j<settings_list.size();j++){
                        result = result + settings_list.get(j);
                    }
                    saveData(result,filename);
                }
            }
        });

        btn_user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(IS_REGISTERED){
                    btn_user_delete.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_user_delete.setClickable(true);
                        }
                    }, 500);
                    AlertDialog.Builder build = new AlertDialog.Builder(SettingsPanelActivity.this);
                    build.setTitle("注意！")
                            .setMessage("您选择了注销用户。这将导致您的专注记录和成就被清空。仍要注销用户吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    IS_REGISTERED = false;
                                    String isReg = "false"+"\r\n";
                                    String username = "default_name"+"\r\n";
                                    settings_list.set(0,isReg);
                                    settings_list.set(1,username);
                                    settings_list.set(10,"0\r\n");
                                    settings_list.set(11,"0\r\n");
                                    settings_list.set(12,"0\r\n");
                                    settings_list.set(13,"0\r\n");
                                    String result = "";
                                    for(int j=0;j<settings_list.size();j++){
                                        result = result + settings_list.get(j);
                                    }
                                    saveData(result,filename);


                                    //清空时间历程
                                    ArrayList<String> TagList=readData("Tag.txt");
                                    TagList.clear();
                                    savenew(TagList,"Tag.txt");

                                    ArrayList<String> FocusTimeList=readData("FocusTime.txt");
                                    FocusTimeList.clear();
                                    savenew(FocusTimeList,"FocusTime.txt");

                                    ArrayList<String> TimeList=readData("Time.txt");
                                    TimeList.clear();
                                    savenew(TimeList,"Time.txt");


                                    Toast.makeText(getApplicationContext(), "账户已注销", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }else{
                    // TODO Auto-generated method stub
                    btn_user_delete.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_user_delete.setClickable(true);
                        }
                    }, 500);
                    AlertDialog.Builder build = new AlertDialog.Builder(SettingsPanelActivity.this);
                    build.setTitle("未注册")
                            .setMessage("您还未注册，因此没有要注销的账号。")
                            .setNegativeButton("好", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .show();
                }
            }
        });

        btn_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input_user_name = new EditText(SettingsPanelActivity.this);
                if(IS_REGISTERED){
                    btn_user_login.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_user_login.setClickable(true);
                        }
                    }, 500);
                    new AlertDialog.Builder(SettingsPanelActivity.this).setTitle("已注册")
                            .setMessage("您已经拥有账户:"+USER_NAME)
                            .setNegativeButton("好", null)
                            .show();
                }else{
                    btn_user_login.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_user_login.setClickable(true);
                        }
                    }, 500);
                    new AlertDialog.Builder(SettingsPanelActivity.this).setTitle("账户注册")
                            .setMessage("用户名长度须介于1~15个字符之间")
                            .setView(input_user_name)
                            .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String input = input_user_name.getText().toString();
                                    InputFilter[] filters = {new InputFilter.LengthFilter(20)};
                                    input_user_name.setFilters(filters);
                                    if (input.equals("")) {
                                        Toast.makeText(getApplicationContext(), "用户名不能为空！", Toast.LENGTH_LONG).show();
                                    }else if(input.length()>15){
                                        Toast.makeText(getApplicationContext(), "用户名过长！", Toast.LENGTH_LONG).show();
                                    }else if(input.length()<=15){
                                        USER_NAME = input + "\r\n";
                                        settings_list.set(1,USER_NAME);
                                        IS_REGISTERED = true;
                                        settings_list.set(0,"true"+"\r\n");
                                        settings_list.set(10,"0\r\n");
                                        settings_list.set(11,"0\r\n");
                                        settings_list.set(12,"0\r\n");
                                        settings_list.set(13,"0\r\n");
                                        String result="";
                                        for(int j=0;j<settings_list.size();j++){
                                            result=result + settings_list.get(j);
                                        }
                                        saveData(result,filename);
                                        Toast.makeText(getApplicationContext(), "积分功能已解锁！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            }
        });

        btn_data_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(IS_REGISTERED){
                    btn_data_delete.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_data_delete.setClickable(true);
                        }
                    }, 500);
                    AlertDialog.Builder build = new AlertDialog.Builder(SettingsPanelActivity.this);
                    build.setTitle("注意！")
                            .setMessage("您选择了清空历史。这将导致您的专注记录和积分成就被清空，但不会删除您的账户。仍要清空历史吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .setPositiveButton("清空", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    settings_list.set(10,"0\r\n");
                                    settings_list.set(11,"0\r\n");
                                    settings_list.set(12,"0\r\n");
                                    settings_list.set(13,"0\r\n");
                                    String result = "";
                                    for(int j=0;j<settings_list.size();j++){
                                        result = result + settings_list.get(j);
                                    }
                                    saveData(result,filename);

                                    //清空时间历程
                                    ArrayList<String> TagList=readData("Tag.txt");
                                    TagList.clear();
                                    savenew(TagList,"Tag.txt");

                                    ArrayList<String> FocusTimeList=readData("FocusTime.txt");
                                    FocusTimeList.clear();
                                    savenew(FocusTimeList,"FocusTime.txt");

                                    ArrayList<String> TimeList=readData("Time.txt");
                                    TimeList.clear();
                                    savenew(TimeList,"Time.txt");

                                    Toast.makeText(getApplicationContext(), "历史已清空", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }else{
                    btn_data_delete.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_data_delete.setClickable(true);
                        }
                    }, 500);
                    // TODO Auto-generated method stub
                    AlertDialog.Builder build = new AlertDialog.Builder(SettingsPanelActivity.this);
                    build.setTitle("未注册")
                            .setMessage("您还未注册，因此没有要清空的历史。")
                            .setNegativeButton("好", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            })
                            .show();
                }
            }
        });


        //设置上一次保存的状态
        settings_list = readData(filename);
        FOCUS_TIME = Long.parseLong(settings_list.get(5).substring(0,settings_list.get(5).length()-1));
        ACHIEVE_REMIND = Boolean.parseBoolean(settings_list.get(4).substring(0,settings_list.get(4).length()-1));
        APP_DETECT = Boolean.parseBoolean(settings_list.get(6).substring(0,settings_list.get(6).length()-1));
        FAIL_MINIMIZE = Boolean.parseBoolean(settings_list.get(7).substring(0,settings_list.get(7).length()-1));
        REMIND_FAILURE = Boolean.parseBoolean(settings_list.get(8).substring(0,settings_list.get(8).length()-1));
        JUMP_TO_CAL = Boolean.parseBoolean(settings_list.get(9).substring(0,settings_list.get(9).length()-1));
        REMIND_METHOD = settings_list.get(3).substring(0,settings_list.get(3).length()-1);

        if(ACHIEVE_REMIND){
            switch_achieve_remind.setChecked(true);
        }else{
            switch_achieve_remind.setChecked(false);
        }
        if(APP_DETECT){
            switch_app_detect.setChecked(true);
        }else{
            switch_app_detect.setChecked(false);
        }
        if(FAIL_MINIMIZE){
            switch_fail_minimize.setChecked(true);
        }else{
            switch_fail_minimize.setChecked(false);
        }
        if(REMIND_FAILURE){
            switch_remind_failure.setChecked(true);
        }else{
            switch_remind_failure.setChecked(false);
        }
        if(JUMP_TO_CAL){
            switch_jump_cal.setChecked(true);
        }else{
            switch_jump_cal.setChecked(false);
        }

        if(FOCUS_TIME == 5){
            spinner_time.setSelection(0,true);
        }else if(FOCUS_TIME == 10){
            spinner_time.setSelection(1,true);
        }
        else if(FOCUS_TIME == 15){
            spinner_time.setSelection(2,true);
        }
        else if(FOCUS_TIME == 20){
            spinner_time.setSelection(3,true);
        }
        else if(FOCUS_TIME == 25){
            spinner_time.setSelection(4,true);
        }
        else if(FOCUS_TIME == 30){
            spinner_time.setSelection(5,true);
        }
        else if(FOCUS_TIME == 40){
            spinner_time.setSelection(6,true);
        }
        else if(FOCUS_TIME == 50){
            spinner_time.setSelection(7,true);
        }
        else if(FOCUS_TIME == 60){
            spinner_time.setSelection(8,true);
        }
        else if(FOCUS_TIME == 90){
            spinner_time.setSelection(9,true);
        }
        else if(FOCUS_TIME == 120){
            spinner_time.setSelection(10,true);
        }

        if(REMIND_METHOD.equals("vibrate")){
            spinner_remind.setSelection(0,true);
        }else if(REMIND_METHOD.equals("ring")){
            spinner_remind.setSelection(1,true);
        }else if(REMIND_METHOD.equals("closed")){
            spinner_remind.setSelection(2,true);
        }else if(REMIND_METHOD.equals("vibrate_and_ring")){
            spinner_remind.setSelection(3,true);
        }

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent mainIntent = new Intent(SettingsPanelActivity.this, MainScreenActivity.class);
                SettingsPanelActivity.this.startActivity(mainIntent);
                SettingsPanelActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }


    public void setAlertWindow() {
        // TODO Auto-generated method stub
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("联系我们")
                .setMessage("您可以将意见发送至以下邮箱："+"\n"+"171250523@smail.nju.edu.cn"+
                        "\n"+"我们会尽快作出反馈:)")
                .setNegativeButton("好", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .show();
    }

    public void setAlertWindow_FailMini() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("注意！")
                .setMessage("您选择了关闭按键监视。此模式下您仍可正常进行专注，但积分将不被记录。仍要关闭这项设定吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch_fail_minimize.setChecked(true);

                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        IS_WARNED = true;
                        String failMini = "false"+"\r\n";
                        settings_list.set(7,failMini);
                        settings_list.set(15,"true\r\n");
                        String result = "";
                        for(int j=0;j<settings_list.size();j++){
                            result = result + settings_list.get(j);
                        }
                        saveData(result,filename);
                    }
                })
                .show();
    }

    //存数据
    private void saveData(String data,String filename) {

        String content = data + "\n";
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //读数据
    private ArrayList<String> readData(String filename){
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }

        File file=new File(textsDir,filename);
        BufferedReader reader=null;
        String temp=null;
        ArrayList<String> settings = new ArrayList<String>();
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                settings.add(temp+"\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return settings;
    }

    //存数据
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void savenew(List<String> strings, String filename) {

        String content = listToString(strings);
        File textsDir = new File(this.getFilesDir().getAbsolutePath() + File.separator + "texts");
        if(!textsDir.exists()){
            textsDir.mkdir();
        }
        File file = new File(textsDir,filename);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //将ArrayList转成String
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();

    }
}