package com.kevinkuai.GLGame;

public class Circle {
	public final Vector center = new Vector();
	public float radius;
	
	public Circle(float x, float y, float radius){
		this.center.set(x,y);
		this.radius = radius;
	}
	
	
}
