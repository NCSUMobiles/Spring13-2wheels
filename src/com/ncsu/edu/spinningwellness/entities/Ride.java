package com.ncsu.edu.spinningwellness.entities;


import javax.xml.bind.annotation.XmlRootElement;

import android.os.Parcel;
import android.os.Parcelable;

@XmlRootElement
public class Ride extends BaseEntity implements Parcelable,Comparable<Ride>{

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ride other = (Ride) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

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
	
	public Ride(Parcel in){
		this.id = in.readString();
		this.name =  in.readString();
		this.source =  in.readString();
		this.dest =  in.readString();
		this.startTime =  in.readLong();
		this.creator =  in.readString();
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(this.id);
	    out.writeString(this.name);
	    out.writeString(this.source);
	    out.writeString(this.dest);
	    out.writeLong(this.startTime);
	    out.writeString(this.creator);
	}
	
	public static final Parcelable.Creator<Ride> CREATOR = new Creator<Ride>() {

	    public Ride createFromParcel(Parcel source) {

	        return new Ride(source);
	    }

	    public Ride[] newArray(int size) {

	        return new Ride[size];
	    }

	};

	@Override
	public int compareTo(Ride another) {
		if(this.startTime < another.startTime){
			return -1;
		} else if(this.startTime > another.startTime) {
			return 1;	
		} else{
			return 0;
		}
	}
}
