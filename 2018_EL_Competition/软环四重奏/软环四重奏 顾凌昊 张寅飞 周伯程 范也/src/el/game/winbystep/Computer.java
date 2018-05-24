package el.game.winbystep;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import el.game.config.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * 电脑出棋的方法
 * @author 14237
 *
 */
public class Computer {
	//信息，用于提示
	final static String msg1="玩家一行动。";
	final static String msg2="电脑思考中。";
	final static String msg3="游戏结束。玩家一胜利";
	final static String msg4="游戏结束。玩家二胜利";
	final static String msg5="不能把木板放在此处！";
	final static String msg6="不能移动到此处！";
	final static String msg7="不能把自己或对方的路线封死！";
	
	WinByStep pane;
	GameData game;
	Chessman chess;
	int baffles=0;
	Timeline timeline=new Timeline();
	long time=0;
	PathChecker bfs;
	public Computer(GameData g,WinByStep win) {
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),event ->{
			if(bfs==null) bfs=game.getBfs();
			if(time==1) {
				timeline.stop();
				
				boolean hasMoved=false;
				if(this.chess.baffles>0) {
					int random=(int)(Math.random()*4);
					if(random<2) {
						hasMoved=this.moveBaffle();
					}
				}
				if(!hasMoved) moveChess();
				//game.changePlayer();
				return;
			}
			time++;
				
		}));
		this.game=g;
		this.pane=win;
		chess=game.getChess2();
		
	}
	
	/**
	 * 视情况移动
	 */
	public void move() {
		if(game.isOver()) return;
		baffles=chess.baffles;
		timeline.play();

	}
	
	/**
	 * 电脑移动棋子的方法
	 * 假设将棋子向四个方向（如果可以）移动，分别计算最小步数
	 * 朝最小步数最小方向移动
	 * 若一个方向不能到达（-1）则视其为999步
	 */
	public void moveChess() {
		int row=chess.getRow();
		int column=chess.getColumn();
		int p_up=-1,p_down=-1,p_left =-1,p_right=-1;
		Cell c=game.getCellArray()[row][column];
		if(row>0&&!c.upBlocked) {
			chess.setLocation(row-1, column);
			p_up=game.getBfs().searchPath(chess);
		};
		if(p_up==-1) p_up=999;
		
		if(column>0&&!c.leftBlocked) {
			chess.setLocation(row, column-1);
			p_left=game.getBfs().searchPath(chess);
		}
		if(p_left==-1) p_left=999;
		
		if(column<Constants.ROW-1&&!c.downBlocked) {
			chess.setLocation(row+1, column);
			p_down=game.getBfs().searchPath(chess);
		}
		if(p_down==-1) p_down=999;
		
		if(column<Constants.COLUMN-1&&!c.rightBlocked) {
			chess.setLocation(row, column+1);
			p_right=game.getBfs().searchPath(chess);
		}
		if(p_right==-1) p_right=999;
		
		int min=Math.min(Math.min(p_left, p_right),Math.min(p_down, p_up));
		if(min==p_down) {
			game.moveChess(row+1, column);
		}else if(min==p_left) {
			game.moveChess(row, column-1);
		}else if(min==p_right) {
			game.moveChess(row, column+1);
		}else {
			game.moveChess(row-1, column);
		}
	
	}
	
	public boolean moveBaffle() {
		Baffle b=this.getEffectiveBaffle();
		if(b!=null) {
			System.out.println("放了一块木板"+checkBaffleEffect(b));
			game.addBaffle(b);
			return true;
		}
		return false;
	
	}
	
	public int checkBaffleEffect(Baffle b) {
		
		if(!game.canSetBaffle(b)) return 0;
		
		
		bfs=game.getBfs();
		Chessman player=game.getChess1();
		int origin=bfs.searchPath(player);

		game.testAddBaffle(b);
		int after=bfs.searchPath(player);
		game.testRemoveBaffle(b);
	
		return after-origin;
	}
	
	public Baffle getEffectiveBaffle() {
		Chessman player=game.getChess1();
		int row=player.getRow();int column=player.getColumn();
		if(!(1<row&&row<Constants.ROW-2&&1<column&&column<Constants.COLUMN-2)) return null;
		
		Baffle[][] b_V=game.getBaffleArray_vertical();
		Baffle[][] b_H=game.getBaffleArray_horizont();
		for(int r=1;r<Constants.ROW-2;r++) {
			for(int c=1;c<Constants.COLUMN-2;c++) {
				Baffle bv=b_V[r][c];
				if(checkBaffleEffect(bv)>0) return bv;
				Baffle bh=b_H[r][c];
				if(checkBaffleEffect(bh)>0) return bh;
			}
		}
		return null;
	}
}
