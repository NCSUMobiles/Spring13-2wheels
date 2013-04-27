package com.ncsu.edu.spinningwellness.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User extends BaseEntity {
	
	String name;
	String emailAddress;
	
	public User() {}
	
	public User(String name, String email) {
		super();
		this.name = name;
		this.emailAddress = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return emailAddress;
	}

	public void setEmail(String email) {
		this.emailAddress = email;
	}

	@Override
	public String toString() {
		return "User: name = " + this.name;
	}
}