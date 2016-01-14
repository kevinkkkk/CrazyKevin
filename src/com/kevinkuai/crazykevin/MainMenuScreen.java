package com.kevinkuai.crazykevin;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.kevinkuai.GLGame.Camera2D;
import com.kevinkuai.GLGame.OverlapTest;
import com.kevinkuai.GLGame.Rectangle;
import com.kevinkuai.GLGame.SpriteBatcher;
import com.kevinkuai.GLGame.Vector;
import com.kevinkuai.framework.Game;
import com.kevinkuai.framework.Input.TouchEvent;

public class MainMenuScreen extends GLScreen {
	Camera2D cam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highScoresBounds;
	Rectangle helpBounds;
	Vector touchPoint;

	public MainMenuScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		cam = new Camera2D(glGraphics, 320, 480);
		batcher = new SpriteBatcher(glGraphics, 100);
		soundBounds = new Rectangle(0,0,64,64);
		playBounds = new Rectangle(160-150,200-36,300,108);
		//highScoresBounds = new Rectangle(160-150, 200-18, 300,36);
		//helpBounds = new Rectangle(160-150,200-36,300,36);
		touchPoint = new Vector();
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvent = glGame.getInput().getTouchEvents();
		glGame.getInput().getKeyEvents();
		int len = touchEvent.size();
		for (int i=0; i<len; i++){
			TouchEvent te = touchEvent.get(i);
			if (te.type == TouchEvent.TOUCH_UP){
				touchPoint.set(te.x,te.y);
				cam.touchToWorld(touchPoint);
				
				if(OverlapTest.pointInRec(playBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new GameScreen(game));
					return;
				}
				
				/*if(OverlapTest.pointInRec(highScoresBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HighScoreScreen(game));
					return;
				}*/
				
				/*if(OverlapTest.pointInRec(helpBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen(game));
					return;
				}*/
				
				if(OverlapTest.pointInRec(soundBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.music.play();
					else
						Assets.music.pause();
					return;
				}
			}
		}
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		cam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(160, 480-10-71,274, 142, Assets.logo);
		batcher.drawSprite(160, 200, 300, 110, Assets.mainMenu);
		batcher.drawSprite(32, 32, 64, 64, 
				Settings.soundEnabled?Assets.soundOn:Assets.soundOff);
		
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Settings.save(game.getFileIO());
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	

}
