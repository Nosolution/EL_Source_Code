package el.game.winbystep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 宽度优先搜索算法
 * @author 14237
 *
 */
public class PathChecker {
	public final static int COLUMN=9;
	public final static int ROW=9;
	
	GameData game;
	Chessman chess;
	Cell[][] cellArray;
	List<Cell> finish,visiting;
	int goalRow;
	
	public PathChecker(GameData game) {
		this.game=game;
		
	}
	
	public int searchPath(Chessman chess) {
		int path=0;
		this.chess=chess;
		goalRow=chess.getGoalRow();
	
		finish=new CopyOnWriteArrayList<Cell>();
		visiting=new CopyOnWriteArrayList<Cell>();
		int row=chess.getRow();
		int column=chess.getColumn();
		Cell now=game.getCellArray()[row][column];//加载棋盘数据
		
		visiting.add(now);//将出发点加入搜索队列
		
		while(!visiting.isEmpty()) {//当还有点被搜索时
			for(Cell c:visiting) {
				if(atGoal(c)) {//如果某点是终点，返回最小步数
					return path;
				}else {
					CopyOnWriteArrayList<Cell> adjacent=getAdjacentCells(c);
					if(!adjacent.isEmpty()) {//将可走的点加入搜索队列
						visiting.addAll(adjacent);
					}
				}
				//移出搜索队列，加入完成队列
				visiting.remove(c);
				finish.add(c);
					
			}
			path++;
		}
		
		return -1;
	}
	
	private CopyOnWriteArrayList<Cell> getAdjacentCells(Cell c){
		CopyOnWriteArrayList<Cell> cells=new CopyOnWriteArrayList<Cell>();
		cellArray=game.getCellArray();
		int row=c.getRow();
		int column=c.getColumn();
		Cell c_up,c_down,c_left,c_right;
		
		if(row>0&&!c.upBlocked) { 
			c_up=cellArray[row-1][column];
			if(!finish.contains(c_up)&&!visiting.contains(c_up)) cells.add(c_up);
		}
		if(column>0&&!c.leftBlocked) {
			c_left=cellArray[row][column-1];
			if(!finish.contains(c_left)&&!visiting.contains(c_left)) cells.add(c_left);
		}
		if(row<ROW-1&&!c.downBlocked) {
			c_down=cellArray[row+1][column];
			if(!finish.contains(c_down)&&!visiting.contains(c_down)) cells.add(c_down);
		} 
		if(column<COLUMN-1&&!c.rightBlocked) {
			c_right=cellArray[row][column+1];
			if(!finish.contains(c_right)&&!visiting.contains(c_right)) cells.add(c_right);
		}
		return cells;
	}
	
	private boolean atGoal(Cell c) {
		int row=c.getRow();
		return row==goalRow;
	}
	

}
