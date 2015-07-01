package com.ring04w.onyourbike.activities;

import com.ring04w.onyourbike.OnYourBike;
import com.ring04w.onyourbike.R;
import com.ring04w.onyourbike.R.id;
import com.ring04w.onyourbike.R.layout;
import com.ring04w.onyourbike.R.menu;
import com.ring04w.onyourbike.model.TimerState;
import com.ring04w.onyourbike.model.Settings;

import android.app.Activity;
import android.content.Intent;
//import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TimerActivity extends Activity {
	private static String CLASS_NAME;

	private static long UPDATE_EVERY = 200;
	
	public TextView counter;
	private Button start;
	private Button stop;
	private Handler handler;
	private UpdateTimer updateTimer;
	private Vibrator vibrate;
	private long lastSeconds;

	private TimerState timer;
	
	public TimerActivity(){
		CLASS_NAME = getClass().getName();
		timer = new TimerState();
	} 

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        
      
        
        counter = (TextView) findViewById(R.id.timer);
        start = (Button)findViewById(R.id.start_button);  
        stop = (Button)findViewById(R.id.stop_button);
        
        vibrate = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        
        if (vibrate == null){
        	Log.w(CLASS_NAME, "No vibration service exists.");
		}
        
        timer.reset();
        
              
        
//        if(BuildConfig.DEBUG){
//        	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//        	.detectAll().penaltyLog().build());
//        	StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//        	.detectAll().penaltyLog().penaltyDeath().build());
//        }
        
        //        start.setText("Start");
    }
    
    public boolean onTouchEvent(MotionEvent event){
    	if (event.getAction() == MotionEvent.ACTION_DOWN){
    		vibrate= (Vibrator)getSystemService(VIBRATOR_SERVICE);
    		long[] pattern = {800, 50, 400, 30};
    		vibrate.vibrate(pattern, 2);
		}
    	return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	Log.d(CLASS_NAME, "Showing menu.");
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()){
		case R.id.menu_settings:
			clickedSettings(null);
			return true;
		default;
		return super.onOptionsItemSelected(item);
		}
    }
    
    public void clickedStart(View view){
    	Log.d(CLASS_NAME, "Clicked the start button");
    	
    	timer.start();
    	enableButtons();
    	
    	handler = new Handler();
    	updateTimer = new UpdateTimer(this);
    	handler.postDelayed(updateTimer, UPDATE_EVERY);
    	timer.display();
    
    	
    }
    
    public void clickedStop(View view){
    	Log.d(CLASS_NAME, "Clicked the stop button");
    	
    	timer.stop();
    	enableButtons();
    	
    	
    	handler.removeCallbacks(updateTimer);
    	updateTimer = null;
    	handler = null;
    	timer.display();
    	
    }
    
    public void clickedSettings(View view){
    	Log.d(CLASS_NAME, "Clicked the setings button");
    	
    	Intent settingsIntent = new Intent(getApplicationContext(),
    			SettingActivity.class);
    	startActivity(settingsIntent);
    
    	
    	
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	
    	if (timer.isRunning()){
    		handler = new Handler();
    		updateTimer = new UpdateTimer(this);
    		handler.postDelayed(updateTimer, UPDATE_EVERY);
			
		}
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	Log.d(CLASS_NAME, "onPause");
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	Log.d(CLASS_NAME, "onResume");
    	
    	enableButtons();
    	timer.display();
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	Log.d(CLASS_NAME, "onStop");
    	
    if (timer.isRunning()){
    	handler.removeCallbacks(updateTimer);
    	updateTimer = null;
    	handler = null;
	}
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	Log.d(CLASS_NAME, "onDestroy");
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	Log.d(CLASS_NAME, "onRestart");
    }
    	
    	
    	public void enableButtons(){
    	Log.d(CLASS_NAME, "enableButtons");
    	start.setEnabled(!timer.isRunning());
    	stop.setEnabled(timer.isRunning());
    }
    
    public void vibrateCheck(){
    	long timeNow = System.currentTimeMillis();
    	long diff = timer.elapsedTime();
    	long seconds = diff / 1000;
    	long minutes = seconds / 60;
    	
    	
    	seconds = seconds % 60;
    	minutes = minutes % 60;
    	
    	if (vibrate != null && seconds == 0 && seconds != lastSeconds){
    		long[] once = {0, 100};
    		long[] twice = {0, 100, 400, 100};
    		long[] thrice = {0, 100, 400, 100, 400, 100};
    		
    		if (seconds == 0){
    			vibrate.vibrate(thrice, -1);
			}
    		else if (seconds % 15 == 0){
    			vibrate.vibrate(twice, -1);
				
			}
    		else if(seconds % 5 == 0){
    			vibrate.vibrate(once, -1);
    		}
			
		}
    	
    	lastSeconds = seconds;
    	
    }

    


class UpdateTimer implements Runnable{
	Activity activity;
	
	public UpdateTimer(Activity activity){
		this.activity = activity;
	}
	
	public void run(){
		Settings settings = ((OnYourBike)getApplication()).getSettings();
		
		timer.display();
		if (timer.isRunning()){
			if (settings.isVibrateOn(activity)){
				vibrateCheck();
			}
			
		}
		if (handler != null){
			handler.postDelayed(this, UPDATE_EVERY);
			
		}
		
		}

		
}

}



