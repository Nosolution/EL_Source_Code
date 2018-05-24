package el.game.winbystep;

import el.game.config.Constants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 进入步步为赢游戏的main方法
 * 版本一：手操12p
 * @author 14237
 *
 */
public class WinByStep extends Pane{
	Pane bg;
	Pane root;
	GameData game;

	final static int GAME_W=500;
	final static int GAME_H=500;
	final static int FRAME_W=1600;
	final static int FRAME_H=800;
		
	public WinByStep() {
		createContent();
		this.getChildren().addAll(bg,root);
		startGame();
	}

	public void startGame() {
		game=new GameData(this);
		root.getChildren().setAll(game.newGame());
	}
	
	private void stopGame() {
		root.getChildren().removeAll();
	}
	
	private void gameOver() {
		stopGame();
		//startGame();
		
	}
	
	/**
	 * 建立子group
	 */
	public void createContent() {
		root=new Pane();
		root.setPrefWidth(FRAME_W);
		root.setPrefHeight(FRAME_H);
	    
		bg=new Pane();
		bg.setPrefWidth(FRAME_W);
		bg.setPrefHeight(FRAME_H);
		Rectangle ground=new Rectangle(30,70,GAME_W+40,GAME_H+20);
		ground.setFill(Color.WHEAT);
		ground.setArcHeight(25);
		ground.setArcWidth(25);
		bg.getChildren().addAll(ground,root);
		
	}
	

}
