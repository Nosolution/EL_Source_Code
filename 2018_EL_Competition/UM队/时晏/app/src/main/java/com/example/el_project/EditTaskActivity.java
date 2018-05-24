package com.example.el_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
/**
* 编辑任务界面，可新添任务或者修改已存在的任务
 * 编辑完成后可选择加入待办或者立即开始
 * @author ns
 */
public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout selectTime;
    private TextView tvDDLTime;
    private CustomDatePicker cdp;
    private CustomTimeToFinishPicker timeToFinishPicker;
    private ImageView iv1,iv2,iv3,iv4,iv5;
    private Switch switchIsDailyTask;
    private MyDatabaseHelper dbHelper;
    private EditText etTaskName;
    private EditText etComment;
    private Button btnFinishEditing;//加入待办按钮
    private Button btnStartNow;//立即开始按钮
    private int selectedImageViewPosition;//被选择的ImageView的位置
    private boolean editMode;//是否是编辑任务
    private int taskId;//数据库中task的ID，作为唯一标识符
    private int taskTimeUsed;
    private RelativeLayout layoutTimeToFinish;//预计完成时间
    private TextView tvTimeToFinish;
    private String hour, minute;//Spinner的显示文本
    private ArrayList<Integer>ivId=new ArrayList<>();//储存紧急程度ImageViewId的ArrayList
    private InputMethodManager mInputMethodManager;//软键盘
    private Toolbar toolbar;
//    private Drawable draw1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //修改界面主题
        BackgroundCollection backgroundCollection = new BackgroundCollection();
        setTheme(backgroundCollection.getTodayTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        findViewById(R.id.edit_task_layout).setOnClickListener(this);//整个页面都监听点击事件
        editMode=false;
        taskId=0;taskTimeUsed=0;
        btnFinishEditing = (Button)findViewById(R.id.finish_editing);
        btnStartNow = (Button)findViewById(R.id.start_now);
        btnFinishEditing.setOnClickListener(this);
        btnStartNow.setOnClickListener(this);
        textChange tc = new textChange();//文本改变监视器
        hour ="00";
        minute ="00";
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        etTaskName = findViewById(R.id.task_name_et);
        etTaskName.addTextChangedListener(tc);
        etComment = findViewById(R.id.comment_et);
        toolbar = findViewById(R.id.activity_edit_task_toolbar);

        toolbar.setBackgroundColor(backgroundCollection.getTodayColor());
        toolbar.setAlpha(0.5f);


        layoutTimeToFinish = findViewById(R.id.layout_select_time_to_finish);
        layoutTimeToFinish.setOnClickListener(this);
        tvTimeToFinish = findViewById(R.id.text_time_to_finish);
        initTimeToFinishPicker();

//        draw1=getDrawable(R.drawable.circle_animation);//点亮动画
        //打开数据库

        dbHelper = new MyDatabaseHelper(this,"TaskStore.db",null,4);

        //DDL选择
        selectTime=(RelativeLayout)findViewById(R.id.selectTime);
        selectTime.setOnClickListener(this);
        tvDDLTime =(TextView)findViewById(R.id.ddlTime);
        initTimePicker();

        selectedImageViewPosition=0;//被选择位置默认为0
        ivId.add(R.id.circle_one);
        ivId.add(R.id.circle_two);
        ivId.add(R.id.circle_three);
        ivId.add(R.id.circle_four);
        ivId.add(R.id.circle_five);

        //是否打开为每日任务
        switchIsDailyTask =(Switch)findViewById(R.id.open);
        switchIsDailyTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //TODO:打开每日工作 待补充
                }else{

                }
            }
        });

        //紧急程度设置
        iv1=(ImageView)findViewById(R.id.circle_one);
        iv1.setOnClickListener(this);

        iv2=(ImageView)findViewById(R.id.circle_two);
        iv2.setOnClickListener(this);

        iv3=(ImageView)findViewById(R.id.circle_three);
        iv3.setOnClickListener(this);

        iv4=(ImageView)findViewById(R.id.circle_four);
        iv4.setOnClickListener(this);

        iv5=(ImageView)findViewById(R.id.circle_five);
        iv5.setOnClickListener(this);

        btnFinishEditing = (Button)findViewById(R.id.finish_editing);
        btnFinishEditing.setOnClickListener(this);
        btnStartNow = (Button)findViewById(R.id.start_now);
        btnStartNow.setOnClickListener(this);

        //判断Intent来源，决定后续操作
        Intent intent=getIntent();
        if(intent.hasExtra("details")){
            editMode=true;
            String[] taskDetails=intent.getStringArrayExtra("details");
            setTaskDetails(taskDetails);
            btnFinishEditing.setText("修改完成");
        }
        initButton(editMode);//根据是否是编辑模式来设置按钮可按或不可按
    }

    private void initTimePicker() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now=sdf.format(new Date());
        tvDDLTime.setText(now);

        cdp=new CustomDatePicker(this,new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvDDLTime.setText(time);
            }
        }, "2018-01-01 00:00", now);
        cdp.showSpecificTime(true);
        cdp.setIsLoop(true);

    }

    private void initTimeToFinishPicker(){
        timeToFinishPicker = new CustomTimeToFinishPicker(this, new CustomTimeToFinishPicker.ResultHandler() {
            @Override
            public void handle(String hour, String min) {
                tvTimeToFinish.setText(hour + ":" + min);
                EditTaskActivity.this.hour = hour;
                EditTaskActivity.this.minute = min;
            }
        });
        timeToFinishPicker.setIsLoop(true);
    }

    private void setTaskDetails(String[] taskDetails){
        taskId=Integer.parseInt(taskDetails[0]);
        etTaskName.setText(taskDetails[1]);
        String[]tempString=taskDetails[2].split(":");
        hour =tempString[0];
        minute =tempString[1];
        tvTimeToFinish.setText(formatTimeUnit(Integer.parseInt(hour)) + ":" + formatTimeUnit(Integer.parseInt(minute)));
        tvDDLTime.setText(taskDetails[3]);

        for(int index=4;index>=0;index--){
            if(taskDetails[4].equals(String.valueOf(index+1))){
                setImageViewSelected(ivId.get(index));
                selectedImageViewPosition=index+1;
            }
        }
        if(taskDetails[5].equals("1")){
            switchIsDailyTask.setChecked(true);
        }
        etComment.setText(taskDetails[6]);
        taskTimeUsed=Integer.parseInt(taskDetails[7]);
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    @Override
    //TODO:onclick
    public void onClick(View v) {

        //开始处理点击事件
        int id=v.getId();
        if(id!=R.id.task_name_et) {
            resetKeyBoard();
        }
        if(ivId.contains(id)){
            if(selectedImageViewPosition!=ivId.indexOf(id)+1){
                setImageViewSelected(id);
                selectedImageViewPosition=ivId.indexOf(id)+1;
            }
        }
        else {
            switch (id) {
                case R.id.selectTime:
                    cdp.show(tvDDLTime.getText().toString());
                    break;
                case R.id.layout_select_time_to_finish:
                    timeToFinishPicker.show(tvTimeToFinish.getText().toString());
                    break;
                case R.id.finish_editing:
                    if(!editMode) {
                        addTask();
                        resetAllComponent();
                        Toast toast = Toast.makeText(EditTaskActivity.this,"成功添加任务",Toast.LENGTH_SHORT);
                        showMyToast(toast,1000);//提示成功添加任务
                    }
                    else {
                        modifyTask();
                        Toast toast = Toast.makeText(EditTaskActivity.this,"成功修改任务",Toast.LENGTH_SHORT);
                        showMyToast(toast,1000);//提示成功修改任务
                        finish();
                    }
                    break;
                case R.id.start_now:
                    //如果不是编辑模式，开始新建的任务，否则开始编辑任务
                    if(!editMode) {
                        addTask();
                        startTask(MyDatabaseOperation.queryLatestTaskId(EditTaskActivity.this));
                        resetAllComponent();
                    }
                    else {
                        modifyTask();
                        startTask(taskId);
                    }
                    finish();
                    break;
            }
        }
    }


    //完成添加任务
    private void addTask(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();//真正打开数据库
        ContentValues values = new ContentValues();//传值工具
        values.put("task", etTaskName.getText().toString() );
        values.put("assumedtime", tvTimeToFinish.getText().toString());
        values.put("deadline", tvDDLTime.getText().toString());//对应每一列传值
        values.put("emergencydegree",selectedImageViewPosition>0? selectedImageViewPosition : 1);//默认值为1
        values.put("isdailytask",isDailyTask());
        values.put("comments", etComment.getText().toString());
        values.put("last_finished_date  ",0);
        values.put("time_used",0);
        db.insert("Tasklist",null,values);//将值传入数据库中的"Tasklist"表
    }

    //修改任务
    private void modifyTask(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("task", etTaskName.getText().toString() );
        values.put("assumedtime", tvTimeToFinish.getText().toString());
        values.put("deadline", tvDDLTime.getText().toString());
        values.put("emergencydegree",selectedImageViewPosition>0? selectedImageViewPosition : 1);
        values.put("isdailytask",isDailyTask());
        values.put("comments", etComment.getText().toString());
        db.update("Tasklist",values,"id=?",new String[]{String.valueOf(taskId)});
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

    private void setImageViewSelected(int id){
        int diff=0;
        ImageView iv;
        AnimationDrawable ad;
        if((diff=selectedImageViewPosition-ivId.indexOf(id)-1)<0){
            for(int i =0;i<-diff;i++){
                iv =findViewById(ivId.get(selectedImageViewPosition+i));
                ad=(AnimationDrawable) iv.getDrawable();
                ad.start();
            }
        }
        else{
            for(int i=0;i<diff;i++){
                iv=findViewById(ivId.get(selectedImageViewPosition-1-i));
                ad=(AnimationDrawable) iv.getDrawable();
                ad.selectDrawable(0);//回到第一帧并暂停
                ad.stop();
            }
        }
    }

    private void resetAllComponent(){
        etTaskName.setText("");
        tvTimeToFinish.setText("00:00");
        hour = "0";
        minute = "0";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now=sdf.format(new Date());
        tvDDLTime.setText(now);
        ImageView iv;
        AnimationDrawable ad;
        for(int i=selectedImageViewPosition;i>0;i--){
            iv=findViewById(ivId.get(i-1));
            ad=(AnimationDrawable) iv.getDrawable();
            ad.selectDrawable(0);//回到第一帧并暂停
            ad.stop();
        }
        selectedImageViewPosition=0;
        switchIsDailyTask.setChecked(false);
        etComment.setText("");
    }

    private void startTask(int id){
        Intent intent=new Intent(EditTaskActivity.this,TaskTimingActivity.class);
        intent.putExtra("intent_task_id",id);
        intent.putExtra("intent_task_name", etTaskName.getText().toString());
        intent.putExtra("intent_task_hours_required", Integer.parseInt(hour));
        intent.putExtra("intent_task_minutes_required", Integer.parseInt(minute));
        intent.putExtra("intent_is_daily_task",isDailyTask());
        intent.putExtra("intent_task_comments", etComment.getText().toString());
        intent.putExtra("intent_time_used",taskTimeUsed);
        startActivity(intent);
    }

    private int isDailyTask(){
        if(switchIsDailyTask.isChecked())
            return 1;
        else
            return 0;
    }

    private void initButton(boolean editMode){
        btnFinishEditing.setEnabled(editMode);
        btnStartNow.setEnabled(editMode);
    }

    private void decideButtonEnable(){//检测文件名是否为空和Spinner选择的时间是否为0
        boolean flag1 = etTaskName.getText().toString().length()>0;
        boolean flag2 = (!hour.equals("00")||!minute.equals("00"));
        if(flag1){
            btnFinishEditing.setEnabled(true);
            btnStartNow.setEnabled(true);
        }
        else{
            btnFinishEditing.setEnabled(false);
            btnStartNow.setEnabled(false);
        }
    }

    private void resetKeyBoard(){
        if (mInputMethodManager.isActive()) {
            mInputMethodManager.hideSoftInputFromWindow(etTaskName.getWindowToken(), 0);
        }
    }

    //内部类，监控Edittext文本变化，实现必填与选填功能
    class textChange implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           decideButtonEnable();
        }
    }

}
