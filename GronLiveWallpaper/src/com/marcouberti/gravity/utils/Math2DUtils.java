package com.marcouberti.gravity.utils;

import java.util.Vector;

import android.graphics.Point;

public class Math2DUtils {
	public static float computeDistance(Point p1, Point p2) {
		return (float)Math.sqrt(Math.pow(p2.x-p1.x,2)+Math.pow(p2.y-p1.y,2));
	}
	
	public static float computeDirection(Point p1, Point p2) {
		float distance = computeDistance(p1,p2);
		float deltaX = p2.x-p1.x;
		float alpha = (float)Math.acos((double)(deltaX/distance));
		return alpha;
	}
	
	public static Point sumVectorWithSameOrigin(Point p1, Point p2) {
		return new Point(p1.x + p2.x,p1.y+p2.y);
	}
	
}
