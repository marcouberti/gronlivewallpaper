package com.marcouberti.gravity.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

public class TargetObject extends Drawable{
	
	public float power;
	Paint fillPaint;
	Paint paint;
	public float x,y,radius;
	public int color;
	
	public TargetObject(int color) {
		this.color = color;
		paint = new Paint();//Paint.ANTI_ALIAS_FLAG
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(6);
		//paint.setMaskFilter(new BlurMaskFilter(15, Blur.OUTER));
		
		fillPaint = new Paint();
		fillPaint.setColor(color);
		fillPaint.setAlpha(100);
		fillPaint.setStyle(Paint.Style.FILL);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(x, y, power, fillPaint);
		canvas.drawCircle(x, y, power, paint);
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
