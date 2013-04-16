package com.example.spinningwellness;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LoadingActivity extends Activity {

	//Introduce an delay
	private final int WAIT_TIME = 2500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("LoadingScreenActivity  screen started");
		setContentView(R.layout.activity_loading);
		findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

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
				System.out.println("Going to Profile Data");
				/* Create an Intent that will start the ProfileData-Activity. */
//				Intent mainIntent = new Intent(LoadingActivity.this,WelcomeActivity.class); 
//				LoadingActivity.this.startActivity(mainIntent); 
//				LoadingActivity.this.finish(); 
			} 
		}, WAIT_TIME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
		return true;
	}

}

