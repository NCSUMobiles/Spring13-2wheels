package com.example.spinningwellness;

import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

import android.os.Bundle;
import android.widget.TextView;

public class LeaderBoardActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Draw menu
		tabProvider = new MyTabHostProvider(LeaderBoardActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.LEADER_BOARD);
		tabView.setCurrentView(R.layout.activity_leader_board);
		setContentView(tabView.render());			
	}

	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Leader Board");		
	}
}