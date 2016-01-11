package com.kevinkuai.crazykevin;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.kevinkuai.GLGame.Camera2D;
import com.kevinkuai.GLGame.OverlapTest;
import com.kevinkuai.GLGame.Rectangle;
import com.kevinkuai.GLGame.SpriteBatcher;
import com.kevinkuai.GLGame.Texture;
import com.kevinkuai.GLGame.TextureRegion;
import com.kevinkuai.GLGame.Vector;
import com.kevinkuai.framework.Game;
import com.kevinkuai.framework.Input.TouchEvent;

public class HelpScreen4 extends GLScreen{
	SpriteBatcher batcher;
	Camera2D cam;
	Rectangle nextBounds;
	Texture helpImage;
	TextureRegion helpRegion;
	Vector touchPoint;

	public HelpScreen4(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		cam = new Camera2D(glGraphics,320,480);
		nextBounds = new Rectangle(320-64,0,64,64);
		touchPoint = new Vector();
		batcher = new SpriteBatcher(glGraphics, 1);
	}
	

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvent = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvent.size();
		
		for (int i = 0; i<len; i++){
			TouchEvent te = touchEvent.get(i);
			if(te.type == TouchEvent.TOUCH_UP){
				touchPoint.set(te.x, te.y);
				cam.touchToWorld(touchPoint);
				if(OverlapTest.pointInRec(nextBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
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
		
		batcher.beginBatch(helpImage);
		batcher.drawSprite(160, 240, 320, 480, helpRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(320-32, 32, -64, 64, Assets.arrow);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		helpImage.dispose();
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		helpImage = new Texture(glGame, "help4.png");
		helpRegion = new TextureRegion(helpImage, 0,0,320,480);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
