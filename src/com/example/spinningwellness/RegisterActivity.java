package com.example.spinningwellness;

import java.net.MalformedURLException;
import java.util.List;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        //get the items from the login screen
        String username = getIntent().getStringExtra("username");
        System.out.println("Welcome " + username + "!");
       
       
        
        // Listening to Login Screen link
//        loginScreen.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// Switching to Login Screen/closing register screen
//				finish();
//			}
//		});
        
    }
}