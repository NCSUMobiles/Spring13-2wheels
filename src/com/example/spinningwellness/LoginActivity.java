package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Listening to login button
		ImageButton button  = (ImageButton) findViewById(R.id.btnLogin);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				BaseActivity.username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
				BaseActivity.password = "ncsuspr2013";//findViewById(R.id.login_username).toString();
				new LoginTask().execute(); 
			}
		});
	}

	private class LoginTask extends AsyncTask<Void,Void,User> {
		Exception error;
		protected User doInBackground(Void... params) {
			//System.out.println("********bckgrnd");
			String xmlRpcUrl = "http://spinningwellness.wordpress.com/xmlrpc.php";
			// Switching to Register screen

			System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
			Wordpress wp;
			try {
				wp = new Wordpress(BaseActivity.username, BaseActivity.password, xmlRpcUrl);
				User u = wp.getUserInfo();
				System.out.println(u.getNickname());
				return u;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e;
			} catch (XmlRpcFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e;
			}
			return null;
		}

		protected void onPostExecute(User result) {
			//System.out.println("********post");
			if(error != null){
				TextView myText = new TextView(getBaseContext());
				myText.setTextColor(Color.RED);
				myText.setGravity(Gravity.CENTER_VERTICAL);//.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
				myText.setText("Login Failed.");

				LinearLayout l = (LinearLayout)findViewById(R.id.login_layout);
				l.addView(myText);
			} else{
				//				Intent i = new Intent(getApplicationContext(), JoinActivity.class);
				//				i.putExtra("username",username);
				//				i.putExtra("password",password);
				//				startActivity(i);
				Intent loadingIntent = new Intent(LoginActivity.this, JoinRidesActivity.class); 
				LoginActivity.this.startActivity(loadingIntent);
			}
		}
	}

	@Override
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Login to Burn Those Calories");		
	}
}