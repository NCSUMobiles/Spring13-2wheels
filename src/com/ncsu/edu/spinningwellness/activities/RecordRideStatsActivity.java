package com.ncsu.edu.spinningwellness.activities;

import java.text.DecimalFormat;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;
import com.sun.org.apache.xml.internal.utils.StopParseException;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RecordRideStatsActivity extends BaseActivity {

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 14 meters
	private static final long MIN_TIME_BW_UPDATES = 5000; // 5 sec

	Ride ride;
	DecimalFormat df = new DecimalFormat("#.###");
	DecimalFormat tf = new DecimalFormat("#.##");

	TextView textViewDistance, textViewAverageSpeed, textViewRideName;
	ImageButton btnPlay, btnPause, btnStop;
	Chronometer chronometer;

	LocationManager locationManager;
	String locationManagerType;
	LocationListener myLocationListener;

	double previousLatitude = 0.0, previousLongitude = 0.0;
	double distanceCovered_met = 0.0;
	double distanceCovered = 0.0;
	long timeOfRide = 0;
	float timeRead_mins = 0;
	double averageSpeed = 0.0;

	boolean isGPSEnabled = false, isNetworkEnabled = false;
	boolean isStarted = false;
	boolean wasPaused = false;
	boolean wasStopped = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(RecordRideStatsActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.JOIN_RIDES);
		tabView.setCurrentView(R.layout.record_ride_stats_activity);
		setContentView(tabView.render());

		locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		registerLocationListener();

		if (!isGPSEnabled && !isNetworkEnabled) {
			Toast.makeText(RecordRideStatsActivity.this, "No Network/GPS", Toast.LENGTH_SHORT).show();
		} else if(isGPSEnabled) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);
			locationManagerType = LocationManager.GPS_PROVIDER;
		} else if(isNetworkEnabled) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);
			locationManagerType = LocationManager.NETWORK_PROVIDER;
		}
		
		setPageElementsAndOnClickListeners();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	
		if(isStarted) {
			timeOfRide = chronometer.getBase() - SystemClock.elapsedRealtime();
		}
		
		savedInstanceState.putDouble("previousLatitude", previousLatitude);
		savedInstanceState.putDouble("previousLongitude", previousLongitude);
		savedInstanceState.putDouble("distanceCovered", distanceCovered);
		savedInstanceState.putLong("timeOfRide", timeOfRide);
		
		savedInstanceState.putBoolean("isStarted", isStarted);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		previousLatitude = savedInstanceState.getDouble("previousLatitude");
		previousLongitude = savedInstanceState.getDouble("previousLongitude");
		distanceCovered = savedInstanceState.getDouble("distanceCovered");
		
		//Adjust time of ride in chronometer
		timeOfRide = savedInstanceState.getLong("timeOfRide");
		
		isStarted = savedInstanceState.getBoolean("isStarted");
		if(isStarted) {
			btnPlay.setVisibility(View.INVISIBLE);
			btnPause.setVisibility(View.VISIBLE);
			chronometer.setBase(SystemClock.elapsedRealtime() + timeOfRide);
			chronometer.start();
		} else {
			btnPlay.setVisibility(View.VISIBLE);
			btnPause.setVisibility(View.INVISIBLE);
			
			//Hack to make sure that chronometer shows correct reading even when 
			//the phone's orientation is changes and chronometer not on
			chronometer.setBase(SystemClock.elapsedRealtime() + timeOfRide);
			chronometer.start();			
			timeOfRide = chronometer.getBase() - SystemClock.elapsedRealtime();
			chronometer.stop();
		}
	}
	
	@Override
	protected void setTitle() {
		ride = getIntent().getParcelableExtra("Ride");

		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("Record Ride");		
	}

	private void registerLocationListener() {
		myLocationListener = new LocationListener() {
			public void onLocationChanged(Location location) {

				System.out.println("in on location changed");
				
				System.out.println("isStarted = " + isStarted);
				
				if(isStarted) {
					
					System.out.println("inside activity started");
					
					if(previousLatitude == 0.0 && previousLongitude == 0.0) {
						previousLatitude = location.getLatitude();
						previousLongitude = location.getLongitude();
						distanceCovered = 0.0;
						
						System.out.println("previois latitude 0");
						
					} else { 
						
						System.out.println("has some value");

						float[] dist = {0};
						
						try {
							Location.distanceBetween(previousLatitude, previousLongitude, location.getLatitude(), location.getLongitude(), dist);
							
							System.out.println("dis = " + dist[0]);
							
						} catch(IllegalArgumentException e) {
							System.out.println("inside catch");
							
							dist[0] = 0;
						}

						previousLatitude = location.getLatitude();
						previousLongitude = location.getLongitude();

						distanceCovered_met += (float) dist[0];  //distance in meters
						distanceCovered = (float) ((distanceCovered_met/1000) * 0.62137);   //distance in miles
						
						System.out.println("dc = " + distanceCovered);
				
						timeRead_mins = readChronometer();  //time read in mins
						averageSpeed = distanceCovered/(timeRead_mins/60); //Avg speed in miles/hr
						
						setDistanceCovered();
						setAvgSpeed();
					}
				}
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}
		};
	}

	private void setPageElementsAndOnClickListeners() {

		chronometer = (Chronometer) findViewById(R.id.chronometer);

		textViewDistance = (TextView) findViewById(R.id.textViewDistance);
		textViewAverageSpeed = (TextView) findViewById(R.id.textViewAverageSpeed);
		
		textViewRideName = (TextView) findViewById(R.id.textViewRecordRideDetailsRideName);
		textViewRideName.setText(ride.getName());

		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if(wasStopped){
					timeOfRide = 0;
					wasStopped = false;
				}
				
				chronometer.setBase(SystemClock.elapsedRealtime() + timeOfRide);
				chronometer.start();
				
				isStarted = true;
				
				locationManager.requestLocationUpdates(locationManagerType, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, myLocationListener);

				btnPlay.setVisibility(View.INVISIBLE);
				btnPause.setVisibility(View.VISIBLE);
			}
		});
		btnPlay.setVisibility(View.VISIBLE);

		btnPause = (ImageButton) findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				timeOfRide = chronometer.getBase() - SystemClock.elapsedRealtime();
				chronometer.stop();

				isStarted = false;
				wasPaused = true;
				
				locationManager.removeUpdates(myLocationListener);

				btnPlay.setVisibility(View.VISIBLE);
				btnPause.setVisibility(View.INVISIBLE);				
			}
		});
		btnPause.setVisibility(View.INVISIBLE);

		btnStop = (ImageButton) findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {			
				if(wasPaused){
					//use the previous timeOfRide value
				}
				else {
				timeOfRide = chronometer.getBase() - SystemClock.elapsedRealtime();
				}
				
				chronometer.stop();
				
				isStarted = false;
				wasPaused = false;
				wasStopped = true;
				
		//		Toast.makeText(RecordRideStatsActivity.this, "Time: " + tf.format(readChronometer()) + "mins", Toast.LENGTH_SHORT).show();
				locationManager.removeUpdates(myLocationListener);

				//Redirect to next page with all the fields in intent
				Intent i = new Intent(getApplicationContext(), LogRideDetailsActivity.class);
				i.putExtra("Ride", ride);
				i.putExtra("DistanceCovered", distanceCovered);
				i.putExtra("AverageSpeed", averageSpeed);
				i.putExtra("TimeOfRide", tf.format(readChronometer()));    //Jay
				startActivity(i);
			}
		});
	}

	
	private float readChronometer(){
		float stoppedMins = 0;
		String chronoText = chronometer.getText().toString();
		String array[] = chronoText.split(":");

		if (array.length == 2) {
	        stoppedMins = Integer.parseInt(array[0]) + (float) (Float.parseFloat(array[1]) / 60);
	      } 
		else if (array.length == 3) {
	        stoppedMins = Integer.parseInt(array[0]) * 60 + Integer.parseInt(array[1]) + (float) (Float.parseFloat(array[2]) / 60);
	      }
	      
	//	Toast.makeText(RecordRideStatsActivity.this, "Time: " + stoppedMins + "mins", Toast.LENGTH_SHORT).show();
		return stoppedMins;
	}

	
	private void setDistanceCovered() {
		textViewDistance.setText("Distance: " + df.format(distanceCovered) + " mi");
	}
	
	private void setAvgSpeed() {
		textViewAverageSpeed.setText("Avg Speed: " + tf.format(averageSpeed) + " mi/hr");
	}
	
	
		
}