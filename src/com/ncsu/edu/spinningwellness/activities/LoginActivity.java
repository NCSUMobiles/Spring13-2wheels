package com.ncsu.edu.spinningwellness.activities;

import java.net.MalformedURLException;

import com.example.spinningwellness.R;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {

	LinearLayout progressBar;
	TextView textViewLoginError;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Listening to login button
		Button button  = (Button) findViewById(R.id.btnLogin);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				validateUserInputAndCallAsyncTask(); 
			}
		});

		progressBar = (LinearLayout) findViewById(R.id.loginSpinner);
		progressBar.setVisibility(View.INVISIBLE);

		textViewLoginError = (TextView) findViewById(R.id.textViewLoginError);
		textViewLoginError.setVisibility(View.INVISIBLE);

	}

	@Override
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("Login to Burn Those Calories");		
	}

	private void validateUserInputAndCallAsyncTask() {

		//Add validation code here
		boolean isValid = true;
		if(isValid) {
			progressBar.setVisibility(View.VISIBLE);
			LinearLayout createRideForm = (LinearLayout) findViewById(R.id.LoginForm);
			createRideForm.setVisibility(View.INVISIBLE);

			new LoginTask().execute();
		} else {
			textViewLoginError.setVisibility(View.VISIBLE);
		}
	}

	private void moveToJoinRidesPage() {
		
		progressBar.setVisibility(View.INVISIBLE);

		LinearLayout createRideForm = (LinearLayout) findViewById(R.id.LoginForm);
		createRideForm.setVisibility(View.VISIBLE);
		
		//clear the text boxes
		TextView textViewUserName = (TextView) findViewById(R.id.textViewLoginUserName);
		textViewUserName.setText("username");
		
		TextView textViewPassword = (TextView) findViewById(R.id.textViewLoginPassword);
		textViewPassword.setText("******");
		
		Intent loadingIntent = new Intent(LoginActivity.this, JoinRidesActivity.class); 
		LoginActivity.this.startActivity(loadingIntent);
	}

	private class LoginTask extends AsyncTask<Void,Void,User> {
		Exception error;
		protected User doInBackground(Void... params) {
			System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
			Wordpress wp;
			try {

				String username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
				String password = "ncsuspr2013";//findViewById(R.id.login_username).toString();

				wp = new Wordpress(username, password, xmlRpcUrl);
				User u = wp.getUserInfo();
				return u;
			} catch (MalformedURLException e) {
				error = e;
			} catch (XmlRpcFault e) {
				error = e;
			}
			return null;
		}

		protected void onPostExecute(User result) {

			//Redirect to join rides page
			if(error == null) {
				//Set variable values in base activity
				BaseActivity.username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
				BaseActivity.password = "ncsuspr2013";//findViewById(R.id.login_username).toString();

				moveToJoinRidesPage();
			} else {
				progressBar.setVisibility(View.INVISIBLE);
				LoginActivity.this.textViewLoginError.setVisibility(View.VISIBLE);
			}
		}
	}
}