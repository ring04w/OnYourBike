package com.ring04w.onyourbike;

import android.app.Activity;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SettingActivity extends Activity{
	private static String CLASS_NAME;
	
	private CheckBox vibrate;
	
	public SettingActivity(){
		CLASS_NAME = getClass().getName();
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        
        vibrate = (CheckBox)findViewById(R.id.vibrate_checkbox);
        
        Settings settings = ((OnYourBike)getApplication()).getSettings();
        vibrate.setChecked(settings.isVibrateOn(this));
        
	}
	
	public void onStop(){
		super.onStop();
		Settings settings = ((OnYourBike)getApplication()).getSettings();
		settings.setVibrate(this, vibrate.isChecked());
		
	}
      

}
