package el.game.winbystep;

import el.game.config.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
 * 棋子
 */
public class Chessman extends Circle{
	
	final static int SIZE=Constants.CELL_SIZE;
	final static int INTERVAL=Constants.CELL_INTERVAL;
	final static int ROW=Constants.ROW;
	final static int COLUMN=Constants.COLUMN;
	
	
	
	int player;//阵营
	int row,column;//行、列
	int goalRow;//目标
	int baffles;//剩余的木板数
	boolean win=false;
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public void setLocation(int row,int column) {
		this.setRow(row);
		this.setColumn(column);
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getGoalRow() {
		return goalRow;
	}

	public void setGoalRow(int goalRow) {
		this.goalRow = goalRow;
	}

	public void checkWin() {
		if(this.row==this.goalRow) win=true;
	}
	
	public Chessman(int player) {
		super(SIZE/2-2);
		
		if(player==1) {
			setRow(ROW-1);
			setColumn(COLUMN/2);
			setGoalRow(0);
			setFill(Color.BLACK);
			
		}else if(player==2) {
			setRow(0);
			setColumn(COLUMN/2);
			setGoalRow(ROW-1);
			setFill(Color.ALICEBLUE);
		}else {
			System.out.println("必须1或2！");
			return;
		}
		this.setCenterX(column*SIZE+(column+1)*INTERVAL+SIZE/2);
		this.setCenterY(row*SIZE+(row+1)*INTERVAL+SIZE/2);
		//this.setTranslateX(100);
		//this.setTranslateY(100);
		baffles=10;
		this.setPlayer(player);
	}
	
	public void moveTo(int row,int column) {
		setRow(row);
		setColumn(column);
		this.setCenterX(column*SIZE+(column+1)*INTERVAL+SIZE/2);
		this.setCenterY(row*SIZE+(row+1)*INTERVAL+SIZE/2);
		
	}
}
