package com.kevinkuai.GLGame;

import android.util.Log;

public class FPSCounter {
	long startTime = System.nanoTime();
	int frames = 0;
	
	public void countFPS(){
		frames++;
		if (System.nanoTime() - startTime >= 1000000000){
			Log.d("FPSCounter ", "fps: "+ frames);
			frames = 0;
			startTime = System.nanoTime();
		}
	}

}
