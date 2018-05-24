package el.game.spacewar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import el.game.config.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;  
import javafx.scene.input.KeyEvent;  
/**
 * 太空大战游戏的pane
 * 游戏介绍：玩家操控一艘小型飞船，在宇宙中与敌人作战。左右键控制方向，前后键移动，空格键射击。
 * 每个回合玩家和电脑各有两发子弹，子弹均消耗完后回合结束，地图刷新，位置改变。五局三胜。
 * 宇宙中有以下障碍物：
 * *弹性墙壁，飞船上去会被弹回来，子弹会折射
 * 宇宙中有以下道具：
 * *瞄准镜，让你能够看清子弹反弹的路线，有效时间一回合
 * *补给箱，让你的子弹数加一
 * *散射场，子弹击中会分成两个，飞船碰撞会被摧毁
 * 
 * @author 顾凌昊
 *
 */
public class SpaceWar extends Pane{
	Ship player,enemy;
	Timeline time=new Timeline();
	Group bullets=new Group();
	Group items=new Group();
	List<Bullet> bulletList=new CopyOnWriteArrayList<Bullet>();
	List<Item> itemList=new CopyOnWriteArrayList<Item>();
	
	int pointP,pointE;
	Text pointPText,pointEText;
	Starfield starfield;
	ParticleEffect particleManager=new ParticleEffect();
	EnemyAI ai;
	
	
	
	boolean gameover=false;
	
	long waittime=Constants.ROUND_INTERVAL;
	
	
	Font f=Font.loadFont(getClass().getResourceAsStream("fonts/cod_font.ttf"),25);
	
	GraphicsContext gc;
	
	
	Group background=new Group();
	Group gameitems=new Group();
	
	public SpaceWar() {
		super();
	
		Canvas canvas=new Canvas(Constants.GAMEFRAME_W,Constants.GAMEFRAME_H);
		gc=canvas.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		
		this.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) {
			requestFocus();
			switch(event.getCode()) {
			case LEFT:
				player.setTurning(Constants.TURN_LEFT);
				player.setvAngular(player.getvAngularMax());
				break;
			case RIGHT:
				player.setTurning(Constants.TURN_RIGHT);
				player.setvAngular(player.getvAngularMax());
				break;
			case UP:
			//	System.out.println("UP");
				player.setOnward(true);
				player.setvLine(player.getvLineMax());
			//	System.out.println(player.isOnward()+"        "+player.getvLine()+"        "+player.getvLineMax());
				break;
			case SPACE:
				if(!player.isAlive()) return;
				System.out.println("射击");
				if(player.isShootable()) {
					Bullet b=player.shoot(new Image(getClass()
			                .getResource("pics/playerbullet.gif").toExternalForm()),12, 10);
					b.setTeam(Constants.PLAYER);
					bullets.getChildren().addAll(b);
					bulletList.add(b);
					
				}
	
				break;
			default:
				break;
			
			}
		}
		
		});
		
		this.setOnKeyReleased(event ->{
			switch(event.getCode()) {
			case LEFT:
				player.setTurning(Constants.TURN_NONE);
				break;
			case RIGHT:
				player.setTurning(Constants.TURN_NONE);
				break;
			case UP:
				player.setOnward(false);
				break;
			default:
				break;
			}
		});
		
		pointEText=new Text("COMPUTER:"+pointE);
		pointEText.setLayoutX(Constants.GAMEFRAME_W-230);
		pointEText.setLayoutY(40);
		pointEText.setFont(f);
		pointEText.setStroke(Color.RED);
		
		pointPText=new Text("PLAYER:"+pointP);
		pointPText.setLayoutX(40);
		pointPText.setLayoutY(40);
		pointPText.setFont(f);
		pointPText.setStroke(Color.BLUE);
		
		this.starfield=new Starfield();
		background.getChildren().addAll(starfield,pointEText,pointPText);
		
		
		this.getChildren().addAll(background,gameitems,particleManager);
		this.setPrefSize(Constants.GAMEFRAME_W, Constants.GAMEFRAME_H);
		newRound();
		startTimeline();
	}
	
	public void startTimeline() {
		
		
		time.setCycleCount(Timeline.INDEFINITE);
		time.getKeyFrames().setAll(getBulletKeyFrame(),getShipKeyFrame(),getItemKeyFrame(),getRoundKeyFrame());
		time.play();
		
	
	}
	
	/**
	 * 获得检查自机和敌机的时间线
	 * @return
	 */
	public KeyFrame getBulletKeyFrame() {
		KeyFrame key=new KeyFrame(Duration.seconds(0.016) ,event ->{
			for(Bullet b:bulletList) {
				if(b.isAlive()) {
					b.move();
					if(enemy.isAlive()&&player.isAlive()) {
						if(b.getTeam()==Constants.COMPUTER) b.hit(player);
						else b.hit(enemy);
					}
					b.checkAlive();
				}else {
					//b.setVisible(false);
					b.setTranslateX(-100);
					bullets.getChildren().remove(b);
					bulletList.remove(b);
				}
			}
		});
		return key;
	}
	
	
	public KeyFrame getItemKeyFrame() {
		
		KeyFrame key=new KeyFrame(Duration.seconds(0.016),event ->{
			
			for(Item b:itemList) {
				if(b.isAlive()) {
					if(enemy.isAlive()&&player.isAlive()) {
						enemy.crash(b);
						player.crash(b);
					}
				}else {
					//b.setVisible(false);
					b.setTranslateX(-100);
					items.getChildren().remove(b);
					itemList.remove(b);
				}
			
			
			}
		});
		return key;
	}
	
	
	
	
	public KeyFrame getShipKeyFrame() {
		KeyFrame key=new KeyFrame(Duration.seconds(0.016),event ->{
			//if(gameover) return;
			if(player.isAlive()) {
				player.turn();
				player.forward();
				player.shiftdown();
				
			}else {
				if(waittime==Constants.ROUND_INTERVAL) {
					this.pointE++;
					this.pointEText.setText("COMPUTER:"+pointE);
					player.setVisible(false);
					gameover=true;
					
					particleManager.createExplosion(player);
				}
			}
			if(enemy.isAlive()) {
				ai.control();
				Bullet b=ai.shoot();
				if(b!=null) {
					b.setTeam(Constants.COMPUTER);
					bullets.getChildren().addAll(b);
					bulletList.add(b);
					
				}
				enemy.turn();
				enemy.forward();
				enemy.shiftdown();
			}else {
				if(waittime==Constants.ROUND_INTERVAL) {
					enemy.setVisible(false);
					this.pointP++;
					this.pointPText.setText("PLAYER:"+pointP);
					gameover=true;
					
					particleManager.createExplosion(enemy);
				}
			}
			
		});
		return key;
	}
	
	public KeyFrame getRoundKeyFrame() {
		KeyFrame key=new KeyFrame(Duration.seconds(0.016),event ->{
			
			starfield.update();
			particleManager.update();
			
			if(gameover) {
				waittime--;
				if(waittime==0) {
					System.out.println("新一局开始！");
					newRound();
					gameover=false;
					waittime=Constants.ROUND_INTERVAL;
				}
			}
		});
		return key;
	}
	
	public void newRound() {
		player=new Ship(new Image(getClass()
                .getResource("pics/player.png").toExternalForm()),Constants.SHIP_SIZE,
				(int)(Math.random()*300+50),(int)(Math.random()*450+50),6,6);
		
		enemy=new Ship(new Image(getClass()
                .getResource("pics/enemy.png").toExternalForm()),Constants.SHIP_SIZE,
				(int)(Math.random()*300+450),(int)(Math.random()*450+50),5,4);
		enemy.setFirerate(500);
		ai=new EnemyAI(enemy);
		ai.setTarget(player);
		bullets=new Group();
		bulletList=new CopyOnWriteArrayList<Bullet>();
		items=new Group();
		itemList=new CopyOnWriteArrayList<Item>();
		items=new Group();
		
		Item_Invincible ii=new Item_Invincible(this, new Image(getClass()
                .getResource("pics/FlashIcon.gif").toExternalForm()), 60, Math.random()*600,Math.random()*400);
		items.getChildren().add(ii);
		itemList.add(ii);
		
		
		
		gameitems.getChildren().setAll(bullets,items,player,enemy);
		
	}
	
	
	
}
