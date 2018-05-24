package com.example.el_project;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
* 主界面，显示待办的任务列表
 * 可进行新增任务、开始任务、显示详情、删除任务等操作
 * @author ns
 */

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private List<com.example.el_project.Task> mTaskList = new ArrayList<>();
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton baseFab;
    private android.support.design.widget.FloatingActionButton fabDelete, fabDetail, fabStart;
    private View mask;
    private boolean isFabOpened = false;
    private Animation in_from_fab;
    private Animation out_to_fab;
	private DrawerLayout mDrawerLayout;
	private Switch switchClockStatus;
	private Switch switchMusicStatus;
	private TextView tvClockTime;
    private LinearLayout clockTimeLayout;
    private TextView tvBreakClockTime;
    private LinearLayout breakClockTimeLayout;
    private int selectedPosition;//被选中的Item位置
    private boolean isExit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        //判断是否是第一次打开应用
        if (GeneralSetting.getFirstOpen(this)){
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        }

        //修改界面主题
        BackgroundCollection backgroundCollection = new BackgroundCollection();
        setTheme(backgroundCollection.getTodayTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedPosition=-1;
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
	    mDrawerLayout=findViewById(R.id.activity_main_drawer_layout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);  //如果确定每个item的内容不会改变RecyclerView的大小，设置这个选项可以提高性能
        mask = findViewById(R.id.mask);

	    //设置背景
        mDrawerLayout.setBackgroundResource(backgroundCollection.getTodayBackground());
        RelativeLayout layoutSetting = findViewById(R.id.activity_main_setting_upper);
        layoutSetting.setBackgroundColor(backgroundCollection.getTodayColor());


        //设置部分
        tvClockTime = findViewById(R.id.text_choose_time);
        clockTimeLayout = (LinearLayout)findViewById(R.id.main_clock_set_layout);
        tvBreakClockTime = findViewById(R.id.text_choose_break_time);
        breakClockTimeLayout = (LinearLayout)findViewById(R.id.main_break_clock_set_layout);
	    switchClockStatus =findViewById(R.id.switch_if_tomato_clock_on);
	    switchMusicStatus =findViewById(R.id.switch_if_music_on);
	    switchClockStatus.setOnCheckedChangeListener(this);
	    switchMusicStatus.setOnCheckedChangeListener(this);
	    clockTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                final String[] itemToSelect = {"20分钟", "30分钟", "40分钟", "50分钟"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("时长");
                builder.setItems(itemToSelect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GeneralSetting.setTomatoClockTime(MainActivity.this, which * 10 + 20);
                        refreshSetting();
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
	    breakClockTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                final String[] itemToSelect = {"5分钟", "10分钟", "15分钟", "20分钟"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("休息时长");
                builder.setItems(itemToSelect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GeneralSetting.setTomatoBreakTime(MainActivity.this, which * 5 + 5);
                        refreshSetting();
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        //创建默认的线性LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //设置Adapter
        adapter = new RecyclerViewAdapter(mTaskList);
        recyclerView.setAdapter(adapter);

        //初始化接口实例，实现点击功能
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Task task = mTaskList.get(position);
                if (!isFabOpened&&selectedPosition==-1) {
                    selectedPosition=position;
                    task.switchBackground();
                    adapter.changeItemBackGround(position);
                    openMenu(baseFab);
                    mask.setVisibility(View.VISIBLE);
                }
                else if(isFabOpened&&selectedPosition!=position){
                    Task previousTask=mTaskList.get(selectedPosition);
                    previousTask.switchBackground();
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            adapter.changeItemBackGround(selectedPosition);
                        }
                    }.start();
                    closeMenu(baseFab);
                    selectedPosition=-1;
                }
                else{
                    closeMenu(baseFab);
                    selectedPosition=-1;
                    task.switchBackground();
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            adapter.changeItemBackGround(position);
                        }
                    }.start();
                }
            }
            //无长按功能
            @Override
            public void onItemLongClick(View view, int position) { }
        });

        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == -1) {
                    mask.setVisibility(View.GONE);
                    return;
                }
                Task previousTask=mTaskList.get(selectedPosition);
                previousTask.switchBackground();
                adapter.changeItemBackGround(selectedPosition);
                closeMenu(baseFab);
                selectedPosition=-1;
                mask.setVisibility(View.GONE);
            }
        });


        baseFab =findViewById(R.id.fab_base);
        fabDelete = findViewById(R.id.fab_delete);
        fabDetail = findViewById(R.id.fab_detail);
        fabStart = findViewById(R.id.fab_start);
        fabDelete.setVisibility(View.INVISIBLE);
        fabDetail.setVisibility(View.INVISIBLE);
        fabStart.setVisibility(View.INVISIBLE);

        //基础悬浮按钮点击逻辑
        baseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFabOpened) {
                    Intent intent = new Intent(MainActivity.this,EditTaskActivity.class);
                    startActivityForResult(intent,1);//对是否点击了完成按钮实现监听
                } else {
                    closeMenu(baseFab);
                    Task task = mTaskList.get(selectedPosition);
                    task.switchBackground();
                    adapter.changeItemBackGround(selectedPosition);
                    selectedPosition=-1;
                }
            }
        });
        //立即开始任务
        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startTask();
                closeMenu(baseFab);
                selectedPosition=-1;
            }
        });

        //显示详情
        fabDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails(selectedPosition);
            }
        });

        //删除任务
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(selectedPosition);
                closeMenu(baseFab);
                selectedPosition=-1;
            }
        });
    }

    //每次恢复主界面就刷新任务列表
    @Override
    protected void onResume() {
        MyDatabaseOperation.refreshAllDailyTask(MainActivity.this);
        refreshTask();
        adapter.refreshItemView();
        refreshSetting();
        super.onResume();
    }

    // 点击task后，悬浮按钮产生的动画
    public void openMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, -155, -135 );
        animator.setDuration(500);
        animator.setAutoCancel(true);
        animator.start();
        isFabOpened = true;
        in_from_fab = AnimationUtils.loadAnimation(MainActivity.this, R.anim.in_from_fab);
        fabDelete.setVisibility(View.VISIBLE);
        fabDetail.setVisibility(View.VISIBLE);
        fabStart.setVisibility(View.VISIBLE);
        fabDelete.startAnimation(in_from_fab);
        fabDetail.startAnimation(in_from_fab);
        fabStart.startAnimation(in_from_fab);
    }

    // 点击关闭后，悬浮按钮的动画
    public void closeMenu(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", -135, 20, 0);
        animator.setDuration(500);
        animator.start();
        isFabOpened = false;
        out_to_fab = AnimationUtils.loadAnimation(MainActivity.this, R.anim.out_to_fab);
        fabDelete.startAnimation(out_to_fab);
        fabDetail.startAnimation(out_to_fab);
        fabStart.startAnimation(out_to_fab);
    }

    private void refreshTask(){
        mTaskList.clear();
        List<Task> temp = MyDatabaseOperation.getTaskList(MainActivity.this);
        if (temp != null) {
            mTaskList.addAll(temp);
        }
    }

    //开始任务，启动计时界面
    private void startTask(){
        Intent intent=new Intent(MainActivity.this,TaskTimingActivity.class);
        intent.putExtra("intent_task_id",mTaskList.get(selectedPosition).getId());
        intent.putExtra("intent_task_name",mTaskList.get(selectedPosition).getName());
        intent.putExtra("intent_task_hours_required", mTaskList.get(selectedPosition).getHourRequired());
        intent.putExtra("intent_task_minutes_required", mTaskList.get(selectedPosition).getMinuteRequired());
        intent.putExtra("intent_is_daily_task",mTaskList.get(selectedPosition).getIsDailyTask());
        intent.putExtra("intent_task_comments",mTaskList.get(selectedPosition).getComments());
        intent.putExtra("intent_time_used",mTaskList.get(selectedPosition).getTimeUsed());
        startActivity(intent);
    }

    //显示详情对话框
    private void showDetails(final int position){
        final AlertDialog.Builder detailsDialog =
                new AlertDialog.Builder(MainActivity.this);
        detailsDialog.setIcon(R.drawable.circle);
        detailsDialog.setTitle("详情");
        detailsDialog.setMessage(constructDetails(position));
        detailsDialog.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        detailsDialog.setNegativeButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] taskDetails=mTaskList.get(position).getThisTaskInfo();
                Intent intent=new Intent(MainActivity.this,EditTaskActivity.class);
                intent.putExtra("details",taskDetails);
                startActivity(intent);
                closeMenu(baseFab);
                selectedPosition=-1;
            }
        });
        detailsDialog.show();
    }

    //构造详情对话框显示的任务信息
    private String constructDetails(int position){
        String finalString= "";
        String temp="";
        finalString+="任务："+mTaskList.get(selectedPosition).getName()+"\n";
        finalString+="预计时间:"+mTaskList.get(position).getHourRequired()+"时"+mTaskList.get(position).getMinuteRequired()+"分\n";
        temp=mTaskList.get(selectedPosition).getDeadline();
        finalString+="最后日期："+(temp==null ? "无":temp)+"\n";
        finalString+="紧急程度："+mTaskList.get(selectedPosition).getEmergencyDegree()+"\n";
        finalString+="是否是每日任务："+(mTaskList.get(selectedPosition).getIsDailyTask()==1? "是":"不是")+"\n";
        finalString+="备注:"+mTaskList.get(selectedPosition).getComments()+"\n";
        return finalString;
    }

    private void deleteTask(int position){
        MyDatabaseOperation.deleteTask(MainActivity.this,mTaskList.get(position).getId());
        refreshTask();
        adapter.refreshItemView();
    }

    //	绑定Menu布局
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    //对HomeAsUp按钮点击事件进行处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                mDrawerLayout.openDrawer(GravityCompat.END);
            default:
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switch_if_music_on:
                if(compoundButton.isChecked()) {
                    GeneralSetting.setMusicOn(MainActivity.this, true);
                }
                else {
                    GeneralSetting.setMusicOn(MainActivity.this, false);
                }
                break;
            case R.id.switch_if_tomato_clock_on:
                if(compoundButton.isChecked()){
                    GeneralSetting.setTomatoClockEnable(this, true);
                }else {
                    GeneralSetting.setTomatoClockEnable(this, false);
                }
                refreshSetting();
                break;
        }
    }

    private void refreshSetting(){
        switchMusicStatus.setChecked(GeneralSetting.getMusicOn(this));
        switchClockStatus.setChecked(GeneralSetting.getTomatoClockEnable(this));

        //修改设置内显示番茄钟时长
        if(GeneralSetting.getTomatoClockEnable(this)){
            clockTimeLayout.setVisibility(View.VISIBLE);
            breakClockTimeLayout.setVisibility(View.VISIBLE);
        }else {
            clockTimeLayout.setVisibility(View.GONE);
            breakClockTimeLayout.setVisibility(View.GONE);
        }
        tvClockTime.setText(GeneralSetting.getTomatoClockTime(this) + "分钟");
        tvBreakClockTime.setText(GeneralSetting.getTomatoBreakTime(this) + "分钟");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };

    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast toast=Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT);
            showMyToast(toast,1000);
            mHandler.sendEmptyMessageDelayed(0, 1000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    //自定义Toast显示时间，cnt为所需显示时间
    public void showMyToast(final Toast toast, final int cnt){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        },cnt);
    }
}


