package el.game._ui;

import el._main.Controller;
import el.game.config.Constants;
import el.game.spacewar.SpaceWar;
import el.game.winbystep.WinByStep;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game_Main extends AnchorPane{
	private final static int width=Constants.GAMEFRAME_W;
	private final static int height=Constants.GAMEFRAME_H;
	private Stage stage;
	
	private Controller control;
	
	public Game_Main(Controller c) {
		control=c;
		this.getChildren().add(createTitle());
	}
	
	public Pane createTitle() {
		Pane root=new Pane();
		ImageView gameBackground=new ImageView(new Image("el/game/_ui/resource 3.png"));
		
		
		Button b1,b2,b3;
		b1=new Button("太空大战");
		b1.setOnAction(event ->{
			Pane p1=new SpaceWar();
			p1.setLayoutX(1280/2-400);p1.setLayoutY(20);
			p1.getChildren().add(backButton());
			this.getChildren().setAll(p1);
		});
		b1.setLayoutX(0);
		b1.setId("b1");
		b2=new Button("步步为赢");
		b2.setOnAction(event ->{
			Pane p2=new WinByStep();
			p2.setLayoutX(1280/2-250);p2.setLayoutY(20);
			
			p2.getChildren().add(backButton());
			this.getChildren().setAll(p2);
		});
		b2.setLayoutX(100);
		b3=new Button("退出游戏");
		b3.setOnAction(event ->{
			control.toTitle();
		});
		b3.setLayoutX(200);
		
		root.getChildren().addAll(gameBackground,b1,b2,b3);
		
		return root;
	}
	
	
	public Button backButton() {
		Button exit=new Button("返回主界面");
		exit.setOnAction(event ->{
			this.getChildren().setAll(createTitle());
		});
		return exit;
	}

}
