package com.ncsu.edu.spinningwellness.managers;

public interface Constants {

	///////////////////////////
	//BASE
	///////////////////////////

	public String BASE_URL = "http://spinningwellness2013.appspot.com";

//	public String BASE_URL = "http://localhost:8888";

	///////////////////////////
	//RIDE RELATED
	///////////////////////////

	public String RIDE_URL = BASE_URL + "/resources/l2wride";

	public String CREATE_RIDE_URL = RIDE_URL + "/createride";

	public String UPDATE_RIDE_URL = RIDE_URL + "/updateride";

	public String DELETE_RIDE_URL = RIDE_URL + "/deleteride";

	public String VIEW_RIDE_URL = RIDE_URL + "/viewride";

	public String VIEW_PAST_RIDES_FROM_LAST_WEEK_URL = RIDE_URL + "/viewpastridesfromlastweek";

	public String VIEW_PAST_RIDES_URL = RIDE_URL + "/viewpastrides";

	public String VIEW_UPCOMING_RIDES_FROM_LAST_WEEK_URL = RIDE_URL + "/viewupcomingridesfromnextweek";

	public String VIEW_UPCOMING_RIDES_URL = RIDE_URL + "/viewupcomingrides";

	public String ADD_PARTICIPANT_TO_RIDE_URL = RIDE_URL + "/addparticipanttoride";

	public String DELETE_PARTICIPANT_FROM_RIDE_URL = RIDE_URL + "/removeparticipanttoride";

	public String VIEW_PARTICIPANTS_FOR_RIDE_URL = RIDE_URL + "/viewparticipantsforride";
	
	public String VIEW_MY_UPCOMING_RIDES_FOR_NEXT_WEEK_URL = RIDE_URL + "/myupcomingridesfornextweek";
	
	public String VIEW_MY_UPCOMING_RIDES_URL = RIDE_URL + "/myupcomingrides";
	
	public String VIEW_MY_PAST_RIDES_FOR_NEXT_WEEK_URL = RIDE_URL + "/mypastridesfromlastweek";
	
	public String VIEW_MY_PAST_RIDES_URL = RIDE_URL + "/mypastrides";

	///////////////////////////
	//USER RELATED
	///////////////////////////

	public String USER_URL = BASE_URL + "/resources/l2wuser";

	public String CREATE_USER_URL = USER_URL + "/createuser";

	public String DELETE_USER_URL = USER_URL + "/deleteuser";

	public String LOG_USER_ACTIVITY_URL = USER_URL + "/loguseractivity";

	public String VIEW_PAST_ACTIVITY_FOR_LAST_WEEK_URL = USER_URL + "/viewpastactivityforlastweek";
	
	public String VIEW_PAST_ACTIVITY_URL = USER_URL + "/viewpastactivity";
	
	public String VIEW_WORKOUT_DETAILS_FOR_LAST_WEEK_URL = USER_URL + "/workoutdetailsforlastweek";
	
	public String VIEW_WORKOUT_DETAILS_URL = USER_URL + "/workoutdetails";
	
	public String VIEW_MY_BEST_RIDE_URL = USER_URL + "/mybestride";
	
	public String VIEW_MY_LOGGED_PAST_RIDES_FOR_LAST_WEEK_URL = USER_URL + "/myloggedpastridesfromlastweek";
	
	public String VIEW_MY_LOGGED_PAST_RIDES_URL = USER_URL + "/myloggedpastrides";
	
	public String VIEW_TOP_PERFORMERS_FOR_LAST_WEEK_URL = USER_URL + "/topPerformersforlastweek";
	
	public String VIEW_TOP_PERFORMERS_URL = USER_URL + "/topPerformers";
	
	public String VIEW_ALL_USERS = USER_URL + "/getallusers";
	
	public String VIEW_USER_URL = USER_URL + "/viewUser";
	
	public static String adminPassword = "ncsuspr2013";
	public static String adminEmail = "spinningwellness2013@gmail.com";
	public static String adminUserName = "spinningwellness";
	
}