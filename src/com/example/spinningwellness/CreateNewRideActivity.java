package com.example.spinningwellness;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ncsu.edu.spinningwellness.managers.RidesManager;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewRideActivity extends BaseActivity {
	//	Button createButton;
	//	 public void onClick(View arg0) {
	//	    	if(arg0.getId() == R.id.btnCreateRide){
	//	    		System.out.println("Clicked create new ride");
	//	    		//define a new Intent for the second Activity
	//	    		Intent intent = new Intent(this,UpcomingRidesActivity.class);
	//	     
	//	    		//start the second Activity
	//	    		this.startActivity(intent);
	//	    	}
	//	 }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to create new ride.xml
		setContentView(R.layout.activity_create_new_ride);

		Button button  = (Button) findViewById(R.id.btnCreateRide);

		// Listening to login button
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Date startDate;

				Calendar cal = Calendar.getInstance();
				cal.set(2013, 2, 18);
				startDate = cal.getTime();
				RidesManager.createRide("Ride to SJ", "source", "dest", startDate, "prajakta");

				Toast.makeText(CreateNewRideActivity.this, "New Ride is created!", Toast.LENGTH_SHORT).show();
				Button button1 = (Button) findViewById(R.id.btnCreateRide);
				button1.setEnabled(false);

			}
		});
	}
	
	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Create Ride");		
	}
}