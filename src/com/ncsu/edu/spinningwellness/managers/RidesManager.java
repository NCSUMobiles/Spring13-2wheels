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
import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

public class RidesManager {

	public static String createRide(String name, String source, String dest,
			Date startTime, String creator) {

		Date currentTimestamp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMddyyHHmmssSSS");
		HttpPost post = RestClientUtils.createHttpPostRequest(
				Constants.CREATE_RIDE_URL, 
				new Ride(df.format(currentTimestamp), name, source, dest, Utils.convertDateToString(startTime), creator).toJSON()
				);

		return RestClientUtils.executeRequest(post);
	}

	public static String updateRide(String id, 
			String name, 
			String source, 
			String dest,
			Date startTime, 
			String creator) {
		HttpPost post = RestClientUtils.createHttpPostRequest(
				Constants.UPDATE_RIDE_URL + "/" + id, 
				new Ride(id, name, source, dest, Utils.convertDateToString(startTime), creator).toJSON()
				);
		return RestClientUtils.executeRequest(post);
	}

	public static String deleteRide(String id) {
		HttpDelete delete = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_RIDE_URL + "/" + id);
		return RestClientUtils.executeRequest(delete);
	}

	public static Ride viewRide(String id) {
		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_RIDE_URL + "/" + id);
		String JSON = RestClientUtils.executeRequest(viewRide);

		return (Ride)Utils.JSONToObject(JSON, Ride.class);
	}

	public static List<Ride> viewPastRides() {
		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_RIDES_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;		
	}	

	public static List<Ride> viewPastRidesFromPastWeek() {
		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_PAST_RIDES_FROM_LAST_WEEK_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;		
	}	

	public static List<Ride> viewMyPastRides(String userName) {
		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(
				Constants.VIEW_MY_PAST_RIDES_URL + 
				"/" + 
				userName
				);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;		
	}	

	public static List<Ride> viewUpcomingRides() {

		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_UPCOMING_RIDES_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;		
	}		

	public static List<Ride> viewUpcomingRidesFromNextWeek() {
		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_UPCOMING_RIDES_FROM_LAST_WEEK_URL);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;		
	}

	public static String addParticipantToRide(String rideId, String userName) {

		Date currentTimestamp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMddyyHHmmssSSS");

		HttpPost addParticipantToRide = RestClientUtils.createHttpPostRequest(Constants.ADD_PARTICIPANT_TO_RIDE_URL,
				new Participant(df.format(currentTimestamp), rideId, userName).toJSON()
				);

		return RestClientUtils.executeRequest(addParticipantToRide);
	}

	public static String deleteParticipantToRide(String rideId, String userName) {
		HttpDelete deleteParticipantFromRide = RestClientUtils.createHttpDeleteRequest(Constants.DELETE_PARTICIPANT_FROM_RIDE_URL +
				"/" + rideId +
				"/" + userName
				);
		return RestClientUtils.executeRequest(deleteParticipantFromRide);
	}

	public static List<Participant> viewParticipantsForRide(String rideId) {
		List<Participant> participants = new ArrayList<Participant>();

		HttpGet viewParticipantsForRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_PARTICIPANTS_FOR_RIDE_URL +
				"/" + rideId
				);
		String JSON = RestClientUtils.executeRequest(viewParticipantsForRide);

		@SuppressWarnings("unchecked")
		List<Object> ps =  Utils.JSONToObjectList(JSON, Participant.class);
		for(Object p: ps) {
			Participant participant = (Participant) p;  
			participants.add(participant);
		}
		return participants;
	}

	public static List<Ride> viewMyUpcomingRidesFromNextWeek(String userName) {

		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(Constants.VIEW_MY_UPCOMING_RIDES_FOR_NEXT_WEEK_URL +
				Constants.VIEW_MY_UPCOMING_RIDES_URL + 
				"/" + 
				userName
				);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);

		@SuppressWarnings("unchecked")
		List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
		for(Object r: rs) {
			Ride ride = (Ride) r;  
			rides.add(ride);
		}
		return rides;		
	}		

	public static List<Ride> viewMyUpcomingRides(String userName) {

		List<Ride> rides = new ArrayList<Ride>();

		HttpGet viewRide = RestClientUtils.createHttpGetRequest(
				Constants.VIEW_MY_UPCOMING_RIDES_URL + 
				"/" + 
				userName
				);
		String ridesJSON = RestClientUtils.executeRequest(viewRide);
		if(ridesJSON != null){
		
			@SuppressWarnings("unchecked")
			List<Object> rs =  Utils.JSONToObjectList(ridesJSON, Ride.class);
			for(Object r: rs) {
				Ride ride = (Ride) r;  
				rides.add(ride);
			}
		}
		return rides;		
	}		
}