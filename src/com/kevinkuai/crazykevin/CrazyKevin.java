package com.kevinkuai.crazykevin;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.kevinkuai.GLGame.GLGame;
import com.kevinkuai.framework.Screen;

public class CrazyKevin extends GLGame{

	boolean firstTimeCreate = true;
	
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new MainMenuScreen(this);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(Settings.soundEnabled)
			Assets.music.pause();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate){
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		}else{
			Assets.reload();
		}
	}

}
