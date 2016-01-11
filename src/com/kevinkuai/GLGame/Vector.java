package com.kevinkuai.GLGame;

import android.util.FloatMath;

public class Vector {
	public static float TO_RADIANS = (1/180.0f)*(float)Math.PI;
	public static float TO_DEGREE = (1/(float)Math.PI)*180.0f;
	public float x,y;
	
	public Vector(){
		
	}

	public Vector(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public Vector(Vector other){
		this.x=other.x;
		this.y=other.y;
		
	}
	
	public Vector copy(){
		return new Vector(x,y);
	}
	
	public Vector set(float x, float y){
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector set(Vector other){
		this.x = other.x;
		this.y = other.y;
		return this;
	}
	
	public Vector add(float x, float y){
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector add(Vector other){
		this.x += other.x;
		this.y += other.y;
		return this;
		
	}
	
	public Vector sub(float x, float y){
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector sub(Vector other){
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector mul(float scalar){
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public float len(){
		return FloatMath.sqrt(x*x+y*y);
	}
	
	public Vector nor(){
		if (len()!= 0){
			this.x /= len();
			this.y /= len();
			 
		}
		return this;
	}
	
	public float angle(){
		float angle = (float)Math.atan2(y, x) * TO_DEGREE;
		if (angle < 0){
			angle += 360;
		}
		return angle;
	}
	
	public Vector rotate(float angle){
		float rad = angle * TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);
		
		float newX = this.x * cos - this.y * sin;
		float newY = this.x *sin + this.y * cos;
		
		this.x = newX;
		this.y = newY;
		
		return this;
		
	}
	
	public float dis(Vector other){
		float disX = this.x - other.x;
		float disY = this.y - other.y;
		float dis = FloatMath.sqrt(disX * disX + disY * disY);
		return dis;
	}
	
	public float dis(float x, float y){
		float disX = this.x - x;
		float disY = this.y - y;
		float dis = FloatMath.sqrt(disX * disX + disY * disY);
		return dis;
	}

	public float disSquared(Vector v2) {
		// TODO Auto-generated method stub
		float dis = (v2.x - this.x)*(v2.x - this.x) + (v2.y - this.y)*(v2.y-this.y);
		return dis;
	}

	public float disSquared(float x, float y) {
		// TODO Auto-generated method stub
		float dis = (x - this.x)*(x - this.x) + (y - this.y)*(y-this.y);
		return dis;
	}
}
