package com.ncsu.edu.spinningwellness.managers;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import com.ncsu.edu.spinningwellness.managers.RidesManager;
import com.ncsu.edu.spinningwellness.managers.UsersManager;

public class Test {
	
	private static void createUsers() {
		UsersManager.createUser("amarja");
		UsersManager.createUser("minakshi");
		UsersManager.createUser("prajakta");
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
		RidesManager.createRide("Ride1", "source", "dest", startDate, "prajakta");
		
		cal.set(2013, 3, 6);
		startDate = cal.getTime();
		RidesManager.createRide("Ride2", "source", "dest", startDate, "minakshi");
		
		cal.set(2013, 3, 4);
		startDate = cal.getTime();
		RidesManager.createRide("Ride3", "source", "dest", startDate, "prajakta");
		
		startDate = new Date();
		cal.set(2013, 3, 5);
		startDate = cal.getTime();
		RidesManager.createRide("Ride4", "source", "dest", startDate, "amarja");

		startDate = new Date();
		cal.set(2013, 9, 5);
		startDate = cal.getTime();
		RidesManager.createRide("Ride4", "source", "dest", startDate, "amarja");
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
//		Test.createUsers();
//		Test.createRides();
		
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
		
		RidesManager.viewPastRides();
		
		RidesManager.viewPastRidesFromPastWeek();
		
		RidesManager.viewUpcomingRidesFromNextWeek();

		RidesManager.viewUpcomingRides();
		
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
//		Calendar cal1 = Calendar.getInstance();
//		
//		cal1.set(2013, Calendar.MARCH, 22);
//		UsersManager.logActivity("032913203559601", "amarja", 10.5d, 0.9d, 34.9d, 102d, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 23);
//		UsersManager.logActivity("032913203559566", "amarja", 10.5d, 0.9d, 34.9d, 102d, cal1.getTime());
//	
//		cal1.set(2013, Calendar.MARCH, 24);
//		UsersManager.logActivity("032913203559623", "amarja", 10.5d, 0.9d, 34.9d, 102d, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 13);
//		UsersManager.logActivity("032913203559660", "amarja", 10.5d, 0.9d, 34.9d, 102d, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 10);
//		UsersManager.logActivity("032913203559680", "amarja", 100.5d, 0.9d, 34.9d, 102d, cal1.getTime());
//		
//		cal1.set(2013, Calendar.MARCH, 26);
//		UsersManager.logActivity("032913203559700", "amarja", 20.5d, 0.9d, 34.9d, 102d, cal1.getTime());
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