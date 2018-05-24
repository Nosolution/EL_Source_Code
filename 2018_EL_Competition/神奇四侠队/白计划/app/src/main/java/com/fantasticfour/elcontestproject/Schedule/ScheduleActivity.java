package com.fantasticfour.elcontestproject.Schedule;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;

import com.fantasticfour.elcontestproject.Achievement.AchievementActivity;
import com.fantasticfour.elcontestproject.Calendar.CalendarActivity;
import com.fantasticfour.elcontestproject.Instance.AdvancedCountdownTimer;
import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Plan;
import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Schedule;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;
import com.fantasticfour.elcontestproject.User.UserActivity;
import com.fantasticfour.elcontestproject.WNoise.WNoiseActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.Date;
import java.util.Locale;

import android.widget.Button;
import android.widget.TimePicker;

import java.util.List;
import java.util.ArrayList;
public class ScheduleActivity extends AppCompatActivity {
    public static final int s_AddPlanRequestCode = 100;
    public static final int s_EditPlanRequestCode = 101;

    public TextView text;
    private SchedulePlanListAdapter m_PlanListAdapter;
    private List<Plan> m_PlanList;
    //0为拖动,1为删除
    public int Mode;
    RecyclerView m_rv_PlanList;
    //倒计时
    private Button m_btn_StartTimer;
    private TextView m_tv_TimerTime;
    private MainCountDownTimer m_MainTimer;
    public int m_TotalMinutes=25;
    private int button_begin_state=0;
    private View.OnClickListener m_SetTimerTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TimePickerDialog(ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    SetTimerTime(hourOfDay*60+minute);
                }
            }, m_TotalMinutes/60, m_TotalMinutes%60, true).show();
        }
    };
    private View.OnClickListener m_PauseTimerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            m_MainTimer.ChangeMode();
        }
    };

    //计时器
    public class MainCountDownTimer extends AdvancedCountdownTimer{
        private static final long s_TimerFactoryMillis=60*1000L;
        private static final int s_TimerFactorySeconds=60;
        private static final long s_TimerInterval=1000L;
        private boolean bePaused = false;
        int m_SecondsInFuture;
        int m_PassedSeconds;
        public MainCountDownTimer(int totalMinute){
            super(totalMinute*s_TimerFactoryMillis,s_TimerInterval);
            m_SecondsInFuture = totalMinute*s_TimerFactorySeconds;
            m_PassedSeconds = 0;
        }
        @Override
        public void onTick(long millisUntilFinished, int percent){
            long seconds = millisUntilFinished/1000L;
            m_PassedSeconds = (int)(m_SecondsInFuture-seconds);
            m_tv_TimerTime.setText(String.format("%02d:%02d:%02d",seconds/3600,seconds/60%60,seconds%60));
        }
        @Override
        public void onFinish(){
            m_tv_TimerTime.setText("00:00:00");
        }
        public void ChangeMode(){
            bePaused = !bePaused;
            if(bePaused)
                pause();
            else
                resume();
        }
        public int GetPassedSeconds(){
            return m_PassedSeconds;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity_main);
        Instance.Init(getApplicationContext());

        m_PlanList=Instance.s_Schedule.GetPlanList();

        //切换拖动、删除模式
        final Button btn_DragEditModeChange= findViewById(R.id.title_rightbutton);
        btn_DragEditModeChange.setBackground(getDrawable(R.drawable.spanner));
        Mode = 0;
        btn_DragEditModeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Mode==0){
                    Mode=1;btn_DragEditModeChange.setBackground(getDrawable(R.drawable.tick));
                    m_PlanListAdapter.changeMode();
                }else{
                    Mode=0;btn_DragEditModeChange.setBackground(getDrawable(R.drawable.spanner));
                    m_PlanListAdapter.changeMode();
                }
            }
        });
        //倒计时
        m_tv_TimerTime=findViewById(R.id.time);
        m_tv_TimerTime.setText(String.format("%02d:%02d",(m_TotalMinutes)/60,(m_TotalMinutes)%60));
        m_tv_TimerTime.setOnClickListener(m_SetTimerTimeOnClickListener);
        m_btn_StartTimer = findViewById(R.id.button_begin);
        m_btn_StartTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (button_begin_state==0) {
                    m_MainTimer=new MainCountDownTimer(m_TotalMinutes);
                    m_MainTimer.start();
                    button_begin_state=1;
                    m_btn_StartTimer.setBackground(getDrawable(R.drawable.stop_brown));
                    m_tv_TimerTime.setOnClickListener(m_PauseTimerOnClickListener);
                }
                else {
                    Instance.s_AchievementLibrary.CompleteFocusing(m_MainTimer.GetPassedSeconds());
                    m_MainTimer.cancel();
                    button_begin_state=0;
                    m_btn_StartTimer.setBackground(getDrawable(R.drawable.start_brown));
                    m_tv_TimerTime.setText(String.format("%02d:%02d",(m_TotalMinutes)/60,(m_TotalMinutes)%60));
                    m_tv_TimerTime.setOnClickListener(m_SetTimerTimeOnClickListener);
                }
            }
        });

        //添加任务
        Button startAddActivity = findViewById(R.id.title_leftbutton);
        startAddActivity.setBackground(getDrawable(R.drawable.plus));
        startAddActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ScheduleActivity.this,ScheduleAddPlanActivity.class);
                intent.putExtra("requestCode", s_AddPlanRequestCode);
                startActivityForResult(intent,s_AddPlanRequestCode);
            }
        });
        //添加第一个任务
        TextView tv_AddFirstPlan = findViewById(R.id.schedule_tv_activity_main_add_first_plan);
        tv_AddFirstPlan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ScheduleActivity.this,ScheduleAddPlanActivity.class);
                intent.putExtra("requestCode", s_AddPlanRequestCode);
                startActivityForResult(intent,s_AddPlanRequestCode);
            }
        });
        CheckPlanNumAddFirstPlan();

        //主界面显示系统时间
        text = findViewById(R.id.title_middletext);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScheduleActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日",new Locale("zh","CN"));
        Date date = new Date();
        text.setText(bartDateFormat.format(date));

        //PlanList
        m_rv_PlanList = findViewById(R.id.task_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        m_rv_PlanList.setLayoutManager(layoutManager);

        m_PlanListAdapter=new SchedulePlanListAdapter(this, m_PlanList);
        m_rv_PlanList.setAdapter(m_PlanListAdapter);

        SchedulePlanListTouchHelperCallback mSchedulePlanListTouchHelperCallback = new SchedulePlanListTouchHelperCallback(m_PlanListAdapter);
        ItemTouchHelper ith = new ItemTouchHelper(mSchedulePlanListTouchHelperCallback);
        ith.attachToRecyclerView(m_rv_PlanList);
        m_PlanListAdapter.SetItemTouchHelperCallback(mSchedulePlanListTouchHelperCallback);


        //转跳至各个界面
        findViewById(R.id.buttonMusic).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, WNoiseActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.schedule_btn_activity_main_achievement).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ScheduleActivity.this, AchievementActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.schedule_btn_activity_main_user).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ScheduleActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //解决CountDownTimer内存泄漏问题
        if(m_MainTimer!=null)
            m_MainTimer.cancel();
        Instance.Destroy();
    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)return;
        if (requestCode==s_AddPlanRequestCode) {
            Plan plan = (Plan) data.getSerializableExtra("result_plan");
            m_PlanList.add(0, plan);
            Instance.s_Schedule.AddPlan(plan);
            m_PlanListAdapter.notifyItemInserted(0);
            m_PlanListAdapter.notifyItemRangeChanged(1, m_PlanList.size()-1);

            m_rv_PlanList.scrollToPosition(0);
            CheckPlanNumAddFirstPlan();
        }else if(requestCode==s_EditPlanRequestCode) {
            int position = data.getIntExtra("plan_position", -1);
            Plan obj = m_PlanList.get(position);

            obj.change((Plan) data.getSerializableExtra("result_plan"));
            Instance.s_Schedule.UpdatePlan(obj);
            m_PlanListAdapter.notifyItemChanged(position);

            m_rv_PlanList.scrollToPosition(position);
        }
    }
    public void CheckPlanNumAddFirstPlan(){
        TextView tv_AddFirstPlan = findViewById(R.id.schedule_tv_activity_main_add_first_plan);
        if(Instance.s_Schedule.GetPlanNum() == 0)
            tv_AddFirstPlan.setVisibility(View.VISIBLE);
        else
            tv_AddFirstPlan.setVisibility(View.GONE);
    }
    private void SetTimerTime(int minutes){
        m_TotalMinutes = minutes;
        m_tv_TimerTime.setText(String.format("%02d:%02d",(m_TotalMinutes)/60,(m_TotalMinutes)%60));
    }
    private void RefreshPlanList(){
        m_PlanList = Instance.s_Schedule.GetPlanList();
        m_PlanListAdapter.RefreshPlanList(m_PlanList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshPlanList();
    }
}
