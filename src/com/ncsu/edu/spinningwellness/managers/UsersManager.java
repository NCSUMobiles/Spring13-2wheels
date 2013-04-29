package com.ncsu.edu.spinningwellness.managers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import com.ncsu.edu.spinningwellness.Utils.RestClientUtils;
import com.ncsu.edu.spinningwellness.Utils.Utils;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.entities.User;
import com.ncsu.edu.spinningwellness.entities.UserActivity;

public class UsersManager {

	public static String createUser(String name, String email) {
		HttpPost post = RestClientUtils.createHttpPostRequest(Constants.CREATE_USER_URL, new User(name, email).toJSON());
		return RestClientUtils.executeRequest(post);
	}

	public static String deleteUser(String name) {
		HttpDelete delete = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_USER_URL + "/" + name);
		return RestClientUtils.executeRequest(delete);
	}

	public static String logActivity(String rideId, String userName, double distaceCovered,
			double cadence, double averageSpeed, double caloriesBurned, double heartRate, Date activityDate) {

		Date currentTimestamp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMddyyHHmmssSSS");
		UserActivity ua = new UserActivity(
				df.format(currentTimestamp), 
				rideId, 
				userName, 
				distaceCovered,cadence, 
				averageSpeed, 
				caloriesBurned, 
				heartRate,
				Utils.convertDateToString(activityDate)
				);
		HttpPost post = RestClientUtils.createHttpPostRequest(Constants.LOG_USER_ACTIVITY_URL, ua.toJSON());
		return RestClientUtils.executeRequest(post);
	}

	public static List<UserActivity> viewPastActivityForLastWeek(String userName) {

		List<UserActivity> activities = new ArrayList<UserActivity>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_ACTIVITY_FOR_LAST_WEEK_URL + "/" + userName);
		String JSON = RestClientUtils.executeRequest(get);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(JSON, UserActivity.class);
		for(Object r: rs) {
			UserActivity ride = (UserActivity) r;  
			activities.add(ride);
		}
		return activities;
	}

	public static List<UserActivity> viewPastActivity(String userName) {
		List<UserActivity> activities = new ArrayList<UserActivity>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_ACTIVITY_URL + "/" + userName);
		String JSON = RestClientUtils.executeRequest(get);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(JSON, UserActivity.class);
		for(Object r: rs) {
			UserActivity ride = (UserActivity) r;  
			activities.add(ride);
		}
		return activities;
	}

	public static UserActivity viewPastActivityForARide(String userName, String rideId) {
		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_ACTIVITY__FOR_A_RIDE_URL 
				+ "/" + userName
				+ "/" + rideId);
		String JSON = RestClientUtils.executeRequest(get);

		return (UserActivity)Utils.JSONToObject(JSON, UserActivity.class);
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

	public static List<Ride> viewLoggedPastRidesForLastWeek(String userName) {

		List<Ride> rides = new ArrayList<Ride>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_MY_LOGGED_PAST_RIDES_FOR_LAST_WEEK_URL + "/" + userName);
		String JSON = RestClientUtils.executeRequest(get);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(JSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;
	}

	public static List<Ride> viewLoggedPastRides(String userName) {
		List<Ride> rides = new ArrayList<Ride>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_MY_LOGGED_PAST_RIDES_URL	 + "/" + userName);
		String JSON = RestClientUtils.executeRequest(get);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(JSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;
	}

	public static List<User> viewTopPerformersForLastWeek() {
		List<User> users = new ArrayList<User>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_TOP_PERFORMERS_FOR_LAST_WEEK_URL);
		String JSON = RestClientUtils.executeRequest(get);

		@SuppressWarnings("unchecked")
		List<Object> us =  Utils.JSONToObjectList(JSON, User.class);
		for(Object u: us) {
			User user = (User) u;  
			users.add(user);
		}
		return users;
	}

	public static List<User> viewTopPerformers() {
		List<User> users = new ArrayList<User>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_TOP_PERFORMERS_URL);
		String JSON = RestClientUtils.executeRequest(get); 

		@SuppressWarnings("unchecked")
		List<Object> us =  Utils.JSONToObjectList(JSON, User.class);
		for(Object u: us) {
			User user = (User) u;  
			users.add(user);
		}
		return users;
	}
	
	public static List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		HttpGet get = RestClientUtils.createHttpGetRequest(Constants.VIEW_ALL_USERS);
		String JSON = RestClientUtils.executeRequest(get); 
		System.out.println(JSON);
		if(JSON != null){
			@SuppressWarnings("unchecked")
			List<Object> us =  Utils.JSONToObjectList(JSON, User.class);
			for(Object u: us) {
				User user = (User) u;  
				users.add(user);
			}
		}
		return users;
	}
	
	public static User getUser(String userName) {

		HttpGet viewUser = RestClientUtils.createHttpGetRequest(Constants.VIEW_USER_URL + "/" + userName);
		String JSON = RestClientUtils.executeRequest(viewUser);

		return (User)Utils.JSONToObject(JSON, User.class);
	}
}