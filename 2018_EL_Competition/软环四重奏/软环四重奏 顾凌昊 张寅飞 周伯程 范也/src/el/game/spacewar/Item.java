package el.game.spacewar;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Item extends Pane{
	
	Image img;
	double size;
	boolean alive=true;
	SpaceWar space;
	long effectTime,time=0;
	Ship owner=null,target=null;
	Timeline timeline;
	
	public Item(SpaceWar space,Image img,double size,double x,double y) {
		this.space=space;
		this.size=size;
		this.setPrefSize(size, size);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.getChildren().add(new ImageView(img));
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Ship getOwner() {
		return owner;
	}

	public void setOwner(Ship owner) {
		this.owner = owner;
	}

	public Ship getTarget() {
		return target;
	}

	public void setTarget(Ship target) {
		this.target = target;
	}

	public void takeEffect() {
		this.setVisible(false);
		space.particleManager.createExplosion(this);
	}
	
	
}
