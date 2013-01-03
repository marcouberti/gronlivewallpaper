package com.marcouberti.gravitylivewallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class SetWallpaperActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    //Open wallpaper configurator
		setWallpaper();
		finish();
  }

  public void setWallpaper() {
	    Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
	    intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
	        new ComponentName(this, MyWallpaperService.class));
	    startActivity(intent);
  }
} 