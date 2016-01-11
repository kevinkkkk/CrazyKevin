package com.kevinkuai.GLGame;

public class GameObject {
	public final Vector pos;
	public final Rectangle bounds;
	
	public GameObject(float x, float y, float width, float height){
		this.pos = new Vector(x,y);
		this.bounds = new Rectangle (x-width/2, y-height/2,width,height);
	}

}
