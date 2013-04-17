package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;

import com.ncsu.edu.customadapters.CustomEntry;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

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

public class JoinActivity extends BaseActivity {

	List<Ride> rideList = new ArrayList<Ride>();
	ArrayList<CustomEntry> rideEntry = new ArrayList<CustomEntry>();
	private LinearLayout progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(JoinActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.JOIN_RIDES);
		tabView.setCurrentView(R.layout.activity_join);
		setContentView(tabView.render());			

		progressBar = (LinearLayout) findViewById(R.id.Spinner);
		progressBar.setVisibility(View.VISIBLE);

		new GetUpcomingRidesTask().execute();		
	}

	private void displayListView() {
		
		System.out.println("In display list view");
		
		progressBar.setVisibility(View.INVISIBLE);
		
		System.out.println("Stopping spinner");
		
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
					Intent i = new Intent(getApplicationContext(), ViewRidesActivity.class);
					i.putExtra("RideDetails", selectedRide);
					startActivity(i);
				}else{
					Toast.makeText(getApplicationContext(), "An error occured." , Toast.LENGTH_SHORT).show();
				}
				//				for(Ride r:rideList){
				//					if(view.findViewById(R.id.textVal).toString().equalsIgnoreCase(r.getName())){
				//						selectedRide = r;
				//						System.out.println("Ride details passed : " + selectedRide.getName());
				//						break;
				//					}
				//				}

				//				Intent i = new Intent(getApplicationContext(), ViewRidesActivity.class);
				//				i.putExtra("RideDetails", selectedRide);
				//				
				//				startActivity(i);
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
			if (v == null) {
				LayoutInflater vi =
						(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.join_list, null);
				holder = new ViewHolder();
				holder.item1 = (TextView) v.findViewById(R.id.textVal);
				holder.item2 = (CheckBox) v.findViewById(R.id.isJoined);
				holder.start = (Button) v.findViewById(R.id.startRide);
				holder.start.setOnClickListener(mStartButtonClickListener);
				holder.item2.setOnCheckedChangeListener(mStarCheckedChangeListener);
				v.setTag(holder);
			}
			else
				holder=(ViewHolder)v.getTag();

			final CustomEntry custom = entries.get(position);
			if (custom != null) {
				holder.item1.setText(custom.getTextVal());
				holder.item2.setEnabled(custom.isJoined());
			}

			return v;
		}

		private OnClickListener mStartButtonClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("****start button clicked");
				final ListView listView = (ListView) findViewById(R.id.listView1);
				final int position = listView.getPositionForView(v);
				if (position != ListView.INVALID_POSITION) {
					System.out.println( rideList.get(position).getName());
					Intent i = new Intent(getApplicationContext(), RecordUserActivity.class);
					i.putExtra("RideDetails", rideList.get(position));
					startActivity(i);
				}
			}
		};

		private OnCheckedChangeListener mStarCheckedChangeListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.out.println("checked isjoined");
				final ListView listView = (ListView) findViewById(R.id.listView1);
				final int position = listView.getPositionForView(buttonView);
				if (position != ListView.INVALID_POSITION) {
					//todo:update mStarStates[position] = isChecked;


				}
			}
		};


	}

	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Join Ride");		
	}
	
	public class GetUpcomingRidesTask extends AsyncTask<Void,Void,List<Ride>> {
		Exception error;

		protected List<Ride> doInBackground(Void... params) {
			System.out.println("in doInBG");
			return RidesManager.viewUpcomingRides();
		}

		protected void onPostExecute(List<Ride> result) {
			if(error != null){

			} else {
				rideList = result;
				for(Ride r:rideList){
					rideEntry.add(new CustomEntry(r.getName(), true));
				}				
				displayListView();
			}
		}
	}
}