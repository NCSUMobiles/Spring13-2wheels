package com.example.spinningwellness;

import com.ncsu.edu.tabpanel.TabHostProvider;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public abstract class BaseActivity extends Activity {

	protected final String SPINNING_WEELNESS = "SPINNING WELLNESS";
	protected TabHostProvider tabProvider;

	protected static String username;
	protected static String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//create custom title bar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.base_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.base_activity_title_bar);
		setTitle();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	protected abstract void setTitle();	
}