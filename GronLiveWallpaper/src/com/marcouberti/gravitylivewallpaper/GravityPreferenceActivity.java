package com.marcouberti.gravitylivewallpaper;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class GravityPreferenceActivity extends PreferenceActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.prefs);
  }
} 