package el.game.winbystep;


import el.game.config.Constants;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/*
 * 棋盘上的格子
 */
public class Cell extends Rectangle{
	
	final static int SIZE=Constants.CELL_SIZE;
	final static int INTERVAL=Constants.CELL_INTERVAL;
	final static int ROW=Constants.ROW;
	final static int COLUMN=Constants.COLUMN;
	
	
	
	
	
	
	
	GameData game;
	int row,column;
	Paint color;
	boolean upBlocked,downBlocked,leftBlocked,rightBlocked;//上、下、左、右有没有被锁死
	public Cell(int row,int column,GameData game) {
		super(SIZE,SIZE);
		this.game=game;
		setRow(row);
		setColumn(column);
		setX(column*SIZE+(column+1)*INTERVAL);
		setY(row*SIZE+(row+1)*INTERVAL);
		//相间的颜色
		int i=row*COLUMN+column;
		if(i%2==0)
			this.color=Color.GRAY;
		else 
			this.color=Color.DARKGRAY;
		setFill(color);
		//单击的时候判断能否移动。若能则移动，不能则显示信息
		this.setOnMouseClicked(event ->{
			if(game.getAction()!=1) return;
			int _row=game.getPlayer().getRow();
			int _column=game.getPlayer().getColumn();
			boolean moveable=false;
			if(row==_row&&Math.abs(column-_column)==1) {
				if(column>_column&&!leftBlocked) moveable=true;
				if(column<_column&&!rightBlocked) moveable=true;
			}
			if(column==_column&&Math.abs(row-_row)==1) {
				if(row>_row&&!upBlocked) moveable=true;
				if(row<_row&&!downBlocked) moveable=true;
			}
			if(moveable) game.moveChess(this.getRow(), this.getColumn());
			else {
				game.setMessage(GameData.msg6);
			}
		});
		//鼠标经过时改变颜色
		this.setOnMouseEntered(event ->{
			if(game.getAction()!=1) return;
			int _row=game.getPlayer().getRow();
			int _column=game.getPlayer().getColumn();
			boolean moveable=false;
			if(row==_row&&Math.abs(column-_column)==1) {
				if(column>_column&&!leftBlocked) moveable=true;
				if(column<_column&&!rightBlocked) moveable=true;
			}
			if(column==_column&&Math.abs(row-_row)==1) {
				if(row>_row&&!upBlocked) moveable=true;
				if(row<_row&&!downBlocked) moveable=true;
			}
			if(moveable) this.setFill(Color.WHITE);;
			
		});
		//鼠标移开时颜色还原
		this.setOnMouseExited(event ->{
			//if(game.getAction()!=1) return;
			this.setFill(color);
		});
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
	public boolean isUpBlocked() {
		return upBlocked;
	}
	public void setUpBlocked(boolean upBlocked) {
		this.upBlocked = upBlocked;
	}
	public boolean isDownBlocked() {
		return downBlocked;
	}
	public void setDownBlocked(boolean downBlocked) {
		this.downBlocked = downBlocked;
	}
	public boolean isLeftBlocked() {
		return leftBlocked;
	}
	public void setLeftBlocked(boolean leftBlocked) {
		this.leftBlocked = leftBlocked;
	}
	public boolean isRightBlocked() {
		return rightBlocked;
	}
	public void setRightBlocked(boolean rightBlocked) {
		this.rightBlocked = rightBlocked;
	}
	
	
}
