package el.game.spacewar;

import java.util.ArrayList;

import el.game.config.Constants;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * 背景星空
 * @author 14237
 *
 */
public class Starfield extends Pane{
	private int width=800,height=600;
	int starnumbers=25;
	Image star_blue=new Image(this.getClass().getResource("pics/particle_bluestar.gif").toExternalForm());
	Image star_white=new Image(this.getClass().getResource("pics/particle_whitestar.gif").toExternalForm());
	
	ArrayList<Star> whitestars=new ArrayList<Star>();
	ArrayList<Star> bluestars=new ArrayList<Star>();
	
	GraphicsContext gc;
	
	public Starfield() {
		this.setPrefSize(width, height);
		
		Rectangle black=new Rectangle(width,height);
		this.getChildren().add(black);
		
		
		for(int i=0;i<starnumbers;i++) {
			double x,y;
			x=Math.random()*width;
			y=Math.random()*height;
			Star white=new Star(x,y,star_white,0.18);
			x=Math.random()*width;
			y=Math.random()*height;
			Star blue=new Star(x,y,star_blue,0.25);
			
			whitestars.add(white);
			bluestars.add(blue);
			//System.out.println("add two stars");
			this.getChildren().addAll(white,blue);
		}
		
		update();
	}
	

	public void update() {
		if(whitestars.isEmpty()||bluestars.isEmpty()) return;
		for(Star s:whitestars) {
			s.move();
		}
		for(Star s:bluestars) {
			s.move();
		}
	}
	
	
	/**
	 * 星星的类
	 * @author 14237
	 *
	 */
	class Star extends Pane{
		double speed;
		Image img;
		
		public Star() {}
		public Star(double x,double y,Image img,double speed) {
			this.setTranslateX(x);
			this.setTranslateY(y);
			this.img=img;
			this.getChildren().add(new ImageView(img));
			this.setPrefSize(img.getWidth(), img.getHeight());
			this.speed=speed;
		}
		
		public void move() {
			double _y=this.getTranslateY()-speed;
			
			if(_y<0) this.setTranslateY(height);
			else this.setTranslateY(_y);
		}
	}
}
