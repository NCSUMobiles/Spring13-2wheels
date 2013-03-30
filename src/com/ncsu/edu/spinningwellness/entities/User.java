package com.ncsu.edu.spinningwellness.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User extends BaseEntity {
	
	String name;
	
	public User() {}
	
	public User(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "User: name = " + this.name;
	}
}