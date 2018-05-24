package com.example.administrator.el_done1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class jbweb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jbweb);
        WebView webView= (WebView)findViewById(R.id.webView);
        webView.loadUrl("http://www.baidu.com");
    }
}
