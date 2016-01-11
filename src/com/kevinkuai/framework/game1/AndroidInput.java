package com.kevinkuai.framework.game1;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.kevinkuai.framework.Input;

public class AndroidInput implements Input{
	
	AccelerometerHandler accel;
	KeyboardHandler keyboard;
	TouchHandler touch;
	
	@SuppressWarnings("deprecation")
	public AndroidInput(Context context, View view, float scaleX, float scaleY){
		accel = new AccelerometerHandler(context);
		keyboard = new KeyboardHandler (view);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touch = new SingleTouchHandler(view, scaleX, scaleY);
		else
			touch = new MultiTouchHandler (view, scaleX, scaleY);
	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		// TODO Auto-generated method stub
		return keyboard.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		return touch.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		return touch.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		return touch.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		// TODO Auto-generated method stub
		return accel.getAccelX();
	}

	@Override
	public float getAccelY() {
		// TODO Auto-generated method stub
		return accel.getAccelY();
	}

	@Override
	public float getAccelZ() {
		// TODO Auto-generated method stub
		return accel.getAccelZ();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		// TODO Auto-generated method stub
		return keyboard.getKeyEvents();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return touch.getTouchEvents();
	}

}
