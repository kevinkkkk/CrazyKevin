package com.kevinkuai.crazykevin;

import com.kevinkuai.GLGame.DynamicGameObject;

public class Kevin extends DynamicGameObject{
	public static final int KEVIN_STATE_UP = 0;
	public static final int KEVIN_STATE_FALL =1;
	public static final int KEVIN_STATE_HIT =2;
	public static final float KEVIN_UP_VELOCITY = 11;
	public static final float KEVIN_MOVE_VELOCITY = 20;
	public static final float KEVIN_WIDTH = 0.8f;
	public static final float KEVIN_HEIGHT = 0.8f;

	float stateTime;
	int state;
	
	public Kevin(float x, float y) {
		super(x, y, KEVIN_WIDTH, KEVIN_HEIGHT);
		// TODO Auto-generated constructor stub
		state = KEVIN_STATE_FALL;
		stateTime = 0;
		
	}
	
	
	public void update(float deltaTime){
		velocity.add(World.gravity.x*deltaTime, World.gravity.y*deltaTime);
		pos.add(velocity.x*deltaTime, velocity.y*deltaTime);
		bounds.lowleft.set(pos.x-bounds.width/2, pos.y-bounds.height/2);
		
		if (velocity.y>0 && state != KEVIN_STATE_HIT){
			if(state != KEVIN_STATE_UP){
				state = KEVIN_STATE_UP;
				stateTime =0;
			}
		}
		
		if (velocity.y<0 && state != KEVIN_STATE_HIT){
			if(state != KEVIN_STATE_FALL){
				state = KEVIN_STATE_FALL;
				stateTime =0;
			}
		}
		
		if(pos.x<0)
			pos.x=World.WORLD_WIDTH;
		if(pos.x>World.WORLD_WIDTH)
			pos.x = 0;
		
		stateTime += deltaTime;
	}
	
	public void hitBad(){
		velocity.set(0,0);
		state = KEVIN_STATE_HIT;
		stateTime=0;
	}

	public void hitPlatForm(){
		velocity.y = KEVIN_UP_VELOCITY;
		state = KEVIN_STATE_UP;
		stateTime=0;
	}
	
	public void hitSpring(){
		velocity.y = KEVIN_UP_VELOCITY*1.5f;
		state=KEVIN_STATE_UP;
		stateTime=0;
		
	}
}
