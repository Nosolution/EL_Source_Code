package el.media._ui;

import el._main.Controller;
import el.media.player.SimpleMediaPlayer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/** 测试文件
* */
public class Media_Main extends AnchorPane {

	Controller control;
	
	public Media_Main(Controller c) {
		control=c;
		Group root = new Group();
        BorderPane pane = new BorderPane();

        root.getChildren().add(pane);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        pane.setBottom(hbox);
       
       

        //测试嵌入式调用
        //注意地址！！！！
        SimpleMediaPlayer player = SimpleMediaPlayer.newInstance(getClass().getClassLoader().getResource("el/media/res/test02.mp4").toString());
        pane.setCenter(player);
        BorderPane.setAlignment(player,Pos.CENTER);
        pane.setLayoutX(170);
        pane.setLayoutY(200);
        
        Button exit=new Button("返回主界面");
        exit.setLayoutY(630);
        exit.setLayoutX(600);
        exit.setPrefSize(100, 40);
        hbox.getChildren().add(exit);
        
		exit.setOnAction(event ->{
			player.getController().getMediaPlayer().stop();
			c.toTitle();
		});
		this.getChildren().add(new ImageView(new Image("el/media/_ui/resource 7.png")));
		this.getChildren().addAll(root,exit);

	}
}
