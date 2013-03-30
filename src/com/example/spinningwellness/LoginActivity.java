package com.example.spinningwellness;

import java.util.List;
import java.net.MalformedURLException;

import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {
	String username ;
	String password;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		ImageButton button  = (ImageButton) findViewById(R.id.btnLogin);

		// Listening to login button
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
				password = "ncsuspr2013";//findViewById(R.id.login_username).toString();
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
				wp = new Wordpress(username, password, xmlRpcUrl);
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
				Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
				i.putExtra("username",username);
				i.putExtra("password",password);
				startActivity(i);
			}
		}
	}
}