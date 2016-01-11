package com.kevinkuai.crazykevin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.kevinkuai.GLGame.OverlapTest;
import com.kevinkuai.GLGame.Vector;

public class World {
	
	public interface worldListener{
		public void jump();
		public void highJump();
		public void hit();
		public void coin();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT =15*15;
	public static final int WORLD_STATE_RUNNING =0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER =2;
	public static final Vector gravity = new Vector(0,-12);

	
	public final Kevin kevin;
	public final List<PlatForm> platforms;
	public final List<Bad> bads;
	public final List<Spring> springs;
	public Castle castle;
	public final List<Coin> coins;
	public final worldListener listener;
	public final Random rand;
	
	public float heightSoFar;
	public int score;
	public int state;
	
	public World(worldListener listener){
		this.kevin = new Kevin(5,1);
		this.platforms = new ArrayList<PlatForm>();
		this.bads = new ArrayList<Bad>();
		this.springs = new ArrayList<Spring>();
		this.coins = new ArrayList<Coin>();
		this.listener = listener;
		rand = new Random();
		gernerateLevel();
		
		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void gernerateLevel() {
		// TODO Auto-generated method stub
		float y = PlatForm.PLATFORM_HEIGHT/2;
		float maxJumpHeight = Kevin.KEVIN_UP_VELOCITY*Kevin.KEVIN_UP_VELOCITY/(2*-gravity.y);
		while (y<WORLD_HEIGHT-WORLD_WIDTH/2){
			int type = rand.nextFloat()>0.8f ? PlatForm.PLATFORM_TYPE_MOVING
											   :PlatForm.PLATFORM_TYPE_STATIC;
			float x = rand.nextFloat()*(WORLD_WIDTH-PlatForm.PLATFORM_WIDTH)
					+PlatForm.PLATFORM_WIDTH/2;
			
			PlatForm platform = new PlatForm(type,x,y);
			platforms.add(platform);
			
			if(rand.nextFloat()>0.9f && type != PlatForm.PLATFORM_TYPE_MOVING){
				Spring spring = new Spring(platform.pos.x, platform.pos.y
						+PlatForm.PLATFORM_HEIGHT/2+Spring.SPRING_HEIGHT/2);
				springs.add(spring);
			}
			
			if (rand.nextFloat()>0.95f && y>15){
				Bad bad = new Bad(platform.pos.x+rand.nextFloat(), 
								  platform.pos.y+Bad.BAD_HEIGHT+rand.nextFloat()*2);
				bads.add(bad);
			}
			
			if (rand.nextFloat()>0.6f){
				Coin coin = new Coin(platform.pos.x+rand.nextFloat(),
									 platform.pos.y+Coin.COIN_HEIGHT+rand.nextFloat()*3);
				coins.add(coin);
			}
			
			y += (maxJumpHeight - 0.5f);
			y -= rand.nextFloat()*(maxJumpHeight/3);
		}
		
		castle  = new Castle(WORLD_WIDTH/2, y);
	}
	
	public void update(float deltaTime, float accelX){
		updateKevin(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateCoins(deltaTime);
		updateBads(deltaTime);
		if(kevin.state != Kevin.KEVIN_STATE_HIT)
			checkCollisions();
		checkGameOver();
	}

	private void checkGameOver() {
		// TODO Auto-generated method stub
		if((heightSoFar-7.5f)>kevin.pos.y){
			state = WORLD_STATE_GAME_OVER;
		}
		
	}

	private void checkCollisions() {
		// TODO Auto-generated method stub
		checkPlatFormColl();
		checkBadColl();
		checkItemColl();
		checkCastleColl();
		
	}

	private void checkCastleColl() {
		// TODO Auto-generated method stub
		if(OverlapTest.overlapRecs(castle.bounds, kevin.bounds)){
			state = WORLD_STATE_NEXT_LEVEL;
		}
		
	}

	private void checkItemColl() {
		// TODO Auto-generated method stub
		int len = coins.size();
		for (int i=0; i<len; i++){
			Coin coin = coins.get(i);
			if(OverlapTest.overlapRecs(coin.bounds, kevin.bounds)){
				score += Coin.COIN_SCORE;
				listener.coin();
				coins.remove(coin);
				len = coins.size();
			}
		}
		
		if(kevin.velocity.y>0)
			return;
		
		len = springs.size();
		for (int i=0; i<len; i++){
			Spring spring = springs.get(i);
			if(OverlapTest.overlapRecs(spring.bounds, kevin.bounds)){
				kevin.hitSpring();
				listener.highJump();
			}
		}
	}

	private void checkBadColl() {
		// TODO Auto-generated method stub
		int len = bads.size();
		for (int i=0; i<len; i++){
			Bad bad = bads.get(i);
			if(OverlapTest.overlapRecs(bad.bounds, kevin.bounds)){
				kevin.hitBad();
				listener.hit();
			}
		}
		
	}

	private void checkPlatFormColl() {
		// TODO Auto-generated method stub
		if(kevin.velocity.y>0)
			return;
		
		int len = platforms.size();
		for(int i=0; i<len; i++){
			PlatForm platform = platforms.get(i);
			if(OverlapTest.overlapRecs(kevin.bounds, platform.bounds)){
				kevin.hitPlatForm();
				listener.jump();
				if(rand.nextFloat()>0.5f){
					platform.pulverize();
				}
				break;
			}
		}
		
	}

	private void updateBads(float deltaTime) {
		// TODO Auto-generated method stub
		int len = bads.size();
		for (int i=0; i<len; i++){
			Bad bad = bads.get(i);
			bad.update(deltaTime);
		}
		
	}

	private void updateCoins(float deltaTime) {
		// TODO Auto-generated method stub
		int len = coins.size();
		for (int i=0; i<len; i++){
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
		
	}

	private void updatePlatforms(float deltaTime) {
		// TODO Auto-generated method stub
		int len = platforms.size();
		for (int i=0; i<len; i++){
			PlatForm platform = platforms.get(i);
			platform.update(deltaTime);
			if (platform.state==PlatForm.PLATFORM_STATE_PULVERIZING && 
					platform.stateTime>PlatForm.PLATFORM_PULVERIZE_TIME){
				platforms.remove(platform);
				len = platforms.size();
			}
		}
		
	}

	private void updateKevin(float deltaTime, float accelX) {
		// TODO Auto-generated method stub
		if(kevin.state != Kevin.KEVIN_STATE_HIT && kevin.pos.y<=0.5f)
			kevin.hitPlatForm();
		if(kevin.state != Kevin.KEVIN_STATE_HIT)
			kevin.velocity.x=(-accelX/10)*Kevin.KEVIN_MOVE_VELOCITY;
		kevin.update(deltaTime);
		heightSoFar = Math.max(kevin.pos.y, heightSoFar);
		
	}
	

}
