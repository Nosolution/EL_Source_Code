package el.clock._ui;

import java.text.DecimalFormat;
import java.util.Date;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PausingStage {

	Clock clock;
	Stage stage;
	Text text = new Text("计时已暂停。。。");
	Button continue1 = new Button("继续"), music = new Button("音乐"), game = new Button("游戏");
	double doneTime;
	double remainingTime;
	
	public PausingStage(Clock clock) {
		this.clock = clock;
		clock.start.setDisable(true);
		clock.pause.setDisable(true);
		stage = new Stage();
		Group root = new Group();
		long duration = clock.getpausingTime() - clock.getStartingTime();
		
		//设置time
		doneTime = (double)(duration)/60000;
		remainingTime = (double)clock.getSumTime()/60 - doneTime;
		DecimalFormat df = new DecimalFormat(".00");
		doneTime = Double.parseDouble(df.format(doneTime));
		remainingTime = Double.parseDouble(df.format(remainingTime));
		
		clock.task.actualTime += doneTime; 
		
		//设置text
		text.setLayoutX(65);text.setLayoutY(35);
		
		//设置button
		continue1.setLayoutX(5); music.setLayoutX(85); game.setLayoutX(165);
		continue1.setLayoutY(60); music.setLayoutY(60); game.setLayoutY(60);
		continue1.setOnAction(e ->{
			exitWindow(0);
		});
		music.setOnAction(e ->{
			exitWindow(1);
		});
		game.setOnAction(e ->{
			exitWindow(2);
		});
		
		
		root.getChildren().addAll(text, continue1, music, game);
		Scene scene = new Scene(root, 300, 150, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	
	public void exitWindow(int i) {
		switch(i) {
			case 2:{
				clock.pauseToMusic();
				break;
			}
			case 1:{
				clock.pauseToGame();
				break;
			}
			case 0:{
				clock.pause.setDisable(false);  //恢复按钮
				clock.task.validTime += doneTime * getRate(System.currentTimeMillis());
				clock.continueTiming();
			}
		}
		clock.start.setDisable(false);
		stage.close();
	}
	
	
	private double getRate(long currentTimeMillis) {
		double PauseTime = (double)(currentTimeMillis - clock.getpausingTime());
		if(PauseTime < doneTime*0.2) {
			return 1;
		}
		else if(PauseTime <= doneTime) {
			double ratio = (PauseTime - doneTime*0.2)/(doneTime*0.8);
			return 0.6 + 0.4*ratio;
		}
		else {
			return 0.6;
		}
	}
}
