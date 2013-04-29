package com.ncsu.edu.spinningwellness.activities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.Utils.Utils;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.entities.User;
import com.ncsu.edu.spinningwellness.entities.UserActivity;
import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.spinningwellness.managers.UsersManager;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewRideDetailsActivity extends BaseActivity {

	Ride ride;
	Bundle Caller;
	LinearLayout progressBar, personalStats;
	TextView txtViewPastError;
	UserActivity userDetails; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		Caller=getIntent().getExtras();
		//Caller = getIntent().getExtra("Caller");
		//System.out.println("Caller in onCreate : "+ Caller.getString("Caller"));
		TabView tabView;
		
		tabProvider = new MyTabHostProvider(ViewRideDetailsActivity.this);
		
		System.out.println("Ride name : "+ride.getName());
		
		if(Caller.getString("Caller").equals("JoinRide"))
		{
			tabView = tabProvider.getTabHost(MenuConstants.JOIN_RIDES);
			tabView.setCurrentView(R.layout.view_ride_details_activity);
		
		}
		else
		{
			System.out.println("In past rides tab!");
			tabView = tabProvider.getTabHost(MenuConstants.PAST_RIDES);
			new getuserDetailsTask().execute();
			System.out.println("Executed async task");
			if(userDetails != null)
			{
				System.out.println("Activity Details - " + 
                    "Average Speed = " + userDetails.getAverageSpeed() + 
                    "Distance Covered = " + userDetails.getDistaceCovered());
            	tabView.setCurrentView(R.layout.view_past_ride_details_activity);
			}
			else
			{
				System.out.println("userDetails returned null");
				tabView.setCurrentView(R.layout.view_ride_details_activity);
				
			}	
			
			System.out.println("End of past tab");
		}
		
		setContentView(tabView.render());

		System.out.println("Loading spinner");
		progressBar = (LinearLayout) findViewById(R.id.rideDetailsSpinner);
		progressBar.setVisibility(View.VISIBLE);
		
		personalStats = (LinearLayout) findViewById(R.id.personalstats);
		txtViewPastError = (TextView) findViewById(R.id.txtViewPastError);
		
		
		if(Caller.getString("Caller").equals("PastRide") && (userDetails == null))
		{
			
			System.out.println("Settin visibility of layout");
			personalStats.setVisibility(View.VISIBLE);
			System.out.println("Settin visibility of textview");				
			txtViewPastError.setVisibility(View.VISIBLE);
		}
		else if(Caller.getString("Caller").equals("JoinRide"))
		{
			System.out.println("Settin visibility of layout");
			personalStats.setVisibility(View.INVISIBLE);
			System.out.println("Settin visibility of textview");				
			txtViewPastError.setVisibility(View.INVISIBLE);
		}
		
		addListenerOnButton();

		System.out.println("Calling fillRideDetails");
		fillRideDetails();
		if((Caller.getString("Caller").equals("PastRide")) && (userDetails != null))
		{
			System.out.println("Calling fillRideStats");
			fillRideStats();
		}
		progressBar = (LinearLayout) findViewById(R.id.rideDetailsSpinner);
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setTitle() {
		ride = getIntent().getParcelableExtra("Ride");
	//	System.out.println("Ride received :"+ride.getName());
		Bundle Caller=getIntent().getExtras();
		//System.out.println("Caller before : "+ Caller.getString("Caller"));
		
	//	System.out.println("Caller in after : "+ Caller);
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		if(Caller.getString("Caller").equals("JoinRide"))
			myTitleText.setText("Upcoming Ride Details");
		else
			myTitleText.setText("Past Ride Details");
	}

	void fillRideDetails() {

		
		((TextView) findViewById(R.id.textViewRideDetailsRideName)).setText(ride.getName());
		((TextView) findViewById(R.id.textViewRideDetailsSource)).setText(ride.getSource());
		((TextView) findViewById(R.id.textViewRideDetailsDestination)).setText(ride.getDest());
		((TextView) findViewById(R.id.textViewRideDetailsCreator)).setText(ride.getCreator());
		
		Date rideDate = Utils.convertStringToDate(ride.getStartTime());

		String[] formats = new String[] {"dd-MMM-yy", "HH:mm"};
		
		SimpleDateFormat dfForRideDate = new SimpleDateFormat(formats[0], Locale.US);
		((TextView) findViewById(R.id.textViewRideDetailsDate)).setText(dfForRideDate.format(rideDate));

		SimpleDateFormat dfForRideTime = new SimpleDateFormat(formats[1], Locale.US);
		((TextView) findViewById(R.id.textViewRideDetailsTime)).setText(dfForRideTime.format(rideDate));
	}

	void fillRideStats(){
		
	//	String format = "HH:mm";
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat tf = new DecimalFormat("#.##");
		
//		SimpleDateFormat RideDuration = new SimpleDateFormat(format, Locale.US);
		
		//Need to add appropriate data
		((TextView) findViewById(R.id.textViewDistanceCovered)).setText(df.format(userDetails.getDistaceCovered()) + " mi");
		((TextView) findViewById(R.id.textViewAverageSpeed)).setText(tf.format(userDetails.getAverageSpeed()) + " mi/hr");
		//To edit later
		((TextView) findViewById(R.id.textViewTime)).setText(ride.getSource());
		((TextView) findViewById(R.id.textViewCalories)).setText(df.format(userDetails.getCaloriesBurned()));
		((TextView) findViewById(R.id.textViewCadence)).setText(df.format(userDetails.getCadence()));
		((TextView) findViewById(R.id.textViewHeartRate)).setText(df.format(userDetails.getHeartRate()));		
		
	}
	
	public void addListenerOnButton() {

		final Context context = this;

		Button member_button  = (Button) findViewById(R.id.btnViewParticipants);
		member_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent member_intent = new Intent(context, ViewParticipantsForARideActivity.class);
				member_intent.putExtra("Ride", ride);
				startActivity(member_intent);   
			}
		});
	}	
	
	//AsynTask for getting user details
			public class getuserDetailsTask extends AsyncTask<Void, Void, UserActivity> {
		               Exception error;

		               protected UserActivity doInBackground(Void... params) {
		                       return UsersManager.viewPastActivityForARide(BaseActivity.username, ride.getId());
		               }

		               protected void onPostExecute(UserActivity result) {
		                       if(error != null){

		                       } else {
		                               UserActivity ua = result;
		                               if(ua.getId() != null)
		                                       System.out.println("User Activity " + ua.getCadence());
		                       }
		               }
		       }


}