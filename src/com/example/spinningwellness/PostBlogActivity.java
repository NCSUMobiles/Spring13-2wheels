package com.example.spinningwellness;

import java.net.MalformedURLException;
import java.util.List;


import net.bican.wordpress.Page;
import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import redstone.xmlrpc.XmlRpcFault;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostBlogActivity extends BaseActivity {

	String username;
	String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_blog_activity);

		TextView postBlog = (TextView) findViewById(R.id.btnPostBlog);
		postBlog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = getIntent().getStringExtra("username");
				password = getIntent().getStringExtra("password");
				new PostBlogTask().execute();
			}
		});
	}

	

	private class PostBlogTask extends AsyncTask<Void,Void,User> {
		Exception error;
		protected User doInBackground(Void... params) {
			//System.out.println("********bckgrnd");
			String xmlRpcUrl = "http://spinningwellness.wordpress.com/xmlrpc.php";
			// Switching to Register screen

			System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
			Wordpress wp;
			try {
				wp = new Wordpress(username, password, xmlRpcUrl);
				List<Page> plist = wp.getPages();
				for(Page p:plist){
					int i = p.getPage_id();
				}
				Page recentPost = new Page();
				recentPost.setDescription(((TextView) findViewById(R.id.txtexperience)).getText().toString());
				String result = wp.newPost(recentPost, true);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e;
			} catch (XmlRpcFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e;
			}
			return null;
		}

		protected void onPostExecute(User result) {
			//System.out.println("********post");
			if(error != null){
				Toast.makeText(PostBlogActivity.this, "Issue in posting to blog", Toast.LENGTH_SHORT).show();
			} else{
				Toast.makeText(PostBlogActivity.this, "Post Successful", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void setTitle() {
		final TextView myTitleText = (TextView)findViewById(R.id.myTitle);
		myTitleText.setText(SPINNING_WEELNESS + " " + "Post To Blog");		
	}

}
