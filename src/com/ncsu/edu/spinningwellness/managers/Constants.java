package com.ncsu.edu.spinningwellness.managers;

public interface Constants {
	
	///////////////////////////
	//BASE
	///////////////////////////

	//public String BASE_URL = "http://spinningwellness2013.appspot.com";

	public String BASE_URL = "http://localhost:8888";
	
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
	
	///////////////////////////
	//USER RELATED
	///////////////////////////

	public String USER_URL = BASE_URL + "/resources/l2wuser";

	public String CREATE_USER_URL = USER_URL + "/createuser";
		
	public String DELETE_USER_URL = USER_URL + "/deleteuser";
	
	public String LOG_USER_ACTIVITY_URL = USER_URL + "/";
	
	

}