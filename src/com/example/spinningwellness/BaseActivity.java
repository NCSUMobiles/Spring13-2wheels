package com.example.spinningwellness;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public abstract class BaseActivity extends Activity {
	
	public final String SPINNING_WEELNESS = "SPINNING WELLNESS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
		
		setTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.welcome_menu, menu);
		return true;
	}

	/***** Menu Settings ******/

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent i;
		switch (item.getItemId())
		{
		case R.id.menu_create_new_ride:
			i = new Intent(getApplicationContext(),CreateNewRideActivity.class);
			startActivity(i);
			return true;

		case R.id.menu_past:
			i = new Intent(getApplicationContext(), MyPastRidesActivity.class);
			startActivity(i);
			//         Toast.makeText(WelcomeActivity.this, "Past Rides is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_edit_del:

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public abstract void setTitle();
}
