package com.ncsu.edu.spinningwellness.entities;

import com.google.gson.Gson;

public class BaseEntity {
	
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
