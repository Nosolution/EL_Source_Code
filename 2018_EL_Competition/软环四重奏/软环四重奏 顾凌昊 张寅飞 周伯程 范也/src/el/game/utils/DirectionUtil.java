package el.game.utils;

public class DirectionUtil {
	public static double getDirection(double x,double y, double tx,double ty) {
		double rotate=0;
		if(ty-y==0) return (tx>x)?90 :180;
		double tan=Math.abs(tx-x)/Math.abs(ty-y);
		double angle=Math.toDegrees(Math.atan(tan));
		if(ty>y) rotate=(tx>x)?180-angle :180+angle;
		else rotate=(tx>x)?angle :360-angle;
		return rotate;
	}
}
