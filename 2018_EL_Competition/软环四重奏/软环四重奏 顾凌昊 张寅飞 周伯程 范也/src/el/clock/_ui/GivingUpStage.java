package el.clock._ui;

import java.text.DecimalFormat;
import java.util.Date;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GivingUpStage {

	Clock clock;
	Stage stage;
	Label lb1 = new Label();
	Label lb2 = new Label();
	Label lb3 = new Label();
	Button confirm=new Button("确认"),keep=new Button("继续坚持");
	double doneTime;
	double remainingTime;
	
	public GivingUpStage(Clock clock) {
		this.clock = clock;
		clock.start.setDisable(true);
		clock.pause.setDisable(true);
		stage = new Stage();
		Group root = new Group();
		long duration = clock.getpausingTime() - clock.getStartingTime();
		
		//设置time
		doneTime = (double)(duration)/60000;
		remainingTime = (double)(clock.sumTime)/60;
		DecimalFormat df = new DecimalFormat(".00");
		doneTime = Double.parseDouble(df.format(doneTime));
		remainingTime = Double.parseDouble(df.format(remainingTime));
		
		clock.task.actualTime += doneTime;
		
		//设置label
		lb1.setLayoutX(5);lb1.setLayoutY(30);
		lb2.setLayoutX(5);lb2.setLayoutY(60);
		lb3.setLayoutX(5);lb3.setLayoutY(90);
		lb1.setText("您已经专注了"+doneTime+"分钟,");
		lb2.setText("距离目标专注时间还有"+remainingTime+"分钟,");
		lb3.setText("确认放弃吗？");
		
		//设置button
		confirm.setLayoutX(5); keep.setLayoutX(85);
		confirm.setLayoutY(120); keep.setLayoutY(120);
		confirm.setOnAction(e ->{
			clock.task.validTime += doneTime * 0.6;
			exitWindow(1);
		});
		keep.setOnAction(e ->{
			clock.task.validTime += doneTime * 0.9;
			long now = System.currentTimeMillis();
			clock.task.addPause(clock.getpausingTime(), now);
			exitWindow(0);
		});
		
		root.getChildren().addAll(lb1,lb2,lb3,confirm, keep);
		Scene scene = new Scene(root, 300, 150, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	
	public void exitWindow(int i) {
		switch(i) {
			case 1:{
				clock.givingUp = true;
				clock.task.validTime += doneTime * 0.6;
				clock.stopTiming();
				break;
			}
			case 0:{
				clock.pause.setDisable(false);
				clock.task.validTime += doneTime * 0.9;
				clock.continueTiming();
			}
		}
		clock.start.setDisable(false);
		stage.close();
	}
	
}
