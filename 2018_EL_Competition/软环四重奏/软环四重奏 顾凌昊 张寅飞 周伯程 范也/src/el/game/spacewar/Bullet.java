package el.game.spacewar;

import el.game.config.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Bullet extends Parent{
	//final static long NO_DAMAGE_TIME=4;
	
	//long time=NO_DAMAGE_TIME;
	private double rotate;
	private double speed;
	private ImageView img;
	public int x,y;
	public int size;
	boolean alive=true;
	boolean damagable=true;
	
	int team;
	
	public Bullet() {}
	
	public Bullet(Ship ship,Image img,int speed,int size) {
		this.size=size;
		
		this.img=new ImageView(img);
		this.img.setLayoutX(0);
		this.img.setLayoutY(0);
		this.img.setFitWidth(size);
		this.img.setFitHeight(size);
		getChildren().add(this.img);
		
		this.setTranslateX(ship.getTranslateX()+Constants.SHIP_SIZE/2-size/2);
		this.setTranslateY(ship.getTranslateY()+Constants.SHIP_SIZE/2-size/2);
		rotate=ship.getRotate();
		this.setRotate(rotate);
		
		this.speed=speed;
		
		
	}
	
	
	public void move() {
		//if(time>0) time--;
		//if(time==0) this.damagable=true;
	//	System.out.println("is running");
		double radians=Math.toRadians(rotate);
		double dy=-Math.cos(radians)*speed;
		double dx=Math.sin(radians)*speed;
		this.setTranslateX(this.getTranslateX()+dx);
		this.setTranslateY(this.getTranslateY()+dy);
		
	}
	public void checkAlive() {
		if(this.getTranslateX()<-20||this.getTranslateX()>=Constants.GAMEFRAME_W
				||this.getTranslateY()<-20||this.getTranslateY()>=Constants.GAMEFRAME_H) {
			this.setAlive(false);
 	 	}
	}
	
	public boolean checkHit(Ship ship) {
		if(!this.isAlive()||!this.damagable) return false;
		boolean hit=false;
		double cX=this.getTranslateX()+size/2;
		double cY=this.getTranslateY()+size/2;
		if(cX>ship.getTranslateX()&&cX<ship.getTranslateX()+ship.getSize()
				&&cY>ship.getTranslateY()&&cY<ship.getTranslateY()+ship.getSize()) {
			hit=true;
		}
		
		
		return hit;
	}
	
	
	public void hit(Ship ship) {
		if(checkHit(ship)) {
			this.setAlive(false);
			if(!ship.invincible) ship.setAlive(false);
		}
	}

	
	
	
	
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	
}
