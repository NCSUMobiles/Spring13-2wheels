package com.example.spinningwellness;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.tabpanel.MenuConstants;
import com.ncsu.edu.tabpanel.MyTabHostProvider;
import com.ncsu.edu.tabpanel.TabView;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewRideDetailsActivity extends BaseActivity {

	TableLayout	ride_table;
	Ride ride;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Draw menu
		tabProvider = new MyTabHostProvider(ViewRideDetailsActivity.this);
		TabView tabView = tabProvider.getTabHost(MenuConstants.PAST_RIDES);
		tabView.setCurrentView(R.layout.view_ride_details_activity);
		setContentView(tabView.render());

		addListenerOnButton();

		ride_table=(TableLayout)findViewById(R.id.ride_table);

		fillRideDetails(ride);      
	}

	@Override
	public void setTitle() {
		ride = getIntent().getParcelableExtra("Ride");

		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Ride Details for Ride:" + ride.getName());
	}

	void fillRideDetails(Ride RideDetails) {

		TableRow row;
		TextView t1, t2;

		//Converting to dip unit
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());

		String[] item1={"Ride Name:","Start at:","Ends at: ","Time:","Creator:"};

		String time;
		Date date = new Date(RideDetails.getStartTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm");

		String[] item2={RideDetails.getName(),RideDetails.getSource(),RideDetails.getDest(),format.toString(),RideDetails.getCreator()};
		for (int i = 0; i<5; i++) {

			row = new TableRow(this);

			t1 = new TextView(this);
			t1.setTextColor(Color.parseColor("#ff87aa14"));
			t1.setTypeface(Typeface.DEFAULT_BOLD);

			t2 = new TextView(this);
			t2.setTextColor(Color.parseColor("#ff87aa14"));

			t1.setText(item1[i]);
			t2.setText(" "+item2[i]);

			t2.setTypeface(null, 1);
			t1.setTextSize(20);
			t2.setTextSize(20);
			t2.setBackgroundResource(R.layout.cell_shape);

			t1.setHeight(35 * dip);
			t2.setHeight(35 * dip);

			t1.setWidth(50 * dip);
			t2.setWidth(110 * dip);
			t1.setPadding(20*dip, 0, 0, 0);

			row.addView(t1);       	
			row.addView(t2);
			ride_table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	public void addListenerOnButton() {

		final Context context = this;

		Button member_button  = (Button) findViewById(R.id.btnviewmembers);
		member_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent member_intent = new Intent(context, ViewParticipantsForARideActivity.class);
				member_intent.putExtra("RideDetails", ride);
				startActivity(member_intent);   
			}
		});
	}
}