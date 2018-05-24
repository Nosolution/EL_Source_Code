package el.clock._ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 结束时闪现的窗口，未使用
 * @author 14237
 *
 */
public class OverStage {
	Stage stage;
	
	public OverStage() {
		
		String url=getClass().getClassLoader().getResource("el/clock/res/over.mp3").toString();
		Media media=new Media(url);
		new MediaPlayer(media).play();
		
		
		
		stage=new Stage();
		Rectangle bg=new Rectangle(200,80);
		bg.setFill(Color.WHITE);
		Pane root=new Pane();
		Label msg=new Label("时间到了！");
		msg.setLayoutY(20);
		stage.setTitle("结束提醒");
		Button exit=new Button("confirm");
		exit.setLayoutY(60);
		exit.setOnAction(e ->{
			stage.close();
		});
		root.getChildren().addAll(bg,msg,exit);
		stage.setScene(new Scene(root));
		
		stage.show();
		
	}
}
