package com.example.spinningwellness;

import java.util.List;
import java.net.MalformedURLException;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				String password = "ncsuspr2013";
				String username = "spinningwellness";
			    String xmlRpcUrl = "http://spinningwellness.wordpress.com/xmlrpc.php";
			    try {
			    	System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
					Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
					User u = wp.getUserInfo();
					System.out.println(u.getFirstname());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlRpcFault e) {
					// TODO Auto-generated catch block
					android.util.Log.v("WPJAVA", "Exception ["+e.getMessage()+"]", e);
					e.printStackTrace();
				}
				startActivity(i);
			}
			
		});
    }
}