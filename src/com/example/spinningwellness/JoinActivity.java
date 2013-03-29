package com.example.spinningwellness;

import java.util.ArrayList;
import java.util.List;
 
import android.app.Activity;
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
     
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        //Generate list View from ArrayList
  displayListView();
  
 }
  
 private void displayListView() {
 
  //Array list of countries
  List<String> countryList = new ArrayList<String>();
  countryList.add("Aruba");
  countryList.add("Anguilla");
  countryList.add("Netherlands Antilles");
  countryList.add("Antigua and Barbuda");
  countryList.add("Bahamas");
  countryList.add("Belize");
  countryList.add("Bermuda");
  countryList.add("Barbados");
  countryList.add("Canada");
  countryList.add("Costa Rica");
  countryList.add("Cuba");
  countryList.add("Cayman Islands");
  countryList.add("Dominica");
  countryList.add("Dominican Republic");
  countryList.add("Guadeloupe");
  countryList.add("Grenada");
  countryList.add("Greenland");
  countryList.add("Guatemala");
  countryList.add("Honduras");
  countryList.add("Haiti");
  countryList.add("Jamaica");
   
  //create an ArrayAdaptar from the String Array
  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    R.layout.join_list, countryList);
  ListView listView = (ListView) findViewById(R.id.listView1);
  // Assign adapter to ListView
  listView.setAdapter(dataAdapter);
   
  //enables filtering for the contents of the given ListView
  listView.setTextFilterEnabled(true);
 
  listView.setOnItemClickListener(new OnItemClickListener() {
   public void onItemClick(AdapterView<?> parent, View view,
     int position, long id) {
       // When clicked, show a toast with the TextView text
    Toast toast = Toast.makeText(getApplicationContext(),
      ((TextView) view).getText(), Toast.LENGTH_LONG);
    toast.setGravity(Gravity.TOP, 25, 300); 
    toast.show();
   }
  });
   
 }
}