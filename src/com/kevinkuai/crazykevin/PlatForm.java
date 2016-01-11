package com.kevinkuai.crazykevin;

import com.kevinkuai.GLGame.DynamicGameObject;

public class PlatForm extends DynamicGameObject{

	public static final float PLATFORM_WIDTH = 2;
	public static final float PLATFORM_HEIGHT = 0.5f;
	public static final int PLATFORM_TYPE_STATIC = 0;
	public static final int PLATFORM_TYPE_MOVING = 1;
	public static final int PLATFORM_STATE_NORMAL = 0;
	public static final int PLATFORM_STATE_PULVERIZING = 1;
	public static final float PLATFORM_PULVERIZE_TIME = 0.2f*4;
	public static final int PLATFORM_VELOCITY = 1;
	
	int type;
	int state;
	float stateTime;
	
	public PlatForm(int type, float x, float y) {
		super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		// TODO Auto-generated constructor stub
		this.type = type;
		this.state = PLATFORM_STATE_NORMAL;
		this.stateTime =0;
		if(type==PLATFORM_TYPE_MOVING){
			velocity.x = PLATFORM_VELOCITY;
		}
	}
	
	public void update(float deltaTime){
		if(type==PLATFORM_TYPE_MOVING){
			pos.add(velocity.x*deltaTime, velocity.y*deltaTime);
			
			if(pos.x < PLATFORM_WIDTH/2){
				pos.x=PLATFORM_WIDTH/2;
				velocity.set(PLATFORM_VELOCITY, 0);
			}
			
			if (pos.x > (World.WORLD_WIDTH - PLATFORM_WIDTH/2)){
				pos.x=World.WORLD_WIDTH - PLATFORM_WIDTH/2;
				velocity.set(-PLATFORM_VELOCITY, 0);
			}
			
			bounds.lowleft.set(pos.x-PLATFORM_WIDTH/2, pos.y-PLATFORM_HEIGHT/2);
		}
		
		
		stateTime += deltaTime;
	}
	
	
	public void pulverize(){
		state = PLATFORM_STATE_PULVERIZING;
		stateTime = 0;
		velocity.x = 0;
	}

}
