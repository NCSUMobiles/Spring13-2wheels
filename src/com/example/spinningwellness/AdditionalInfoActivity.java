package com.example.spinningwellness;

import java.util.Date;

import com.ncsu.edu.spinningwellness.managers.UsersManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AdditionalInfoActivity extends BaseActivity {

	String rideId, userName;
	double distaceCovered, averageSpeed, caloriesBurned;
	Date activityDate = new Date();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.heartrate_cadence);
		//	    userName = getIntent().getStringExtra("username");
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		final Context context = this;

		Button challenge_button  = (Button) findViewById(R.id.btnCadHrt);

		challenge_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//				String heartrate = findViewById(R.id.heartrate).toString();
				//				String cadence = findViewById(R.id.cadence).toString();
				//				double dcad;
				//				if(cadence == null) {
				//					dcad=0.0;
				//				}
				//				else 
				//					dcad = Double.parseDouble(cadence);
				//				UsersManager.logActivity(rideId,userName,distaceCovered,dcad,averageSpeed, caloriesBurned,activityDate);
				//			    
				Toast.makeText(getApplicationContext(), " User Activity has been recorded!", Toast.LENGTH_LONG).show();
				setContentView(R.layout.activity_record_user);

			}

		});
	}


}




