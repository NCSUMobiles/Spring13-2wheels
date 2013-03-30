package com.example.spinningwellness;

import java.util.List;
import java.net.MalformedURLException;

import redstone.xmlrpc.XmlRpcFault;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ViewRidesActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_details);
        addListenerOnButton();
    }
    
    public void addListenerOnButton() {
    	
    	final Context context = this;
        
        Button member_button  = (Button) findViewById(R.id.btnviewmembers);
        ImageButton join_button = (ImageButton) findViewById(R.id.btnjoin); 
        
        member_button.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View arg0) {
 
			    Intent member_intent = new Intent(context, ViewMemberActivity.class);
                            startActivity(member_intent);   
 
			}
 
		});
        
        join_button.setOnClickListener(new OnClickListener() {
       	 
			@Override
			public void onClick(View arg0) {
 
			    Intent join_intent = new Intent(context, JoinActivity.class);
                startActivity(join_intent);   
 
			}
 
		});
           
    }
}
        	 
    