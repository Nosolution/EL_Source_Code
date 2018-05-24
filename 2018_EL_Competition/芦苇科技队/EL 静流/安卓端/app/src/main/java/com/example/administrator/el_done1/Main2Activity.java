package com.example.administrator.el_done1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;




/*此方案废弃*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/
/*废弃方案，暂时忽略*/

public class Main2Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 把Activity的标题去掉
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置布局
        setContentView(new CountDownView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

