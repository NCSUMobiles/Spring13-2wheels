package com.ncsu.edu.spinningwellness.activities;

import com.example.spinningwellness.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class CoverPageActivity extends Activity {

	//Introduce an delay
	private final int WAIT_TIME = 2500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.cover_page_activity);
		new Handler().postDelayed(new Runnable(){ 
			@Override 
			public void run() { 
				//Simulating a long running task
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/* Create an Intent that will start the ProfileData-Activity. */
				Intent loginIntent = new Intent(CoverPageActivity.this,LoginActivity.class); 
				CoverPageActivity.this.startActivity(loginIntent); 
				CoverPageActivity.this.finish(); 
			} 
		}, WAIT_TIME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cover_page, menu);
		return true;
	}
}