package com.example.spinningwellness;

import java.util.List;
import java.net.MalformedURLException;

import com.ncsu.edu.spinningwellness.entities.Ride;

import redstone.xmlrpc.XmlRpcFault;

import android.app.Activity;
import android.app.Application;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

public class ViewMemberActivity extends Activity {

	TableLayout member_table;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_members);
        addListenerOnButton();
        member_table = (TableLayout)findViewById(R.id.member_table);
        Ride RideId = getIntent().getParcelableExtra("RideId");
        System.out.println("Ride Name via getName"+RideId.getName());
        fillRideDetails(RideId);      
    }
	
	
    void fillRideDetails(Ride RideId) {
    	
    	TableRow row;
        TextView t1, t2;
        
//        Class.forName("com.mysql.jdbc.Driver") ;
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAME", "usrname", "pswd") ;
//        Statement stmt = conn.createStatement() ;
//        String query = "select userName from participant where rideId=RideId.getName();" ;
//        ResultSet rs = stmt.executeQuery(query) ;
//		  Display all names in result set        
        
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());
        
        //while rs not empty
        //while (rs.next())
        //{
        //t1.setText(rs.getString(col number for name));
        	row = new TableRow(this);
        	
        	t1 = new TextView(this);
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
}




