package com.ncsu.edu.spinningwellness.activities;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Date;

import net.bican.wordpress.Page;
import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import redstone.xmlrpc.XmlRpcFault;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.UsersManager;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogRideDetailsActivity extends BaseActivity {

	Ride ride;

	double distanceCovered, averageSpeed = 0.0, cadence, heartRate, timeOfRide = 0;

	TextView textViewRideName, textViewDistanceCovered, textViewTimeOfRide, textViewAverageSpeed, textViewHeartRate, textViewCadence, textViewExperience;
	Button btnSubmit;
	
	LinearLayout progressBar;
	static TextView textViewLoginError;
	
	DecimalFormat df = new DecimalFormat("#.###");
	DecimalFormat tf = new DecimalFormat("#.##");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle b = getIntent().getExtras();
		distanceCovered = b.getDouble("DistanceCovered");
		averageSpeed = b.getDouble("AverageSpeed");
		timeOfRide = b.getDouble("TimeOfRide");

		//Draw menu
		tabProvider = new MyTabHostProvider(LogRideDetailsActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.JOIN_RIDES);
		tabView.setCurrentView(R.layout.log_ride_details_activity);
		setContentView(tabView.render());

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setFormFields();
		textViewLoginError = (TextView) findViewById(R.id.textViewLogRideError);
		textViewLoginError.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setTitle() {
		ride = getIntent().getParcelableExtra("Ride");
		
		

		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("Log Ride Details");
	}

	private void validateUserInputAndCallAsyncTask() {

		//Add validation code herer
		boolean isValid = true;
		if(isValid) {
			progressBar.setVisibility(View.VISIBLE);
			LinearLayout createRideForm = (LinearLayout) findViewById(R.id.logRideDetailsForm);
			createRideForm.setVisibility(View.INVISIBLE);

			new LogRideDetailsTask().execute();
			
		} else {
			//Show the error message to user
			System.out.println("Error in user input");
		}
	}

	private void setFormFields() {
		
		progressBar = (LinearLayout) findViewById(R.id.logRideDetailsSpinner);
		progressBar.setVisibility(View.INVISIBLE);

		//Get the elements on the form
		textViewRideName = (TextView) findViewById(R.id.textViewLogRideDetailsRideName);
		textViewRideName.setKeyListener(null);
		
		textViewDistanceCovered = (TextView) findViewById(R.id.textViewLogRideDetailsDistanceCovered);
		textViewDistanceCovered.setKeyListener(null);
		
		textViewTimeOfRide = (TextView) findViewById(R.id.textViewLogRideDetailsRideTime);
		textViewTimeOfRide.setKeyListener(null);
		
		textViewAverageSpeed = (TextView) findViewById(R.id.textViewLogRideDetailsAverageSpeed);
		textViewAverageSpeed.setKeyListener(null);
		
		textViewHeartRate = (TextView) findViewById(R.id.textViewLogRideDetailsHeartRate);
		
		textViewCadence = (TextView) findViewById(R.id.textViewLogRideDetailsCadence);
		
		textViewExperience = (TextView) findViewById(R.id.textViewLogRideDetailsExperience);
		
		//Set the parameters which are recorded automatically
		textViewRideName.setText(ride.getName());
		textViewDistanceCovered.setText(df.format(distanceCovered) + " mi");
		textViewAverageSpeed.setText(tf.format(averageSpeed) + " mi/hr");
		textViewTimeOfRide.setText(tf.format(timeOfRide) + " mins");

		//Set onClickListener for the submit button
		Button button  = (Button) findViewById(R.id.btnSubmit);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				validateUserInputAndCallAsyncTask();
			}
		});
	}

	private void moveToJoinRidesPage() {
		
		progressBar.setVisibility(View.INVISIBLE);

		LinearLayout logRideDetailsForm = (LinearLayout) findViewById(R.id.logRideDetailsForm);
		logRideDetailsForm.setVisibility(View.VISIBLE);

		//clear the text boxes
		textViewRideName.setText("");
		textViewDistanceCovered.setText("");
		textViewTimeOfRide.setText("");
		textViewAverageSpeed.setText("");
		textViewHeartRate.setText("");
		textViewCadence.setText("");
		textViewExperience.setText("");

		Intent joinRidesIntent = new Intent(LogRideDetailsActivity.this, JoinRidesActivity.class); 
		LogRideDetailsActivity.this.startActivity(joinRidesIntent);
		
	}

	private class LogRideDetailsTask extends AsyncTask<Void,Void,User> {

		Exception errorPostBlog;
		Exception errorLogDetails;

		protected User doInBackground(Void... params) {

			//Log activity to backend
			Date activityDate = new Date();
			String cadenceString = ((TextView) findViewById(R.id.textViewLogRideDetailsCadence)).getText().toString().trim();
			if(!cadenceString.equals("")){
				cadence = Double.parseDouble(cadenceString);
			} else {
				cadence = 0;
			}
			
			String hrString = ((TextView) findViewById(R.id.textViewLogRideDetailsHeartRate)).getText().toString().trim();
			if(!cadenceString.equals("")){
				heartRate = Double.parseDouble(hrString);
			} else {
				heartRate = 0;
			}
			
			String result = UsersManager.logActivity(ride.getId(), BaseActivity.username, distanceCovered, cadence, averageSpeed, timeOfRide, heartRate, activityDate);
			if(!result.equalsIgnoreCase("Success")) {
				errorLogDetails = new Exception();
			}

			String blogText = ((TextView) findViewById(R.id.textViewLogRideDetailsExperience)).getText().toString();
			if(blogText != null && !blogText.equals("")){
				//Post to blog
				System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
				Wordpress wp;
				try {
					wp = new Wordpress(BaseActivity.username, BaseActivity.password, xmlRpcUrl);
					Page recentPost = new Page();
					recentPost.setDescription(blogText);
					wp.newPost(recentPost, true);
				} catch (MalformedURLException e) {
					errorPostBlog = e;
				} catch (XmlRpcFault e) {
					errorPostBlog = e;
				}
			}
			return null;
		}

		protected void onPostExecute(User result) {
			//Redirect to join rides page
		    if(errorPostBlog != null && errorLogDetails != null){
				Toast.makeText(getApplicationContext(), "An error occured.", Toast.LENGTH_SHORT).show();
			}else if(errorPostBlog != null){
				Toast.makeText(getApplicationContext(), "An error occured while posting to blog.", Toast.LENGTH_SHORT).show();
			} else if (errorLogDetails != null){
				Toast.makeText(getApplicationContext(), "An error occured while logging the details.", Toast.LENGTH_SHORT).show();
			}
			moveToJoinRidesPage();
//		    textViewLoginError = (TextView) findViewById(R.id.textViewLogRideError);
//			textViewLoginError.setVisibility(View.VISIBLE);
		}
	}
}