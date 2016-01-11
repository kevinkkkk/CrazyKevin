package com.kevinkuai.framework.game1;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.kevinkuai.framework.Input.TouchEvent;
import com.kevinkuai.framework.game1.Pool.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler{

	boolean[] isTouched = new boolean[20];
	int[] touchX = new int[20];
	int[] touchY = new int[20];
	Pool<TouchEvent> touchPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
	float scaleX, scaleY;
	
	public MultiTouchHandler (View view, float scaleX, float scaleY){
		
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>(){

			@Override
			public TouchEvent creatObject() {
				// TODO Auto-generated method stub
				return new TouchEvent();
			}
			
		};
		touchPool = new Pool<TouchEvent> (factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		synchronized (this){
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction()& 
					MotionEvent.ACTION_POINTER_ID_MASK)
					>> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerId = event.getPointerId(pointerIndex);
			TouchEvent touchEvent;
			
			switch (action){
			
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				touchEvent = touchPool.newObject();
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				touchEvent.pointer = pointerId;
				touchEvent.x = touchX[pointerId]=(int)(event.getX(pointerIndex)*scaleX); 
				touchEvent.y = touchY[pointerId]=(int)(event.getY(pointerIndex)*scaleY);
				isTouched[pointerId] = true;
				touchEventBuffer.add(touchEvent);
				break;
				
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_POINTER_UP:
				touchEvent = touchPool.newObject();
				touchEvent.type = TouchEvent.TOUCH_UP;
				touchEvent.pointer = pointerId;
				touchEvent.x = touchX[pointerId]=(int)(event.getX(pointerIndex)*scaleX); 
				touchEvent.y = touchY[pointerId]=(int)(event.getY(pointerIndex)*scaleY);
				isTouched[pointerId] = false;
				touchEventBuffer.add(touchEvent);
				break;
				
			case MotionEvent.ACTION_MOVE:
				int pointCount = event.getPointerCount();
				for (int i = 0; i <pointCount; i++){
					pointerIndex = i;
					pointerId = event.getPointerId(pointerIndex);
					touchEvent = touchPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[pointerId]=(int)(event.getX(pointerIndex)*scaleX); 
					touchEvent.y = touchY[pointerId]=(int)(event.getY(pointerIndex)*scaleY);
					//isTouched[pointerId] = true;
					touchEventBuffer.add(touchEvent);
				}
				break;
			}
			return true;
		}
		
	}

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (pointer<0 || pointer>=20)
			return false;
		else
			return isTouched[pointer];
		}
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		synchronized (this){
			if (pointer < 0 || pointer >= 20)
				return 0;
			else
				return touchX[pointer];
		}
		
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		synchronized (this){
			if (pointer < 0 || pointer >= 20)
				return 0;
			else
				return touchY[pointer];
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		synchronized (this){
			int len = touchEvents.size();
			for (int i =0; i < len; i++)
				touchPool.free(touchEvents.get(i));
			touchEvents.clear();
			touchEvents.addAll(touchEventBuffer);
			touchEventBuffer.clear();
			return touchEvents;
		}
		
	}

}
