package com.example.spinningwellness;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class RecordUserActivity extends BaseActivity implements OnClickListener {

	double lat1, long1, start_lat, start_long, dest_lat, dest_long;
    Location location, loc1, loc2; 
    float results_in_meter = 0;
    float results_in_miles = 0;
    static float prev_miles = 0;
    float record_miles = 0, avg_speed = 0;
    DecimalFormat df = new DecimalFormat("#.###");
	DecimalFormat df2 = new DecimalFormat("#.##");
    float[] dist = new float[3];
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    protected LocationManager locationManager;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 14 meters
    private static final long MIN_TIME_BW_UPDATES = 5000; // 5 sec
    boolean not_first_run = false, paused = false;
	
    private TextView welcome_user, disttext, speedtext;
     String username="",password="";

    //Button start_but, stop_but, reset_but;
	ImageButton play_but, pause_but, stop_but, cancel_but;
    Chronometer myChronometer;
    long time = 0; 
    //long final_time = 0;
    LocationListener myLocationListener;
    
    /***** Welcome Screen  *****/
    
        @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_user_activity);
		
		username = getIntent().getStringExtra("username");
		password = getIntent().getStringExtra("password");
	    TextView welcome_user = (TextView) findViewById(R.id.welcome_user);
	    welcome_user.setText("Welcome " + username + "!");
        
	    play_but= (ImageButton) findViewById(R.id.img_play);
	    play_but.setOnClickListener(this);
	        
	    pause_but= (ImageButton) findViewById(R.id.img_pause);
	    pause_but.setOnClickListener(this);
	    
	    stop_but = (ImageButton) findViewById(R.id.img_stop);
	    stop_but.setOnClickListener(this);
	    
	    cancel_but = (ImageButton) findViewById(R.id.img_cancel);
	    cancel_but.setOnClickListener(this);
	    
	  /*  start_but= (Button) findViewById(R.id.start);
        start_but.setOnClickListener(this);
        
        stop_but = (Button) findViewById(R.id.stop);
        stop_but.setOnClickListener(this);
        
        reset_but = (Button) findViewById(R.id.reset);
        reset_but.setOnClickListener(this);
       */
	    
        myChronometer = (Chronometer) findViewById(R.id.chronometer1);
        disttext = (TextView) findViewById(R.id.disttext);
        speedtext = (TextView) findViewById(R.id.speedtext);
		
/*		start_lat = loc1.getLatitude();
		start_long = loc1.getLongitude();               
*/
        }
    
    public Location getLocation() {
        try {
        	Context mContext = RecordUserActivity.this;
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            results_in_meter = 0;
            results_in_miles = 0;
            
            myLocationListener = new LocationListener() 
    		{
    			public void onLocationChanged(Location loc1) 
    		   	{
    				
    				 if(start_lat != 0 && start_long != 0) 
    				 { 
    					 try
    					 {
    						 Location.distanceBetween(start_lat, start_long, loc1.getLatitude(), loc1.getLongitude(), dist);
       				     }
    					 catch (IllegalArgumentException e) 
    					 {
    						 dist[0] = 0;		
    						 System.out.println("inside illegal arg");
    					 }
    					 results_in_meter += (float) dist[0];
    					 results_in_miles = (float) ((results_in_meter/1000) * 0.62137);
    				}

    				 	welcome_user = (TextView) findViewById(R.id.welcome_user);
    				 	float total_miles = results_in_miles + prev_miles;
       				 	record_miles = total_miles;
       				 	disttext.setText(" Distance: " + df.format(total_miles) + " mi");
    				 	//System.out.println(results_in_miles + "  " + total_miles);
    				 
    				 	start_lat = loc1.getLatitude();
    				 	start_long = loc1.getLongitude();

    		   	}

    			public void onProviderDisabled(String provider)
    		    {
    				// Update application if provider disabled.
    				
    		    }
    		    
    			public void onProviderEnabled(String provider)
    		    {
    				// Update application if provider enabled.
    		    }
    		    
    			public void onStatusChanged(String provider, int status, Bundle extras)
    		    {
    				// Update application if provider hardware status changed.
    		    }
    		    
    		};
    		
            
            if (!isGPSEnabled && !isNetworkEnabled) 
            {
                // no network provider is enabled
            	Toast.makeText(RecordUserActivity.this, "No Network/GPS", Toast.LENGTH_SHORT).show();
            } 
            
            else 
            {
                this.canGetLocation = true;
                
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);
                    Log.d("Network", "Network");
                    
                    if (locationManager != null) 
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat1 = location.getLatitude();
                            long1 = location.getLongitude();
                        }
                    }
                }
                
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) 
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) 
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat1 = location.getLatitude();
                                long1 = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }
 
    @Override
	public void onClick(View arg0) 
	{
		switch(arg0.getId())
		{
		case R.id.img_play:
			
			if(!not_first_run)
			{
				not_first_run = true;
			}
			else if (paused)
			{
				not_first_run = true;
				paused = false;
			}
			else
			{
				time = 0;
			}
			
			myChronometer.setBase(SystemClock.elapsedRealtime() + time);
			myChronometer.start();
			play_but.setVisibility(arg0.GONE);
			pause_but.setVisibility(arg0.VISIBLE);
				//start_but.setText("Pause");
				//start_but.setBackgroundColor(Color.GREEN);
			loc1 = getLocation();
			start_lat = loc1.getLatitude();
			start_long = loc1.getLongitude();
			cancel_but.setVisibility(arg0.GONE);
			stop_but.setClickable(true);
				//stop_but.setBackgroundColor(Color.rgb(156,208,0));
				//reset_but.setBackgroundColor(Color.rgb(156,208,0));
			
			break;
		
			//	else
		case R.id.img_pause:
			//start_but.setText("Start");
			paused = true;
			time = myChronometer.getBase() - SystemClock.elapsedRealtime();
			myChronometer.stop();
			pause_but.setVisibility(arg0.GONE);
			play_but.setVisibility(arg0.VISIBLE);
			cancel_but.setVisibility(arg0.GONE);
			stop_but.setClickable(true);
			prev_miles = results_in_miles;
			locationManager.removeUpdates(myLocationListener);
			start_lat = 0.0;
			start_long = 0.0;
			//start_but.setBackgroundColor(Color.rgb(156,208,0));
			//stop_but.setBackgroundColor(Color.rgb(156,208,0));
			//reset_but.setBackgroundColor(Color.rgb(156,208,0));
			
			break;
			
		case R.id.img_stop:
			final long final_time = SystemClock.elapsedRealtime() - myChronometer.getBase();
			myChronometer.stop();
			avg_speed = (results_in_miles)*1000*3600/final_time;
			//disttext.setText("Distance recorded: " + results_in_miles);
			speedtext.setText("Avg Speed: " + df.format(avg_speed) +" mi/hr");
			Toast.makeText(RecordUserActivity.this, "Distance & Avg Speed recorded", Toast.LENGTH_SHORT).show();
			pause_but.setVisibility(arg0.GONE);
			play_but.setVisibility(arg0.VISIBLE);
			cancel_but.setVisibility(arg0.VISIBLE);
			play_but.setClickable(false);
			stop_but.setClickable(false);
			locationManager.removeUpdates(myLocationListener);
			start_lat = 0;
			start_long = 0;
			prev_miles = 0;
			//stop_but.setBackgroundColor(Color.GREEN);
			//start_but.setText("Start");
			//start_but.setBackgroundColor(Color.rgb(156,208,0));
			//reset_but.setVisibility(arg0.VISIBLE);
			//reset_but.setBackgroundColor(Color.rgb(156,208,0));
			break;
		
		case R.id.img_cancel:
			myChronometer.setBase(SystemClock.elapsedRealtime());
			pause_but.setVisibility(arg0.GONE);
			play_but.setVisibility(arg0.VISIBLE);
			disttext.setText("Distance: 0");
			speedtext.setText("Avg Speed: 0");
			locationManager.removeUpdates(myLocationListener);
			prev_miles = 0;
			start_lat = 0;
			start_long = 0;
			play_but.setClickable(true);
			stop_but.setClickable(false);
			//reset_but.setBackgroundColor(Color.GREEN);
			//start_but.setBackgroundColor(Color.rgb(156,208,0));
			//stop_but.setBackgroundColor(Color.rgb(156,208,0));
			break;
		}
	   
	}   
	
	
	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Log Activity");		
	}
    
}
