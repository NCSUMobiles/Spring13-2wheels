package com.ncsu.edu.spinningwellness.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
/**
 * 
 * @author Owner
 *
 */
public class EventsCalendar {
	/**
	 * 
	 * @param curActivity
	 * @param title
	 * @param addInfo
	 * @param place
	 * @param status
	 * @param startDate
	 * @param needReminder
	 * @param needMailService
	 * @return
	 */
	public static long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate, boolean needReminder, boolean needMailService, String username, String email) {
	    String eventUriString = "content://com.android.calendar/events";
	    ContentValues eventValues = new ContentValues();

	    eventValues.put("calendar_id", 1); // id, We need to choose from
	                                        // our mobile for primary
	                                        // its 1
	    eventValues.put("title", title);
	    eventValues.put("description", addInfo);
	    eventValues.put("eventLocation", place);
	    startDate = startDate +  1000 * 60 * 60; // For next 1hr
	    long endDate = startDate + 1000 * 60 * 60; // For next 1hr
	    Calendar c  = Calendar.getInstance();
	    c.setTime(new Date(startDate));
	    
	    Calendar c1 = Calendar.getInstance();
	    c1.setTime(new Date(endDate));
	    System.out.println("start at: "+c.get(Calendar.DATE) + " " + c.get(Calendar.MONTH) + " " + c.get(Calendar.YEAR));
	    System.out.println("ends at: ");

	    eventValues.put("dtstart", startDate);
	    eventValues.put("dtend", endDate);

	    // values.put("allDay", 1); //If it is bithday alarm or such
	    // kind (which should remind me for whole day) 0 for false, 1
	    // for true
	    eventValues.put("eventStatus", status); // This information is
	    // sufficient for most
	    // entries tentative (0),
	    // confirmed (1) or canceled
	    // (2):
//	    eventValues.put("visibility", 3); // visibility to default (0),
//	                                        // confidential (1), private
	                                        // (2), or public (3):
//	    eventValues.put("transparency", 0); // You can control whether
	                                        // an event consumes time
	                                        // opaque (0) or transparent
	                                        // (1).
	    eventValues.put("hasAlarm", 1); // 0 for false, 1 for true
	    eventValues.put("eventTimezone", TimeZone.getDefault().getID());

	   // Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
	    Uri url = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
	    System.out.println(url.getLastPathSegment());
	    long eventID = Long.parseLong(url.getLastPathSegment());
	    System.out.println("event: " + eventID);
	    
	    if (needReminder) {
	        String reminderUriString = "content://com.android.calendar/reminders";

	        ContentValues reminderValues = new ContentValues();

	        reminderValues.put("event_id", eventID);
	        reminderValues.put("minutes", 5); // Default value of the
	                                            // system. Minutes is a
	                                            // integer
	        reminderValues.put("method", 1); // Alert Methods: Default(0),
	                                            // Alert(1), Email(2),
	                                            // SMS(3)

	        Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
	    }

	    /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

	    if (needMailService) {
	        String attendeuesesUriString = "content://com.android.calendar/attendees";

	        /********
	         * To add multiple attendees need to insert ContentValues multiple
	         * times
	         ***********/
	        ContentValues attendeesValues = new ContentValues();

	        attendeesValues.put("event_id", eventID);
	        attendeesValues.put("attendeeName", username); // Attendees name
	        attendeesValues.put("attendeeEmail", email);// Attendee email
	        attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
	                                                        // Relationship_None(0),
	                                                        // Organizer(2),
	                                                        // Performer(3),
	                                                        // Speaker(4)
	        attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
	                                                // Required(2), Resource(3)
	        attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
	                                                    // Decline(2),
	                                                    // Invited(3),
	                                                    // Tentative(4)

	        Uri attendeesUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
	    }

	    return eventID;

	}
}