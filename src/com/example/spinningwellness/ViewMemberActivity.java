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

public class ViewMemberActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_members);
        addListenerOnButton();
    }
    
    public void addListenerOnButton() {
    	
    	final Context context = this;
        
        Button challenge_button  = (Button) findViewById(R.id.btnchallenge);
        
        challenge_button.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View arg0) {
 
			    Intent challenge_intent = new Intent(context, ChallengeFriendActivity.class);
                            startActivity(challenge_intent);   
 
			}
 
		});
        
                  
    }
}




