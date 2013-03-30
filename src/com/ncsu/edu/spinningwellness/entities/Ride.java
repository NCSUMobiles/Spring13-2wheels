package com.ncsu.edu.spinningwellness.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ride extends BaseEntity {

	String id;
	String name;
	String source;
	String dest;
	long startTime;
	String creator;
	
	public Ride() {}

	public Ride(String id, String name, String source, String dest,
			long startTime, String creator) {
		super();
		this.id = id;
		this.name = name;
		this.source = source;
		this.dest = dest;
		this.startTime = startTime;
		this.creator = creator;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Override
	public String toString() {
		return "Ride: id = " + this.id + ", " + 
				"name = " + this.name + ", " +
				"source = " + this.source + ", " +
				"destination = " + this.dest + ", " +
				"creator = " + this.creator + ", " +
				"startTime = " + this.startTime
				;
	}
}
