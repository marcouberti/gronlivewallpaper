package com.marcouberti.gravity.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class SpaceAnimatedObject extends UniverseObject{
	Rect destRect;
	Path path;
	
	private Bitmap bitmap;		// the animation sequence
	private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	private int frameNr;		// number of frames in animation
	private int currentFrame;	// the current frame
	private long frameTicker;	// the time of the last frame update
	private int framePeriod;	// milliseconds between each frame (1000/fps)

	private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	private int spriteHeight;	// the height of the sprite
	
	public SpaceAnimatedObject(Context context, Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
		paint = new Paint();//Paint.ANTI_ALIAS_FLAG
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		//paint.setStrokeWidth(3);
		paint.setMaskFilter(new BlurMaskFilter(5, Blur.OUTER));
		//this.drawable = image;
		//bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
		rotation = (float)(Math.random()*180);
		path = new Path();
		
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		System.out.println("BITMAP= "+bitmap);
		System.out.println("FC= "+frameCount);
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
	}

	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}

	public void draw(Canvas canvas) {
		// where to draw the sprite
		Rect destRect = new Rect((int)x, (int)y, (int)(x + spriteWidth), (int)(y + spriteHeight));
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}
	
	/*
	@Override
	public void draw(Canvas canvas) {
		// rotate the canvas on center of the text to draw
		//canvas.save();
		//canvas.rotate(rotation, x, y-(radius/2));

		canvas.drawCircle(x, y, radius, paint);
		//canvas.drawCircle(x, y-(radius), radius, paint);
	
		//path.reset();
		//path.moveTo(x, y);
		//path.quadTo(x-30, y-40, x-50, y-30);
		//canvas.drawPath(path, paint);
		
		//canvas.drawCircle(x, y-(radius*2), radius, paint);
		//destRect = new Rect((int)(x-radius),(int)(y-radius),(int)(x+radius),(int)(y+radius));
		//canvas.drawBitmap(bitmap, null,destRect, null);
		
		//canvas.restore();
	}
	*/

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}
	
}
