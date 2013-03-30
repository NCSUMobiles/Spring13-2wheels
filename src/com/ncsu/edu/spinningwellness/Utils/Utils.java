package com.ncsu.edu.spinningwellness.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Utils {
	
	public static long convertDateToLong(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		return Long.parseLong(df.format(date));
	}

	public static Date convertLongToDate(Long date) {
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		try {
			return df.parse(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object JSONToObject(String JSON, Class objectType) {
		Gson gson = new Gson();
		return gson.fromJson(JSON, objectType);
	}
	
	public static List<Object> JSONToObjectList(String JSON, Class objectType) {

		List<Object> list = new ArrayList<Object>();
		
		JsonElement json = new JsonParser().parse(JSON); 
		JsonArray array= json.getAsJsonArray();
	 
		Iterator iterator = array.iterator();
		while(iterator.hasNext()){
			JsonElement json2 = (JsonElement)iterator.next();
			Gson gson = new Gson();
			list.add(gson.fromJson(json2, objectType));
		}		
		return list;
	}
}