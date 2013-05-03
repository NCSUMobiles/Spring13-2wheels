package com.ncsu.edu.spinningwellness.activities;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class SWCommon {
	
	public String BASE_URL = "http://spinningwellness2013.appspot.com";
	
	/*make a get request
	 * Call: "/resources/l2w/viewpastrides"
	 */
	public void callWebServiceGet(String call){  
		HttpClient httpclient = new DefaultHttpClient();  
		HttpGet request = new HttpGet(BASE_URL + call);  
		//	     request.addHeader("deviceId", deviceId);  
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result;
		try {  
			result = httpclient.execute(request, handler);  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		httpclient.getConnectionManager().shutdown();  
	}
	
	public void callWebServicePost(String call){  
		HttpClient httpclient = new DefaultHttpClient();  
		HttpPost request = new HttpPost(BASE_URL + call);  
		//	     request.addHeader("deviceId", deviceId);  
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result;
		try {  
			result = httpclient.execute(request, handler);  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		httpclient.getConnectionManager().shutdown();  
	}
}
