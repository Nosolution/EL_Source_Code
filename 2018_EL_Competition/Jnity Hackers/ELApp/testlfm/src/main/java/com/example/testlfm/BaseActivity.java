package com.example.testlfm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yhao.fixedfloatwindow.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
    }
}
