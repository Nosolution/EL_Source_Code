package com.example.a11579.easylifework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Breath extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath);
        TextView txv=(TextView)findViewById(R.id.textView67);
        txv.setText("                          "+"腹式呼吸法\n" +
                "\n"+
                "取仰卧或舒适的腹式呼吸冥想坐姿，\n" +
                "放松全身。观察自然呼吸一段时间。\n" +
                "右手放在腹部肚脐，左手放在胸部。\n" +
                "吸气时，最大限度地向外扩张腹部，\n" +
                "胸部保持不动。\n" +
                "呼气时，最大限度地向内收缩腹部，\n" +
                "胸部保持不动。\n" +
                "循环往复，保持每一次呼吸的节奏一致。\n" +
                "细心体会腹部的一起一落。\n" +
                "经过一段时间的练习之后，\n" +
                "就可以将手拿开，\n" +
                "只是用意识关注呼吸过程即可。\n"+
                "多次心无旁骛地练习有助于提高专注度！");
    }
}
