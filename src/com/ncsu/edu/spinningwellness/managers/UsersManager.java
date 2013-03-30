package com.ncsu.edu.spinningwellness.managers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import com.google.gson.Gson;
import com.ncsu.edu.spinningwellness.Utils.RestClientUtils;
import com.ncsu.edu.spinningwellness.Utils.Utils;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.entities.User;
import com.ncsu.edu.spinningwellness.entities.UserActivity;

public class UsersManager {

	public static void createUser(String name) {
		HttpPost post = RestClientUtils.createHttpPostRequest(Constants.CREATE_USER_URL, new User(name).toJSON());
		RestClientUtils.executeRequest(post);
	}
	
	public static void deleteUser(String name) {
		HttpDelete delete = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_USER_URL + "/" + name);
		RestClientUtils.executeRequest(delete);
	}
	
	public static void logActivity(String rideId, String userName, double distaceCovered,
			double cadence, double averageSpeed, double caloriesBurned, Date activityDate) {
		
		Date currentTimestamp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMddyyHHmmssSSS");
		UserActivity ua = new UserActivity(
				df.format(currentTimestamp), 
				rideId, 
				userName, 
				distaceCovered,cadence, 
				averageSpeed, 
				caloriesBurned, 
				Utils.convertDateToLong(activityDate)
				);
		HttpPost post = RestClientUtils.createHttpPostRequest(Constants.LOG_USER_ACTIVITY_URL, ua.toJSON());
		RestClientUtils.executeRequest(post);
	}
	
	public static void viewPastActivityForLastWeek(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_ACTIVITY_FOR_LAST_WEEK_URL + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static void viewPastActivity(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_ACTIVITY_URL + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static void viewWorkoutDetailsForLastWeek(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_WORKOUT_DETAILS_FOR_LAST_WEEK_URL + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static void viewWorkoutDetails(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_WORKOUT_DETAILS_URL + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static Ride viewBestRide(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_MY_BEST_RIDE_URL + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);

		return (Ride)Utils.JSONToObject(rideJSON, Ride.class);
	}
	
	public static void viewLoggedPastRidesForLastWeek(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_MY_LOGGED_PAST_RIDES_FOR_LAST_WEEK_URL + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static void viewLoggedPastRides(String userName) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_MY_LOGGED_PAST_RIDES_URL	 + "/" + userName);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static void viewTopPerformersForLastWeek() {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_TOP_PERFORMERS_FOR_LAST_WEEK_URL);
		String rideJSON = RestClientUtils.executeRequest(get);
	}
	
	public static void viewTopPerformers() {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_TOP_PERFORMERS_URL);
		String rideJSON = RestClientUtils.executeRequest(get); 
	}
}