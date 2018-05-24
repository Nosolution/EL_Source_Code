package el.game.spacewar;

import java.util.ArrayList;

import el.game.config.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * 粒子特效
 * @author 14237
 *
 */
public class ParticleEffect extends Pane{
	
	final Image particle_orange=new Image(this.getClass().getResource("pics/particle_orange.gif").toExternalForm(),5,5, true, true);
	
	ArrayList<Explosion> explosions=new ArrayList<Explosion>();
	
	public ParticleEffect() {
		this.setPrefWidth(Constants.GAMEFRAME_W);
		this.setPrefHeight(Constants.GAMEFRAME_H);
		
	}
	
	
	public Pane createExplosion(Ship ship) {
		Explosion explo=null;
		
		double x=ship.getTranslateX()+Constants.SHIP_SIZE/2;
		double y=ship.getTranslateY()+Constants.SHIP_SIZE/2;
		double range=Constants.SHIP_SIZE/2;
		int number=40;
		double speed=13;
		long time=80;
		
		explo=new Explosion(x,y,range,number,speed,time);
		explosions.add(explo);
		this.getChildren().add(explo);
		return explo;
	}
	
	public Pane createExplosion(Item item) {
		Explosion explo=null;
		
		double x=item.getTranslateX()+item.size/2;
		double y=item.getTranslateY()+item.size/2;
		double range=item.size/2;
		int number=20;
		double speed=13;
		long time=40;
		
		explo=new Explosion(x,y,range,number,speed,time);
		explosions.add(explo);
		this.getChildren().add(explo);
		return explo;
	}
	
	public void update() {
		if(explosions.isEmpty()) {
			return;
		}
		for(Explosion e:explosions) {
			if(e.over) {
				this.getChildren().remove(e);
				explosions.remove(e);
				return;
			}
			e.update();
		}
	}
	
	public boolean haveExplosion() {
		return !explosions.isEmpty();
	}
	
	
	
	
	/**
	 * 爆炸类
	 * @author 14237
	 *
	 */
	class Explosion extends Pane{
		Particle[] particles;
		boolean over=false;
		int particlenumber;
		
		public Explosion() {}
		
		public Explosion(double x,double y,double range,int numbers,double speed,long time) {
			
			this.setPrefWidth(Constants.GAMEFRAME_W);
			this.setPrefHeight(Constants.GAMEFRAME_H);
			
			
			this.particlenumber=numbers;
			particles=new Particle[particlenumber];
			for(int i=0;i<numbers;i++) {
				double dx=Math.random()*range-range/2;
				double dy=Math.random()*range-range/2;
				double change1=Math.random()+0.5;
				double change2=Math.random()+0.5;
				
				Particle p=new Particle(dx+x,dy+y,1,Math.random()*360,particle_orange,(long)(time*change1),speed*change2);
				particles[i]=p;
				this.getChildren().add(p);
			}
			
		}
		
		public void update() {
			if(over) return;
			for(int i=0;i<particlenumber;i++) {
				Particle p=particles[i];
				if(p==null) continue;
				if(p.isAlive()) {
					p.update();
				}else {
					//this.getChildren().remove(i);
					particles[i]=null;
					continue;
				}
			}
			checkOver();
		}
		
		
		public void checkOver() {
			for(Particle p:particles) {
				if(p==null) continue;
				if(p.isAlive()) return;
			}
			over=true;
		}
	}
	
	/**
	 * 粒子类
	 * @author 14237
	 *
	 */
	class Particle extends Pane{
		Image img;
		boolean alive=true;
		long lifetime,time;
		double opaque=0;
		double speed,maxspeed;
		double degree;//0-360
		int size;
		
		public Particle() {}
		
		public Particle(double x,double y,int size,double direction,Image img,long lifetime,double speed) {
			this.img=img;
			this.getChildren().add(new ImageView(img));
			this.size=size;
			this.degree=Math.toDegrees(direction);
			this.setTranslateX(x);
			this.setTranslateY(y);
			this.speed=speed;
			this.maxspeed=speed;
			this.time=0;
			this.lifetime=lifetime;
		}
		
		public void update() {
			if(!alive) return;
			if(speed<=0) {
				this.setOpacity(0);
				this.setAlive(false);
				return;
			}
			this.setOpacity(opaque);
			opaque=1-1f/(float)lifetime*(float)time;
			
			this.setTranslateX(this.getTranslateX()+this.speed*Math.cos(degree));
			this.setTranslateY(this.getTranslateY()+this.speed*Math.sin(degree));
			speed=Math.min(speed*0.95,speed-maxspeed/lifetime);
			
			if(time++>lifetime) this.alive=false;
		}

		public boolean isAlive() {
			return alive;
		}

		public void setAlive(boolean alive) {
			this.alive = alive;
		}
		
		
	}
	
	
}
