package com.ring04w.onyourbike;

import com.ring04w.onyourbike.model.Settings;

import android.app.Application;

public class OnYourBike extends Application{
	protected Settings settings;
	
	public Settings getSettings(){
		if(settings == null){
			settings = new Settings();
		}
		return settings;
	}

	
	public void setSettings(Settings settings){
		this.settings = settings;
	}
}
