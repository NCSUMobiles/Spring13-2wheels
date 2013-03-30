package com.example.spinningwellness;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class WelcomeActivity extends Activity {

	double lat1, long1, start_lat, start_long, dest_lat, dest_long;
    Location location, loc1, loc2; 
    float results_in_meter = 0;
    float results_in_miles = 0;
    DecimalFormat df = new DecimalFormat("#.###");
    float[] dist = new float[3];
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    protected LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 3; // 5 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 5000; // 5 sec
    
    private TextView welcome_user, disttext;
    String username,password;
    

    /*** Welcome Screen  *****/
    
        @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		username = getIntent().getStringExtra("username");
		password = getIntent().getStringExtra("password");
	    TextView welcome_user = (TextView) findViewById(R.id.welcome_user);
	    welcome_user.setText("Welcome " + username + "!");
		final Location loc1 = getLocation();
		
		start_lat = loc1.getLatitude();
		start_long = loc1.getLongitude();               
		
	/*	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
	*/
	}
    
    public Location getLocation() {
        try {
        	Context mContext = WelcomeActivity.this;
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            
            LocationListener myLocationListener = new LocationListener() 
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
    					 }
    					 results_in_meter += (float) dist[0];
    					 results_in_miles = (float) ((results_in_meter/1000) * 0.62137);
    					 
    					 }

    				 welcome_user = (TextView) findViewById(R.id.welcome_user);
    				 disttext = (TextView) findViewById(R.id.disttext);
    				 
    //				 welcome_user.setText(start_lat + " " + start_long);
    				 disttext.setText("Distance: " + df.format(results_in_miles) + " mi");
    				    
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
    		
            
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat1 = location.getLatitude();
                            long1 = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
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
 
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.welcome_menu, menu);
        return true;
    }
 
 
    /***** Menu Settings ******/
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent i;
        switch (item.getItemId())
        {
        case R.id.menu_join:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(WelcomeActivity.this, "Join rides is Selected", Toast.LENGTH_SHORT).show();
            i = new Intent(getApplicationContext(), JoinActivity.class);
			startActivity(i);
            return true;
 
        case R.id.menu_past:
            Toast.makeText(WelcomeActivity.this, "Past Rides is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_upcoming:
            Toast.makeText(WelcomeActivity.this, "Upcoming is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_activity:
            Toast.makeText(WelcomeActivity.this, "Activity is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_about:
            Toast.makeText(WelcomeActivity.this, "About is Selected", Toast.LENGTH_SHORT).show();
            return true;
            
        case R.id.menu_post_blog:
        	Toast.makeText(WelcomeActivity.this, "Post Blog is Selected", Toast.LENGTH_SHORT).show();
        	i = new Intent(getApplicationContext(), JoinActivity.class);
			i.putExtra("username",username);
			i.putExtra("password",password);
			startActivity(i);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
    
}
