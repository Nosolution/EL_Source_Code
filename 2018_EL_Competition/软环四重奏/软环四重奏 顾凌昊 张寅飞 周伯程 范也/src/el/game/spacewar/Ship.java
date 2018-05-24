package el.game.spacewar;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import el.game.config.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;

/**
 * 太空大战飞机类
 * @author 14237
 *
 */
public class Ship extends Parent{
	
	private double vAngularMax,vLineMax,vAngular,vLine;
	private ImageView img;
	public double x,y;
	public double size;
	private boolean shootable=true;
	private boolean moveable=true;
	boolean alive=true;
	
	private long firerate=200;
	
	
	int turning=Constants.TURN_NONE;
	
	boolean onward=false;
	boolean braking=false;
	boolean invincible=false;
	
	boolean up,left;
	
	long prepareCount=Constants.SHIP_PREPARE;
	
	public Ship() {
		
	}
	public Ship(Image img,int size,int x,int y,int v,int w) {
		
		this.img=new ImageView(img);
		this.img.setLayoutX(0);
		this.img.setLayoutY(0);
		this.img.setFitWidth(size);
		this.img.setFitHeight(size);
		getChildren().add(this.img);
		
		this.size=size;
		this.setTranslateX(x);
		this.setTranslateY(y);
		
		this.vLineMax=v;
		this.vAngularMax=w;
		this.vLine=0;
		this.vAngular=0;
		
		prepareCount=Constants.SHIP_PREPARE;
		
		this.setMoveable(false);
		this.setShootable(false);

	}

	public void addImageView(Image img,double size) {
		ImageView iv=new ImageView(img);
		iv.setLayoutX(0);
		iv.setLayoutY(0);
		iv.setFitWidth(size);
		iv.setFitHeight(size);
		getChildren().add(iv);
	}
	public void addCircle(Circle c) {
		this.getChildren().add(c);
	}
	
	public void removeCircle(Circle c) {
		this.getChildren().remove(c);
	}
	
	/**
	 * 转弯的方法
	 */
	public void turn() {
		if(!this.moveable) return;
		if(getTurning()==Constants.TURN_NONE) return;
		//this.setvAngular(this.getvAngularMax());
		if(getTurning()==Constants.TURN_LEFT) this.setRotate(this.getRotate()-vAngular);
		else this.setRotate(this.getRotate()+vAngular);
	}

	/**
	 * 前进的方法
	 */
	public void forward() {
		
		if(this.prepareCount>0) {
			this.setVisible((prepareCount%Constants.SHIP_PREPARE_MOD<=Constants.SHIP_PREPARE_MOD/2)?true :false);
			if(--prepareCount==0) {
				this.setMoveable(true);
				this.setShootable(true);
			}
			return;
		}
		
		
		
		if(!this.moveable) return;
		
		if(!isOnward()&&this.vLine==0) return;
		//this.vLine=this.vLineMax;
		double cX=this.getTranslateX()+this.getSize()/2;
		double cY=this.getTranslateY()+this.getSize()/2;
		if(this.getRotate()>360) this.setRotate(this.getRotate()-360);
		if(this.getRotate()<0) this.setRotate(360-this.getRotate());
		
		if(this.getRotate()<180) left=false; 
		else left=true;
		if(this.getRotate()>90&&this.getRotate()<270) up=false;
		else up=true;
		
		
		//上下边的反弹
		if((cY<this.size/2&&up)										//上
				||(cY>Constants.GAMEFRAME_H-this.size/2&&!up)) {	//下
			this.setRotate(-this.getRotate()+540);
		}
		//左右边的反弹
		if((cX<this.size/2&&left)									//左
				||(cX>Constants.GAMEFRAME_W-this.size/2&&!left)) {	//右
			this.setRotate(-this.getRotate()+360);
		}
		double radians=Math.toRadians(this.getRotate());
		double dy=-Math.cos(radians)*vLine;
		double dx=Math.sin(radians)*vLine;
		this.setTranslateX(this.getTranslateX()+dx);
		this.setTranslateY(this.getTranslateY()+dy);
	}
	
	/**
	 * 射击的方法
	 * @param img
	 * @param speed
	 * @param size
	 * @return
	 */
	public Bullet shoot(Image img,int speed,int size) {
		if(!shootable) return null;
		new Thread() {
			
			@Override
			public void run() {
				shootable=false;
				try {
					Thread.sleep(firerate);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				shootable=true;
			}
		}.start();
		return new Bullet(this,img,speed,size);
	}
	
	public boolean checkCrash(Item i) {
		if(!i.isAlive()||!this.isAlive()) return false;
		double x=this.getTranslateX()+this.size/2;
		double y=this.getTranslateY()+this.size/2;
		double _x=i.getTranslateX()+i.size/2;
		double _y=i.getTranslateY()+i.size/2;
		double d=Math.sqrt((x-_x)*(x-_x)+(y-_y)*(y-_y));
		
		return (d<this.size/2+i.size/2);
	}
	
	public void crash(Item i) {
		if(!this.checkCrash(i)) return;
		i.setOwner(this);
		i.setAlive(false);
		i.takeEffect();
	}
	
	
	/**
	 * 刹车的方法
	 */
	public void brake() {
		
	}
	/**
	 * 减速的方法
	 */
	public void shiftdown() {
		if(isOnward()) return;
		//if(getTurning()==Constants.TURN_NONE&&this.vAngular>0) this.vAngular-=this.vAngularMax*0.2;
		if(this.vLine>0) this.vLine=Math.max(this.vLine-0.1, 0);
		//System.out.println(this.getvLine()+"");
		if(getTurning()==Constants.TURN_NONE) this.vAngular=Math.max(this.vAngular-0.1, 0);
	}
	
	public double getvAngular() {
		return vAngular;
	}
	public void setvAngular(double vAngular) {
		this.vAngular = vAngular;
	}
	
	public boolean isShootable() {
		return shootable;
	}
	
	public long getFirerate() {
		return firerate;
	}
	public void setFirerate(long firerate) {
		this.firerate = firerate;
	}
	public void setShootable(boolean shootable) {
		this.shootable = shootable;
	}
	public double getvAngularMax() {
		return vAngularMax;
	}
	public void setvAngularMax(double vAngularMax) {
		this.vAngularMax = vAngularMax;
	}
	public double getvLineMax() {
		return vLineMax;
	}
	public void setvLineMax(double vLineMax) {
		this.vLineMax = vLineMax;
	}
	public double getvLine() {
		return vLine;
	}
	public void setvLine(double vLine) {
		this.vLine = vLine;
	}
	public double getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTurning() {
		return turning;
	}
	public void setTurning(int turning) {
		this.turning = turning;
	}
	public boolean isOnward() {
		return onward;
	}
	public void setOnward(boolean onward) {
		this.onward = onward;
	}
	public boolean isBraking() {
		return braking;
	}
	public void setBraking(boolean braking) {
		this.braking = braking;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isMoveable() {
		return moveable;
	}
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	public boolean isInvincible() {
		return invincible;
	}
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	
	
}
