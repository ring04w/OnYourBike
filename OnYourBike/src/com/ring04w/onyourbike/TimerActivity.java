package com.ring04w.onyourbike;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class TimerActivity extends ActionBarActivity {
	
	private static String CLASS_NAME;
	
	public TimerActivity(){
		CLASS_NAME = getClass().getName();
	} 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        
        TextView timer = (TextView) findViewById(R.id.timer);
//        Button start = (Button)findViewById(R.id.startOK);
        Log.d(CLASS_NAME, "Setting text.");
//        if(BuildConfig.DEBUG){
//        	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//        	.detectAll().penaltyLog().build());
//        	StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//        	.detectAll().penaltyLog().penaltyDeath().build());
//        }
        
         timer.setText("On your bike");
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
}
