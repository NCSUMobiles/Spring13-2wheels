package com.ncsu.edu.spinningwellness.tabpanel;

import android.app.Activity;

public abstract class TabHostProvider {
	
	public Activity context;

	public TabHostProvider(Activity context) {
		this.context = context;
	}

	public abstract TabView getTabHost(String category);
}