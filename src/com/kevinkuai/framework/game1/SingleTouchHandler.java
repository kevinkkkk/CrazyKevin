package com.kevinkuai.framework.game1;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.kevinkuai.framework.Input.TouchEvent;
import com.kevinkuai.framework.game1.Pool.PoolObjectFactory;

public class SingleTouchHandler implements TouchHandler{
	
	boolean isTouched;
	int touchX, touchY;
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY){
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>(){

			@Override
			public TouchEvent creatObject() {
				// TODO Auto-generated method stub
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		synchronized(this){
			if(pointer == 0)
				return isTouched;
			else
				return false;
		}
		
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		synchronized(this){
			return touchX;
		}
		
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		synchronized(this){
			return touchY;
		}
		
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		synchronized (this){
			int len = touchEventsBuffer.size();
			for (int i = 0; i<len; i++)
				touchEventPool.free(touchEventsBuffer.get(i));
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
		
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		synchronized (this){
			TouchEvent touchEvent = touchEventPool.newObject();
			switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
				
			case MotionEvent.ACTION_MOVE:
				touchEvent.type = TouchEvent.TOUCH_DRAGGED;
				isTouched = true;
				break;
				
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				touchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = false;
				break;
			}
			touchEvent.x = touchX= (int) (event.getX()*scaleX);
			touchEvent.y = touchY = (int)(event.getY()*scaleY);
			touchEventsBuffer.add(touchEvent);
			
			return true;
		}
	}
	
	

}
