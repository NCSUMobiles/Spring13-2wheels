package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.ncsu.edu.spinningwellness.entities.Participant;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;

import redstone.xmlrpc.XmlRpcFault;

import android.app.Activity;
import android.app.Application;
import android.app.ActionBar.LayoutParams;
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

public class ViewMemberActivity extends BaseActivity {

	
	TableLayout member_table;
	List<Participant> participants = new ArrayList<Participant>();
	Ride RideId; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_members);
        addListenerOnButton();
        member_table = (TableLayout)findViewById(R.id.member_table);
        RideId = getIntent().getParcelableExtra("RideId");
        System.out.println("Ride Name via getName"+RideId.getName());
        fillParticipantDetails(RideId);      
    }
	
	
    void fillParticipantDetails(Ride RideId) {
    	
    	TableRow row;
        TextView t1, t2;
        
        new ViewParticipantsTask(RideId.getId()).execute();
       
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for(Participant p: participants) {
        	
        	 System.out.println("Participant name in  "+p.getUserName());
           	row = new TableRow(this);
        	
        	t1 = new TextView(this);
        	t1.setText(p.getUserName());
        	

        	t1.setTextColor(Color.parseColor("#ff87aa14"));
        	t1.setTypeface(Typeface.DEFAULT_BOLD);

        	t1.setText(RideId.getName());
            t1.setTextSize(20);
            t1.setHeight(35 * dip);
            t1.setWidth(50 * dip);
            t1.setPadding(20*dip, 0, 0, 0);

            row.addView(t1);       	
            member_table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }
    
    public void addListenerOnButton() {
    	
    	final Context context = this;
        
        Button challenge_button  = (Button) findViewById(R.id.btnchallenge);
        
        challenge_button.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View arg0) {
 
			    Intent challenge_intent = new Intent(context, ChallengeFriendActivity.class);
                            startActivity(challenge_intent);   
 
			}
 
		});
        
                  
    }
    
    public class ViewParticipantsTask extends AsyncTask<Void,Void,List<Participant>> {
		Exception error;
		Intent i;
		String RideId;
		
		public ViewParticipantsTask(String Id) {
			// TODO Auto-generated constructor stub
			RideId = Id;
		}

        
		
		void setIntent(Intent intent){
			i=intent;
		}
		
		protected List<Participant> doInBackground(Void... params) {
			
			return RidesManager.viewParticipantsForRide(RideId);
		}

		protected void onPostExecute(List<Participant> result) {
			if(error != null){
				 
			} else{
				 List<Participant> participants = result;
				 for(Participant p:participants){
					 System.out.println(p.getUserName());
				 }
				
			}
		}
	}

	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "View Activity");		
	}

}




