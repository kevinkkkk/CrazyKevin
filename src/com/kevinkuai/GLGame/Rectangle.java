package com.kevinkuai.GLGame;

public class Rectangle {
	public final Vector lowleft;
	public float width, height;
	
	public Rectangle(float x, float y, float width, float height){
		this.lowleft=new Vector(x,y);
		this.width = width;
		this.height = height;
	}
	
	

}
