package com.marcouberti.gravity.bean;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public abstract class UniverseObject extends Drawable{
	public float x,y,velx,vely,radius;//unità/sec per le velocità
	public float rotation = 0f;
	public boolean destroyed = false;
	protected Paint paint;
	protected int shape;
}
