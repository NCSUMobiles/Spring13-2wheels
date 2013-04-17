package com.example.spinningwellness;

import java.util.Calendar;
import java.util.Date;

import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreateRideActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(CreateRideActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.CREATE_RIDE);
		tabView.setCurrentView(R.layout.create_new_ride_activity);
		setContentView(tabView.render());			

		//Listener for create ride button
		Button button  = (Button) findViewById(R.id.btnCreateRide);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Date startDate;

				Calendar cal = Calendar.getInstance();
				cal.set(2013, 2, 18);
				startDate = cal.getTime();
				RidesManager.createRide("Ride to SJ", "source", "dest", startDate, "prajakta");

				Toast.makeText(CreateRideActivity.this, "New Ride is created!", Toast.LENGTH_SHORT).show();
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