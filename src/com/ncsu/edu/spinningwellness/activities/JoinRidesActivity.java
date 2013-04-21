package com.ncsu.edu.spinningwellness.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.text.Position;

import com.example.spinningwellness.R;
import com.ncsu.edu.spinningwellness.customadapters.CustomEntry;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.spinningwellness.tabpanel.MenuConstants;
import com.ncsu.edu.spinningwellness.tabpanel.MyTabHostProvider;
import com.ncsu.edu.spinningwellness.tabpanel.TabView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JoinRidesActivity extends BaseActivity {

	List<Ride> rideList = new ArrayList<Ride>();
	ArrayList<CustomEntry> rideEntry = new ArrayList<CustomEntry>();
	private LinearLayout progressBar;
	Ride selectedRideSave;
	int selectedRidePosition;
	public List<Ride> myJoinedRidesList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Draw menu
		tabProvider = new MyTabHostProvider(JoinRidesActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.JOIN_RIDES);
		tabView.setCurrentView(R.layout.join_rides_activity);
		setContentView(tabView.render());			

		progressBar = (LinearLayout) findViewById(R.id.Spinner);
		progressBar.setVisibility(View.VISIBLE);

		new GetUpcomingRidesTask().execute();	
	}

	private void displayListView() {

		progressBar.setVisibility(View.INVISIBLE);
		//sort by date
		List<CustomEntry> tempJoined = new ArrayList<CustomEntry>();
		List<CustomEntry> tempNotJoined = new ArrayList<CustomEntry>();
		for(CustomEntry ce:rideEntry){
			if(ce.isJoined()){
				tempJoined.add(ce);
			}else{
				tempNotJoined.add(ce);
			}
		}

		Collections.sort(tempJoined);
		Collections.sort(tempNotJoined);

		rideEntry.clear();
		rideEntry.addAll(tempJoined);
		rideEntry.addAll(tempNotJoined);

		List<Ride> temp = new ArrayList<Ride>();
		for(CustomEntry r:rideEntry){
			for(Ride r1:rideList){
				if(r1.getId().equals(r.getRideid())){
					temp.add(r1);
				}
			}
		}
		rideList.clear();
		rideList.addAll(temp);
		//create an ArrayAdaptar from the String Array
		CustomAdapter dataAdapter = new CustomAdapter(this, R.id.textVal, rideEntry);
		final ListView listView = (ListView) findViewById(R.id.listView1);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//  listView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);
		//listView.setItemsCanFocus(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), " Clicked " , Toast.LENGTH_SHORT).show();

				Ride selectedRide = null;
				int positionView = listView.getPositionForView(view);
				if (positionView != ListView.INVALID_POSITION) {
					System.out.println( rideList.get(positionView).getName());
					//start view activity
					selectedRide = rideList.get(positionView);
					Intent i = new Intent(getApplicationContext(), ViewRideDetailsActivity.class);
					i.putExtra("RideDetails", selectedRide);
					startActivity(i);
				}else{
					Toast.makeText(getApplicationContext(), "An error occured." , Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public class CustomAdapter extends ArrayAdapter<CustomEntry> {
		private ArrayList<CustomEntry> entries;
		private Activity activity;

		public CustomAdapter(Activity a, int textViewResourceId, ArrayList<CustomEntry> entries) {
			super(a, textViewResourceId, entries);
			this.entries = entries;
			this.activity = a;
		}

		public class ViewHolder{
			public TextView item1;
			public CheckBox item2;
			public Button start;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			//			if (v == null) {
			LayoutInflater vi =
					(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.join_rides_list, null);
			holder = new ViewHolder();
			holder.item1 = (TextView) v.findViewById(R.id.textVal);
			holder.item2 = (CheckBox) v.findViewById(R.id.isJoined);

			holder.start = (Button) v.findViewById(R.id.startRide);
			final CustomEntry custom = entries.get(position);
			if (custom != null) {
				holder.item1.setText(custom.getTextVal());
				holder.item2.setChecked(custom.isJoined());
			}
			holder.start.setOnClickListener(mStartButtonClickListener);
			holder.item2.setOnCheckedChangeListener(mStarCheckedChangeListener);

			//			}
			//			else
			holder=(ViewHolder)v.getTag();
			v.setTag(holder);
			return v;
		}

		private OnClickListener mStartButtonClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("****start button clicked");
				final ListView listView = (ListView) findViewById(R.id.listView1);
				final int position = listView.getPositionForView(v);
				if (position != ListView.INVALID_POSITION) {
					Intent i = new Intent(getApplicationContext(), RecordRideStatsActivity.class);
					i.putExtra("Ride", rideList.get(position));
					startActivity(i);
				}
			}
		};

		private OnCheckedChangeListener mStarCheckedChangeListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.out.println("checked isjoined" + isChecked); 
				final ListView listView = (ListView) findViewById(R.id.listView1);
				final int position = listView.getPositionForView(buttonView);
				if (position != ListView.INVALID_POSITION) {
					//todo:update mStarStates[position] = isChecked;
					Ride r = rideList.get(position);
					selectedRideSave = r;
					selectedRidePosition = position;
					progressBar = (LinearLayout) findViewById(R.id.Spinner);
					progressBar.setVisibility(View.VISIBLE);
					if(isChecked){
						new JoinRideTask().execute();
					}else{
						new UnJoinRideTask().execute();
					}
				}
			}
		};
	}

	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WELLNESS + " " + "Join Ride");		
	}

	//AsynTask for getting the list of rides
	public class GetUpcomingRidesTask extends AsyncTask<Void,Void,List<Ride>> {
		Exception error;

		protected List<Ride> doInBackground(Void... params) {
			return RidesManager.viewUpcomingRides();
		}

		protected void onPostExecute(List<Ride> result) {
			if(error != null){

			} else {
				rideList = result;
				new GetJoinedRidesForUser().execute();
			}
		}
	}

	//AsynTask for getting the list of rides
	public class GetJoinedRidesForUser extends AsyncTask<Void,Void,List<Ride>> {
		Exception error;

		protected List<Ride> doInBackground(Void... params) {
			return RidesManager.viewMyUpcomingRides(username);
		}

		protected void onPostExecute(List<Ride> result) {
			myJoinedRidesList = result;
			List<CustomEntry> temp = new ArrayList<CustomEntry>();
			for(Ride r:rideList){
				if(myJoinedRidesList.contains(r)){
					temp.add(new CustomEntry(r.getId(),r.getStartTime(),r.getName(), true));
				}else{
					temp.add(new CustomEntry(r.getId(),r.getStartTime(),r.getName(), false));
				}
			}	
			for(CustomEntry r:temp){
				if(r.isJoined()){
					rideEntry.add(r);
				}
			}
			for(CustomEntry r:temp){
				if(!r.isJoined()){
					rideEntry.add(r);
				}
			}


			displayListView();
		}
	}

	//AsynTask for joining the rides
	public class JoinRideTask extends AsyncTask<Void,Void,String> {
		Exception error;

		protected String doInBackground(Void... params) {
			return  RidesManager.addParticipantToRide(selectedRideSave.getId(), username);
		}

		protected void onPostExecute(String result) {
			if(result.equalsIgnoreCase("Success")){
				Toast.makeText(getApplicationContext(), "Joined the ride successfully." , Toast.LENGTH_SHORT).show();
				//					selectedStar.setSelected(true);
			}else{
				Toast.makeText(getApplicationContext(), "An error occured." , Toast.LENGTH_SHORT).show();
			}
			//change order in rideEntry
			ArrayList<CustomEntry> temp = new ArrayList<CustomEntry>();
			temp.add(rideEntry.get(selectedRidePosition));
			temp.get(0).setJoined(true);
			for(CustomEntry r:rideEntry){
				if(!selectedRideSave.getId().equals(r.getRideid())){
					temp.add(r);
				}
			}
			rideEntry = temp;
			displayListView();
		}
	}

	//AsynTask for joining the rides
	public class UnJoinRideTask extends AsyncTask<Void,Void,String> {
		Exception error;

		protected String doInBackground(Void... params) {
			return  RidesManager.deleteParticipantToRide(selectedRideSave.getId(), username);
		}

		protected void onPostExecute(String result) {
			if(result.equalsIgnoreCase("Success")){
				Toast.makeText(getApplicationContext(), "UnJoined the ride successfully." , Toast.LENGTH_SHORT).show();
				//							selectedStar.setSelected(true);
			}else{
				Toast.makeText(getApplicationContext(), "An error occured." , Toast.LENGTH_SHORT).show();
			}
			//change order in rideEntry
			ArrayList<CustomEntry> temp = new ArrayList<CustomEntry>();
			
			for(CustomEntry r:rideEntry){
				if(!selectedRideSave.getId().equals(r.getRideid())){
					temp.add(r);
				}
			}
			temp.add(rideEntry.get(selectedRidePosition));
			temp.get(temp.size()-1).setJoined(false);
			rideEntry = temp;
			displayListView();
		}
	}
}