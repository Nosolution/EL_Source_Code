package el.clock._ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import el._main.DataController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Clock extends AnchorPane{
	
	Button start,pause;
	boolean running = false;
	boolean pausing = false;
	boolean givingUp = false;
	private Label timer = new Label();
	private long startingTime;
	private long pausingTime;
	private long endingTime;
	private Timeline timeline = new Timeline();
	ArrayList<ArrayList<Object>> notes;

	Task task;
	long sumTime;
	
	public Clock() {
		//初始化相关变量
		
		timer.setAlignment(Pos.CENTER);
		timer.setLayoutX(900);
		timer.setLayoutY(360);
		timer.setFont(new Font("黑体",50));
				
		start=new Button("start");
		start.setLayoutX(900);
		start.setLayoutY(600);
		start.setOnAction(e ->{
			if(!running) {
				SettingStage ss = new SettingStage(this);
				if(ss.suceedToSet) {
					StartNewTask(startingTime, ss.getTime() * 1000);
				}
			}
			else {
				setPausing(true);
				pausingTime = System.currentTimeMillis();
				new GivingUpStage(this);
				start.setText("give up");
			}	
		});
		
		pause=new Button("pause");
		pause.setLayoutX(1000);
		pause.setLayoutY(600);
		pause.setOnAction(e ->{
			setPausing(true);
			pausingTime = System.currentTimeMillis();
			new PausingStage(this);
		});
		pause.setDisable(true);	
		
		
		this.getChildren().add(new ImageView(new Image("el/clock/_ui/resource 4.png")));
		this.getChildren().addAll(start,pause,timer);
	
	}
	
	

	public void startTiming(long t) {
		startingTime = System.currentTimeMillis();
		timeline = new Timeline();
		setRunning(true);
		setSumTime(t);
		timer.setText(toTime(sumTime));
		start.setText("give up");
		pause.setDisable(false);
		
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),e ->{
			if(!pausing) {
				sumTime--;
				timer.setText(toTime(sumTime));
				if(sumTime==0) {
					stopTiming();
				}
			}
		}) );
		timeline.play();
	}

    //任务结束
	public void stopTiming() {
		pausing = false;
		running = false;
		sumTime = 0;
		timer.setText(toTime(0));
		timeline.stop();
		start.setText("start");
		pause.setDisable(true);
		
		//中途放弃在放弃窗口中已完成计时
		if(givingUp) {
			endingTime = pausingTime;
			givingUp = false;
		}
		//计时结束
		else {
			endingTime = System.currentTimeMillis();
			task.actualTime = task.planTime;
			task.validTime += endingTime - startingTime;
			new OverStage();
		}
		
		EndTask(endingTime);
	}
	
	//任务继续
	public void continueTiming() {
		startingTime = System.currentTimeMillis();
		task.addPause(pausingTime, startingTime);
		setPausing(false);
	}
	
	
	public void pauseToMusic() {
		// TODO Auto-generated method stub
		
	}
	public void pauseToGame() {
		// TODO Auto-generated method stub
		
	}

	private void StartNewTask(long start, long plan) {
		task = new Task(start,plan);
	}

	
	
	
	private void EndTask(long end) {
		task.setEndTime(end);
		
		ArrayList<Day> days = DataController.getDays();
		if(days.isEmpty()) {
			days.add(new Day(task));
		}else {
			Day lastDay = days.get(days.size()-1);
			if(lastDay.getStartTime()<=end&&end<=lastDay.getEndTime()) {
				days.get(days.size()-1).addTask(task);
			}else days.add(new Day(task));
		}
		
		DataController.saveDays(days);
		
	}


	public String toTime(long t) {
		int h=(int) (t/3600);
		int m=(int) ((t-h*3600)/60);
		int s=(int) (t%60);
		return String.format("%02d", h)+":"+String.format("%02d", m)+":"+String.format("%02d", s);
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isPausing() {
		return pausing;
	}

	public void setPausing(boolean pausing) {
		this.pausing = pausing;
	}


	public Label getTimer() {
		return timer;
	}

	public void setTimer(Label timer) {
		this.timer = timer;
	}

	public long getSumTime() {
		return sumTime;
	}

	public void setSumTime(long sumTime) {
		this.sumTime = sumTime;
	}
	
	public long getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(long startingTime) {
		this.startingTime = startingTime;
	}

	public long getpausingTime() {
		return pausingTime;
	}

	public void setpausingTime(long pausingTime) {
		this.pausingTime = pausingTime;
	}

	public long getendingTime() {
		return endingTime;
	}

	public void setendingTime(long endingTime) {
		this.endingTime = endingTime;
	}

}
