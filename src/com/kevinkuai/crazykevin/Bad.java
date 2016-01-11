package com.kevinkuai.crazykevin;

import com.kevinkuai.GLGame.DynamicGameObject;

public class Bad extends DynamicGameObject{
	public static final float BAD_WIDTH = 1;
	public static final float BAD_HEIGHT = 0.6f;
	public static final float BAD_VELOCITY = 3;
	
	float stateTime = 0;

	public Bad(float x, float y) {
		super(x, y, BAD_WIDTH, BAD_HEIGHT);
		// TODO Auto-generated constructor stub
		velocity.set(BAD_VELOCITY, 0);
	}
	
	public void update(float deltaTime){
		pos.add(velocity.x*deltaTime, velocity.y*deltaTime);
		
		
		if(pos.x < BAD_WIDTH/2){
			pos.x=BAD_WIDTH/2;
			velocity.set(BAD_VELOCITY, 0);
		}
		
		if (pos.x > (World.WORLD_WIDTH - BAD_WIDTH/2)){
			pos.x=World.WORLD_WIDTH - BAD_WIDTH/2;
			velocity.set(-BAD_VELOCITY, 0);
		}
		
		bounds.lowleft.set(pos).sub(BAD_WIDTH/2, BAD_HEIGHT/2);
		stateTime += deltaTime;
	}

}
