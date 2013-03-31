package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;

import com.ncsu.edu.spinningwellness.entities.Ride;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class JoinActivity extends Activity {
	List<String> rideNameList = new ArrayList<String>();
	List<Ride> rideList = new ArrayList<Ride>();
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        rideList = getIntent().getParcelableArrayListExtra("rideList");
        for(Ride r:rideList){
        	rideNameList.add(r.getName());
        }
        //Generate list View from ArrayList
  displayListView();
  
 }
  
 private void displayListView() {
 
  //Array list of Rides
//  rideList.add("RtoDurham");
//  rideList.add("RtoCary");
//  rideList.add("RtoGreensboro");
//  rideList.add("RtoLakeRaleigh");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  rideList.add("RtoRTP");
//  countryList.add("Belize");
//  countryList.add("Bermuda");
//  countryList.add("Barbados");
//  countryList.add("Canada");
//  countryList.add("Costa Rica");
//  countryList.add("Cuba");
//  countryList.add("Cayman Islands");
//  countryList.add("Dominica");
//  countryList.add("Dominican Republic");
//  countryList.add("Guadeloupe");
//  countryList.add("Grenada");
//  countryList.add("Greenland");
//  countryList.add("Guatemala");
//  countryList.add("Honduras");
//  countryList.add("Haiti");
//  countryList.add("Jamaica");
   
  //create an ArrayAdaptar from the String Array
  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.join_list,rideNameList);
  ListView listView = (ListView) findViewById(R.id.listView1);
 
  // Assign adapter to ListView
  listView.setAdapter(dataAdapter);
 
  //  listView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
   
  //enables filtering for the contents of the given ListView
  listView.setTextFilterEnabled(true);
 
  listView.setOnItemClickListener(new OnItemClickListener() {
   public void onItemClick(AdapterView<?> parent, View view,
     int position, long id) {
       // When clicked, show a toast with the TextView text
    Toast toast = Toast.makeText(getApplicationContext(),
      ((TextView) view).getText(), Toast.LENGTH_LONG);
    
   
	   
//	   String text = ((TextView) view).getText().toString();
    toast.setGravity(Gravity.TOP, 25, 300); 
    toast.show();
    Ride selectedRide = null;
    for(Ride r:rideList){
    	if(((TextView) view).getText().toString().equalsIgnoreCase(r.getName())){
    		selectedRide = r;
    		break;
    	}
    }
    
    Intent i = new Intent(getApplicationContext(), ViewRidesActivity.class);
    i.putExtra("RideDetails", selectedRide);
	startActivity(i);
   }
  });
   
 }
}