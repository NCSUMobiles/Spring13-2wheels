package com.ncsu.edu.spinningwellness.managers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ncsu.edu.spinningwellness.Utils.Utils;
import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.spinningwellness.managers.UsersManager;

public class Test {
	
	private static void createUsers() {
		UsersManager.createUser("amarja");
		UsersManager.createUser("minakshi");
		UsersManager.createUser("prajakta");
		UsersManager.createUser("sruthi");
		UsersManager.createUser("jay");
	}
	
	private static void createRides() {
		Date startDate;
		
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 2, 18);
		startDate = cal.getTime();
		RidesManager.createRide("Ride1", "source", "dest", startDate, "prajakta");
		
		cal.set(2013, 2, 21);
		startDate = cal.getTime();
		RidesManager.createRide("Ride2", "source", "dest", startDate, "minakshi");
		
		cal.set(2013, 2, 22);
		startDate = cal.getTime();
		RidesManager.createRide("Ride3", "source", "dest", startDate, "prajakta");
		
		startDate = new Date();
		cal.set(2013, 2, 23);
		startDate = cal.getTime();
		RidesManager.createRide("Ride4", "source", "dest", startDate, "amarja");
		
		cal = Calendar.getInstance();
		cal.set(2013, 2, 29);
		startDate = cal.getTime();
		RidesManager.createRide("Ride5", "source", "dest", startDate, "jay");
		
		cal.set(2013, 3, 4);
		startDate = cal.getTime();
		RidesManager.createRide("Ride6", "source", "dest", startDate, "sruthi");
		
		startDate = new Date();
		cal.set(2013, 3, 5);
		startDate = cal.getTime();
		RidesManager.createRide("Ride7", "source", "dest", startDate, "sruthi");

		cal.set(2013, 3, 6);
		startDate = cal.getTime();
		RidesManager.createRide("Ride8", "source", "dest", startDate, "minakshi");
		
		startDate = new Date();
		cal.set(2013, 9, 5);
		startDate = cal.getTime();
		RidesManager.createRide("Ride9", "source", "dest", startDate, "amarja");
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
//		Test.createUsers();
//		Test.createRides();
//		
//		RidesManager.deleteRide("032913013814373");
//		
//		UsersManager.deleteUser("amarja");
//		
//		UsersManager.deleteUser("minakshi");
//		
//		Date startDate = new Date(2013, 12, 03);
//		RidesManager.updateRide("032913013814629", "Ride2 - updated", "source - updated", "dest - updated", startDate, "prajakta");
//		
//		RidesManager.viewRide("032913013814629");
//		
//		RidesManager.viewPastRides();
//		
//		RidesManager.viewPastRidesFromPastWeek();
//		
//		RidesManager.viewUpcomingRidesFromNextWeek();
//
//		RidesManager.viewUpcomingRides();
//		
//		//check add participant with userName and rideId
//		RidesManager.addParticipantToRide("032913062845274", "minakshi");		
//
//		//check duplicate participant add
//		RidesManager.addParticipantToRide("032913062845274", "minakshi");
//		
//		//check view participants for ride
//		RidesManager.viewParticipantsForRide("032913062845274");
//		
//		//check delete participant
//		RidesManager.deleteParticipantToRide("032913062845274", "prajakta");
//		
//		//check ride delete when participants present for that ride
//		RidesManager.deleteRide("032913062845274");
//		
//		//check user delete when participants present with that user
//		UsersManager.deleteUser("amarja");
//
		Calendar cal1 = Calendar.getInstance();
		
		cal1.set(2013, Calendar.MARCH, 22);
		UsersManager.logActivity("033113191654957", "prajakta", 10.5d, 0.9d, 34.9d, 102d, 85, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 23);
//		UsersManager.logActivity("033113183451344", "minakshi", 10.5d, 0.9d, 34.9d, 102d, 87, cal1.getTime());
//	
//		cal1.set(2013, Calendar.MARCH, 24);
//		UsersManager.logActivity("033113183452090", "amarja", 10.5d, 0.9d, 34.9d, 102d, 75, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 13);
//		UsersManager.logActivity("033113183453912", "amarja", 10.5d, 0.9d, 34.9d, 102d, 80, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 10);
//		UsersManager.logActivity("033113183452389", "jay", 100.5d, 0.9d, 34.9d, 102d, 78, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 26);
//		UsersManager.logActivity("033113183452775", "sruthi", 20.5d, 0.9d, 34.9d, 102d, 82, cal1.getTime());
//		
//		UsersManager.viewPastActivity("amarja");
//		
//		UsersManager.viewPastActivityForLastWeek("amarja");
//		
//		UsersManager.viewWorkoutDetails("amarja");
//		
//		UsersManager.viewWorkoutDetailsForLastWeek("amarja");
//		
//		UsersManager.viewBestRide("amarja");
	}
}