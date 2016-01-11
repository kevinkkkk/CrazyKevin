package com.kevinkuai.crazykevin;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.kevinkuai.GLGame.Camera2D;
import com.kevinkuai.GLGame.OverlapTest;
import com.kevinkuai.GLGame.Rectangle;
import com.kevinkuai.GLGame.SpriteBatcher;
import com.kevinkuai.GLGame.Vector;
import com.kevinkuai.crazykevin.World.worldListener;
import com.kevinkuai.framework.Game;
import com.kevinkuai.framework.Input.TouchEvent;

public class GameScreen extends GLScreen{
	
	static final int GAME_READY =1;
	static final int GAME_RUNNING = 2;
	static final int GAME_OVER = 3;
	static final int GAME_PAUSED = 4;
	static final int GAME_LEVEL_END =5;
	
	int state;
	Camera2D camera;
	Vector touchPoint;
	SpriteBatcher batcher;
	World world;
	worldListener listener;
	WorldRender render;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	int lastScore;
	String scoreString;

	public GameScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		state = GAME_READY;
		camera = new Camera2D(glGraphics, 320,480);
		touchPoint = new Vector();
		batcher = new SpriteBatcher(glGraphics, 1000);
		listener = new worldListener(){

			@Override
			public void jump() {
				// TODO Auto-generated method stub
				Assets.playSound(Assets.upSound);
				
			}

			@Override
			public void highJump() {
				// TODO Auto-generated method stub
				Assets.playSound(Assets.highUpSound);
				
			}

			@Override
			public void hit() {
				// TODO Auto-generated method stub
				Assets.playSound(Assets.hitSound);
				
			}

			@Override
			public void coin() {
				// TODO Auto-generated method stub
				Assets.playSound(Assets.coinSound);
				
			}};
		world = new World(listener);
		render = new WorldRender(glGraphics, batcher, world);
		pauseBounds = new Rectangle(320-64,480-64,64,64);
		resumeBounds = new Rectangle(160-96, 240,192,36);
		quitBounds = new Rectangle(160-96,240-36,192,36);
		lastScore = 0;
		scoreString = "score: 0";
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		if(deltaTime>0.1f)
			deltaTime=0.1f;
		
		switch(state){
		case GAME_READY:
			updateReady();
			break;
			
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
			
		case GAME_PAUSED:
			updatePaused();
			break;
			
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
			
		case GAME_OVER:
			updateGameOver();
			break;
		}
		
		
	}

	private void updateGameOver() {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i<len; i++){
			TouchEvent te=touchEvents.get(i);
			if(te.type != TouchEvent.TOUCH_UP)
				continue;
			game.setScreen(new MainMenuScreen(game));
		}
		
	}

	private void updateLevelEnd() {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents=game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i=0; i<len; i++){
			TouchEvent te = touchEvents.get(i);
			if (te.type != TouchEvent.TOUCH_UP)
				continue;
			
			world = new World(listener);
			render = new WorldRender(glGraphics, batcher, world);
			world.score = lastScore;
			state = GAME_READY;
		}
		
	}

	private void updatePaused() {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i=0; i<len;i++){
			TouchEvent te = touchEvents.get(i);
			if(te.type == TouchEvent.TOUCH_UP ){
				touchPoint.set(te.x,te.y);
				camera.touchToWorld(touchPoint);
				if(OverlapTest.pointInRec(resumeBounds, touchPoint)){
					state = GAME_RUNNING;
					return;
				}
				if(OverlapTest.pointInRec(quitBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
		
	}

	private void updateRunning(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i=0; i<len;i++){
			TouchEvent te = touchEvents.get(i);
			if (te.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(te.x,te.y);
			camera.touchToWorld(touchPoint);
			
			if(OverlapTest.pointInRec(pauseBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state=GAME_PAUSED;
				return;
			}
		}
		world.update(deltaTime, game.getInput().getAccelX());
		if(world.score != lastScore){
			lastScore=world.score;
			scoreString = " "+lastScore;
		}
		
		if(world.state==World.WORLD_STATE_NEXT_LEVEL){
			state = GAME_LEVEL_END;
		}
		
		if(world.state==World.WORLD_STATE_GAME_OVER){
			state=GAME_OVER;
			if(lastScore>Settings.highscores[4]){
				scoreString = "new highScore: "+lastScore;
			}else
				scoreString = "Score: "+lastScore;
			Settings.addScore(lastScore);
			Settings.save(game.getFileIO());
		}
		
	}

	private void updateReady() {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i=0; i<len; i++){
			TouchEvent te = touchEvents.get(i);
			if(te.type==TouchEvent.TOUCH_DOWN){
				state=GAME_RUNNING;
				return;
			}
			
		}
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		render.render();
		
		camera.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		
		switch(state){
		case GAME_READY:
			presentReady();
			break;
			
		case GAME_RUNNING:
			presentRunning();
			break;
			
		case GAME_PAUSED:
			presentPause();
			break;
			
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
			
		case GAME_OVER:
			presentGameOver();
			break;
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentGameOver() {
		// TODO Auto-generated method stub
		batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);
		float scoreWidth = Assets.font.glyWidth*scoreString.length();
		Assets.font.drawText(batcher, scoreString, 160-scoreWidth/2, 480-20);
	}

	private void presentLevelEnd() {
		// TODO Auto-generated method stub
		/*String topText = "sorry kev, the magician is ...";
		String bottomText = "in another place!";
		float topWidth = topText.length()*Assets.font.glyWidth;
		float bottomWidth = bottomText.length()*Assets.font.glyWidth;
		Assets.font.drawText(batcher, topText, 160-topWidth/2, 480-40);
		Assets.font.drawText(batcher, bottomText, 160-bottomWidth/2, 40);*/
		batcher.beginBatch(Assets.level);
		batcher.drawSprite(160, 240, 320, 234, Assets.levelRegion);
	}

	private void presentPause() {
		// TODO Auto-generated method stub
		batcher.drawSprite(160, 240, 192, 96, Assets.pauseMenu);
		Assets.font.drawText(batcher, scoreString, 16, 480-20);
	}

	private void presentRunning() {
		// TODO Auto-generated method stub
		batcher.drawSprite(320-32, 480-32, 64, 64, Assets.pause);
		Assets.font.drawText(batcher, scoreString, 16, 480-20);
	}

	private void presentReady() {
		// TODO Auto-generated method stub
		batcher.drawSprite(160, 120, 192, 32, Assets.ready);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		if(state==GAME_RUNNING)
			state=GAME_PAUSED;
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
