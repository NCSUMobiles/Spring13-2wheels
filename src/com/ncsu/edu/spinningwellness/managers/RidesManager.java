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
import com.ncsu.edu.spinningwellness.entities.Participant;
import com.ncsu.edu.spinningwellness.entities.Ride;

public class RidesManager {

	public static void createRide(String name, String source, String dest,
			Date startTime, String creator) {

		Date currentTimestamp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMddyyHHmmssSSS");
		HttpPost post = RestClientUtils.createHttpPostRequest(
				Constants.CREATE_RIDE_URL, 
				new Ride(df.format(currentTimestamp), name, source, dest, Utils.convertDateToLong(startTime), creator).toJSON()
				);
		RestClientUtils.executeRequest(post);
	}
	
	public static void updateRide(String id, 
			String name, 
			String source, 
			String dest,
			Date startTime, 
			String creator) {
		HttpPost post = RestClientUtils.createHttpPostRequest(
				Constants.UPDATE_RIDE_URL + "/" + id, 
				new Ride(id, name, source, dest, Utils.convertDateToLong(startTime), creator).toJSON()
				);
		RestClientUtils.executeRequest(post);
	}

	public static void deleteRide(String id) {
		HttpDelete delete = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_RIDE_URL + "/" + id);
		RestClientUtils.executeRequest(delete);
	}
	
	public static void viewRide(String id) {
		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_RIDE_URL + "/" + id);
		String rideJSON = RestClientUtils.executeRequest(viewRide);
	}
	
	public static void viewPastRides() {
		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_RIDES_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);
	}	
	
	public static void viewPastRidesFromPastWeek() {
		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_RIDES_FROM_LAST_WEEK_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);
	}	

	public static List<Ride> viewUpcomingRides() {
		
		List<Ride> rides = new ArrayList<Ride>();
		
		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_UPCOMING_RIDES_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);
		
		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			System.out.println(r.toString());
			rides.add(ride);
		}
		
		return rides;
		
	}		
	
	public static void viewUpcomingRidesFromNextWeek() {
		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_UPCOMING_RIDES_FROM_LAST_WEEK_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);
	}
	
	public static void addParticipantToRide(String rideId, String userName) {

		Date currentTimestamp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMddyyHHmmssSSS");

		
		HttpPost addParticipantToRide = RestClientUtils.createHttpPostRequest(Constants.ADD_PARTICIPANT_TO_RIDE_URL,
				new Participant(df.format(currentTimestamp), rideId, userName).toJSON()
				);
		String ridesJSON = RestClientUtils.executeRequest(addParticipantToRide);
	}
	
	public static void deleteParticipantToRide(String rideId, String userName) {
		HttpDelete deleteParticipantFromRide = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_PARTICIPANT_FROM_RIDE_URL +
				"/" + rideId +
				"/" + userName
				);
		String ridesJSON = RestClientUtils.executeRequest(deleteParticipantFromRide);
	}
	
	public static void viewParticipantsForRide(String rideId) {
		HttpGet viewParticipantsForRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_PARTICIPANTS_FOR_RIDE_URL +
				"/" + rideId
				);
		String ridesJSON = RestClientUtils.executeRequest(viewParticipantsForRide);
	}
}