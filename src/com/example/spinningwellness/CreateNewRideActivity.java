package com.example.spinningwellness;

import java.net.MalformedURLException;
import java.util.List;

import redstone.xmlrpc.XmlRpcFault;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CreateNewRideActivity extends Activity implements OnClickListener{
	
	 public void onClick(View arg0) {
	    	if(arg0.getId() == R.id.btnRegister){
	    		System.out.println("Clicked register");
	    		//define a new Intent for the second Activity
	    		Intent intent = new Intent(this,UpcomingRidesActivity.class);
	     
	    		//start the second Activity
	    		this.startActivity(intent);
	    	}
	 }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_create_new_ride);
        
        View v = findViewById(R.id.btnRegister);
    	//set event listener
            v.setOnClickListener(this);
        
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        //get the items from the login screen
        String username = getIntent().getStringExtra("username");
        System.out.println("Welcome " + username + "!");
       
        TextView postBlog = (TextView) findViewById(R.id.post_blog);
        
        // Listening to Login Screen link
//        loginScreen.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// Switching to Login Screen/closing register screen
//				finish();
//			}
//		});
        
        postBlog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				// TODO Auto-generated method stub
				String username = getIntent().getStringExtra("username");
				String password = getIntent().getStringExtra("password");
				String xmlRpcUrl = "http://spinningwellness.wordpress.com/xmlrpc.php";
				try {
					Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
					List<Page> plist = wp.getPages();
					for(Page p:plist){
						int i = p.getPage_id();
						
					}
					 Page recentPost = new Page();
					 recentPost.setDescription("Test post from java");
					 String result = wp.newPost(recentPost, true);
					 
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlRpcFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println();
			}
		});
        
       
    }
    
   
    
}