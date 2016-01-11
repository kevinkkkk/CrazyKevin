package com.kevinkuai.GLGame;

public class DynamicGameObject extends GameObject{

	public final Vector velocity;
	public final Vector accel;
	
	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.velocity = new Vector();
		this.accel = new Vector();
		
	}

}
