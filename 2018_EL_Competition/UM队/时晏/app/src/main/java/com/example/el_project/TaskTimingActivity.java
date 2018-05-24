package com.example.el_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * 任务计时界面，即任务进行中的界面，右侧划出可进行设置
 * 任务进行中的计时界面，管理任务进行中时，背景乐，番茄钟，用户暂停放弃完成操作
 * @author NA
 * @version 2.6
 *
 * */

public class TaskTimingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
	private DrawerLayout mDrawerLayout;
	private Switch switchClockStatus;
	private Switch switchMusicStatus;
	private Button btnChangeMusic;                     //切换歌曲按钮
	private TextView tvClockTime;
	private LinearLayout clockTimeLayout;
	private TextView tvBreakClockTime;
	private LinearLayout breakClockTimeLayout;

	private CountTimer timer;
	private CountTimer totalTimer;                    //总计耗时
	private boolean havingTaskOngoing = false;     //是否有任务正在进行中
	private boolean taskStatuePaused = false;        //任务暂停或进行中Flag
	private boolean locked = false;

	private ImageButton btnTaskFinished;                //任务完成，按钮
	private ImageButton btnThrowTask;                   //放弃任务，按钮
	private ImageButton btnPause;                       //暂停

	private TextView tvTaskTimeCount;                //显示任务已经过时间
	private int taskSecGone = 0;                       //任务已过时间
	private int taskTotalSecUsed = 0;                    //任务共用时间
	private Handler taskTimeRefreshHandler;
	private Handler taskTotalTimeRefreshHandler;
	private TextView tvTimeLeft;
	private TextView tvBelowTimeLeft;
	private TextView tvLeftToTimeLeft;
	private boolean isQuickTask = false;             //是否为快速任务，即无预计时间的任务
	private MusicController musicController;
	private CountDownTimer tomatoClockCountDown;   //番茄钟倒计时
	private CountDownTimer tomatoClockBreakCountDown;//番茄钟休息倒计时
	private CountDownTimer breakFiveMinCountDown;   //是否切出满5分钟的提醒

	private int taskId;                              //任务id
	private long taskMillisRequired;                 //任务预计用时，毫秒
	private String taskName;                         //任务名
	private String taskComments;                      //任务备注
	private int taskTimeUsed;                        //任务此前已用时间

	private LinearLayout remarkLayout;             //备注所在的布局
	private LinearLayout.LayoutParams remarkLayoutLayoutParams; //布局大小
	private TextView tvRemark;                   //备注
    private ImageView imgDial;
	private Toolbar toolbar;

	private BroadcastReceiver screenOffReceiver;   //接收熄屏广播
	private String startTime;                     //开始时间，以年月日时分秒计，作为唯一标识一项进行任务活动的key
	private int breakCount = 0;                   //切出活动计数
	private boolean withinOneSecondAfterPause = false;    //是否是pause之后1s内
	private boolean isOutOfApp = false;
	private SaveStatue saveFinishStatue = SaveStatue.QUIT;    //最后的完成状态

	public enum SaveStatue{
		QUIT,
		FINISH
	}

	private CircleProgress tomatoClockProgress, tomatoBreakProgress,circleProgress2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//修改界面主题
		BackgroundCollection backgroundCollection = new BackgroundCollection();
		setTheme(backgroundCollection.getTodayTheme());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_timing);

		circleProgress2=(CircleProgress)findViewById(R.id.cp2);
		circleProgress2.setmTotalProgress(100);
		circleProgress2.setProgress(100);

		tomatoClockProgress =(CircleProgress)findViewById(R.id.cp1);
		tomatoClockProgress.setmTotalProgress(GeneralSetting.getTomatoClockTime(this) * 60);
		tomatoBreakProgress = (CircleProgress)findViewById(R.id.cp_break);
		tomatoClockProgress.setmTotalProgress(GeneralSetting.getTomatoBreakTime(this) * 60);

		//初始化Toolbar
		toolbar = findViewById(R.id.setting_toolbar);
		setSupportActionBar(toolbar);
		mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
		ActionBar actionBar = getSupportActionBar();
		toolbar.setBackgroundColor(backgroundCollection.getTodayColor() + 0x80000000);

		if(actionBar!=null){
			actionBar.setDisplayHomeAsUpEnabled(true);  //显示导航按钮
//			actionBar.setHomeAsUpIndicator(R.drawable.category_white_31);  //设置导航按钮图标
		}

		//初始化设置界面具体内容
		switchClockStatus = findViewById(R.id.switch_if_tomato_clock_on);
		switchMusicStatus = findViewById(R.id.switch_if_music_on);
		btnChangeMusic = findViewById(R.id.bt_change_music);
		tvClockTime = findViewById(R.id.text_choose_time);
		clockTimeLayout = findViewById(R.id.task_timing_clock_set_time);
		tvBreakClockTime = findViewById(R.id.text_choose_break_time);
		breakClockTimeLayout = findViewById(R.id.task_timing_break_clock_set_time);

		//初始化计时主要界面的内容
		tvTaskTimeCount = findViewById(R.id.time_action);
		tvTimeLeft = findViewById(R.id.time_left_to_finish);
		tvBelowTimeLeft = findViewById(R.id.text_below_time_left);
		tvLeftToTimeLeft = findViewById(R.id.text_left_to_time_left);
		tvRemark = findViewById(R.id.edit_remark);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_task_timing_drawer_layout);
//		tomatoClockCountDownTime = findViewById(R.id.tomato_text);
		btnTaskFinished = findViewById(R.id.finish_button);
		btnThrowTask = findViewById(R.id.give_up_button);
		btnPause = findViewById(R.id.pause_button);
		imgDial = findViewById(R.id.image_dial);
		LinearLayout layoutMain = findViewById(R.id.activity_task_timing_linearLayout);
		RelativeLayout layoutSetting = findViewById(R.id.activity_task_timing_setting_upper);
		layoutMain.setBackgroundResource(backgroundCollection.getTodayBackground());
		layoutSetting.setBackgroundColor(backgroundCollection.getTodayColor());

		//设置设置界面相关监听器
		switchClockStatus.setChecked(GeneralSetting.getTomatoClockEnable(this));
		switchMusicStatus.setChecked(GeneralSetting.getMusicOn(this));
		switchClockStatus.setOnCheckedChangeListener(this);
		switchMusicStatus.setOnCheckedChangeListener(this);
		clockTimeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				android.support.v7.app.AlertDialog dialog;
				final String[] itemToSelect = {"20分钟", "30分钟", "40分钟", "50分钟"};
				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(TaskTimingActivity.this);
				builder.setTitle("时长");
				builder.setItems(itemToSelect, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						GeneralSetting.setTomatoClockTime(TaskTimingActivity.this, which * 10 + 20);
						refreshSetting();
						if(!taskStatuePaused){
							initStartTomatoClock();
						}
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
		});breakClockTimeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				android.support.v7.app.AlertDialog dialog;
				final String[] itemToSelect = {"5分钟", "10分钟", "15分钟", "20分钟"};
				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(TaskTimingActivity.this);
				builder.setTitle("休息时长");
				builder.setItems(itemToSelect, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						GeneralSetting.setTomatoBreakTime(TaskTimingActivity.this, which * 5 + 5);
						refreshSetting();
						if(taskStatuePaused){
							initStartTomatoClockBreak();
						}
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
		btnChangeMusic.setOnClickListener(this);


		//设置计时控制部分所有涉及到的有关的监听器
		btnTaskFinished.setOnClickListener(this);
		btnThrowTask.setOnClickListener(this);
		btnPause.setOnClickListener(this);

		//初始化设置表现
		refreshSetting();


		//取得开始任务时传来的任务详细信息
		final Intent intentTaskInfo = getIntent();
		taskId = intentTaskInfo.getIntExtra("intent_task_id", 0);
		if (taskId == 0) finish();                                            //若未收到任务ID，退出
		taskName = intentTaskInfo.getStringExtra("intent_task_name");
		int taskHoursRequired = intentTaskInfo.getIntExtra("intent_task_hours_required", 0);
		int taskMintersRequired = intentTaskInfo.getIntExtra("intent_task_minutes_required", 0);
		taskComments = intentTaskInfo.getStringExtra("intent_task_comments");
		taskMillisRequired = hourMinSec2Millis(taskHoursRequired, taskMintersRequired, 0);
		taskTimeUsed = intentTaskInfo.getIntExtra("intent_time_used", 0);

		//初始化显示的Task各项信息
		toolbar.setTitle(taskName);                                        //设置toolbar标题显示任务名
		tvRemark.setText(taskComments);                                  //设置备注显示
        RelativeLayout leftTimeLayout = findViewById(R.id.layout_left_time_to_finish);
        if (taskMillisRequired <= 0){
            isQuickTask = true;
            tvLeftToTimeLeft.setText("总计");
            tvBelowTimeLeft.setText("花费时间");
			tomatoClockProgress.setProgress(GeneralSetting.getTomatoClockTime(this) * 60);
        }

		//向数据库存储本次信息
		startTime = MyDatabaseOperation.addFinishTaskWithStartTime(this, taskName);

		//设置系统熄屏的广播接收
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		screenOffReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (withinOneSecondAfterPause && isOutOfApp) {
					breakCount--;
					isOutOfApp = false;
				}
				locked = true;
			}
		};
		registerReceiver(screenOffReceiver, filter);

		//刷新任务有效时间和任务总计时间
		taskTimeRefreshHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				taskSecGone = msg.what;
			}
		};
		taskTotalTimeRefreshHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				taskTotalSecUsed = msg.what;
			}
		};

		//初始化然后启动正向计时
		initCountTimer();
		//从被回收内存恢复，但感觉问题还是挺大
		if(savedInstanceState !=null) {
			if (savedInstanceState.getBoolean("is_task_going_on", false)) {
				timer.cancel();
				timer.startWithPassedTime(savedInstanceState.getLong("millis_gone", 0));
			} else {
				timer.start();
			}
		}
		else {
			timer.start();
		}

		totalTimer = new CountTimer(1000) {
			@Override
			public void onTick(long millisGoneThrough) {
				taskTotalTimeRefreshHandler.sendEmptyMessage((int)(millisGoneThrough/1000));
				if(isQuickTask){
					tvTimeLeft.setText(millis2HourMinSecString(millisGoneThrough, 2));
				}
			}
		};
		totalTimer.start();

		//设置有任务正在进行的Flag，可能会有用
		havingTaskOngoing = true;
		//设置音乐开启
		musicController = new MusicController(this);
		if(GeneralSetting.getMusicOn(this)) {
			musicController.start();
		}

		//旋转刻度图形
        imgDial.setImageResource(R.drawable.circle_gapped_doing);

		//更新是否启用番茄钟动画
		refreshTomatoClockVisible();

		//初始化并启动番茄钟
		if(GeneralSetting.getTomatoClockEnable(this)){
			initStartTomatoClock();
		}

	}

	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.toolbar,menu);
		return true;
	}

	//对HomeAsUp按钮点击事件进行处理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
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
				    GeneralSetting.setMusicOn(TaskTimingActivity.this, true);
					if(!musicController.isPlaying() && havingTaskOngoing){
						musicController.restart();
					}
				}
				else {
				    GeneralSetting.setMusicOn(TaskTimingActivity.this, false);
				    if(musicController.isPlaying()){
				    	musicController.stop();
					}
				}
				break;
			case R.id.switch_if_tomato_clock_on:
				if(compoundButton.isChecked()) {
				    GeneralSetting.setTomatoClockEnable(TaskTimingActivity.this, true);

					//打开番茄钟
					initStartTomatoClock();
				}
				else {
				    GeneralSetting.setTomatoClockEnable(TaskTimingActivity.this, false);

					//关闭番茄钟
					if (tomatoClockCountDown != null) {
						tomatoClockCountDown.cancel();
						tomatoClockCountDown = null;
					}
					if(tomatoClockBreakCountDown != null){
						tomatoClockBreakCountDown.cancel();
						tomatoClockBreakCountDown = null;
					}
				}
				refreshTomatoClockVisible();
				break;
		}
		refreshSetting();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.finish_button:
				if (MyDatabaseOperation.isDailyTask(this, taskId)){
					MyDatabaseOperation.setDailyTaskFinished(this,taskId);
				}else {
					removeTaskFromDB();     //从数据库中移除相应Task
				}
				musicController.stop();     //音乐停止播放
				showFinishingActivity();    //显示完成界面
				havingTaskOngoing = false;
				saveFinishStatue = SaveStatue.FINISH;
				breakCount--;
				finish();
				break;

			case R.id.give_up_button:
				onBackPressed();
				break;

			case R.id.pause_button:
				if(!taskStatuePaused) {
					pause();
				}
				else {
					resume();
				}
				break;
			case R.id.bt_change_music:
				musicController.randomSwitch();
				break;
		}
	}

	@Override //活动Destroy时，为避免倒计时器的可能问题，取消并释放计时器
	protected void onDestroy() {
		if(timer != null){
			timer.cancel();
			timer = null;
		}

		if(totalTimer != null){
			totalTimer.cancel();
			totalTimer = null;
		}

		//释放音乐控制器
		if(musicController != null) {
			musicController.release();
			musicController = null;
		}

		//释放两个番茄钟计时器
		if(tomatoClockCountDown != null){
			tomatoClockCountDown.cancel();
			tomatoClockCountDown = null;
		}
		if(tomatoClockBreakCountDown != null){
			tomatoClockBreakCountDown.cancel();
			tomatoClockBreakCountDown = null;
		}
		if(breakFiveMinCountDown != null){
			breakFiveMinCountDown.cancel();
			breakFiveMinCountDown = null;
		}

		unregisterReceiver(screenOffReceiver);

		saveTaskFinishToDB(saveFinishStatue.ordinal());

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		isOutOfApp = false;
		locked = false;
//        resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (!locked) {
			isOutOfApp = true;
//        pause();
			breakCount++;
			withinOneSecondAfterPause = true;
			new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						sleep(4000);
						withinOneSecondAfterPause = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();

			//若连续切出应用5分钟后，提醒
			if (breakFiveMinCountDown != null) {
				breakFiveMinCountDown.cancel();
				breakFiveMinCountDown = null;
			}
			breakFiveMinCountDown = new CountDownTimer(5 * 60 * 1000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {
				}

				@Override
				public void onFinish() {
					if (isOutOfApp) {
						sendNotification("你已离开5分钟", "你已离开应用5分钟，继续工作吧");
					}
				}
			};
			breakFiveMinCountDown.start();
		}
	}

	@Override
	public void onBackPressed() {
		final AlertDialog onBackAskQuitDialog =
				new AlertDialog.Builder(this)
						.setTitle("放弃任务")
						.setMessage("是否放弃当前任务退出")
						.setPositiveButton("放弃任务", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				musicController.stop();
				havingTaskOngoing = false;
				saveFinishStatue = SaveStatue.QUIT;
				breakCount--;
				new Thread() {
					@Override
					public void run() {
						super.run();
						MyDatabaseOperation.giveUpFinishingTask(TaskTimingActivity.this,taskId,taskSecGone);
					}
				}.start();
				finish();
			}
		})
			.setNegativeButton("继续任务", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		})
				.create();
		onBackAskQuitDialog.show();
		onBackAskQuitDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GRAY);
		onBackAskQuitDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
	}


	@Override //保存实体状态，在内存被回收时也可恢复
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("millis_gone", timer.getMillisPassed());
		outState.putBoolean("is_task_going_on", havingTaskOngoing);
	}


	//从数据库删除当前任务
	private void removeTaskFromDB(){
		MyDatabaseOperation.deleteTask(this, taskId);
	}


	//修改任务，主要是修改预计完成时间
	private void changeTask(long dueTimeToFinish){
		//TODO:修改任务描述，自动修改预计完成时间
	}

	private void pause(){
		musicController.pause();
		timer.pause();

		taskStatuePaused = true;
		btnPause.setBackgroundResource(R.drawable.stop);


		//若开启番茄钟，开始番茄钟任务进行时间计时
		if (tomatoClockCountDown != null) {
			tomatoClockCountDown.cancel();
			tomatoClockCountDown = null;
		}
		if(GeneralSetting.getTomatoClockEnable(this) && GeneralSetting.getTomatoClockEnable(this)) {
			initStartTomatoClockBreak();
		}

		tvTaskTimeCount.setText("休息中");
		tomatoClockProgress.setProgress(0);
	}

	private void resume(){
		if(GeneralSetting.getMusicOn(this)) {
			musicController.resume();
		}
		timer.resume();

		taskStatuePaused = false;
		btnPause.setBackgroundResource(R.drawable.doing);
		tomatoClockProgress.setmTotalProgress(GeneralSetting.getTomatoClockTime(this) * 60);

		//若开启番茄钟，开始番茄钟任务休息时间计时
		if(tomatoClockBreakCountDown != null){
			tomatoClockBreakCountDown.cancel();
			tomatoClockBreakCountDown = null;
		}
		if (GeneralSetting.getTomatoClockEnable(this) && GeneralSetting.getTomatoClockEnable(this)) {
			initStartTomatoClock();
		}

		tomatoBreakProgress.setProgress(0);
	}

	//显示完成界面
	private void showFinishingActivity(){
		Intent intent = new Intent(this, FinishActivity.class);
		intent.putExtra("task_total_time_used", taskTotalSecUsed);
		intent.putExtra("task_time_used", taskSecGone);
		intent.putExtra("break_count", breakCount);
		startActivity(intent);
	}

	//初始化计时器，内部修改每秒行为，计时器开始计时前必须初始化
	private void initCountTimer(){
		timer = new CountTimer(1000) {
			@Override
			public void onTick(long millisGoneThrough) {
				tvTaskTimeCount.setText(millis2HourMinSecString(millisGoneThrough));
				taskTimeRefreshHandler.sendEmptyMessage((int)(millisGoneThrough/1000));
				if(!isQuickTask) {
					tvTimeLeft.setText(millis2HourMinSecString(Math.max((taskMillisRequired - taskTimeUsed * 1000 - millisGoneThrough + 60000), 0), 2));
				}
				imgDial.setPivotX(imgDial.getWidth() / 2);
				imgDial.setPivotY(imgDial.getHeight() / 2);
				imgDial.setRotation((int)(millisGoneThrough / 1000) * 5);
			}
		};
	}

	private void initStartTomatoClock(){

		if (tomatoClockCountDown != null){
			tomatoClockCountDown.cancel();
		}
		tomatoClockProgress.setmTotalProgress(GeneralSetting.getTomatoClockTime(this) * 60);
		tomatoClockCountDown = new CountDownTimer(GeneralSetting.getTomatoClockTime(this) * 60000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				tomatoClockProgress.setProgress(GeneralSetting.getTomatoClockTime(TaskTimingActivity.this) * 60 - (int)(millisUntilFinished / 1000));
			}

			@Override
			public void onFinish() {
				sendNotification("番茄钟计时到", "工作很久了，休息一下吧");
			}
		};
		tomatoClockCountDown.start();
	}

	private void initStartTomatoClockBreak(){
		if (tomatoClockBreakCountDown != null){
			tomatoClockBreakCountDown.cancel();
		}
		tomatoBreakProgress.setmTotalProgress(GeneralSetting.getTomatoBreakTime(this) * 60);
		tomatoClockBreakCountDown = new CountDownTimer(GeneralSetting.getTomatoBreakTime(this) * 60000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				tomatoBreakProgress.setProgress(GeneralSetting.getTomatoBreakTime(TaskTimingActivity.this) * 60 - (int)(millisUntilFinished / 1000));
			}

			@Override
			public void onFinish() {
				sendNotification("番茄钟计时到", "休息有一会了，开始工作吧");

				AlertDialog breakFinishDialog = new AlertDialog.Builder(TaskTimingActivity.this)
						.setTitle("番茄钟休息计时到")
						.setMessage("休息有一会了，开始工作吧")
						.setPositiveButton("开始工作", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								resume();
							}
						})
						.setNegativeButton("再休息一会", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								initStartTomatoClockBreak();
							}
						})
						.create();
				breakFinishDialog.show();


			}
		};
		tomatoClockBreakCountDown.start();
	}

	//发送通知，暂只用于番茄钟提醒
	//@param:提醒的标题，提醒的正文内容
	private void sendNotification(String title, String text){

		Intent intent = new Intent(this, TaskTimingActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel("channel_tomato_clock", "ChannelTomatoClock", NotificationManager.IMPORTANCE_DEFAULT);
			channel.enableLights(true);
			channel.setLightColor(Color.GREEN);
			channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
			channel.enableVibration(true);
			channel.setVibrationPattern(new long[]{1000, 1000, 2000, 2000, 1000, 1000});
			channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
			notificationManager.createNotificationChannel(channel);
		}
		Notification notification= new NotificationCompat.Builder(this, "channel_tomato_clock")
				.setAutoCancel(true)
				.setContentText(text)
				.setContentTitle(title)
				.setSmallIcon(R.drawable.icon6)
				.setContentIntent(pendingIntent)
				.setDefaults(Notification.DEFAULT_SOUND)
				.setVisibility(Notification.VISIBILITY_PUBLIC)
//				.setFullScreenIntent(pendingIntent, false)
				.build();

		try {
			notificationManager.notify(1, notification);
			Log.d("TaskTimingActivity", "onFinish: TomatoClockStop");
		}catch (NullPointerException e){
			Log.e("TaskTimeActivity", "onFinish: " + e.toString());
		}
	}

	private void refreshTomatoClockVisible(){
		if(GeneralSetting.getTomatoClockEnable(this)){
			if(!taskStatuePaused){
				tomatoClockProgress.setmTotalProgress(GeneralSetting.getTomatoClockTime(this) * 60);
			}else {
				tomatoClockProgress.setmTotalProgress(GeneralSetting.getTomatoBreakTime(this) * 60);
			}
		}else {
			tomatoClockProgress.setmTotalProgress(100);
			tomatoClockProgress.setProgress(100);
		}
	}

	private void refreshSetting(){
		switchMusicStatus.setChecked(GeneralSetting.getMusicOn(this));
		switchClockStatus.setChecked(GeneralSetting.getTomatoClockEnable(this));

		if(GeneralSetting.getMusicOn(this)){
		    btnChangeMusic.setVisibility(View.VISIBLE);
        }else {
		    btnChangeMusic.setVisibility(View.GONE);
        }
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

	private void saveTaskFinishToDB(final int statue){
		new Thread(){
			@Override
			public void run() {
				super.run();
				MyDatabaseOperation.editFinishTaskWhenFinishing(TaskTimingActivity.this, startTime, taskSecGone, statue, breakCount);
				Log.d("TEST", "saveTaskFinishToDB: " + taskSecGone);
			}
		}.start();
	}

	//从毫秒转换到一个字符串的时间，显示时间时可调用
	public String millis2HourMinSecString(long millis){
		long second = millis / 1000;
		long minute = second / 60;
		long hour = minute / 60;
		minute = minute % 60;
		second = second % 60;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	public String millis2HourMinSecString(long millis, int itemNum){
		long second = millis / 1000;
		long minute = second / 60;
		long hour = minute / 60;
		minute = minute % 60;
		second = second % 60;
		if(itemNum == 2){
			return String.format("%02d:%02d", hour, minute);
		}else {
			return String.format("%02d:%02d:%02d", hour, minute, second);
		}
	}

	//从时分秒计转换到秒
	public int hourMinSec2Seconds(int hour, int minute, int second){
		return hour * 3600 + minute * 60 + second;
	}

	//从时分秒计转换到毫秒，注意：返回类型为long
	public long hourMinSec2Millis(int hour, int minute, int second){
		return (long) hourMinSec2Seconds(hour, minute, second) * 1000;
	}

}
