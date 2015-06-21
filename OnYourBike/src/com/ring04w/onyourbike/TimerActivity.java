package com.ring04w.onyourbike;

import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    	
    	
    	
    	setTimeDisplay();
    	
    }
    
    public void clickedStop(View view){
    	Log.d(CLASS_NAME, "Clicked the stop button");
    	
    	timerRunning = false;
    	lastStopped = System.currentTimeMillis();
    	enableButtons();
    	setTimeDisplay();
    	
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
    
    	
}


class UpdateTimer implements Runnable{
	public void run(){
		
	}
}
