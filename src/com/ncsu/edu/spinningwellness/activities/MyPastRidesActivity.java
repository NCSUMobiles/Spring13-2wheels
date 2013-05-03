package com.ncsu.edu.spinningwellness.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.spinningwellness.R;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyPastRidesActivity extends BaseActivity {

	List<Ride> myPastRides = new ArrayList<Ride>();
	ArrayList<String> myPastRideNames = new ArrayList<String>();
	private LinearLayout progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(MyPastRidesActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.PAST_RIDES);
		tabView.setCurrentView(R.layout.my_past_rides_activity);
		setContentView(tabView.render());			

		progressBar = (LinearLayout) findViewById(R.id.myPastRidesSpinner);
		progressBar.setVisibility(View.VISIBLE);

		new GetMyPastRidesTask().execute();		
	}

	private void displayListView() {

		progressBar.setVisibility(View.INVISIBLE);

		//create an ArrayAdaptar from the String Array
		MyPastRidesCustomAdapter dataAdapter = new MyPastRidesCustomAdapter(this, R.id.myPastRidesTextVal, myPastRideNames);
		final ListView listView = (ListView) findViewById(R.id.myPastRidesListView);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Ride selectedRide = null;
				String Caller = "PastRide";
				int positionView = listView.getPositionForView(view);
				if (positionView != ListView.INVALID_POSITION) {
					//start view activity
					selectedRide = myPastRides.get(positionView);
					Intent i = new Intent(getApplicationContext(), ViewPastRideDetailsActivity.class);
					i.putExtra("Ride", selectedRide);
					i.putExtra("Caller",Caller);
					startActivity(i);
				} else {
					Toast toast = Toast.makeText(getApplicationContext(), "An error occured.", Toast.LENGTH_SHORT);
					TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
					v.setTextColor(getResources().getColor(R.color.red));
					toast.show();
				}
			}
		});	}

	@Override
	protected void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText("My Past Rides");		
	}

	public class GetMyPastRidesTask extends AsyncTask<Void,Void,List<Ride>> {
		Exception error;

		protected List<Ride> doInBackground(Void... params) {
			return RidesManager.viewMyPastRides(BaseActivity.username);
		}

		protected void onPostExecute(List<Ride> result) {
			if(error != null){

			} else {
				myPastRides = result;
				for(Ride r: myPastRides){
					myPastRideNames.add(r.getName());
				}				
				displayListView();
			}
		}
	}

	public class MyPastRidesCustomAdapter extends ArrayAdapter<String> {
		
		private ArrayList<String> myPastRides;
		private Activity activity;

		public MyPastRidesCustomAdapter(Activity a, int textViewResourceId, ArrayList<String> myPastRides) {
			super(a, textViewResourceId, myPastRides);
			this.myPastRides = myPastRides;
			this.activity = a;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.my_past_rides_list, null);
				holder = new ViewHolder();
				holder.myPastRideName = (TextView) v.findViewById(R.id.myPastRidesTextVal);
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			final String custom = myPastRides.get(position);
			if (custom != null) {
				holder.myPastRideName.setText(custom);
			}

			return v;
		}
		
		public class ViewHolder {
			public TextView myPastRideName;
		}
	}
}