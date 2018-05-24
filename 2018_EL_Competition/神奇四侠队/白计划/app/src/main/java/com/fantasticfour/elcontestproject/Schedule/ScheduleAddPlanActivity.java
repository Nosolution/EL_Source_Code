package com.fantasticfour.elcontestproject.Schedule;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Plan;
import com.fantasticfour.elcontestproject.R;

import java.util.Date;

public class ScheduleAddPlanActivity extends AppCompatActivity{
    private EditText m_et_Description,m_et_Details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity_add);
        ModifyDisplay();

        TextView tv_Title = findViewById(R.id.title_middletext);
        m_et_Description = findViewById(R.id.EditText_Name);
        m_et_Details = findViewById(R.id.EditText_Description);
        Intent intent = getIntent();
        int requestCode = intent.getIntExtra("requestCode", -1);
        if(requestCode == ScheduleActivity.s_EditPlanRequestCode){
            Plan plan = (Plan) intent.getSerializableExtra("source_plan");
            m_et_Description.setText(plan.m_Description);
            m_et_Details.setText(plan.m_Details);
            tv_Title.setText("你的任务");
        }
        else if(requestCode == ScheduleActivity.s_AddPlanRequestCode)
            tv_Title.setText("添加新任务");

        //确定按钮
        Button button_ok= findViewById(R.id.Button_ok);
        button_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Plan plan = new Plan(m_et_Description.getText().toString(),m_et_Details.getText().toString(),new Date());
                Intent intent = getIntent();
                intent.putExtra("result_plan", plan);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        //返回按钮
        Button button_cancel= findViewById(R.id.Button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
    private void ModifyDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();display.getSize(size);
        params.height = (int) (size.y*0.65);
        params.width = (int) (size.x*0.8);
        getWindow().setAttributes(params);
    }
}
