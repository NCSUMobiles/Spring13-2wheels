package com.example.spinningwellness;

import java.util.Calendar;
import java.util.Date;

import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setFormFields();
	}

	@Override
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Create Ride");		
	}

	private void setFormFields() {

		//Listener for create ride button
		Button button  = (Button) findViewById(R.id.btnCreateRide);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				System.out.println("In onclick listener");

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
				
				RidesManager.createRide(rideName, source, destination, startDate, username);

				Toast.makeText(CreateRideActivity.this, "New Ride is created!", Toast.LENGTH_SHORT).show();
			}
		});
	}
}