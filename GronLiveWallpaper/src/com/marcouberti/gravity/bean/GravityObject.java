package com.marcouberti.gravity.bean;

import com.marcouberti.gravity.core.ApplicationManager;
import com.marcouberti.gravity.core.UniverseManager;
import com.marcouberti.gravity.utils.Math2DUtils;

import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;

public class GravityObject extends UniverseObject{
	public float power;
	int frameCount = 0;
	
	public GravityObject() {
		paint = new Paint();//Paint.ANTI_ALIAS_FLAG
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		//paint.setMaskFilter(new BlurMaskFilter(15, Blur.OUTER));
	}
	
	public float computeAttraction(Point p) {
		float distance = Math2DUtils.computeDistance(p, new Point((int)x,(int)y));
		return (float)(power*(1/Math.pow(distance, 2)));
	}
	

	@Override
	public void draw(Canvas canvas) {
		frameCount++;
		if(frameCount > 50) {
			this.destroyed = true;
			//Lo rimpiazzo con un altro per dare dinamismo al wallpaper
			GravityObject go = new GravityObject();
	        go.power = 10;
	        go.x = (int)(Math.random()*ApplicationManager.SCREEN_W);
	        go.y = (int)(Math.random()*ApplicationManager.SCREEN_H);
	        go.radius = 5;
	        go.velx = 0;
	        go.vely = 0;
	        UniverseManager.queueGravityObject(go);
		}
		//canvas.drawCircle(x, y, 8, paint);
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
}
