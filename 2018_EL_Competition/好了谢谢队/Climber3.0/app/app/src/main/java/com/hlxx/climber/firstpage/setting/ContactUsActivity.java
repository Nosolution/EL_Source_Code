package com.hlxx.climber.firstpage.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.hlxx.climber.R;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar mToolbar = findViewById(R.id.toolbar_contact_us);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("联系我们");
        }
        ImageView imageView = (ImageView) findViewById(R.id.imagelsn);
        imageView.setImageResource(R.mipmap.contact_us);
    }
}
