package com.ring04w.onyourbike;

import android.R.bool;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Settings {
	private static String CLASS_NAME;
	
	private static String VIBRATE = "vibrate";
	protected boolean vibrateOn;
	
	
	public Settings(){
		CLASS_NAME = getClass().getName();
	}
	

	
	public boolean isVibrateOn(Activity activity){
		Log.d(CLASS_NAME, "isVibrateOn");
		SharedPreferences preferences = 
				activity.getPreferences(Activity.MODE_PRIVATE);
		
		
		return vibrateOn;
	}
	
	public void setVibrate(Activity activity, boolean vibrate){
		vibrateOn = vibrate;
		SharedPreferences preferences = 
				activity.getPreferences(Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(VIBRATE, vibrate);
		editor.apply();
		
	}


}
