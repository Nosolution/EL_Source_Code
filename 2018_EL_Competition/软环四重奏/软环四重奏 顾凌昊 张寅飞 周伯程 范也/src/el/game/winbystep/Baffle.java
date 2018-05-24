package el.game.winbystep;

import el.game.config.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * 木板
 */
public class Baffle extends Rectangle{
	
	final static int HORIZONTAL=Constants.HORIZONTAL;
	final static int VERTICAL=Constants.VERTICAL;
	final static int SIZE=Constants.CELL_SIZE;
	final static int INTERVAL=Constants.CELL_INTERVAL;
	final static int BAFFLE_LONG=Constants.CELL_SIZE*2+Constants.CELL_INTERVAL;
	final static int BAFFLE_SHORT=Constants.CELL_INTERVAL;
	
	
	
	
	
	
	int direction=HORIZONTAL;
	int row,column;
	boolean fixed=false;
	boolean enabled=true;
	GameData game;
	
	
	public int getDirection() {
		return direction;
	}



	public void setDirection(int direction) {
		this.direction = direction;
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



	public boolean isFixed() {
		return fixed;
	}



	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public Baffle(int row,int column,int dir,GameData data) {
		direction=dir;
		this.row=row;
		this.column=column;
		this.game=data;
		
		if(dir==HORIZONTAL) {
			setX(column*SIZE+(column+1)*INTERVAL);
			setY(row*SIZE+row*INTERVAL);
			this.setWidth(BAFFLE_LONG);
			this.setHeight(BAFFLE_SHORT);
		}else {
			setX(column*SIZE+column*INTERVAL);
			setY(row*SIZE+(row+1)*INTERVAL);
			this.setWidth(BAFFLE_SHORT);
			this.setHeight(BAFFLE_LONG);
		}
		this.setFill(Color.DARKMAGENTA);
		this.setOnMouseEntered(event ->{
			if(game.getAction()!=1) return;
			if(!enabled) return;
			if(!this.fixed) {
				this.setOpacity(100);
			}
		});
		this.setOnMouseExited(event ->{
			//if(game.getAction()!=1) return;
			if(!enabled) return;
			if(!this.fixed) {
				this.setOpacity(0);
			}else this.setOpacity(100);
		});
		this.setOnMousePressed(event ->{
			if(game.getAction()!=1) return;
			game.addBaffle(this);
		});
		
		
		this.setOpacity(0);
	}
}
