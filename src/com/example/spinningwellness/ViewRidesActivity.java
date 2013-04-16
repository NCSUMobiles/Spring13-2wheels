package com.example.spinningwellness;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.example.spinningwellness.ViewMemberActivity.ViewParticipantsTask;
import com.ncsu.edu.spinningwellness.entities.Participant;
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
import android.renderscript.ProgramFragmentFixedFunction.Builder.Format;
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
import android.widget.Toast;


public class ViewRidesActivity extends BaseActivity {
	
	 /** Called when the activity is first created. */
    TableLayout	ride_table;
    Ride RideDetails;
    String RideId;
	String username;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ride_details);
        addListenerOnButton();
        ride_table=(TableLayout)findViewById(R.id.ride_table);
     //   Bundle extras = getIntent().getExtras(); 
        RideDetails = getIntent().getParcelableExtra("RideDetails");
        username = getIntent().getStringExtra("username");
        System.out.println("Ride Name via getName"+RideDetails.getName());
        fillRideDetails(RideDetails);      
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
        for (int i = 0; i<5; i++)
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
//        ImageButton join_button = (ImageButton) findViewById(R.id.btnjoin); 
        
        member_button.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View v) {
 
			    Intent member_intent = new Intent(context, ViewMemberActivity.class);
			    member_intent.putExtra("RideId", RideDetails);
                System.out.println("Ride details passed from ViewRides: " + RideDetails.toString());
            	startActivity(member_intent);   
 
			}
 
		});
        /*TODO Move to join rides activity on star click*/
//        join_button.setOnClickListener(new OnClickListener() {
//       	 
//			@Override
//			public void onClick(View v) {
//				  // When clicked, show a toast with the TextView text
//				   
//				    RideId=RideDetails.getId();
//				    new AddParticipantsTask().execute();				    
//				    Toast.makeText(getApplicationContext(), username+" has been added to ride!", Toast.LENGTH_LONG).show();
//				    //setContentView(R.layout.activity_join);
//			}
// 
//		});
           
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
	
	public class AddParticipantsTask extends AsyncTask<Void,Void,String> {
		Exception error;
		Intent i;
		
		public AddParticipantsTask() {
			// TODO Auto-generated constructor stub
			
		}        
		
		void setIntent(Intent intent){
			i=intent;
		}
		
		protected String doInBackground(Void... params) {
			
			System.out.println("Printing in background task "+ RideId+" Username : "+username);
			return RidesManager.addParticipantToRide(RideId,username);
			
		}

		protected void onPostExecute(String result) {
			if(error != null){
				 
			} else{
				 String participants = result;
				 System.out.println("Printing in addparticipants async task "+participants);
				
			}
		}
	}

}
        	 
    