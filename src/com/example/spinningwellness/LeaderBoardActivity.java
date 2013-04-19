package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;

import com.ncsu.edu.customadapters.UserCustomAdapter;
import com.ncsu.edu.spinningwellness.entities.User;
import com.ncsu.edu.spinningwellness.managers.UsersManager;
import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LeaderBoardActivity extends BaseActivity {

	List<User> topRiders = new ArrayList<User>();
	List<String> users = new ArrayList<String>();
	private LinearLayout progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Draw menu
		tabProvider = new MyTabHostProvider(LeaderBoardActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.LEADER_BOARD);
		tabView.setCurrentView(R.layout.leader_board_activity);
		setContentView(tabView.render());			
		
		progressBar = (LinearLayout) findViewById(R.id.leaderBoardSpinner);
		progressBar.setVisibility(View.VISIBLE);

		new GetLeaderBoardTask().execute();
	}

	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Leader Board");		
	}

	private void displayListView() {

		progressBar.setVisibility(View.INVISIBLE);

		//create an ArrayAdaptar from the String Array
		UserCustomAdapter dataAdapter = new UserCustomAdapter(this, R.id.leaderBoardTextVal, users);
		final ListView listView = (ListView) findViewById(R.id.leaderBoardListView);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);
	}


	public class GetLeaderBoardTask extends AsyncTask<Void,Void,List<User>> {
		Exception error;

		protected List<User> doInBackground(Void... params) {
			return UsersManager.viewTopPerformers();
		}

		protected void onPostExecute(List<User> result) {
			if(error != null){

			} else {
				topRiders = result;
				for(User r: topRiders){
					users.add(r.getName());
				}				
				displayListView();
			}
		}
	}
}