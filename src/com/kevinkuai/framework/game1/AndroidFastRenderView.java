package com.kevinkuai.framework.game1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable{
	
	AndroidGame game;
	Bitmap framebuffer;
	Thread tt = null;
	SurfaceHolder sh;
	volatile boolean running = false;

	public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
		super(game);
		// TODO Auto-generated constructor stub
		this.game = game;
		this.framebuffer = framebuffer;
		this.sh = getHolder();
	}
	
	public void resume() {
		running = true;
		tt = new Thread(this);
		tt.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.d("AndroidGame log", "Surface thread stated");
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (running){
			if (!sh.getSurface().isValid())
				continue;
			
			float deltaTime = (System.nanoTime()
					-startTime)/1000000000.0f;
			startTime = System.nanoTime();
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);
			
			Canvas canvas = sh.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			sh.unlockCanvasAndPost(canvas);
		}
		
	}
	
	public void pause(){
		running = false;
		while (true){
			try {
				tt.join();
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			
			}
			
		}
	}
	

}
