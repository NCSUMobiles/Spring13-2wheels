package com.ncsu.edu.spinningwellness.activities;

import java.util.Calendar;
import java.util.Date;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateRideActivity extends BaseActivity {
	
	LinearLayout progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(CreateRideActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.CREATE_RIDE);
		tabView.setCurrentView(R.layout.create_new_ride_activity);
		setContentView(tabView.render());			

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		addListenerForCreateRideButton();

		progressBar = (LinearLayout) findViewById(R.id.createRideSpinner);
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Create Ride");		
	}

	private void addListenerForCreateRideButton() {

		//Listener for create ride button
		Button button  = (Button) findViewById(R.id.btnCreateRide);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				validateUserInputAndCallAsyncTask();
			}
		});
	}

	private void validateUserInputAndCallAsyncTask() {

		//Add validation code herer
		boolean isValid = true;
		if(isValid) {
			progressBar.setVisibility(View.VISIBLE);
			LinearLayout createRideForm = (LinearLayout) findViewById(R.id.createRideForm);
			createRideForm.setVisibility(View.INVISIBLE);
			
			new CreateRideTask().execute();
		} else {
			//Show the error message to user
			System.out.println("Error in user input");
		}
	}

	private void moveToJoinRidesPage() {
		Intent joinRidesIntent = new Intent(CreateRideActivity.this, JoinRidesActivity.class); 
		CreateRideActivity.this.startActivity(joinRidesIntent);
	}

	private class CreateRideTask extends AsyncTask<Void, Void, Ride> {

		Exception error;

		protected Ride doInBackground(Void... params) {

			String rideName = ((TextView) findViewById(R.id.textViewCreateRideRideName)).getText().toString();
			String source = ((TextView) findViewById(R.id.textViewCreateRideSource)).getText().toString();
			String destination = ((TextView) findViewById(R.id.textViewCreateRideDestination)).getText().toString();

			int day = ((DatePicker) findViewById(R.id.createRideDatePicker)).getDayOfMonth();
			int month = ((DatePicker) findViewById(R.id.createRideDatePicker)).getMonth() + 1;
			int year = ((DatePicker) findViewById(R.id.createRideDatePicker)).getYear();

			int hour = ((TimePicker) findViewById(R.id.createRideTimePicker)).getCurrentHour();
			int minute = ((TimePicker) findViewById(R.id.createRideTimePicker)).getCurrentMinute();

			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hour, minute);
			Date startDate = calendar.getTime();

			String result = RidesManager.createRide(rideName, source, destination, startDate, username);
			if(!result.equalsIgnoreCase("success")) {
				error = new Exception();
			}

			return null;
		}

		protected void onPostExecute(Ride result) {

			//Redirect to join rides page
			if(error == null) {
				moveToJoinRidesPage();
			} else {
				//Display the error to user
			}
		}
	}
}