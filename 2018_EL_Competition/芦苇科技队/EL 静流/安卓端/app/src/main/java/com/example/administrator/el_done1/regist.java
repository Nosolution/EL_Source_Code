package com.example.administrator.el_done1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class regist extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
    }

    public void register(View view) {
        String str1="";
        String str2="";
        String str3="";
        EditText editText1 =(EditText)findViewById(R.id.editText1);
        str1=editText1.getText().toString();
        EditText editText2=(EditText)findViewById(R.id.editText2);
        str2=editText2.getText().toString();
        EditText editText3=(EditText)findViewById(R.id.editText3);
        str3=editText3.getText().toString();
        if(str1.equals("")){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            editText1.setText("");
            editText2.setText("");
            editText3.setText("");

        }
        else if (str2.equals("")||str3.equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            editText1.setText("");
            editText2.setText("");
            editText3.setText("");

        }
        else{
            if(str2.equals(str3)) {
                if (login.user_name.indexOf(str1) == -1) {
                    Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                    login.user_name.add(str1);
                    login.user_pwd.add(str2);
                    Intent intent = new Intent(this, login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this,"用户已存在",Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");

                }
            }
            else {

                Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
            }
        }
    }
}
