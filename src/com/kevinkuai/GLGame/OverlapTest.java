package com.kevinkuai.GLGame;

public class OverlapTest {
	
	public static boolean overlapCircle(Circle c1, Circle c2){
		float dis = c1.center.disSquared(c2.center);
		float bigRR = c1.radius + c2.radius;
		return dis < bigRR * bigRR;
	}

	public static boolean overlapRecs(Rectangle r1, Rectangle r2){
		if (r1.lowleft.x < (r2.lowleft.x + r2.width)&&
				(r1.lowleft.x +r1.width) > r2.lowleft.x&&
				r1.lowleft.y < (r2.lowleft.y+r2.height)&&
				(r1.lowleft.y + r1.height)>r2.lowleft.y)
			return true;
		else
			return false;
	}
	
	public static boolean overlapCirRec(Circle c, Rectangle r){
		float closestX = c.center.x;
		float closestY = c.center.y;
		
		if (c.center.x < r.lowleft.x){
			closestX = r.lowleft.x;
		}else if (c.center.x > r.lowleft.x + r.width){
			closestX = r.lowleft.x + r.width;
		}
		if (c.center.y < r.lowleft.y){
			closestY = r.lowleft.y;
		}else if (c.center.y > r.lowleft.y + r.height){
			closestY = r.lowleft.y + r.height;
		}
		
		return c.center.disSquared(closestX, closestY)<c.radius*c.radius;
	}
	
	public static boolean pointInCir(Circle c, Vector v){
		return (c.center.disSquared(v)<=(c.radius*c.radius));
	}
	
	public static boolean pointInCir(Circle c, float x, float y){
		return (c.center.disSquared(x,y)<=(c.radius*c.radius));
	}
	
	public static boolean pointInRec (Rectangle r, Vector v){
		return (v.x >= r.lowleft.x && v.x <= (r.lowleft.x +r.width)&&
				v.y >= r.lowleft.y && v.y <= (r.lowleft.y + r.height));
	}
	
	public static boolean pointInRec (Rectangle r, float x, float y){
		return (x >= r.lowleft.x && x <= (r.lowleft.x +r.width)&&
				y >= r.lowleft.y && y <= (r.lowleft.y + r.height));
	}

}
