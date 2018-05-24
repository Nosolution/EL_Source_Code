package com.example.a11579.easylifework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class shuoming extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuoming);
        TextView txv=(TextView)findViewById(R.id.textView60);
        txv.setText("    "+"终于来到了令人兴奋的说明环节，就由我小果果来给您讲述一下专注树的使用方法。\n" +
                "    "+"首先，你可以设定任务和时间来专注完成任务，如果没有完成，则可以在日志里记录下失败的原因，长按可以清除日志。\n" +
                "    "+"其次，每次完成任务都可以获得5个苹果的奖励，苹果可以用来兑换更多的水果哦！\n" +
                "    "+"同时，你还可以用一定数量的水果来兑换此水果的称号！\n" +
                "    "+"然后，我还给欧洲和非洲的大家准备了抽奖环节，只有真正的欧皇才能获得的猕猴桃！\n" +
                "    "+"哎，说了这么多，还是先去完成任务搞点苹果吧，希望你能变得越来越专注！!");
    } public void atClick(View v){
        Intent it=new Intent(this,Breath.class);
        startActivity(it);
    }
}
