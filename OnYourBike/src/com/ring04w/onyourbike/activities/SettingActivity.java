package com.ring04w.onyourbike.activities;

import com.ring04w.onyourbike.BuildConfig;
import com.ring04w.onyourbike.OnYourBike;
import com.ring04w.onyourbike.R;
import com.ring04w.onyourbike.R.id;
import com.ring04w.onyourbike.R.layout;
import com.ring04w.onyourbike.helpers.Toaster;
import com.ring04w.onyourbike.model.Settings;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.YuvImage;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
        
        setupActionBar();
        
	}
	
	
	private void setupActionBar() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ActionBar actionBar = new getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	public void gotoHome(){
		Intent timer = 
				new Intent(getApplicationContext(), TimerActivity.class);
		timer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(timer);
		
	}

    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
        case android.R.id.home:
            gotoHome(); 
            return true; 
        default: 
            return super.onOptionsItemSelected(item);
        } 
    } 
 	
	
	public void onStop(){
		super.onStop();
		Settings settings = ((OnYourBike)getApplication()).getSettings();
		settings.setVibrate(this, vibrate.isChecked());
		
	}
	
	public void vibrateChanged(View view){
		Toast toast =new Toast(this);
		
		if (vibrate.isChecked()){
			toast.make(R.string.vibrate_on);
		}else{
			toast.make(R.string.vibrate_off);
		}
	}
	
	public void goBack(View view){
		finish();
	}
      

}
