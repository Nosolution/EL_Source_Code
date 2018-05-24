package com.fantasticfour.elcontestproject.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.Ins_Schedule.Plan;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;
import com.fantasticfour.elcontestproject.Schedule.ScheduleActivity;
import com.fantasticfour.elcontestproject.Schedule.ScheduleAddPlanActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fantasticfour.elcontestproject.Schedule.ScheduleActivity.s_AddPlanRequestCode;
import static com.fantasticfour.elcontestproject.Schedule.ScheduleActivity.s_EditPlanRequestCode;

public class CalendarActivity extends AppCompatActivity implements CalendarPlanAdapter.CalendarPlanAdapterAttachActivity{
    private Button back;
    private Button today;
    private Button mAddPlan;
    private Button mEditPlan;
    private TextView title;
    private LineChart weekChart;
    private RecyclerView planListView;
    private TextView[] weekDayList ;
    private DateUtils dateUtils;
    private CalendarPlanAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private DisplayMetrics metric;
    private List<Plan> planList;
    private int screenWidth;  //手机屏幕宽度（PX）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity_main);
/*        for(int i = 0; i < 7; i++){
            Date date1 = new Date(118, 4, 20+i);
            Instance.s_Schedule.ChangeDate(date1);
            int max = (int)(Math.random() * 10);
            for(int j = 0; j < max; j++){
                Plan plan = new Plan();
                plan.m_Description = String.valueOf(j);
                plan.m_Date = date1;
                plan.m_Details = "fff";
                plan.m_Completed = (int)Math.round(Math.random());
                Instance.s_Schedule.AddPlan(plan);
            }
        }*/
        initWindow();
        initView();
        initDateUtils();
        initInstance();
        initWeekChart();
        initAdapter();
        initLinearLayoutManager();
        initItemTouchHelper();
        titleShow();
        highlightShow();
        setClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)return;
        if (requestCode==s_AddPlanRequestCode) {
            Plan plan = (Plan) data.getSerializableExtra("result_plan");
            plan.m_Date = dateUtils.getDayOnDisplay();
            planList.add(0, plan);
            adapter.resetMPlanList(planList);
            Instance.s_Schedule.AddPlan(plan);
            adapter.notifyItemInserted(0);
            adapter.notifyItemRangeChanged(1, planList.size()-1);
            planListView.scrollToPosition(0);
            initWeekChart();
        }else if(requestCode==s_EditPlanRequestCode) {
            int position = data.getIntExtra("plan_position", -1);
            Plan obj = planList.get(position);
            obj.change((Plan) data.getSerializableExtra("result_plan"));
            Instance.s_Schedule.UpdatePlan(obj);
            adapter.notifyItemChanged(position);
            planListView.scrollToPosition(position);
        }
    }

    public void initWindow(){
        metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth =metric.widthPixels;
    }
    public void initView(){
        back = findViewById(R.id.title_leftbutton);
        back.setBackground(getDrawable(R.drawable.left_arrow));
        today = findViewById(R.id.title_rightbutton);
        today.setBackground(getDrawable(R.drawable.to_my_place));
        mAddPlan = findViewById(R.id.calendar_planlist_plus);
        mEditPlan = findViewById(R.id.calendar_planlist_spanner);
        title = findViewById(R.id.title_middletext);
        weekChart = findViewById(R.id.calendar_weekchart);
        planListView = findViewById(R.id.calendar_planlist);
        weekDayList = new TextView[7];
        TextView sun = findViewById(R.id.calendar_week_sun);
        weekDayList[0] = sun;
        TextView mon = findViewById(R.id.calendar_week_mon);
        weekDayList[1] = mon;
        TextView tue = findViewById(R.id.calendar_week_tue);
        weekDayList[2] = tue;
        TextView wed = findViewById(R.id.calendar_week_wed);
        weekDayList[3] = wed;
        TextView thu = findViewById(R.id.calendar_week_thu);
        weekDayList[4] = thu;
        TextView fri = findViewById(R.id.calendar_week_fri);
        weekDayList[5] = fri;
        TextView sat = findViewById(R.id.calendar_week_sat);
        weekDayList[6] = sat;
    }
    public void initDateUtils(){
        dateUtils = new DateUtils();
    }
    public void initInstance(){
        Instance.s_Schedule.ChangeDate(dateUtils.getDayOnDisplay());
    }
    public void initWeekChart(){
        LineData lineData = getLineData();
        showChart(weekChart, lineData);
    }
    public void initAdapter(){
        planList = Instance.s_Schedule.GetPlanList();
        adapter = new CalendarPlanAdapter(planList, this);
        planListView.setAdapter(adapter);
    }
    public void resetAdapter(){
        adapter.resetMPlanList(Instance.s_Schedule.GetPlanList());
        planList = Instance.s_Schedule.GetPlanList();
        adapter.notifyDataSetChanged();
    }
    public void initLinearLayoutManager(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        planListView.setLayoutManager(linearLayoutManager);
    }
    public void initItemTouchHelper(){
        CalendarPlanListItemTouchHelperCallback calendarPlanListItemTouchHelperCallback = new CalendarPlanListItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(calendarPlanListItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(planListView);
        adapter.setmCalendarPlanListItemTouchHelperCallback(calendarPlanListItemTouchHelperCallback);
    }
    public void titleShow(){
        Date date1 = dateUtils.getDayOnDisplay();
        title.setText(new SimpleDateFormat("yyyy-MM-dd").format(date1));
/*        String year = String.valueOf(date1.getYear() + 1900);
        String month = String.valueOf(date1.getMonth() + 1);
        String day = String.valueOf(date1.getDate());
        title.setText(year + '-' + month + '-' + day);*/
    }
    public void highlightShow(){
        for(int i = 0; i < 7; i++){
            weekDayList[i].setTextColor(Color.rgb(255, 100, 100));
            weekDayList[i].setBackgroundColor(Color.rgb(250, 250, 250));
        }
        weekDayList[dateUtils.getDayOnDisplay().getDay()].setTextColor(Color.rgb(250, 250, 250));
        weekDayList[dateUtils.getDayOnDisplay().getDay()].setBackground(getDrawable(R.drawable.week_selected_highlight));
    }
    public void setClickListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//back点击事件
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChangeWeek = false;
                if((dateUtils.getToWeek().get(0).getYear() != dateUtils.getWeekOnDisplay().get(0).getYear()) || (dateUtils.getToWeek().get(0).getMonth() != dateUtils.getWeekOnDisplay().get(0).getMonth()) || (dateUtils.getToWeek().get(0).getDate() != dateUtils.getWeekOnDisplay().get(0).getDate())) {
                    isChangeWeek = true;
                }
                changeDateOnShow(dateUtils.getToday());
                dateUtils.setWeekOnDisplay(dateUtils.getToWeek());
                if(isChangeWeek){
                    initWeekChart();
                }
            }
        });//today点击事件
        mAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this,ScheduleAddPlanActivity.class);
                intent.putExtra("requestCode", s_AddPlanRequestCode);
                startActivityForResult(intent,s_AddPlanRequestCode);
            }
        });//加号点击事件
        mEditPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.mIsEditEnable){
                    adapter.mIsEditEnable = false;
                    mEditPlan.setBackground(getDrawable(R.drawable.spanner_black));
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.mIsEditEnable = true;
                    mEditPlan.setBackground(getDrawable(R.drawable.cross_red));
                    adapter.notifyDataSetChanged();
                }
            }
        });//扳手点击事件
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date1 = new Date(year - 1900, month, dayOfMonth);
                        changeDateOnShow(date1);
                        dateUtils.setWeekOnDisplay(dateUtils.weekCount(date1));
                        initWeekChart();
                    }

                };
                DatePickerDialog dialog = new DatePickerDialog(CalendarActivity.this, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK, listener, dateUtils.getDayOnDisplay().getYear() + 1900, dateUtils.getDayOnDisplay().getMonth(), dateUtils.getDayOnDisplay().getDate());
                DatePicker datePicker = dialog.getDatePicker();
                datePicker.setCalendarViewShown(false);
                datePicker.setSpinnersShown(true);
                dialog.show();
            }
        });//头顶日期点击事件
        for(int i = 0; i < 7; i++){
            weekDayList[i].setOnClickListener(new WeekDayOnClickListener(i));
        }
    }
    public LineData getLineData(){
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("日");
        xValues.add("一");
        xValues.add("二");
        xValues.add("三");
        xValues.add("四");
        xValues.add("五");
        xValues.add("六");
        //x轴的数据
        ArrayList<Entry> planNum = new ArrayList<>();//计划总数的数据
        ArrayList<Entry> planCompletedNum = new ArrayList<>();//完成计划数的数据
        ArrayList<Date> week = dateUtils.getWeekOnDisplay();
        for(int i = 0; i < 7; i++){
            Instance.s_Schedule.ChangeDate(week.get(i));
            planNum.add(new Entry(Instance.s_Schedule.GetPlanNum(), i));
            planCompletedNum.add(new Entry(Instance.s_Schedule.GetCompletedPlanName(), i));
        }//初始化数据
        Instance.s_Schedule.ChangeDate(dateUtils.getDayOnDisplay());
        LineDataSet lineDataSetPlanNum = new LineDataSet(planNum, "计划总数");//计划总数曲线以及其设置

        lineDataSetPlanNum.setDrawCubic(true);//设置为圆滑曲线
        lineDataSetPlanNum.setCubicIntensity(0.2f);
        lineDataSetPlanNum.setLineWidth(3f);//线宽
        lineDataSetPlanNum.setColor(Color.rgb(205, 255, 164));//曲线颜色
        lineDataSetPlanNum.setValueTextColor(Color.rgb(123, 124, 121));//数值的颜色
        lineDataSetPlanNum.setValueTextSize(16f);//数值字体大小
        lineDataSetPlanNum.setDrawFilled(true);//填充
        lineDataSetPlanNum.setFillColor(Color.rgb(205, 255, 164));//填充颜色
        lineDataSetPlanNum.setDrawCircles(true);//设置原点
        lineDataSetPlanNum.setCircleSize(5f);
        lineDataSetPlanNum.setCircleColor(Color.rgb(164, 237, 255));//圆点颜色
        lineDataSetPlanNum.setDrawHorizontalHighlightIndicator(false);//自带高亮显示
        lineDataSetPlanNum.setDrawVerticalHighlightIndicator(false);

        LineDataSet lineDataSetPlanCompletedNum = new LineDataSet(planCompletedNum, "已完成计划数");//已完成计划数以及其设置

        lineDataSetPlanCompletedNum.setDrawCubic(true);//设置为圆滑曲线
        lineDataSetPlanCompletedNum.setCubicIntensity(0.2f);
        lineDataSetPlanCompletedNum.setLineWidth(3f);//线宽
        lineDataSetPlanCompletedNum.setColor(Color.rgb(255, 218, 164));//曲线颜色
        lineDataSetPlanCompletedNum.setValueTextColor(Color.rgb(123, 124, 121));//数值的颜色
        lineDataSetPlanCompletedNum.setValueTextSize(16f);//数值字体大小
        lineDataSetPlanCompletedNum.setDrawFilled(true);//填充
        lineDataSetPlanCompletedNum.setFillColor(Color.rgb(255, 218, 164));//填充颜色
        lineDataSetPlanCompletedNum.setDrawCircles(true);//设置原点
        lineDataSetPlanCompletedNum.setCircleSize(5f);
        lineDataSetPlanCompletedNum.setCircleColor(Color.rgb(164, 237, 255));//圆点颜色
        lineDataSetPlanCompletedNum.setDrawHorizontalHighlightIndicator(false);//自带高亮显示
        lineDataSetPlanCompletedNum.setDrawVerticalHighlightIndicator(false);





        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSetPlanNum);
        lineDataSets.add(lineDataSetPlanCompletedNum);

        LineData lineData = new LineData(xValues, lineDataSets);
        return lineData;
    }
    public void showChart(final LineChart lineChart, LineData lineData){
        lineData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return "" + (int)v;
            }
        });
        lineChart.setDrawBorders(false);//在折线图上添加边框
        lineChart.setDescription("");//数据描述
        lineChart.setDrawGridBackground(false);//设置表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE);//表格颜色&透明度
        lineChart.animateY(800);
        lineChart.setTouchEnabled(true);//可点击
        lineChart.setDragEnabled(true);//可拖动
        lineChart.setScaleEnabled(false);//可缩放
        lineChart.setVisibleXRange(1, 7);//x轴显示范围
        lineChart.setData(lineData);
        lineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent motionEvent) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent motionEvent) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent motionEvent) {
                float position = motionEvent.getX();
                int day = Math.round((((position * (float) 1080) / (float)screenWidth) - 20) / 160);
                changeDateOnShow(dateUtils.getWeekOnDisplay().get(day));
            }//weekChart点击事件

            @Override
            public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                if(v > 200 && Math.abs(v / v1) > 2){
                    dateUtils.setPreWeek();
                    changeDateOnShow(dateUtils.getDayOnDisplay());
                    initWeekChart();
                }
                if(v < -200 && Math.abs(v / v1) > 2){
                    dateUtils.setNextWeek();
                    changeDateOnShow(dateUtils.getDayOnDisplay());
                    initWeekChart();
                }
            }//weekChart滑动事件

            @Override
            public void onChartScale(MotionEvent motionEvent, float v, float v1) {

            }

            @Override
            public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {

            }
        });//设置weekChart的手势


        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);



        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisLineColor(Color.WHITE);//x轴颜色
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
        xAxis.setTextColor(Color.WHITE);//字体颜色
        xAxis.setTextSize(2f);//字体大小
        xAxis.setDrawGridLines(false);//显示网格线
        xAxis.removeAllLimitLines();

        YAxis leftAxis = lineChart.getAxisLeft();//y轴设置
        leftAxis.removeAllLimitLines();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);//不显示左Y轴

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.removeAllLimitLines();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);//不显示右Y轴

        lineChart.animate();
    }
    private void changeDateOnShow(Date date){
        dateUtils.setDayOnDisplay(date);
        initInstance();
        titleShow();
        highlightShow();
        resetAdapter();
    }
    class WeekDayOnClickListener implements View.OnClickListener{
        private int mDay;
        public WeekDayOnClickListener(int day){
            mDay = day;
        }
        @Override
        public void onClick(View v) {
            changeDateOnShow(dateUtils.getWeekOnDisplay().get(mDay));
        }
    }
    public Drawable getDrawableForAdapter(int id){
        return getDrawable(id);
    }
    public void itemOnClick(int m_Position){
        Intent intent = new Intent(CalendarActivity.this,ScheduleAddPlanActivity.class);
        intent.putExtra("requestCode", ScheduleActivity.s_EditPlanRequestCode);
        intent.putExtra("plan_position", m_Position);
        intent.putExtra("source_plan", planList.get(m_Position));
        startActivityForResult(intent,ScheduleActivity.s_EditPlanRequestCode);
    }
}
