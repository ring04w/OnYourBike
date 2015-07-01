package com.ring04w.onyourbike.helpers;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Toaster {
	public String CLASS_NAME;
	private static int TOAST_DURATION = Toast.LENGTH_SHORT;
	private final Context context;
	
	public Toaster(Context context){
		CLASS_NAME = getClass().getName();
		
		this.context = context;
	}
	
	public void make(int resource){
		Toast toast = Toast.makeText(context, resource, TOAST_DURATION);
		
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		
		
		toast.show();
	}
	
	
}
