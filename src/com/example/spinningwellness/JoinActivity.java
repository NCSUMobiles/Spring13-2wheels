package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;

import com.ncsu.edu.customadapters.CustomEntry;
import com.ncsu.edu.spinningwellness.entities.Ride;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JoinActivity extends Activity {
	List<String> rideNameList = new ArrayList<String>();
	ArrayList<CustomEntry> rideEntry = new ArrayList<CustomEntry>();
	List<Ride> rideList = new ArrayList<Ride>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		rideList = getIntent().getParcelableArrayListExtra("rideList");
		for(Ride r:rideList){
			rideNameList.add(r.getName());
			rideEntry.add(new CustomEntry(r.getName(), true));
		}
		//		rideEntry.add(new CustomEntry("prajakta", true));
		//Generate list View from ArrayList
		displayListView();

	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.welcome_menu, menu);
		return true;
	}


	/***** Menu Settings ******/

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent i;
		switch (item.getItemId())
		{
		case R.id.menu_create_new_ride:
			i = new Intent(getApplicationContext(),CreateNewRideActivity.class);
			startActivity(i);
			return true;

		case R.id.menu_past:
			i = new Intent(getApplicationContext(), MyPastRidesActivity.class);
			startActivity(i);
			//         Toast.makeText(WelcomeActivity.this, "Past Rides is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_edit_del:

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void displayListView() {

		//create an ArrayAdaptar from the String Array
		CustomAdapter dataAdapter = new CustomAdapter(this, R.id.listView1,rideEntry);
		final ListView listView = (ListView) findViewById(R.id.listView1);

		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		//  listView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));

		//enables filtering for the contents of the given ListView
		listView.setTextFilterEnabled(true);
		//listView.setItemsCanFocus(false);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Toast.makeText(getApplicationContext(), "Click ListItemNumber " + position,Toast.LENGTH_LONG).show();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), " Clicked " , Toast.LENGTH_SHORT).show();

				Ride selectedRide = null;
				int positionView = listView.getPositionForView(view);
				if (positionView != ListView.INVALID_POSITION) {
					System.out.println( rideList.get(positionView).getName());
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
					Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
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

}