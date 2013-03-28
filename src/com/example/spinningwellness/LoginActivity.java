package com.example.spinningwellness;

import java.util.List;
import java.net.MalformedURLException;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        Button button  = (Button) findViewById(R.id.btnLogin);
        
        // Listening to login button
        button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				String username = "spinningwellness";//findViewById(R.id.login_username).toString();//;
				String password = "ncsuspr2013";//findViewById(R.id.login_password).toString(); //;
			    String xmlRpcUrl = "http://spinningwellness.wordpress.com/xmlrpc.php";
			    try {
			    	System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
			    	Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
					User u = wp.getUserInfo();
					System.out.println(u.getFirstname());
					i.putExtra("username",username);
					i.putExtra("password",password);
					//i.putExtra("wordpress",wp);
					startActivity(i);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (XmlRpcFault e) {
					//android.util.Log.v("WPJAVA", "Exception ["+e.getMessage()+"]", e);
					TextView myText = new TextView(getBaseContext());
					myText.setTextColor(Color.RED);
					myText.setGravity(Gravity.CENTER_VERTICAL);//.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
					myText.setText("Login Failed.");

					LinearLayout l = (LinearLayout)findViewById(R.id.login_layout);
					l.addView(myText);
				}	
			}
		});
    }
}