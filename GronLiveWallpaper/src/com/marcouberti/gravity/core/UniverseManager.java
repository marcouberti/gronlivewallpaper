package com.marcouberti.gravity.core;

import java.util.ArrayList;
import java.util.Vector;

import com.marcouberti.gravity.bean.GravityObject;
import com.marcouberti.gravity.bean.NonAttractedObject;
import com.marcouberti.gravity.bean.RepulsorObject;
import com.marcouberti.gravity.bean.SpaceObject;
import com.marcouberti.gravity.utils.Math2DUtils;

import android.graphics.Point;
import android.util.Log;

public class UniverseManager {
	private static final float ATTRITO = 0.05F;
	public static ArrayList<GravityObject> gravityObjects = new ArrayList<GravityObject>();
	public static ArrayList<SpaceObject> spaceObjects = new ArrayList<SpaceObject>();
	public static ArrayList<NonAttractedObject> nonAttractedObjects = new ArrayList<NonAttractedObject>();
	
	private static ArrayList<GravityObject> tempGravityObjects = new ArrayList<GravityObject>();
	private static ArrayList<SpaceObject> tempSpaceObjects = new ArrayList<SpaceObject>();
	private static ArrayList<NonAttractedObject> tempNonAttractedObjects = new ArrayList<NonAttractedObject>();
	
	private static RepulsorObject repulsor = new RepulsorObject();
	public static float repulsorX,repulsorY,repulsorPower;
	public static boolean repulsorActive =false;

	public static void initialize() {
		gravityObjects.clear();
		spaceObjects.clear();
		nonAttractedObjects.clear();
		tempGravityObjects.clear();
		tempSpaceObjects.clear();
		tempNonAttractedObjects.clear();	
		
		repulsor.destroyed = true;
		gravityObjects.add(repulsor);
	}
	
	private static void addSpaceObject(SpaceObject o) {
		spaceObjects.add(o);
	}
	
	private static void addGravityObject(GravityObject o) {
		gravityObjects.add(o);
	}
	
	private static void addNonAttractedObject(NonAttractedObject o) {
		nonAttractedObjects.add(o);
	}
	
	private static long historyTs = 0;
	static long currentTs;
	static long delta;
	public static void updateUniverse() {
		currentTs = System.currentTimeMillis();
		delta = currentTs - historyTs;
		historyTs = currentTs;
		//Log.d("DELTA",""+delta);
		
		if(historyTs == 0) {//il primo giro salta e aggiorno il ts
			return;
		}
		
		//Aggiorno il repulsor
		
		repulsor.x = repulsorX;
		repulsor.y = repulsorY;
		repulsor.power = repulsorPower;
		repulsor.destroyed = !repulsorActive;
		
		
		//Prima controllo se ci sono nuovi oggetti da aggiungere/rimuovere all'universo
		if(tempGravityObjects.size() > 0) {
			for(GravityObject tgo: tempGravityObjects) {
				addGravityObject(tgo);
				//se in questo punto c'era già un altro GO lo rimuovo e non aggiungo niente altro
				/*
				GravityObject existGO = existGOinThisRegion(tgo);
				if(existGO != null) {
					existGO.destroyed = true;
				}else {//altrimenti ne aggiungo uno nuovo
					addGravityObject(tgo);
				}
				*/
			}
			tempGravityObjects.clear();
		}
		if(tempSpaceObjects.size() > 0) {
			for(SpaceObject tso: tempSpaceObjects) {
				addSpaceObject(tso);
			}
			tempSpaceObjects.clear();
		}
		if(tempNonAttractedObjects.size() > 0) {
			for(NonAttractedObject nao: tempNonAttractedObjects) {
				addNonAttractedObject(nao);
			}
			tempNonAttractedObjects.clear();
		}
		
		//Controllo se ci sono COLLISIONI con i GRAVITY OBJECT
		/*
		for(SpaceObject so: spaceObjects) {
			for(GravityObject go: gravityObjects) {
				double dx =Math.abs(so.x - go.x);
				double dy = Math.abs(so.y - go.y);
				double distance = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
				if(distance <= go.power+so.radius){
					so.destroyed =true;
					break;
				}
			}
		}
		*/
		/*
		//Controllo se ci sono GOAL
		for(SpaceObject so: spaceObjects) {
			for(NonAttractedObject nao: nonAttractedObjects) {
				double dx =Math.abs(so.x - nao.x);
				double dy = Math.abs(so.y - nao.y);
				double distance = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
				if(distance <= nao.radius+so.radius){
					so.destroyed =true;
					break;
				}
			}
		}
		*/
		
		float dX;
		float dY;
		double D;
		double alpha;
		double Fd;
		double fdx;
		double fdy;
		
		//Per ogni space object calcolo la sua nuova posizione sulla base della sua velocità/direzione precedente e tutte
		//le forze che lo attraggono
		for(SpaceObject so: spaceObjects) {
			if(so.destroyed) continue;
			for(GravityObject go: gravityObjects) {
				
				if(go.destroyed) continue;
				dX = Math.abs(go.x - so.x);
				dY = Math.abs(go.y - so.y);
				D = Math.sqrt(Math.pow(dX, 2)+Math.pow(dY, 2));
				alpha =Math.acos(dX/D);
				//double componenteX = D*Math.cos(alpha);
				//double componenteY = D*Math.sin(alpha);
				Fd = (go.power*1000)/Math.pow(D, 1.5);
				if(Fd > 100) Fd = 100;
				
				fdx = Fd*Math.cos(alpha);
				fdy = Fd*Math.sin(alpha);
				
				if(go instanceof RepulsorObject) {
					fdx = -fdx;
					fdy = -fdy;
				}
				
				//Primo caso so in basso a sinistra e go in alto a destra
				if(so.x < go.x && so.y > go.y) {
					so.velx = (float)(so.velx + fdx);
					so.vely = (float)(so.vely - fdy);
				}
				//Secondo caso so in alto a sinistra e go in basso a destra
				else if(so.x < go.x && so.y < go.y) {
					so.velx = (float)(so.velx + fdx);
					so.vely = (float)(so.vely + fdy);
				}
				//Terzo caso so in alto a destra e go in basso a sinistra
				else if(so.x > go.x && so.y < go.y) {
					so.velx = (float)(so.velx - fdx);
					so.vely = (float)(so.vely + fdy);
				}
				//Quarto caso so in basso a destra e go in alto a sinistra
				else if(so.x > go.x && so.y > go.y) {
					so.velx = (float)(so.velx - fdx);
					so.vely = (float)(so.vely - fdy);
				}
				
			}

			//Se fermo non faccio nulla
            if(so.velx != 0) {
                //caso velx > 0
                if(so.velx > 0 && (so.velx - Math.abs(so.velx*ATTRITO)) >=0) {
                    so.velx -= Math.abs(so.velx*ATTRITO);
                }
                //caso velx < 0
                else if(so.velx < 0 && (so.velx + Math.abs(so.velx*ATTRITO)) <=0) {
                        so.velx += Math.abs(so.velx*ATTRITO);
                    }
                else {
                    so.velx = 0;
                }
            }
           
            if(so.vely != 0) {
                //caso vely > 0
                if(so.vely > 0 && (so.vely - Math.abs(so.vely*ATTRITO)) >=0) {
                    so.vely -= Math.abs(so.vely*ATTRITO);
                }
                //caso vely < 0
                else if(so.vely < 0 && (so.vely + Math.abs(so.vely*ATTRITO)) <=0) {
                        so.vely += Math.abs(so.vely*ATTRITO);
                    }
                else {
                    so.vely = 0;
                }
            }
			
			//AGGIORNO LA POSIZIONE sulla base delle nuove velocità calcolate
			so.x = (float)(so.x + ((double)(so.velx)*(double)((double)delta/(double)1000)));
			so.y = (float)(so.y + ((double)(so.vely)*(double)((double)delta/(double)1000)));
			
			//AGGIORNO LA ROTAZIONE
			//so.rotation = (float)(Math.toDegrees(Math.atan2(0-so.velx,so.vely-0))); 
			

			//LIMITI UNIVERSO
			if(so.x+so.radius > ApplicationManager.SCREEN_W) {
				so.x = ApplicationManager.SCREEN_W-so.radius;
				so.velx = -so.velx;
				//so.destroyed = true;
			}
			
			if(so.y+so.radius > ApplicationManager.SCREEN_H){
				so.y = ApplicationManager.SCREEN_H-so.radius;
				so.vely = -so.vely;
				//so.destroyed = true;
			}
			
			if(so.x-so.radius < 0) {
				so.x = 0+so.radius;
				so.velx = -so.velx;
				//so.destroyed = true;
			}
			
			if(so.y-so.radius < 0){
				so.y = 0+so.radius;
				so.vely = -so.vely;
				//so.destroyed = true;
			}
			
		}
	
	}

	private static GravityObject existGOinThisRegion(GravityObject tgo) {
		for(GravityObject go: gravityObjects) {
			if(go.destroyed) continue;
			double dx =Math.abs(tgo.x - go.x);
			double dy = Math.abs(tgo.y - go.y);
			double distance = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
			if(distance <= go.power){
				return go;
			}
		}
		return null;
	}

	public static void queueGravityObject(GravityObject go) {
		tempGravityObjects.add(go);
	}
	public static void queueSpaceObject(SpaceObject so) {
		tempSpaceObjects.add(so);
	}
	public static void queueNonAttractedObject(NonAttractedObject nao) {
		tempNonAttractedObjects.add(nao);
	}
}
