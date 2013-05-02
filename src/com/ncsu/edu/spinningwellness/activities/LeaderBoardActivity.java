package com.ncsu.edu.spinningwellness.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.customadapters.LeaderBoardCustomEntry;
import com.ncsu.edu.spinningwellness.customadapters.LeaderBoardEntryCustomAdapter;
import com.ncsu.edu.spinningwellness.entities.LeaderBoardEntry;
import com.ncsu.edu.spinningwellness.managers.UsersManager;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LeaderBoardActivity extends BaseActivity {

	List<LeaderBoardEntry> topRiders = new ArrayList<LeaderBoardEntry>();
	List<LeaderBoardCustomEntry> users = new ArrayList<LeaderBoardCustomEntry>();
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
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("Leader Board");		
	}

	private void displayListView() {

		progressBar.setVisibility(View.INVISIBLE);

		//create an ArrayAdaptar from the String Array
		LeaderBoardEntryCustomAdapter dataAdapter = new LeaderBoardEntryCustomAdapter(this, R.id.leaderBoardUserName, users);
		final ListView listView = (ListView) findViewById(R.id.leaderBoardListView);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);
	}


	public class GetLeaderBoardTask extends AsyncTask<Void,Void,List<LeaderBoardEntry>> {
		Exception error;

		protected List<LeaderBoardEntry> doInBackground(Void... params) {
			return UsersManager.viewTopPerformers(BaseActivity.username);
		}

		protected void onPostExecute(List<LeaderBoardEntry> result) {
			if(error != null){

			} else {
				topRiders = result;
				for(LeaderBoardEntry r: topRiders){
					users.add(new LeaderBoardCustomEntry(r.getName(), r.getDistanceCovered(), r.getPosition()));
				}				
				displayListView();
			}
		}
	}
}