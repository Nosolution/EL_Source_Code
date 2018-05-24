package el.game.winbystep;

import java.util.Optional;

import el.game.config.Constants;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameData{
	
	final static int SIZE=Constants.CELL_SIZE;
	final static int INTERVAL=Constants.CELL_INTERVAL;
	final static int ROW=Constants.ROW;
	final static int COLUMN=Constants.COLUMN;
	final static int HORIZONTAL=Constants.HORIZONTAL;
	final static int VERTICAL=Constants.VERTICAL;
	final static int BAFFLE_LONG=Constants.CELL_SIZE*2+Constants.CELL_INTERVAL;
	final static int BAFFLE_SHORT=Constants.CELL_INTERVAL;
	
	
	
	
	private WinByStep main;
	
	//信息，用于提示
	final static String msg1="请你行动。";
	final static String msg2="电脑思考中。";
	final static String msg3="游戏结束。玩家胜利";
	final static String msg4="游戏结束。电脑胜利";
	final static String msg5="不能把木板放在此处！";
	final static String msg6="不能移动到此处！";
	final static String msg7="不能把自己或对方的路线封死！";
	public String message=msg1;
	//文本框
	private Text text=new Text(message);
		
	//电脑AI
	Computer computer;
	//棋子
	private Chessman chess1,chess2;
	private int action=1;//控制哪个chess移动
	private Group chess=new Group();
	
	//棋盘格
	private Group cells=new Group();
	private Cell[][] cellArray=new Cell[ROW][COLUMN];
	
	//障碍
	private Group baffles=new Group();
	private Baffle[][] baffleArray_horizont=new Baffle[ROW][COLUMN-1];
	private Baffle[][] baffleArray_vertical=new Baffle[ROW-1][COLUMN];

	//检测有没有封死
	private PathChecker bfs;
	
	//是否结束
	private boolean over=false;
	
	
	
	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public Cell[][] getCellArray() {
		return cellArray;
	}

	public void setCellArray(Cell[][] cellArray) {
		this.cellArray = cellArray;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Node newGame() {
		
		chess=new Group();
		baffles=new Group();
		cells=new Group();

		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				Cell cell=new Cell(i,j,this);
				cellArray[i][j]=cell;
				cells.getChildren().add(cell);
			}
		}
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				if(j<COLUMN-1&&i!=0) {
					Baffle b=new Baffle(i,j,HORIZONTAL,this);
					baffleArray_horizont[i][j]=b;
					baffles.getChildren().add(b);
				}
				if(i<ROW-1&&j!=0) {
					Baffle b=new Baffle(i,j,VERTICAL,this);
					baffleArray_vertical[i][j]=b;
					baffles.getChildren().add(b);
				}
			}
		}
		chess1=new Chessman(1);
		chess2=new Chessman(2);
		chess.getChildren().addAll(chess1,chess2);
		
		chess.setLayoutX(50);
		chess.setLayoutY(80);
		
		cells.setLayoutX(50);
		cells.setLayoutY(80);
		baffles.setLayoutX(50);
		baffles.setLayoutY(80);
		
		text=new Text(message+"剩余木板数："+getPlayer().baffles);
		text.setLayoutX(50);
		text.setLayoutY(45);
		text.setFont(Font.font("微软雅黑",20));
		text.setStroke(Color.CADETBLUE);
		
		
		Pane pane=new Pane();
		pane.getChildren().setAll(getCells(),getBaffles(),getChess(),getText());
		
		computer=new Computer(this,main);
		
		bfs=new PathChecker(this);
		setOver(false);
		return pane;
	}
	
	public Chessman getChess1() {
		return chess1;
	}

	public void setChess1(Chessman chess1) {
		this.chess1 = chess1;
	}

	public Chessman getChess2() {
		return chess2;
	}

	public void setChess2(Chessman chess2) {
		this.chess2 = chess2;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public Group getChess() {
		return chess;
	}

	public void setChess(Group chess) {
		this.chess = chess;
	}

	/*
	 * 移动棋子的方法
	 */
	public void moveChess(int row,int column) {
		
		if(action==1) 
			chess1.moveTo(row, column);
		else 
			chess2.moveTo(row, column);
		checkWin();
		changePlayer();
		
	}
	/*
	 * 换边
	 */
	public void changePlayer() {
		if(action==1) {
			action=2;
			computer.move();
			text.setText(msg2+"剩余木板数："+getPlayer().baffles);
		}else {
			action=1;
			getText().setText(msg1+"剩余木板数："+getPlayer().baffles);
		}
	}
	//获得应该行动的棋子
	public Chessman getPlayer() {
		if(action==1) return chess1;
		return chess2;
	}
	
	/*
	 * 放木板
	 */
	public void addBaffle(Baffle b) {
		if(!canSetBaffle(b)) {
			this.getText().setText(msg5);
			return;
		};
		testAddBaffle(b);
		b.setFixed(true);
		b.setOpacity(100);
		b.setEnabled(false);
		getPlayer().baffles--;
		changePlayer();
		
	}
	
	public boolean canSetBaffle(Baffle b) {
		
		if(getPlayer().baffles<=0||!b.enabled||b.fixed) return false;
		boolean can=true;
		testAddBaffle(b);
		if(bfs.searchPath(this.getChess1())==-1||bfs.searchPath(this.getChess2())==-1) {
			can=false;
		}
		testRemoveBaffle(b);
		return can;
	}
	
	public void testAddBaffle(Baffle b) {
		int row=b.getRow();
		int column=b.getColumn();
		if(b.getDirection()==HORIZONTAL) {
			if(column>0) {
				baffleArray_horizont[row][column-1].setEnabled(false);
			}
			if(column<COLUMN-2) {
				baffleArray_horizont[row][column+1].setEnabled(false);
			}
			if(row>0&&column<COLUMN-1) {
				baffleArray_vertical[row-1][column+1].setEnabled(false);
				
			}
			
			cellArray[row][column].setUpBlocked(true);
			cellArray[row][column+1].setUpBlocked(true);
			cellArray[row-1][column].setDownBlocked(true);
			cellArray[row-1][column+1].setDownBlocked(true);
			
		}else {
			if(row>0) {
				baffleArray_vertical[row-1][column].setEnabled(false);
			}
			if(row<ROW-2) {
				baffleArray_vertical[row+1][column].setEnabled(false);
			}
			if(row<ROW-1&&column>0) {
				baffleArray_horizont[row+1][column-1].setEnabled(false);
				
			}
			cellArray[row][column].setLeftBlocked(true);
			cellArray[row+1][column].setLeftBlocked(true);
			cellArray[row][column-1].setRightBlocked(true);
			cellArray[row+1][column-1].setRightBlocked(true);
			
		}
		b.setEnabled(false);
		b.setFixed(true);
	}
	
	
	//移除某个位置的障碍物
	public void testRemoveBaffle(Baffle b) {
		int row=b.getRow();
		int column=b.getColumn();
		if(b.getDirection()==HORIZONTAL) {
			if(column>0) {
				baffleArray_horizont[row][column-1].setEnabled(true);
				
			}
			if(column<COLUMN-2) {
				baffleArray_horizont[row][column+1].setEnabled(true);
			}
			if(row>0&&column<COLUMN-2) {
				baffleArray_vertical[row-1][column+1].setEnabled(true);
				
			}

			cellArray[row][column].setUpBlocked(false);
			cellArray[row][column+1].setUpBlocked(false);
			cellArray[row-1][column].setDownBlocked(false);
			cellArray[row-1][column+1].setDownBlocked(false);
			
		}else {
			if(row>0) {
				baffleArray_vertical[row-1][column].setEnabled(true);
			}
			if(row<ROW-2) {
				baffleArray_vertical[row+1][column].setEnabled(true);
			}
			if(row<ROW-2&&column>0) {
				baffleArray_horizont[row+1][column-1].setEnabled(true);
				
			}
	
			cellArray[row][column].setLeftBlocked(false);
			cellArray[row+1][column].setLeftBlocked(false);
			cellArray[row][column-1].setRightBlocked(false);
			cellArray[row+1][column-1].setRightBlocked(false);
			
		}
		b.setEnabled(true);
		b.setFixed(false);
	
	}
	
	//检测是否有一方获胜
	public void checkWin() {
		if(over) return;
		if(chess1.getRow()==chess1.getGoalRow()||chess2.getRow()==chess2.getGoalRow()) {
			setOver(true);
			String msg;
			if(action==1) msg=msg3;
			else msg=msg4;
			setMessage(msg);
			
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setTitle("游戏结束");
			alert.setHeaderText(msg);
			//alert.setContentText("是否重新开始？");
			alert.setOnCloseRequest(event ->{
				main.startGame();
			});
			alert.show();
			
		}
	}
	
	public GameData(WinByStep main) {
		this.main=main;
		newGame();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		this.text.setText(message);
	}

	public Group getCells() {
		return cells;
	}

	public void setCells(Group cells) {
		this.cells = cells;
	}

	
	public Group getBaffles() {
		return baffles;
	}

	public void setBaffles(Group baffles) {
		this.baffles = baffles;
	}

	public PathChecker getBfs() {
		return bfs;
	}

	public void setBfs(PathChecker bfs) {
		this.bfs = bfs;
	}

	public Baffle[][] getBaffleArray_horizont() {
		return baffleArray_horizont;
	}

	public void setBaffleArray_horizont(Baffle[][] baffleArray_horizont) {
		this.baffleArray_horizont = baffleArray_horizont;
	}

	public Baffle[][] getBaffleArray_vertical() {
		return baffleArray_vertical;
	}

	public void setBaffleArray_vertical(Baffle[][] baffleArray_vertical) {
		this.baffleArray_vertical = baffleArray_vertical;
	}
	
	
}
