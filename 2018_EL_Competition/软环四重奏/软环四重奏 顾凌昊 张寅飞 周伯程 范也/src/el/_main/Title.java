package el._main;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Title extends AnchorPane{
	Controller c;
	public Title(Controller co) {
		this.c=co;

		ImageView bg=new ImageView(new Image("el/_main/resource 2.png"));
		bg.maxWidth(600);bg.maxHeight(500);
		//bg.resize(600, 500);

		Font f1=new Font("黑体",50);
		Font f2= new Font("黑体", 50);

		/*Label title=new Label("Concentration 2.0");
		title.setId("Concentration");
		title.setFont(f1);title.setTextFill(Color.WHITE);
		title.setLayoutX(200);
		title.setTextFill(Color.web("#e00000"));
		Text name=new Text("by 软环四重奏");
		name.setId("name");
		name.setFont(new Font("黑体",30));name.setLayoutX(200);name.setLayoutY(100);*/
		Label start=new Label("START FOCUS");
		start.setId("start");
		start.setPrefSize(250, 60);start.setFont(f1);
		start.setLayoutX(780);
		start.setLayoutY(275);
		
		start.setOnMouseEntered(e ->{
			start.setTextFill(Color.GRAY);
		});
		start.setOnMouseExited(e ->{
			start.setTextFill(Color.BLACK);
		});
		start.setOnMouseClicked(e ->{
			c.clock();

		});
		
		Label exit=new Label("退出");
		exit.setId("exit");

		exit.setPrefSize(100, 60);
		exit.setLayoutX(1050);
		exit.setLayoutY(650);
		exit.setFont(f2);
		exit.setOnMouseEntered(e ->{
			exit.setTextFill(Color.GRAY);
		});
		exit.setOnMouseExited(e ->{
			exit.setTextFill(Color.BLACK);
		});
		exit.setOnMouseClicked(e ->{
			Platform.exit();
		});

		//以下是按钮。需要美化
		Group buttons=new Group();
		Button play=new Button("Play");
		play.setOnAction(e ->{
			c.play();
		});play.setLayoutY(400);
		play.setPrefSize(100, 30);

		Button chart=new Button("Chart");
		chart.setOnAction(e ->{
			c.chart();
		});chart.setLayoutY(450);
		chart.setPrefSize(100, 30);


		Button media=new Button("Media");
		media.setOnAction(e ->{
			c.media();
		});media.setLayoutY(500);
		media.setPrefSize(100, 30);

		Button clock=new Button("Clock");
		clock.setOnAction(e ->{
			c.clock();
		});clock.setLayoutY(550);
		clock.setPrefSize(100, 30);

		Button note=new Button("Note");
		note.setOnAction(e ->{
			c.note();
		});note.setLayoutY(600);
		note.setPrefSize(100, 30);
		

		buttons.getChildren().addAll(play,chart,media,clock,note);
		buttons.setLayoutX(840);
		buttons.setStyle("-fx-font-size: 20px");
		
	


		
		this.getChildren().addAll(bg,start,exit,buttons);
	}
}
