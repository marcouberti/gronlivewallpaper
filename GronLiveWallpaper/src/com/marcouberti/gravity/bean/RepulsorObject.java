package com.marcouberti.gravity.bean;

import com.marcouberti.gravity.utils.Math2DUtils;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.BlurMaskFilter.Blur;

public class RepulsorObject extends GravityObject{
	
	//long starting,current;
	
	public RepulsorObject() {
		paint = new Paint();//Paint.ANTI_ALIAS_FLAG
		paint.setColor(Color.CYAN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		//paint.setMaskFilter(new BlurMaskFilter(10, Blur.OUTER));
		
		//starting = System.currentTimeMillis();
	}
	
	@Override
	public float computeAttraction(Point p) {
		float distance = Math2DUtils.computeDistance(p, new Point((int)x,(int)y));
		return (float)(power*100000*(1/Math.pow(distance, 2)));
	}
	
	@Override
	public void draw(Canvas canvas) {
		//current = System.currentTimeMillis();
		//canvas.drawCircle(x, y, power, paint);
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub
		
	}

}
