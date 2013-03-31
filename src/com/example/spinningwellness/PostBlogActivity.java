package com.example.spinningwellness;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PostBlogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_blog);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_blog, menu);
		return true;
	}

}
