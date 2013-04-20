package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;

import com.ncsu.edu.customadapters.UserCustomAdapter;
import com.ncsu.edu.spinningwellness.entities.Participant;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ViewParticipantsForARideActivity extends BaseActivity {

	List<Participant> participants = new ArrayList<Participant>();
	List<String> users = new ArrayList<String>();
	private LinearLayout progressBar;
	Ride ride; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(ViewParticipantsForARideActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.PAST_RIDES);
		tabView.setCurrentView(R.layout.view_participants_for_a_ride_activity);
		setContentView(tabView.render());			

		progressBar = (LinearLayout) findViewById(R.id.viewParticipantsForARideSpinner);
		progressBar.setVisibility(View.VISIBLE);

		new ViewParticipantsForARideTask().execute();		
	}
	
	@Override
	protected void setTitle() {
		
		ride = getIntent().getParcelableExtra("RideDetails");

		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Participants for Ride:" + ride.getName());				
	}	

	private void displayListView() {

		progressBar.setVisibility(View.INVISIBLE);

		//create an ArrayAdaptar from the String Array
		UserCustomAdapter dataAdapter = new UserCustomAdapter(this, R.id.userTextVal, users);
		final ListView listView = (ListView) findViewById(R.id.viewParticipantsForARideListView);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);
	}

	public class ViewParticipantsForARideTask extends AsyncTask<Void,Void,List<Participant>> {
		Exception error;

		protected List<Participant> doInBackground(Void... params) {
			return RidesManager.viewParticipantsForRide(ride.getId());
		}

		protected void onPostExecute(List<Participant> result) {
			if(error != null){

			} else{
				List<Participant> participants = result;
				for(Participant p:participants){
					users.add(p.getUserName());
				}
			}
			displayListView();
		}
	}
}