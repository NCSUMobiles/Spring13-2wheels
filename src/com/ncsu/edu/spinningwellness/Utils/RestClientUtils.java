package com.ncsu.edu.spinningwellness.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class RestClientUtils {

	public static HttpGet createHttpGetRequest(String restURL) {

		HttpGet request = new HttpGet(restURL);
		return request;
	}

	public static HttpPost createHttpPostRequest(String restUrl, String JSON) {

		HttpPost post = new HttpPost(restUrl);		
		StringEntity input;
		try {
			
			if(JSON != null) {
				input = new StringEntity(JSON);
				input.setContentType("application/json");
				post.setEntity(input);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}
	
	public static HttpDelete createHttpDeleteRequest(String restUrl) {

		HttpDelete delete = new HttpDelete(restUrl);		
		return delete;
	}
	
	public static String executeRequest(HttpRequestBase request) {
		BufferedReader rd = null;
		StringBuffer op = new StringBuffer();
		try {
			HttpClient client = new DefaultHttpClient();

			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 201) {
			}

			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			String serverOutput = null;
			while ((serverOutput = rd.readLine()) != null) {
				op.append(serverOutput);
			}

			client.getConnectionManager().shutdown();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return op.toString();
	}
}