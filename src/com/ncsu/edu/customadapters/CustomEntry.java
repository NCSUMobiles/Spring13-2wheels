package com.ncsu.edu.customadapters;

public class CustomEntry {
	private String textVal;
	private boolean isJoined;

	public CustomEntry(String string, boolean string2) {
		this.textVal = string;
		this.isJoined = string2;
	}

	public String getTextVal() {
		return textVal;
	}

	public void setTextVal(String textVal) {
		this.textVal = textVal;
	}

	public boolean isJoined() {
		return isJoined;
	}

	public void setJoined(boolean isJoined) {
		this.isJoined = isJoined;
	}
	
}
