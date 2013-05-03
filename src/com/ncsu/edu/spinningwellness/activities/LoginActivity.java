package com.ncsu.edu.spinningwellness.activities;

import java.net.MalformedURLException;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.Utils.UIUtils;
import com.ncsu.edu.spinningwellness.managers.UsersManager;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	LinearLayout progressBar;
	TextView textViewLoginError;
	EditText editTextUsername,editTextPassword;
    CheckBox saveLoginCheckBox;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    Boolean saveLogin;
    String username, password;
    boolean userExists;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setAsteriskLabels();
		
		editTextUsername = (EditText)findViewById(R.id.textViewLoginUserName);
        editTextPassword = (EditText)findViewById(R.id.textViewLoginPassword);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

		// Listening to login button
		Button button  = (Button) findViewById(R.id.btnLogin);
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(isOnline() == true){
					username = editTextUsername.getText().toString();
		            password = editTextPassword.getText().toString();

		            if (saveLoginCheckBox.isChecked()) {
		                loginPrefsEditor.putBoolean("saveLogin", true);
		                loginPrefsEditor.putString("username", username);
		                loginPrefsEditor.putString("password", password);
		                loginPrefsEditor.commit();
		            } else {
		                loginPrefsEditor.clear();
		                loginPrefsEditor.commit();
		            }
				validateUserInputAndCallAsyncTask(); 
				}
				else{
					createInternetDisabledAlert();
				}
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
		
		
		String missing="";
		boolean isValid = true;
		
		editTextUsername = (EditText)findViewById(R.id.textViewLoginUserName);
        editTextPassword = (EditText)findViewById(R.id.textViewLoginPassword);
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
		
		
	//		
		if(username.equals("") || password.equals(""))
			isValid = false;
		
		
		
		

		//Add validation code here
	
		if(isValid) {
			progressBar.setVisibility(View.VISIBLE);
			LinearLayout createRideForm = (LinearLayout) findViewById(R.id.LoginForm);
			createRideForm.setVisibility(View.INVISIBLE);

			new LoginTask().execute();
		} else {
			//Show the error message to user
			textViewLoginError.setText("Login Failed...\nEnter all fields!");
			textViewLoginError.setVisibility(View.VISIBLE);
			Context context = getApplicationContext();
			CharSequence text = "Enter all fields!";
			Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
			TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
			v.setTextColor(getResources().getColor(R.color.red));
			toast.show();			
		}
	}

	private void moveToJoinRidesPage() {
		
		progressBar.setVisibility(View.INVISIBLE);

//		LinearLayout createRideForm = (LinearLayout) findViewById(R.id.LoginForm);
//		createRideForm.setVisibility(View.VISIBLE);
		
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

//				String username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
//				String password = "ncsuspr2013";//findViewById(R.id.login_username).toString();

				username = editTextUsername.getText().toString();
	            password = editTextPassword.getText().toString();
				
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
//				BaseActivity.username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
//				BaseActivity.password = "ncsuspr2013";//findViewById(R.id.login_username).toString();

				BaseActivity.username = editTextUsername.getText().toString();
				BaseActivity.password = editTextPassword.getText().toString();
				//check if this user already exists
				new CheckUserExistsTask().execute();
				
			} else {
				progressBar.setVisibility(View.INVISIBLE);
				
				LinearLayout createRideForm = (LinearLayout) findViewById(R.id.LoginForm);
				createRideForm.setVisibility(View.VISIBLE);
				
				//clear the text boxes
				TextView textViewUserName = (TextView) findViewById(R.id.textViewLoginUserName);
				textViewUserName.setHint("username");
				
				TextView textViewPassword = (TextView) findViewById(R.id.textViewLoginPassword);
				textViewPassword.setHint("******");
				
				textViewLoginError.setText("Login Failed...\nInvalid Username or Password!");
				textViewLoginError.setVisibility(View.VISIBLE);
				Context context = getApplicationContext();
				CharSequence text = "Invalid username or password!";
				Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
				TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
				v.setTextColor(getResources().getColor(R.color.red));
				toast.show();
			}
		}
	}
	
	private class CheckUserExistsTask extends AsyncTask<Void, Void, com.ncsu.edu.spinningwellness.entities.User> {
		Exception error;

		@Override
		protected com.ncsu.edu.spinningwellness.entities.User doInBackground(Void... arg0) {
			com.ncsu.edu.spinningwellness.entities.User u = UsersManager.getUser(username);
			return u;
		}
		
		protected void onPostExecute(com.ncsu.edu.spinningwellness.entities.User result) {
			if(result == null){
				userExists = false;
			}else{
				userExists = true;
			}
			if(!userExists){
				moveToRegisterPage();
			}else{
				moveToJoinRidesPage();
			}
		}
	}
	
	private void moveToRegisterPage() {

		progressBar.setVisibility(View.INVISIBLE);

//		LinearLayout createRideForm = (LinearLayout) findViewById(R.id.LoginForm);
//		createRideForm.setVisibility(View.VISIBLE);

		//clear the text boxes
		TextView textViewUserName = (TextView) findViewById(R.id.textViewLoginUserName);
		textViewUserName.setText("username");

		TextView textViewPassword = (TextView) findViewById(R.id.textViewLoginPassword);
		textViewPassword.setText("******");

		Intent loadingIntent = new Intent(LoginActivity.this, RegisterActivity.class); 
		LoginActivity.this.startActivity(loadingIntent);
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	
	protected void createInternetDisabledAlert() {
	    	AlertDialog builder = new AlertDialog.Builder(this).create();
	    	builder.setMessage("Your Internet is disabled! Please enable it.");

	    	builder.setButton("OK", new DialogInterface.OnClickListener() {

	    		public void onClick(DialogInterface dialog, int id) {
	    			return;
	    		}
	    	});
	    	builder.show();
	    }
	
	private void setAsteriskLabels() {
		
		TextView tvLoginUsername = (TextView) findViewById(R.id.textViewLoginUserNameLabel);
		tvLoginUsername.setText(UIUtils.buildSpannableStringWithAsterisk("Username"));
		
		TextView tvLoginPassword = (TextView) findViewById(R.id.textViewLoginPasswordLabel);
		tvLoginPassword.setText(UIUtils.buildSpannableStringWithAsterisk("Password"));
	}
	
}