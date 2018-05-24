package com.example.administrator.el_done1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class login extends AppCompatActivity {



    static ArrayList<String> user_name=new ArrayList<String>();
    static ArrayList<String> user_pwd=new ArrayList<String>();
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
        setContentView(R.layout.activity_login);

        //存入ActivityManager
        ActivityManager.addActivity(this);



        //隐藏应用默认标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }
    }

    public void log(View view) {
        String str1 = "";
        String str2 = "";
        EditText editText1 = (EditText) findViewById(R.id.account);
        str1 = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.pwd);
        str2 = editText2.getText().toString();
        if (str1.equals("")) {
            Toast.makeText(this, "please entre the account", Toast.LENGTH_SHORT).show();
        } else if (str2.equals("")) {
            Toast.makeText(this, "please entre the password", Toast.LENGTH_SHORT).show();
        } else {
            if (str1.equals("admin") && str2.equals("password")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } else if (!str1.equals("") && !str2.equals("") && user_name.indexOf(str1) != -1 && user_pwd.indexOf(str2) != -1) {
                int index1 = user_name.indexOf(str1);
                if (user_pwd.get(index1).equals(str2)) {


                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();


            }
        }
    }


    public void regist(View view) {
        Intent intent = new Intent(this,regist.class);
        startActivity(intent);
    }
}
