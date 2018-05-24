package com.example.el_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import com.github.mikephil.charting.charts.BarChart;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;
import java.util.ArrayList;
import java.util.List;
/**
 * The type Finish activity.
 * 完成任务界面，完成任务界面显示本次任务完成报告
 * 提示用户分享或继续下一个任务
 *
 * @author NAiveD
 */
public class FinishActivity extends AppCompatActivity {
	private TextView tvScores;  // 用于显示专注度的分数，格式：“__分”
	private TextView tvTaskConsumedTime;  // 单个任务的耗时，格式：“本次任务共耗时 __ 分钟”
	private TextView tvTaskConcentrateTime;  // 单个任务的专注时间，格式：“专注 __ 分钟”
	private TextView tvTodayConcentrateTime;  // 今日的专注时间，格式：“今日已专注 __ 分钟”
	private TextView tvSuggestion;
	private Button btnReturnMain;  // 返回主界面按钮
	private Button btnNextTask;  // 开始下一项任务的按钮
	private Button btnShare;     //分享按钮
	private int scores;  // 传入数据
	private RecyclerView recyclerView;
	private RecyclerViewAdapter adapter;
	private List<Task> recommendedTaskList=new ArrayList<Task>();

	private int taskTotalTimeUsed;         //任务总计完成时间
	private int taskTimeUsed;              //任务有效完成时间
	private int timeUsedToday;             //今日用于完成任务的时间
	private int breakCount;                //任务执行时切出次数
	private int[] taskTimeUsedWeek;        //本周任务每日总计的有效时间

	private Tencent mTencent;
	private MyIUiListener myIUiListener;
	private Bundle params;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		BarChart barChart=findViewById(R.id.data_bar_chart);
		MyBarChartManager myBarChartManager=new MyBarChartManager(barChart);
		tvScores = findViewById(R.id.tv_scores);
		tvTaskConsumedTime = findViewById(R.id.tv_task_consumed_time);
		tvTaskConcentrateTime = findViewById(R.id.tv_task_concentrate_time);
		tvTodayConcentrateTime = findViewById(R.id.tv_today_concentrate_time);
		tvSuggestion=findViewById(R.id.tv_suggestion);
		btnReturnMain = findViewById(R.id.button_return_main);
		btnNextTask = findViewById(R.id.button_next_task);
		btnShare = findViewById(R.id.button_share_to_qzone);
		LinearLayout layoutMain = findViewById(R.id.activity_finish_layout);

		recyclerView=findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recommendedTaskList=MyDatabaseOperation.getRecommendedTaskList(FinishActivity.this);
		if(recommendedTaskList==null || recommendedTaskList.size() == 0)
			btnNextTask.setVisibility(View.GONE);
		adapter = new RecyclerViewAdapter(recommendedTaskList);
		recyclerView.setAdapter(adapter);

		BackgroundCollection backgroundCollection = new BackgroundCollection();
		layoutMain.setBackgroundResource(backgroundCollection.getTodayBackground());

		//得到任务完成信息和本周每日工作学习时长（有效，总未记录）
		getTaskFinishInfo();
		tvSuggestion.setText(MySuggestions.getSuggestion(taskTotalTimeUsed,(double)taskTimeUsed/taskTotalTimeUsed,(double)breakCount/(double)(taskTotalTimeUsed+30)/60));
		//处理数据
		scores =MyAlgorithm.calcScores(taskTotalTimeUsed,taskTimeUsed,breakCount);
		taskTotalTimeUsed+=30;taskTotalTimeUsed/=60;
		taskTimeUsed+=30;taskTimeUsed/=60;
		timeUsedToday+=30;timeUsedToday/=60;
		for(int i=0;i<7;i++)
			taskTimeUsedWeek[i]=(taskTimeUsedWeek[i]+30)/60;

		// 设置TextView的显示格式
		setFormattedString(scores, scores +"分",2.8f,false,tvScores);
		setFormattedString(taskTotalTimeUsed,"本次任务共耗时 " + taskTotalTimeUsed + " 分钟",1.4f,true,tvTaskConsumedTime);
		setFormattedString(taskTimeUsed,"专注"+taskTimeUsed+"分钟",1.4f,true,tvTaskConcentrateTime);
		setFormattedString(timeUsedToday,"今日共专注"+timeUsedToday+"分钟",1.4f,true,tvTodayConcentrateTime);

		//设置x轴的数据
		ArrayList<Float> xValues = new ArrayList<>();
		for (int i = 0; i <= 6; i++) {
			xValues.add((float) i);
		}

		//设置y轴的数据
		List<Float> yValue = new ArrayList<>();
		yValue.add((float) taskTimeUsedWeek[6]);
		for (int j = 0; j <= 5; j++) {
			yValue.add((float) taskTimeUsedWeek[j]);
		}

		//颜色顺序为：背景颜色、Y轴颜色、X轴颜色、柱体颜色、图表标签颜色
		int colorBackground = 0x80000000;
		int resBackground;
		int colorAxisY = Color.WHITE;
		int colorAxisX = Color.WHITE;
		int colorBody = Color.WHITE;
		int colorLabel = Color.WHITE;
		myBarChartManager.showBarChartWithBackGroundRes(xValues,yValue,"每日专注时间",R.drawable.chart_background_shape,colorAxisY,colorAxisX,colorBody,colorLabel);

		TempPicStorageManager storageManager = new TempPicStorageManager(this, "tempPicToShare");
		storageManager.clean();

		btnReturnMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				shareToQQ(FinishActivity.this);
			}
		});

		//设置分享用的QQ实例初始化
		mTencent = Tencent.createInstance("1106810223", getApplicationContext());
		myIUiListener = new MyIUiListener();

		adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Intent intent=new Intent(FinishActivity.this,TaskTimingActivity.class);
				intent.putExtra("intent_task_id",recommendedTaskList.get(position).getId());
				intent.putExtra("intent_task_name",recommendedTaskList.get(position).getName());
				intent.putExtra("intent_task_hours_required",recommendedTaskList.get(position).getHourRequired());
				intent.putExtra("intent_task_minutes_required",recommendedTaskList.get(position).getMinuteRequired());
				intent.putExtra("intent_is_daily_task",recommendedTaskList.get(position).getIsDailyTask());
				intent.putExtra("intent_time_used",recommendedTaskList.get(position).getTimeUsed());
				startActivity(intent);
				finish();
			}

			@Override
			public void onItemLongClick(View view, int position) {}
		});
	}

	//设置显示格式
	private void setFormattedString(int data, String srcString, float size, boolean hasStyle, TextView tv){
		String dataString=String.valueOf(data);
		int startIndex=srcString.indexOf(dataString);
		int endIndex=startIndex+dataString.length();
		SpannableStringBuilder ssb=new SpannableStringBuilder(srcString);
		ssb.setSpan(new RelativeSizeSpan(size),startIndex,endIndex,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		if(hasStyle) ssb.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		tv.setText(ssb);
	}

	//获得任务完成信息
	private void getTaskFinishInfo(){
		Intent intent = getIntent();
		taskTotalTimeUsed = intent.getIntExtra("task_total_time_used", 0);
		taskTimeUsed = intent.getIntExtra("task_time_used", 0);
		timeUsedToday = MyDatabaseOperation.getTotalTimeUsedToday(this) + taskTimeUsed;
		breakCount = intent.getIntExtra("break_count", 0);
		taskTimeUsedWeek = MyDatabaseOperation.getThisWeekPerDayTimeUsedWithCorrection(this, taskTimeUsed);
	}

	//将本次任务分享到QQ
	private void shareToQQ(Context context){
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
		String fileName = format.format(calendar.getTime());

		TempPicStorageManager storageManager = new TempPicStorageManager(context, "tempPicToShare");
		String dirPath = storageManager.getDirPath();

		BackgroundCollection backgroundCollection = new BackgroundCollection();
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmapToShare = BitmapFactory.decodeResource(getResources(), backgroundCollection.getTodayBackground(), options);
		int height = bitmapToShare.getHeight();
		int width = bitmapToShare.getWidth();
		int density = bitmapToShare.getDensity();

		Bitmap.Config config = bitmapToShare.getConfig();
		if(config == null) config = Bitmap.Config.ARGB_8888;
		bitmapToShare = bitmapToShare.copy(config, true);

		Canvas canvas = new Canvas(bitmapToShare);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

		//绘制表层模板
		Bitmap bitmapRefUpper = BitmapFactory.decodeResource(getResources(), R.drawable.share_ref_upper, options);
		canvas.drawBitmap(bitmapRefUpper, 0.0f, 0.0f, paint);

		//绘制弧形，时间占比
		paint.setColor(backgroundCollection.getTodayColor());
		paint.setAlpha(128);
		paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
		int rad = 250;
		RectF rectF = new RectF((int)(0.3f * width) - rad, (int)(0.52f * height) - rad,
				(int)(0.3f * width) + rad, (int)(0.52f * height) + rad);
		canvas.drawArc(rectF, 270.0f, 360.0f * taskTimeUsed / taskTotalTimeUsed, true, paint);
		paint.setColor(Color.WHITE);
		paint.setAlpha(64);
		int radL = 280;
		rectF.set((int)(0.3f * width) - radL, (int)(0.52f * height) - radL,
				(int)(0.3f * width) + radL, (int)(0.52f * height) + radL);
		canvas.drawArc(rectF, 0, 360, true, paint);

		//绘制评分
		paint.setTextSize(392);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAlpha(255);
		canvas.drawText(Integer.toString(scores), width / 2, height / 4, paint);

		//绘制此次有效时长
		paint.setTextSize(192);
		canvas.drawText(secToHourMin(taskTimeUsed * 60), (int)(0.3f * width), (int)(0.535 * height), paint);
		//绘制此次总时长，今日有效时长
		paint.setTextSize(144);
		canvas.drawText(secToHourMin(taskTotalTimeUsed * 60), (int)(0.65f * width), (int)(0.47 * height), paint);
		canvas.drawText(secToHourMin(timeUsedToday * 60), (int)(0.65f * width), (int)(0.58 * height), paint);

		String filePicStoredPath = dirPath + File.separator + fileName + ".jpg";
		File filePicStored = new File(filePicStoredPath);
		try{
			FileOutputStream fileOutputStream = new FileOutputStream(filePicStored);
			bitmapToShare.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}

		shareImgToQQ(filePicStoredPath);
	}


	//QQ分享回调
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Tencent.onActivityResultData(requestCode, resultCode, data, myIUiListener);
		if (requestCode == Constants.REQUEST_API) {
			if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
				Tencent.handleResultData(data, myIUiListener);
			}
		}
	}

	private void shareImgToQQ(String imgUrl){
		params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 设置分享类型为纯图片分享
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrl);// 需要分享的本地图片URL
		params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//默认分享到空间
		mTencent.shareToQQ(FinishActivity.this, params, myIUiListener);
	}

	private String secToHourMin(int sec){
		int min = (sec + 1) / 60;
		int hor = min / 60;
		min = min - hor * 60;
		return String.format(Locale.getDefault(), "%02d:%02d", hor, min);
	}

}
