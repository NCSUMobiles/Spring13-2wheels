package com.ncsu.edu.spinningwellness.managers;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;

import com.ncsu.edu.spinningwellness.Utils.RestClientUtils;
import com.ncsu.edu.spinningwellness.entities.User;

public class UsersManager {

	public static void createUser(String name) {
		HttpPost post = RestClientUtils.createHttpPostRequest(Constants.CREATE_USER_URL, new User(name).toJSON());
		RestClientUtils.executeRequest(post);
	}
	
	public static void deleteUser(String name) {
		HttpDelete delete = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_USER_URL + "/" + name);
		RestClientUtils.executeRequest(delete);
	}
	
	
	
	
}
