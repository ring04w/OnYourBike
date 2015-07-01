package com.ring04w.onyourbike.model;

import java.security.interfaces.DSAKey;

import android.view.Display;

import com.ring04w.onyourbike.activities.TimerActivity;

public class TimerState {
	private String CLASS_NAME;
	
	protected long startedAt;
	private long lastStopped;
	protected boolean running;
	private long lastTime;
	
	
	public TimerState(){
		CLASS_NAME = getClass().getName();
	}
	
	public void start(){
		running = true;
		
		startedAt = System.currentTimeMillis();
	}
	
	public void stop(){
		running = false;
		
		lastStopped = System.currentTimeMillis();
	}
	
	public boolean isRunning(){
		return running;	
		
	}
	
	public void reset(){
		running = false;
		startedAt = System.currentTimeMillis();
		lastStopped = 0;
	}
	
	
	public long elapsedTime(){
		long timeNow;
		if (isRunning()){
			timeNow = System.currentTimeMillis();
		}else{
			timeNow = lastStopped;
		}
	
		return timeNow - startedAt;
	}
	
	public String display(){
		long seconds;
		long minutes;
		long hours;
		long diff;
		
		
		diff = elapsedTime();		
		if (diff < 0){
			diff = 0;
			
		}
		seconds = diff / 1000;
		minutes = seconds / 60;
		hours = minutes / 60;
		
		
		seconds = seconds % 60;
		minutes = minutes % 60;
		
		String timeDisplay =  String.format("%d", hours) + ":"
				+ String.format("%02d", minutes) + ":" 
				+ String.format("%02d", seconds);
		
		return timeDisplay;
		
		
	}


	
}
