package com.kevinkuai.crazykevin;

import javax.microedition.khronos.opengles.GL10;

import com.kevinkuai.GLGame.Animation;
import com.kevinkuai.GLGame.Camera2D;
import com.kevinkuai.GLGame.GLGraphics;
import com.kevinkuai.GLGame.SpriteBatcher;
import com.kevinkuai.GLGame.TextureRegion;

public class WorldRender {
	
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	World world;
	Camera2D camera;
	SpriteBatcher batcher;

	public WorldRender(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
		// TODO Auto-generated constructor stub
		this.glGraphics=glGraphics;
		this.batcher=batcher;
		this.world=world;
		camera = new Camera2D(glGraphics,FRUSTUM_WIDTH,FRUSTUM_HEIGHT);
		
	}

	public void render() {
		// TODO Auto-generated method stub
		if(world.kevin.pos.y>camera.position.y){
			camera.position.y=world.kevin.pos.y;
		}
		camera.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}

	private void renderObjects() {
		// TODO Auto-generated method stub
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderKevin();
		renderPlatforms();
		renderItems();
		renderBad();
		renderCastle();
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	private void renderCastle() {
		// TODO Auto-generated method stub
		batcher.drawSprite(world.castle.pos.x, world.castle.pos.y, 2, 2, Assets.castle);
	}

	private void renderBad() {
		// TODO Auto-generated method stub
		TextureRegion keyframe;
		int len = world.bads.size();
		for (int i=0; i<len; i++){
			Bad bad = world.bads.get(i);
			keyframe = Assets.badFly.getKeyFrame(bad.stateTime, Animation.ANIMATION_LOOPING);
			int side = bad.velocity.x<0?-1:1;
			batcher.drawSprite(bad.pos.x, bad.pos.y, side*1, 1, keyframe);
		}
		
	}

	private void renderItems() {
		// TODO Auto-generated method stub
		TextureRegion keyframe;
		int len = world.coins.size();
		for (int i=0; i<len; i++){
			Coin coin = world.coins.get(i);
			keyframe = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			batcher.drawSprite(coin.pos.x, coin.pos.y, 1, 1, keyframe);
		}
		len = world.springs.size();
		for(int i=0; i<len;i++){
			Spring spring = world.springs.get(i);
			batcher.drawSprite(spring.pos.x, spring.pos.y, 1, 1, Assets.spring);
		}
	}

	private void renderPlatforms() {
		// TODO Auto-generated method stub
		TextureRegion keyFrame;
		int len = world.platforms.size();
		for (int i=0;i<len;i++){
			if(world.platforms.get(i).state==PlatForm.PLATFORM_STATE_PULVERIZING){
				keyFrame = Assets.brokenPlatform.getKeyFrame(
						world.platforms.get(i).stateTime, Animation.ANIMATION_NONLOOPING);
				batcher.drawSprite(world.platforms.get(i).pos.x, world.platforms.get(i).pos.y, 
						PlatForm.PLATFORM_WIDTH, PlatForm.PLATFORM_HEIGHT, keyFrame);
			}else{
				batcher.drawSprite(world.platforms.get(i).pos.x, world.platforms.get(i).pos.y, 
						PlatForm.PLATFORM_WIDTH, PlatForm.PLATFORM_HEIGHT, Assets.platform);
			}
		}
		
	}

	private void renderKevin() {
		// TODO Auto-generated method stub
		TextureRegion keyFrame;
		switch(world.kevin.state){
		case Kevin.KEVIN_STATE_FALL:
			keyFrame=Assets.kevinFall.getKeyFrame(world.kevin.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Kevin.KEVIN_STATE_UP:
			keyFrame=Assets.kevinUp.getKeyFrame(world.kevin.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Kevin.KEVIN_STATE_HIT:
		default:
			keyFrame=Assets.kevinHit;
		}
		float side = world.kevin.velocity.x<0?-1:1;
		batcher.drawSprite(world.kevin.pos.x, world.kevin.pos.y, side*1, 1, keyFrame);
	}

	private void renderBackground() {
		// TODO Auto-generated method stub
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(camera.position.x, camera.position.y, 
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundRegion);
		batcher.endBatch();
	}

}
