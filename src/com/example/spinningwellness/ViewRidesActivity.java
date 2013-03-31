package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.example.spinningwellness.WelcomeActivity.GetUpcomingRidesTask;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;

import redstone.xmlrpc.XmlRpcFault;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ViewRidesActivity extends Activity {
	
	 /** Called when the activity is first created. */
    TableLayout	ride_table;
    Ride RideDetails;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_details);
        addListenerOnButton();
        ride_table=(TableLayout)findViewById(R.id.ride_table);
     //   Bundle extras = getIntent().getExtras(); 
        RideDetails = getIntent().getParcelableExtra("RideDetails");
        System.out.println("Ride Name via getName"+RideDetails.getName());
        fillRideDetails(RideDetails);      
    }
    
    void fillRideDetails(Ride RideDetails) {
    	
    	TableRow row;
        TextView t1, t2;
        
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());
        
        String[] item1={"Ride id:","Ride Name:","Start at:","Ends at: ","Time:","Creator:"};
        String[] item2={RideDetails.getId(),RideDetails.getName(),RideDetails.getSource(),RideDetails.getDest(),String.valueOf(RideDetails.getStartTime()),RideDetails.getCreator()};
        for (int i = 0; i<6; i++)
        {
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
        	//t2 = new TextView(this);
        }
    }
    public void addListenerOnButton() {
    	
    	final Context context = this;
        
        Button member_button  = (Button) findViewById(R.id.btnviewmembers);
        ImageButton join_button = (ImageButton) findViewById(R.id.btnjoin); 
        
        member_button.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View v) {
 
			    Intent member_intent = new Intent(context, ViewMemberActivity.class);
			    member_intent.putExtra("RideId", RideDetails);
                System.out.println("Ride details passed from ViewRides: " + RideDetails.toString());
            	startActivity(member_intent);   
 
			}
 
		});
        
        join_button.setOnClickListener(new OnClickListener() {
       	 
			@Override
			public void onClick(View v) {
 
			    //Functionality using webservices to add user to ride in db
                
			}
 
		});
           
    }
    
	public class GetUpcomingRidesTask extends AsyncTask<Void,Void,List<Ride>> {
		Exception error;
		Intent i;
		
		void setIntent(Intent intent){
			i=intent;
		}
		
		protected List<Ride> doInBackground(Void... params) {
			return RidesManager.viewUpcomingRides();
		}

		protected void onPostExecute(List<Ride> result) {
			if(error != null){
				 
			} else{
				 List<Ride> rideList = result;
				 for(Ride r:rideList){
					 System.out.println(r.getName());
				 }
				 Intent i = new Intent(getApplicationContext(), JoinActivity.class);
				 i.putParcelableArrayListExtra("rideList",(ArrayList<? extends Parcelable>) rideList);
				 startActivity(i);
			}
		}
	}
}
        	 
    