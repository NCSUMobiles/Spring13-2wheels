package com.ncsu.edu.spinningwellness.Utils; 

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ncsu.edu.spinningwellness.entities.Ride;
import com.ncsu.edu.spinningwellness.entities.User;
import com.ncsu.edu.spinningwellness.managers.Constants;

public class EmailDispatcher extends Authenticator{

	private static String mUserName;
	private static String mPassword;
	private static String mHostName;
	private boolean canSend = false;

	public void sendEmailToAll(List<User> u, Ride ride) {
		mUserName=Constants.adminEmail;
		mPassword=Constants.adminPassword;
		mHostName="smtp.gmail.com";

		try {
			Properties props = new Properties(); 
			props.put("mail.smtp.host", mHostName);   
			props.put("mail.smtp.auth", "true");           
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			// this object will handle the authentication
			Session session=Session.getInstance(props,this);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("spinningwellness", "SpinningWellness Admin"));
			for(User user:u){
				if(user.getEmail() != null){
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(user.getEmail(), user.getName()));
					canSend = true;
				} 
			}

			if(canSend){
				//Build message subject string
				StringBuilder msgSubject = new StringBuilder();
				msgSubject.append("New Ride Created: " + ride.getName());
				msg.setSubject(msgSubject.toString());

				//Build message
				StringBuilder msgBody = new StringBuilder();
				msgBody.append("Hello, \n");
				msgBody.append("\n");
				msgBody.append("A new ride has been created by " + ride.getCreator() + "\n");
				msgBody.append("Ride Details - \n");
				msgBody.append("\tRide Name: " + ride.getName() +" \n");
				msgBody.append("\tStarting Point: " + ride.getSource() +" \n");
				msgBody.append("\tEnd Point: " + ride.getDest() +" \n");

				Date rideDate = new Date(ride.getStartTime());
				String[] formats = new String[] {"dd-MMM-yy", "HH:mm"};

				SimpleDateFormat dfForRideDate = new SimpleDateFormat(formats[0], Locale.US);
				msgBody.append("\tDate: " + dfForRideDate.format(rideDate) +" \n");

				SimpleDateFormat dfForRideTime = new SimpleDateFormat(formats[1], Locale.US);
				msgBody.append("\tTime: " + dfForRideTime.format(rideDate) +" \n");

				msgBody.append("\n");
				msgBody.append("You can join the ride using Spinning Wellness app...\n");

				msgBody.append("\n");
				msgBody.append("-Admin\n");

				msg.setText(msgBody.toString());
				Transport transport = session.getTransport("smtp");

				transport.connect(mUserName, mPassword);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override 
	public PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(mUserName, mPassword); 
	} 
}