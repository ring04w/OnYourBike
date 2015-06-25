package com.ring04w.onyourbike;

import android.app.Activity;
import android.content.Intent;
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


public class TimerActivity extends ActionBarActivity {
	private static long UPDATE_EVERY = 200;
	
	private static String CLASS_NAME;
	protected TextView counter;
	protected Button start;
	protected Button stop;
	protected long startedAt;
	protected long lastStopped;
	protected Handler handler;
	protected UpdateTimer updateTimer;
	protected boolean timerRunning;
	protected Vibrator vibrator;
	protected long lastSeconds;

	
	
	public TimerActivity(){
		CLASS_NAME = getClass().getName();
	} 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        
      
        
        counter = (TextView) findViewById(R.id.timer);
        start = (Button)findViewById(R.id.start_button);  
        stop = (Button)findViewById(R.id.stop_button);
        
        timerRunning = false;
        enableButtons();
        Log.d(CLASS_NAME, "Setting text.");
              
        
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
    		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
    		long[] pattern = {800, 50, 400, 30};
    		vibrator.vibrate(pattern, 2);
		}
    	return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	Log.d(CLASS_NAME, "Showing menu.");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void clickedStart(View view){
    	Log.d(CLASS_NAME, "Clicked the start button");
    	
    	timerRunning = true;
    	startedAt = System.currentTimeMillis();
    	enableButtons();
    	
    	handler = new Handler();
    	updateTimer = new UpdateTimer();
    	handler.postDelayed(updateTimer, UPDATE_EVERY);
    	
    	
    	setTimeDisplay();
    	
    }
    
    public void clickedStop(View view){
    	Log.d(CLASS_NAME, "Clicked the stop button");
    	
    	timerRunning = false;
    	lastStopped = System.currentTimeMillis();
    	enableButtons();
    	
    	handler.removeCallbacks(updateTimer);
    	updateTimer = null;
    	handler = null;
    	setTimeDisplay();
    	
    }
    
    public void clickedSettings(View view){
    	Log.d(CLASS_NAME, "Clicked the setings button");
    	
    	Intent settingsIntent = new Intent(getApplicationContext(),
    			SettingActivity.class);
    	startActivity(settingsIntent);
    
    	
    	
    }
    
    @Override
    public void onStart(){
    	vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
    	if (vibrator == null){
    		Log.w(CLASS_NAME, "No vibration service exists.");
		}
    	super.onStart();
    	Log.d(CLASS_NAME, "onStart");
    	
    	if (timerRunning){
    		handler = new Handler();
    		updateTimer = new UpdateTimer();
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
    	setTimeDisplay();
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	Log.d(CLASS_NAME, "onStop");
    	
    if (timerRunning){
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
    	start.setEnabled(!timerRunning);
    	stop.setEnabled(timerRunning);
    }
    
    public void setTimeDisplay(){
    	
    	String display;
    	 long timeNow; 
    	 long seconds;
    	 long minutes;
    	 long hours;
    	 long diff;
    	
    	if(timerRunning){
    		timeNow = System.currentTimeMillis();
    	}else{
    		timeNow = lastStopped;
    	}
    	
    	diff  = timeNow - startedAt;
    	if (diff < 0){
    		diff = 0;
			
		}
    	
    	seconds = diff / 1000;
    	minutes = seconds / 60;
    	hours = minutes / 60;
    	seconds = seconds % 60;
    	minutes = minutes % 60;
    	
    	display = String.format("%d", hours) + ":"
    			+ String.format("%02d", minutes) + ":"
    			+ String.format("%02d", seconds);
    	counter.setText(display);
    	
    }
    
    
    public void vibrateCheck(){
    	long timeNow = System.currentTimeMillis();
    	long diff = timeNow - startedAt;
    	long seconds = diff / 1000;
    	long minutes = seconds / 60;
    	
    	
    	seconds = seconds % 60;
    	minutes = minutes % 60;
    	
    	if (vibrator != null && seconds == 0 && seconds != lastSeconds){
    		long[] once = {0, 100};
    		long[] twice = {0, 100, 400, 100};
    		long[] thrice = {0, 100, 400, 100, 400, 100};
    		
    		if (seconds == 0){
    			vibrator.vibrate(thrice, -1);
			}
    		else if (seconds % 15 == 0){
    			vibrator.vibrate(twice, -1);
				
			}
    		else if(seconds % 5 == 0){
    			vibrator.vibrate(once, -1);
    		}
			
		}
    	
    	lastSeconds = seconds;
    	
    }

    


class UpdateTimer implements Runnable{
	Activity activity;
	
	public void run(){
		setTimeDisplay();
		if(timerRunning){
			vibrateCheck();
			
		}

		
		if (handler != null){
			handler.postDelayed(this, UPDATE_EVERY);
		}
	}
	}




}



