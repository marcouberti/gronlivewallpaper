package com.marcouberti.gravitylivewallpaper;

import java.util.ArrayList;

import com.marcouberti.gravity.bean.GravityObject;
import com.marcouberti.gravity.bean.SpaceObject;
import com.marcouberti.gravity.core.ApplicationManager;
import com.marcouberti.gravity.core.UniverseManager;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyWallpaperService extends WallpaperService {

  @Override
  public Engine onCreateEngine() {
	    return new MyWallpaperEngine();
  }

  private class MyWallpaperEngine extends Engine {
	  	
	  	//bg drawing parameters
	  	private static final int COLOR_POINT_SIZE = 30;
		private static final int OBJECT_NUMBER = 50;
	  	private int[] array_x = new int[COLOR_POINT_SIZE];
	  	private int[] array_y = new int[COLOR_POINT_SIZE];
	  	private int[] array_radius = new int[COLOR_POINT_SIZE];
	  	private int[] array_color = new int[COLOR_POINT_SIZE];
	  	//end bg drawing parameters
	  	
	  	//LISTA PALETTE DISPONIBILI
	  	ArrayList<int[]> paletteList = new ArrayList<int[]>();
	  	
	  	//PALETTA COLORI SCELTA
	  	int[] palette = new int[5];
	  	
		private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private Paint paintCircles = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		private float initX, initY, radius;
		private boolean drawing = false;
	  
	    private final Handler handler = new Handler();
	    
	    private final Runnable drawRunner = new Runnable() {
	      public void run() {
	        draw();
	      }
	    };
	    
	    private int width = -1;
	    private int height = -1;
	    private boolean visible = true;
	    private int choosed_palette = 0;
	    private int choosed_shape = 0;
	
	    public MyWallpaperEngine() {
	      
	      paint.setAntiAlias(true);
	      paint.setColor(Color.WHITE);
	      paint.setStyle(Paint.Style.STROKE);
	      paint.setStrokeJoin(Paint.Join.ROUND);
	      paint.setStrokeWidth(10f);
	      
	      //inizializzo le palette
	      int[] palette0 = new int[5];
		  	palette0[0] = Color.rgb(3,7,16);
		  	palette0[1] = Color.rgb(41,50,33);
		  	palette0[2] = Color.rgb(82,119,128);
		  	palette0[3] = Color.rgb(214,203,157);
		  	palette0[4] = Color.rgb(238,221,151);
		  int[] palette1 = new int[5];
		  	palette1[0] = Color.rgb(43,35,39);
		  	palette1[1] = Color.rgb(144,103,122);
		  	palette1[2] = Color.rgb(255,96,101);
		  	palette1[3] = Color.rgb(255,132,100);
		  	palette1[4] = Color.rgb(254,175,97);
		  int[] palette2 = new int[5];
		  	palette2[0] = Color.rgb(200,216,219);
		  	palette2[1] = Color.rgb(137,176,179);
		  	palette2[2] = Color.rgb(247,247,247);
		  	palette2[3] = Color.rgb(135,129,120);
		  	palette2[4] = Color.rgb(209,193,171);
	  	  int[] palette3 = new int[5];
	  		palette3[0] = Color.rgb(16,1,8);
	  		palette3[1] = Color.rgb(71,4,45);
	  		palette3[2] = Color.rgb(201,43,104);
	  		palette3[3] = Color.rgb(237,107,133);
	  		palette3[4] = Color.rgb(251,224,213);
	  	  int[] palette4 = new int[5];
	  		palette4[0] = Color.rgb(253,229,167);
	  		palette4[1] = Color.rgb(246,199,129);
	  		palette4[2] = Color.rgb(255,179,145);
	  		palette4[3] = Color.rgb(255,236,113);
	  		palette4[4] = Color.rgb(225,64,98);
	  	  int[] palette5 = new int[5];
		  	palette5[0] = Color.rgb(251,213,212);
		  	palette5[1] = Color.rgb(188,218,166);
		  	palette5[2] = Color.rgb(241,167,216);
		  	palette5[3] = Color.rgb(173,185,209);
		  	palette5[4] = Color.rgb(230,140,165);
	  	  int[] palette6 = new int[5];
		  	palette6[0] = Color.rgb(254,252,255);
		  	palette6[1] = Color.rgb(174,247,255);
		  	palette6[2] = Color.rgb(150,175,153);
		  	palette6[3] = Color.rgb(75,75,75);
		  	palette6[4] = Color.rgb(45,79,80);
	      
		  paletteList.add(palette0);
		  paletteList.add(palette1);
		  paletteList.add(palette2);
		  paletteList.add(palette3);
		  paletteList.add(palette4);
		  paletteList.add(palette5);
		  paletteList.add(palette6);
		  
	      handler.post(drawRunner);  
	    }
	
	    @Override
	    public void onVisibilityChanged(boolean visible) {
	      this.visible = visible;
	      if (visible) {
	    	//refresh preferences
	    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyWallpaperService.this);
		    choosed_palette = Integer.parseInt(prefs.getString("palette", "0"));
		    choosed_shape = Integer.parseInt(prefs.getString("shape", "0"));
	    	
		    //inizializzo la paletta
		    palette[0] = paletteList.get(choosed_palette)[0];
		    palette[1] = paletteList.get(choosed_palette)[1];
		    palette[2] = paletteList.get(choosed_palette)[2];
		    palette[3] = paletteList.get(choosed_palette)[3];
		    palette[4] = paletteList.get(choosed_palette)[4];
		      
	    	//inizializzo il bg
		    for(int i=0; i<COLOR_POINT_SIZE; i++) {
		    	  array_color[i]=palette[(int)(Math.random()*5)];
		    	  int x=(int)(Math.random()*width);
		    	  int y=(int)(Math.random()*height);
		    	  array_x[i]=(int)(Math.random()*width);
		    	  array_y[i]=(int)(Math.random()*height);
		    	  array_radius[i]=(int)(Math.random()*200);
		    }  
		    initUniverse();
	        handler.post(drawRunner);
	      } else {
	        handler.removeCallbacks(drawRunner);
	      }
	    }
	    
	    public void onSurfaceDestroyed(SurfaceHolder holder) {
	        super.onSurfaceDestroyed(holder);
	        this.visible = false;
	        handler.removeCallbacks(drawRunner);
	    }

	    public void onSurfaceChanged(SurfaceHolder holder, int format,
	          int width, int height) {
		    	this.width = width;
		    	this.height = height;
		    	
		    	ApplicationManager.SCREEN_W = width;
	        	ApplicationManager.SCREEN_H = height;
	        	
		        super.onSurfaceChanged(holder, format, width, height);
	    }

	    private void initUniverse() {
		        UniverseManager.initialize();
		        
		        for(int i=0; i<OBJECT_NUMBER; i++) {
			        SpaceObject s1 = new SpaceObject(getApplicationContext(), choosed_shape);
			        s1.x = (int)(Math.random()*width);
			        s1.y = (int)(Math.random()*height);
			        s1.velx = 0;
			        s1.vely = 0;
			        s1.radius = 5+(float)(Math.random()*10);
			        UniverseManager.queueSpaceObject(s1);
		        }
		        
		        //Aggiungo una griglia di oggetto gravitazionale
		        
		        GravityObject go = new GravityObject();
		        go.power = 10;
		        go.x = width/2;
		        go.y = height/2;
		        go.radius = 5;
		        go.velx = 0;
		        go.vely = 0;
		        UniverseManager.queueGravityObject(go);
		}

		public void onTouchEvent(MotionEvent event) {
	    	  	int action = event.getAction();
				if (action==MotionEvent.ACTION_MOVE){
					float x = event.getX();
					float y = event.getY();
					radius = (float) Math.sqrt(Math.pow(x-initX, 2) + Math.pow(y-initY, 2));
					
					UniverseManager.repulsorX = x;
					UniverseManager.repulsorY = y;
					//UniverseManager.repulsorPower = radius;
					UniverseManager.repulsorActive = true;
				}
				else if (action==MotionEvent.ACTION_DOWN){
					initX = event.getX();
					initY = event.getY();
					radius = 1;
					drawing = false;//true
					UniverseManager.repulsorX = initX;
					UniverseManager.repulsorY = initY;
					UniverseManager.repulsorPower = 50;
					UniverseManager.repulsorActive = true;
					}
				else if (action==MotionEvent.ACTION_UP){
					drawing = false;
					UniverseManager.repulsorActive = false;
				}
	      }

	      private void draw() {
	        SurfaceHolder myThreadSurfaceHolder = getSurfaceHolder();
	        
	        Canvas canvas = null;
            try {
            	canvas = myThreadSurfaceHolder.lockCanvas(null);
                //synchronized (myThreadSurfaceHolder) {
                	UniverseManager.updateUniverse();
                	try {
        				canvas.drawColor(Color.BLACK);
        				
        				//disegno lo sfondo
        				for(int i=0; i<COLOR_POINT_SIZE; i++) {
        					paintCircles.setColor(array_color[i]);
        					paintCircles.setAlpha(150);
            				canvas.drawCircle(array_x[i], array_y[i], array_radius[i], paintCircles);
        				}
        				
        				/*
        				if(drawing){
        					canvas.drawCircle(initX, initY, radius, paint);
        				}
        		
        				
        				for(NonAttractedObject nao: UniverseManager.nonAttractedObjects) {
        					if(nao.destroyed) continue;
        					//bufferCanvas.drawCircle(so.x, so.y, so.radius, paint);
        					nao.draw(canvas);
        				}
        				*/
        				
        				for(GravityObject go: UniverseManager.gravityObjects) {
        					if(go.destroyed) continue;
        					//canvas.drawCircle(go.x, go.y, go.power, paint);
        					go.draw(canvas);
        				}
        				
        				for(SpaceObject so: UniverseManager.spaceObjects) {
        					if(so.destroyed) continue;
        					//bufferCanvas.drawCircle(so.x, so.y, so.radius, paint);
        					so.draw(canvas);
        				}
        			}catch (Exception e) {
        				e.printStackTrace();
        			}
                //}
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (canvas != null) {
                	myThreadSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
	        
	        handler.removeCallbacks(drawRunner);
	        if (visible) {
	          handler.postDelayed(drawRunner, 5);
	        }
	      }

  } 
  
  
} 
  