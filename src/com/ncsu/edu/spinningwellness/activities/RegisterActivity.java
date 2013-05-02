package com.ncsu.edu.spinningwellness.activities;

import com.example.spinningwellness.R;
import com.example.spinningwellness.R.layout;
import com.example.spinningwellness.R.menu;
import com.ncsu.edu.spinningwellness.activities.JoinRidesActivity.GetUpcomingRidesTask;
import com.ncsu.edu.spinningwellness.managers.UsersManager;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity {
	LinearLayout progressBar;
	LinearLayout logRideDetailsForm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);


		progressBar = (LinearLayout) findViewById(R.id.registerSpinner);
		progressBar.setVisibility(View.INVISIBLE);
		TextView textViewUsername = (TextView)findViewById(R.id.textViewusername);
		textViewUsername.setText(username);
		textViewUsername.setKeyListener(null);
		//Set onClickListener for the submit button
		Button button  = (Button) findViewById(R.id.btnSave);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				progressBar = (LinearLayout) findViewById(R.id.registerSpinner);
				progressBar.setVisibility(View.VISIBLE);
				logRideDetailsForm = (LinearLayout) findViewById(R.id.logRideDetailsForm);
				logRideDetailsForm.setVisibility(View.INVISIBLE);
				new CreateUserTask().execute();
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	private class CreateUserTask extends AsyncTask<Void, Void, String> {
		Exception error;

		@Override
		protected String doInBackground(Void... arg0) {
			
			EditText textViewEmail = (EditText)findViewById(R.id.textViewEmail);
			return UsersManager.createUser(username,textViewEmail.toString());
		}
		
		protected void onPostExecute(String result) {

			progressBar = (LinearLayout) findViewById(R.id.registerSpinner);
			progressBar.setVisibility(View.INVISIBLE);
			if(result.equalsIgnoreCase("Success")){
				//textViewEmail.setText("");

				Intent loadingIntent = new Intent(RegisterActivity.this, JoinRidesActivity.class); 
				RegisterActivity.this.startActivity(loadingIntent);
			}else{
				//userExists = true;
			}
			
		}
	}

	@Override
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("Register");
		
	}
}
