package el.game.spacewar;

import el.game.utils.DirectionUtil;
import javafx.scene.image.Image;

public class EnemyAI {
	final static long FLY_TIME=100;
	final static long PAUSE_TIME=50;
	final static long SEARCH_TIME=250;
	final static int FLY=2;
	final static int PAUSE=1;
	final static int SEARCH=3;
	
	int dirCount=1;
	
	Ship ship,target=null;
	boolean searching,flying;
	
	long time=PAUSE_TIME;
	int status=PAUSE;
	
	public EnemyAI(Ship ship) {
		this.ship=ship;
		this.status=SEARCH;
	}
	
	
	
	public Ship getTarget() {
		return target;
	}



	public void setTarget(Ship target) {
		this.target = target;
	}



	public void control() {
		if(!ship.isMoveable()) return;
		time--;
		switch(status) {
		case FLY:
			//System.out.println("flying");
			this.ship.setOnward(true);
			ship.setvLine(ship.getvLineMax());
			if(time==0) {
				status=(int)(Math.random()*3)+1;
				if(status==PAUSE) time=PAUSE_TIME;
				else if(status==FLY) time=FLY_TIME;
				else time=SEARCH_TIME;
			}
			break;
		case PAUSE:
			//System.out.println("pausing");
			
			this.ship.setOnward(false);
			
			if(time==0) {
				dirCount=1-dirCount;
				status=(int)(Math.random()*3)+1;
				if(status==PAUSE) time=PAUSE_TIME;
				else if(status==FLY) time=FLY_TIME;
				else time=SEARCH_TIME;
			}
			
			if(time%3!=1) return;
			
			if(dirCount==1) this.ship.setRotate(ship.getRotate()+1);
			else this.ship.setRotate(ship.getRotate()-1);
			break;
		case SEARCH:
			
			this.ship.setOnward(true);
			ship.setvLine(ship.getvLineMax());
			
			if(time==0) {
				status=(int)(Math.random()*3)+1;
				if(status==PAUSE) time=PAUSE_TIME;
				else if(status==FLY) time=FLY_TIME;
				else time=SEARCH_TIME;
			}
			
			if(target==null||!target.isAlive()||this.time/20%2==1) return;
			ship.setRotate(DirectionUtil.getDirection(ship.getTranslateX(), ship.getTranslateY(), target.getTranslateX(), target.getTranslateY()));
			break;
		}
		
	}
	
	public Bullet shoot() {
		if(ship.isShootable()) {
			Bullet b=ship.shoot(new Image(getClass()
	                .getResource("pics/playerbullet.gif").toExternalForm()),12, 10);
			return b;
		}
		return null;
	}
}
