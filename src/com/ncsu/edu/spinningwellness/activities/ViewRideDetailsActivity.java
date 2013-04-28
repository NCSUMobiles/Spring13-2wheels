package com.ncsu.edu.spinningwellness.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.Utils.Utils;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewRideDetailsActivity extends BaseActivity {

	Ride ride;
	Bundle Caller;
	LinearLayout progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		Caller=getIntent().getExtras();
		//Caller = getIntent().getExtra("Caller");
		//System.out.println("Caller in onCreate : "+ Caller.getString("Caller"));
		TabView tabView;
		
		tabProvider = new MyTabHostProvider(ViewRideDetailsActivity.this);
		if(Caller.getString("Caller").equals("JoinRide"))
			tabView = tabProvider.getTabHost(MenuConstants.JOIN_RIDES);
		else
			tabView = tabProvider.getTabHost(MenuConstants.PAST_RIDES);
		
		tabView.setCurrentView(R.layout.view_ride_details_activity);
		setContentView(tabView.render());

		progressBar = (LinearLayout) findViewById(R.id.rideDetailsSpinner);
		progressBar.setVisibility(View.VISIBLE);

		addListenerOnButton();

		fillRideDetails();
	//	fillRideStats();

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

//	public void fillRideStats(){
//		
//		((TextView) findViewById(R.id.textViewRideDetailsRideName)).setText(ride.getName());
//		((TextView) findViewById(R.id.textViewRideDetailsSource)).setText(ride.getSource());
//		
//		
//	}
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
}