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

public class HighScoreScreen extends GLScreen{
	Camera2D cam;
	SpriteBatcher batcher;
	Rectangle backBounds;
	Vector touchPoint;
	String[] highScores;
	float xOffset = 0;

	public HighScoreScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		cam = new Camera2D(glGraphics, 320,480);
		batcher = new SpriteBatcher(glGraphics,1);
		backBounds = new Rectangle(0,0,64,64);
		touchPoint=new Vector();
		highScores = new String[5];
		for(int i=0; i<5; i++){
			highScores[i] = (i+1)+"   "+Settings.highscores[i];
			xOffset = Math.max(highScores[i].length()*Assets.font.glyWidth, xOffset);
		}
		xOffset = 160-xOffset/2;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvent = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvent.size();
		for (int i=0; i<len; i++){
			TouchEvent te = touchEvent.get(i);
			if (te.type == TouchEvent.TOUCH_UP){
				touchPoint.set(te.x, te.y);
				cam.touchToWorld(touchPoint);
				if(OverlapTest.pointInRec(backBounds, touchPoint)){
					game.setScreen(new MainMenuScreen(game));
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
		batcher.drawSprite(160, 360, 300, 33, Assets.highScoreRegion);
		
		float y=240;
		for(int i=4; i>=0;i--){
			Assets.font.drawText(batcher, highScores[i], xOffset, y);
			y += Assets.font.glyHeight;
		}
		
		batcher.drawSprite(32, 32, 64, 64, Assets.arrow);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
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
