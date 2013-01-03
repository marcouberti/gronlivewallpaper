package com.marcouberti.gravity.bean;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

public class NonAttractedObject extends UniverseObject{

	public NonAttractedObject(Context context, int image) {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		this.shape = image;
	}
	
	@Override
	public void draw(Canvas canvas) {
		//canvas.drawCircle(x, y, radius, paint);
		Rect destRect = new Rect((int)(x-radius),(int)(y-radius),(int)(x+radius),(int)(y+radius));
		//canvas.drawBitmap(bitmap, null,destRect, paint);
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
