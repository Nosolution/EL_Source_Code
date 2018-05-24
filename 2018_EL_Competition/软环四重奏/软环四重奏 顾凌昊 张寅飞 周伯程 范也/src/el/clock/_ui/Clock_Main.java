package el.clock._ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import el._main.Controller;
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

public class Clock_Main extends Clock{
	Controller control;
	
	
	public Clock_Main(Controller c) {
		super();
		control = c;
		
		Button exit = new Button("返回主界面");
		exit.setOnAction(e ->{
			c.toTitle();
		});
		this.getChildren().add(exit);
		
	}
	
	public ArrayList<ArrayList<Object>> Getnotes(){
		return notes;
	}
}
