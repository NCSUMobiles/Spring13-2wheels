package com.ncsu.edu.spinningwellness.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.Utils.Utils;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.entities.UserActivity;
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

public class ViewPastRideDetailsActivity extends BaseActivity {

	Ride ride;
	LinearLayout progressBar;
	UserActivity userActivity;
	LinearLayout userActivityDetailsForm; 
	LinearLayout userActivityDetailsError;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(ViewPastRideDetailsActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.PAST_RIDES);
		tabView.setCurrentView(R.layout.view_past_ride_details_activity);
		setContentView(tabView.render());

		progressBar = (LinearLayout) findViewById(R.id.rideDetailsSpinner);
		progressBar.setVisibility(View.VISIBLE);

		userActivityDetailsForm = (LinearLayout) findViewById(R.id.userActivityDetails);
		userActivityDetailsForm.setVisibility(View.INVISIBLE);

		userActivityDetailsError = (LinearLayout) findViewById(R.id.textViewPastRideDetailsError);
		userActivityDetailsError.setVisibility(View.INVISIBLE);
		addListenerOnButton();

		new getuserDetailsTask().execute();

		fillRideDetails();

		progressBar = (LinearLayout) findViewById(R.id.rideDetailsSpinner);
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setTitle() {
		ride = getIntent().getParcelableExtra("Ride");

		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("Ride Details");
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

	void fillUserActivity() {

		if(userActivity != null  && userActivity.getId() != null) {

			userActivityDetailsForm.setVisibility(View.VISIBLE);

			((TextView) findViewById(R.id.textViewPastRideAverageSpeed)).setText(((Double) userActivity.getAverageSpeed()).toString());
			((TextView) findViewById(R.id.textViewPastRideCadence)).setText(((Double) userActivity.getCadence()).toString());
			((TextView) findViewById(R.id.textViewPastRideCalories)).setText(((Double) userActivity.getCaloriesBurned()).toString());
			((TextView) findViewById(R.id.textViewPastRideDistanceCovered)).setText(((Double) userActivity.getDistaceCovered()).toString());
			((TextView) findViewById(R.id.textViewPastRideHeartRate)).setText(((Double) userActivity.getHeartRate()).toString());
			((TextView) findViewById(R.id.textViewPastRideTime)).setText(((Double) userActivity.getTimeOfRide()).toString());

			
		} 
	}

	public void addListenerOnButton() {

		final Context context = this;

		Button member_button  = (Button) findViewById(R.id.btnViewParticipants);
		member_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent member_intent = new Intent(context, ViewParticipantsForARideActivity.class);
				member_intent.putExtra("Ride", ride);
				member_intent.putExtra("Caller", "PastRide");
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

			} 
			else
			{
				userActivity = (UserActivity) result;
				System.out.println(userActivity);
				if(userActivity != null  && userActivity.getId() != null)
				{
					userActivityDetailsError.setVisibility(View.INVISIBLE);
					fillUserActivity();
				}
				else 
				{
					userActivityDetailsForm.setVisibility(View.INVISIBLE);			
					LinearLayout userActivityDetailsError = (LinearLayout) findViewById(R.id.textViewPastRideDetailsError);
					userActivityDetailsError.setVisibility(View.VISIBLE);
				}
			}
		}
	}
}