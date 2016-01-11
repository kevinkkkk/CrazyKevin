package com.kevinkuai.framework.game1;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.kevinkuai.framework.Audio;
import com.kevinkuai.framework.FileIO;
import com.kevinkuai.framework.Game;
import com.kevinkuai.framework.Graphics;
import com.kevinkuai.framework.Input;
import com.kevinkuai.framework.Screen;

public abstract class AndroidGame extends Activity implements Game{
	
	AndroidFastRenderView fastView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("AndroidGame log", "AndroidGame main thread stated");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		boolean isLandscape = getResources().getConfiguration().orientation ==
				Configuration.ORIENTATION_LANDSCAPE;
		int framebufferWidth = isLandscape? 480:320;
		int framebufferHeight =isLandscape? 320:480;
		Bitmap framebuffer = Bitmap.createBitmap(framebufferWidth, framebufferHeight, 
				Config.RGB_565);
		
		float scaleX = (float)framebufferWidth 
				/ getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float)framebufferHeight 
				/ getWindowManager().getDefaultDisplay().getHeight();
		
		fastView = new AndroidFastRenderView(this, framebuffer);
		graphics = new AndroidGraphics(getAssets(), framebuffer);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, fastView, scaleX, scaleY);
		
		screen = getStartScreen();
		setContentView(fastView);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		screen.resume();
		fastView.resume();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		fastView.pause();
		screen.pause();
		if (isFinishing())
			screen.dispose();
	}



	@Override
	public Input getInput() {
		// TODO Auto-generated method stub
		return input;
	}

	@Override
	public FileIO getFileIO() {
		// TODO Auto-generated method stub
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return graphics;
	}

	@Override
	public Audio getAudio() {
		// TODO Auto-generated method stub
		return audio;
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen() {
		// TODO Auto-generated method stub
		return screen;
	}

	

}
