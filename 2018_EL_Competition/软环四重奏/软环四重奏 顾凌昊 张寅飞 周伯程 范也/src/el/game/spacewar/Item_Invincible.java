package el.game.spacewar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Item_Invincible extends Item{
	
	public Item_Invincible(SpaceWar space, Image img, double size, double x, double y) {
		super(space, img, size, x, y);
		this.effectTime=200;
	}
	

	@Override
	public void takeEffect() {
		super.takeEffect();
		if(owner==null||!owner.isAlive()) return;
		Circle c=new Circle();
		c.setLayoutX(owner.getSize()/2);
		c.setLayoutY(owner.getSize()/2);
		c.setRadius(size/2);
		c.setFill(Color.ORANGE);
		c.setOpacity(0.45);
		owner.setInvincible(true);
		owner.addCircle(c);
		
		timeline=new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.016),event ->{
			if(this.time<this.effectTime) {
				time++;
				if(!owner.isAlive()) {
					time=effectTime;
				}
			}else {
				owner.setInvincible(false);
				owner.removeCircle(c);
				timeline.stop();
			}
		}) );
		timeline.play();
	}
	
	

}
