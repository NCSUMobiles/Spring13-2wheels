package com.example.spinningwellness;

import java.util.List;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class ChallengeFriendActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend);
        addListenerOnButton();
    }
    
    public void addListenerOnButton() {
    	
    	final Context context = this;
        
        Button search_button  = (Button) findViewById(R.id.btnsearch);
        
        search_button.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View v) {
 
				try {
			//		SearchTask();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();                    
	            }     
			}
 
		});
        
                  
    }

    public void SearchTask() throws Exception
    {
    	EditText search = (EditText)findViewById(R.id.searchfriend);
    }
}
